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
      let upvotes = question['upvotes'];
      let detail = question['detail'];
      let instructorAnswer = question['instructorAnswer'];

      console.log('question (id ' + id + '): user ' + user + ' asked ' + summary
          + ': \"' + detail + '\" at second ' + time + ', resolve status is '
          + resolved + '. ' + upvotes + ' people have upvoted.');
      if (instructorAnswer !== undefined) {
        console.log('instructor ' + instructorAnswer['userId'] +
            ' responded to question ' + instructorAnswer['questionId'] + ': '
            + instructorAnswer['detail'] + '.')
      }
    }
  });

  $.post('/response', {
    'id': 2,
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);

    for (let i = 0; i < responseObject.length; ++i) {
      let response = responseObject[i];
      let id = response['id'];
      let questionId = response['questionId'];
      let detail = response['detail'];
      let userId = response['userId'];
      let postDate = response['postDate'];
      let postTime = response['postTime'];
      let upvotes = response['upvotes'];

      console.log('response (id ' + id + '): user ' + userId + ' replied : \"'
          + detail + '\" at ' + postTime + ' on ' + postDate + ' to question '
          + questionId + '. ' + upvotes + ' people have upvoted.');
    }
  })
});
