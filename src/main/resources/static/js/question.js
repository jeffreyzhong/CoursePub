function printQuestion(question) {
  let id = question['id'];
  let time = question['time'];
  let summary = question['summary'];
  let user = question['user'];
  let resolved = question['resolved'];
  let upvotes = question['upvotes'];
  let detail = question['detail'];
  let instructorAnswer = question['instructorAnswer'];
  let studentAnswer = question['studentAnswer'];

  console.log('question (id ' + id + '): user ' + user + ' asked ' + summary
      + ': \"' + detail + '\" at second ' + time + ', resolve status is '
      + resolved + '. ' + upvotes + ' people have upvoted.');

  if (instructorAnswer !== undefined) {
    console.log('instructor ' + instructorAnswer['userId'] +
        ' responded to question ' + instructorAnswer['questionId'] + ': '
        + instructorAnswer['detail'] + '.')
  }

  if (studentAnswer !== undefined) {
    console.log('student ' + studentAnswer['userId'] +
        ' responded to question ' + studentAnswer['questionId'] + ': '
        + studentAnswer['detail'] + '.')
  }
}

$(document).ready(function() {
  const id = parseInt($('#videoId').html());
  /*
  $.post('/question', {
    'id': id,
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);

    for (let i = 0; i < responseObject.length; ++i) {
      let question = responseObject[i];
      printQuestion(question)
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
  });

  $.post('/relatedStudent', {
    'id': 1,
    'input': 'who is jj'
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);

    for (let i = 0; i < responseObject.length; ++i) {
      let question = responseObject[i];
      printQuestion(question)
    }
  });

  */

  $.post('/relatedInstructor', {
    'id': 1,
  }, function(responseJSON) {
    let responseObject = JSON.parse(responseJSON);
    console.log(responseObject);
  })
});
