package edu.brown.cs.termproject.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Handler class for socket.
 *
 * @author yqin
 */
@Component
class SocketHandler extends TextWebSocketHandler {

  private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
  private static final int DEFAULT_SIZE = 20;
  private static final Gson GSON = new Gson();

  private Map<Integer, BlockingQueue<WebSocketSession>> sessions;

  SocketHandler() {
    this.sessions = new ConcurrentHashMap<>();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session)
      throws IOException {
    Integer id = (Integer) session.getAttributes().get("id");
    if (!sessions.containsKey(id)) {
      sessions.put(id, new ArrayBlockingQueue<>(DEFAULT_SIZE));
    }

    sessions.get(id).add(session);
    logger.info(String.format("session %s enter video %d\n", session.getId(), id));

    session.sendMessage(new TextMessage(GSON.toJson(
        ImmutableMap.of("message",
            String.format("hello you are watching video %d, session %s", id,
                session.getId())))));
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws IOException {
    Map values = GSON.fromJson(message.getPayload(), Map.class);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session,
                                    CloseStatus status) {
    Integer id = (Integer) session.getAttributes().get("id");

    assert (sessions.containsKey(id));
    sessions.get(id).remove(session);

    logger.info(String.format("session %s exit from video %d\n", session.getId(), id));

  }
}

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new SocketHandler(), "/websocket/{id}")
        .addInterceptors(auctionInterceptor());
  }

  @Bean
  public HandshakeInterceptor auctionInterceptor() {
    return new HandshakeInterceptor() {
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

