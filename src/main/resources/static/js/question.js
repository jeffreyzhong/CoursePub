$(document).ready(function() {
  const id = parseInt($('#videoId').html());

  $.post('/question', {
    'id': id,
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);

    for (let i = 0; i < responseObject.length; ++i) {
      let question = responseObject[i];
      let id = question['id'];
      let time = question['time'];
      let summary = question['summary'];
      let user = question['user'];
      let resolved = question['resolved'];

      console.log("question (id " + id + "): user " + user + " asked " + summary + " at second " + time + ", resolve status is " + resolved);
    }
  })
});
