$(document).ready(function() {
  let videoId = $('#videoId').html();
  let socket = new WebSocket('ws://localhost:4567/websocket/' + videoId);

  socket.onmessage = function(message) {
    console.log(message['data']);
  };

  socket.onopen = function() {
    socket.send(JSON.stringify({
      'type': 1,
      'payload': {
        'videoId': 1,
        'summary': 'ask this',
        'detail': 'hey hey hey',
        'questionTimestamp': '00:03:19',
      },
    }));
  };
});
