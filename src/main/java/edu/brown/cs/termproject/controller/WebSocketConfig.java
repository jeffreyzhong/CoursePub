package edu.brown.cs.termproject.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.SocketService;
import edu.brown.cs.termproject.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {

  private enum MESSAGE_TYPE {
    CONNECT, NEW_QUESTION, NEW_ANSWER, UPVOTE
  }

  private static final Logger logger =
      LoggerFactory.getLogger(WebSocketConfig.class);
  private static final int DEFAULT_SIZE = 20;
  private static final Gson GSON = new Gson();

  private UserService userService;
  private SocketService socketService;

  @Autowired
  public WebSocketConfig(UserService userService, SocketService socketService) {
    this.userService = userService;
    this.socketService = socketService;
  }

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler(), "/websocket/{id}")
        .addInterceptors(idInterceptor());
  }

  @Bean
  public TextWebSocketHandler webSocketHandler() {
    return new TextWebSocketHandler() {

      private Map<Integer, BlockingQueue<WebSocketSession>> sessions =
          new ConcurrentHashMap<>();

      @Override
      public void afterConnectionEstablished(WebSocketSession session) {
        Integer id = (Integer) session.getAttributes().get("id");
        if (!sessions.containsKey(id)) {
          sessions.put(id, new ArrayBlockingQueue<>(DEFAULT_SIZE));
        }

        sessions.get(id).add(session);
        logger.info("Session {} enter video {}", session.getId(), id);
      }

      @Override
      public void handleTextMessage(WebSocketSession session, TextMessage message)
          throws IOException {
        Integer id = (Integer) session.getAttributes().get("id");
        Map values = GSON.fromJson(message.getPayload(), Map.class);

        Map<String, Object> payload = (Map<String, Object>) values.get("payload");
        MESSAGE_TYPE type =
            MESSAGE_TYPE.values()[((Double) values.get("type")).intValue()];

        User user;
        try {
          user = userService.get(session.getPrincipal());
        } catch (IllegalArgumentException e) {
          throw new UserNotFoundException(e);
        }
        assert (user != null);

        MESSAGE_TYPE responseType = type;
        Object responsePayload;
        switch (type) {
          case CONNECT:
            logger.info("Connect from {}", session.getId());
            responsePayload = ImmutableMap.of(
                "message", String.format(
                    "hello session %s, you are watching video %d, ",
                    session.getId(), id)
            );
            break;

          case NEW_QUESTION:
            logger.info("New question from {}", session.getId());
            QuestionDto questionDto = new QuestionDto(payload);
            responsePayload = socketService.newQuestion(user, questionDto);
            break;

          case NEW_ANSWER:
            logger.info("Student answer from {}", session.getId());
            ResponseDto responseDto = new ResponseDto(payload);
            responsePayload = socketService.newAnswer(user, responseDto);
            break;

          case UPVOTE:
            logger.info("Upvote from {}", session.getId());
            UpvoteDto upvoteDto = new UpvoteDto(payload);
            responsePayload = socketService.upvote(user, upvoteDto);
            break;

          default:
            responsePayload = Collections.emptyMap();
        }

        TextMessage response = new TextMessage(GSON.toJson(
            ImmutableMap.of(
                "type", responseType.ordinal(),
                "payload", responsePayload
            )
        ));
        for (WebSocketSession other : sessions.get(id)) {
          other.sendMessage(response);
        }
      }

      @Override
      public void afterConnectionClosed(WebSocketSession session,
                                        CloseStatus status) {
        Integer id = (Integer) session.getAttributes().get("id");

        assert (sessions.containsKey(id));
        sessions.get(id).remove(session);

        logger.info("Session {} exit video {}", session.getId(), id);
      }
    };
  }

  @Bean
  public HandshakeInterceptor idInterceptor() {
    return new HandshakeInterceptor() {

      @Override
      public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                     ServerHttpResponse serverHttpResponse,
                                     WebSocketHandler webSocketHandler,
                                     Map<String, Object> attributes) {

        /* extracts video id from path */
        String path = serverHttpRequest.getURI().getPath();
        Integer id = Integer.valueOf(path.substring(path.lastIndexOf('/') + 1));
        /* stores video id in attributes */
        attributes.put("id", id);
        return true;
      }

      @Override
      public void afterHandshake(ServerHttpRequest serverHttpRequest,
                                 ServerHttpResponse serverHttpResponse,
                                 WebSocketHandler webSocketHandler,
                                 Exception exception) {
      }
    };
  }
}

