$(document).ready(function() {
  let videoId = $('#videoId').html();
  let socket = new WebSocket('ws://localhost:4567/websocket/' + videoId);

  socket.onmessage = function(message) {
    console.log(message['data']);
  };

  socket.onopen = function() {
    socket.send(JSON.stringify({
      'type': 2,
      'payload': {
        'questionId': 1,
        'detail': 'blah blah',
      },
    }));
  };
});
