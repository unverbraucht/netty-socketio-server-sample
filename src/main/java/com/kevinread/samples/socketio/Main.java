package com.kevinread.samples.socketio;

public class Main {


  public static void main(String[] args) {
    final var sampleServer = new SampleServer();

    sampleServer.runBlocking();
  }
}