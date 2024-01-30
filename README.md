# Simple Java Netty Socket.IO server sample

This project contains a simple Socket.IO server written in Java
using the [netty-socketio server](https://github.com/mrniko/netty-socketio/) as server
and the official [JS Socket.IO client](https://www.npmjs.com/package/socket.io-client).

## How to run

Build the server through Maven and run the *com.kevinread.samples.socketio.Main* Main class.
This will open a simple Socket.IO server under http://localhost:8081/

There are two clients: one for Node.JS and one running in the browser.
Go to the *front-end* folder and run either *npm run browsertest* (and then open
http://localhost:3000/ in the browser) or *npm run nodetest*.