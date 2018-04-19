package edu.brown.cs.termproject.main;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Handler class for socket.
 *
 * @author yqin
 */
@Component
class SocketHandler extends TextWebSocketHandler {

  private static final int DEFAULT_SIZE = 20;

  private BlockingQueue<WebSocketSession> sessions;

  @Lazy
  SocketHandler() {
    this.sessions = new ArrayBlockingQueue<>(DEFAULT_SIZE);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws IOException {

    session.sendMessage(new TextMessage("ack"));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session,
                                    CloseStatus status) {
    sessions.remove(session);
  }
}

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new SocketHandler(), "/posts");
  }
}
