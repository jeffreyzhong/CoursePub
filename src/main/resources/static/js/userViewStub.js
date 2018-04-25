$(document).ready(function() {
  let socket = new WebSocket('ws://localhost:4567/posts');

  socket.onmessage = function(message) {
    console.log(message);
  };

  socket.onopen = function() {
    socket.send(JSON.stringify({
      'payload': {
        'name': 'this'
      },
    }));
  }
});
