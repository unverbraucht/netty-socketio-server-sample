// ES modules
import { io } from "socket.io-client";


import { Manager } from "socket.io-client";

const manager = new Manager("http://localhost:8081/", {
  autoConnect: true,
});

const socket = manager.socket("/", {
  auth: {
    token: "abcd"
  }
});

socket.on("connect", () => {
  console.log("Socket connected", socket.connected); // true
});

socket.on("connect_error", (error) => {
  console.warn('Connect error', error);
});

console.log('opening socket');
socket.open();
