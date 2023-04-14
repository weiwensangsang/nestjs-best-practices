# Build Some Things: Websocket and Cache



### WebSocket



```javascript
const WebSocket = require('ws');

const server = new WebSocket.Server({ port: 8080 });

server.on('connection', (socket) => {
  console.log('Client connected');

  socket.on('message', (data) => {
    console.log(`Received message: ${data}`);

    // Send the message back to the client
    socket.send(`You said: ${data}`);
  });

  socket.on('close', () => {
    console.log('Client disconnected');
  });
});
```

In this example, we create a WebSocket server using the `ws` module and listen for connections on port 8080. When a client connects, we log a message to the console and listen for any messages the client sends us. When we receive a message, we log it to the console and send a response back to the client.

You can run this code using Node.js by saving it to a file (e.g. `server.js`) and running `node server.js` in your terminal. Then you can connect to the WebSocket server using a WebSocket client (e.g. a browser WebSocket or a command-line tool like `wscat`).



### Cache



```javascript
class Cache {
  constructor() {
    this.cache = {};
    this.timeout = 3000; // Cache expires after 3 seconds
  }

  get(key) {
    const entry = this.cache[key];
    if (entry && entry.expires > Date.now()) {
      // Entry is still valid
      return entry.value;
    } else {
      // Entry has expired or doesn't exist
      delete this.cache[key];
      return null;
    }
  }

  set(key, value) {
    this.cache[key] = {
      value,
      expires: Date.now() + this.timeout
    };
  }

  clear() {
    this.cache = {};
  }
}
```

In this example, we define a `Cache` class with three methods:

- `get`: retrieves a value from the cache by key. If the value has expired or doesn't exist, it returns `null`.
- `set`: adds or updates a value in the cache by key, with an expiration time of 3 seconds.
- `clear`: removes all values from the cache.

You can use this `Cache` class in your Node.js code like this:

```javascript
const myCache = new Cache();

myCache.set('myKey', 'myValue');

console.log(myCache.get('myKey')); // Output: "myValue"

setTimeout(() => {
  console.log(myCache.get('myKey')); // Output: null (expired)
}, 4000);
```

In this example, we create a new `Cache` instance, set a value with key `'myKey'` and value `'myValue'`, and retrieve it using `get`. We then wait for 4 seconds (longer than the cache timeout) and try to retrieve the value again, which returns `null` because it has expired.

