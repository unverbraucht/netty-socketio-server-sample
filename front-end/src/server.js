import express from 'express';
import { createProxyMiddleware } from 'http-proxy-middleware';
import { join } from "path";
import url from 'url';


const __dirname = url.fileURLToPath(new URL('.', import.meta.url));

const app = express();

// We proxy the socket.io requests through to the Java server so we don't have to deal
// with access control headers because we have the same origin for the document
// and the socket.io endpoint
app.use('/socket.io', createProxyMiddleware({
  target: "http://localhost:8081",
  ws: true,
}));
app.use(express.static(join(__dirname, 'public')))
app.listen(3000);

console.info('Open http://localhost:3000 in a browser to run the node.JS client in a browser environment')