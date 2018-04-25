$(document).ready(function() {
  const id = parseInt($('#videoId').html());

  $.post('/instructor/question', {
    'id': id,
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);
    alert("hello" + responseObject['id']);
  })
});