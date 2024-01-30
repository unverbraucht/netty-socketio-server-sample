package com.kevinread.samples.socketio;

import com.corundumstudio.socketio.AuthTokenResult;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.corundumstudio.socketio.namespace.Namespace;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleServer {

  private final Logger logger = LoggerFactory.getLogger(Main.class);

  private final ExceptionListener exceptionListener = new ExceptionListener() {
    @Override
    public void onEventException(Exception e, List<Object> list, SocketIOClient socketIOClient) {
      logger.error("eventException", e);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient socketIOClient) {
      logger.error("disconnectException", e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient socketIOClient) {
      logger.error("connectException", e);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient socketIOClient) {
      logger.error("pingException", e);
    }

    @Override
    public void onPongException(Exception e, SocketIOClient socketIOClient) {
      logger.error("pongException", e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext channelHandlerContext,
        Throwable throwable) {
      return false;
    }

    @Override
    public void onAuthException(Throwable throwable, SocketIOClient socketIOClient) {
      logger.error("authException", throwable);
    }
  };
  private final SocketIOServer server;

  public SampleServer() {
    var config = new Configuration();
    config.setExceptionListener(exceptionListener);
    config.setPort(8081);
    var socketConfig = new SocketConfig();
    socketConfig.setReuseAddress(true);
    socketConfig.setSoLinger(socketConfig.getTcpReceiveBufferSize());
    config.setSocketConfig(socketConfig);
    server = new SocketIOServer(config);
    var name = server.getNamespace(Namespace.DEFAULT_NAME);
    name.addAuthTokenListener((data, client) -> {
      logger.info("Auth data: {}", data);
      return AuthTokenResult.AuthTokenResultSuccess;
    });
    server.addConnectListener(socketIOClient ->
        logger.info("Client connected"));
  }

  public void runBlocking() {
    server.start();
    logger.info("Server started");
    final Thread currentThread = Thread.currentThread();
    synchronized (currentThread) {
      try {
        currentThread.wait();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    server.stop();
    logger.info("Server stopped");

  }
}
