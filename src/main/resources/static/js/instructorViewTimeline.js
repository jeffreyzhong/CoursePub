$(document).ready(function() {
	var container = document.getElementById('visualization');
	var instructorResponse = document.getElementById("instructorResponse");
	var videoId = document.getElementById("videoId").html;
	var postParameters = {videoId:videoId};
	$.post("/instructor/question", postParameters, responseJSON => {
		var responseObject = JSON.parse(responseJSON);
		
	})
	instructorResponse.addEventListener('keydown', autosize);

	function autosize(){
	  var el = this;
	  setTimeout(function(){
		el.style.cssText = 'height:auto; padding:0';
		// for box-sizing other than "content-box" use:
		// el.style.cssText = '-moz-box-sizing:content-box';
		el.style.cssText = 'height:' + el.scrollHeight + 'px';
	  },0);
	}

	  // Create a DataSet (allows two way data-binding)
	  var items = new vis.DataSet([
		{id: 1, content: 'Question 1', start: new Date(0,0,0,0,1,2,0)},
		{id: 2, content: 'Question 2', start: new Date(0,0,0,0,2,3,0)},
		{id: 3, content: 'Question 3', start: new Date(0,0,0,0,3,4,0)},
		{id: 4, content: 'Question 4', start: new Date(0,0,0,0,5,2,0)},
		{id: 5, content: 'Question 5', start: new Date(0,0,0,0,5,10,0)},
		{id: 6, content: 'Question 6', start: new Date(0,0,0,0,5,2,0)},
		{id: 7, content: 'Question 7', start: new Date(0,0,0,0,4,40,0)},
		{id: 8, content: 'Question 8', start: new Date(0,0,0,0,5,40,0)},
		{id: 9, content: 'Question 9', start: new Date(0,0,0,0,9,15,0)},
		{id: 10, content: 'Question 10', start: new Date(0,0,0,0,8,30,0)},
		{id: 11, content: 'Question 11', start: new Date(0,0,0,0,7,50,0)},
		{id: 12, content: 'Question 12', start: new Date(0,0,0,0,7,40,0)},
		{id: 13, content: 'Question 13', start: new Date(0,0,0,0,2,30,0)},
		{id: 14, content: 'Question 14', start: new Date(0,0,0,0,4,5,0)},
		{id: 15, content: 'Question 15', start: new Date(0,0,0,0,6,20,0)},
		{id: 16, content: 'Question 16', start: new Date(0,0,0,0,6,35,0)},
		{id: 17, content: 'Question 17', start: new Date(0,0,0,0,3,20,0)},
		{id: 18, content: 'Question 18', start: new Date(0,0,0,0,9,50,0)},
		{id: 19, content: 'Question 19', start: new Date(0,0,0,0,9,10,0)},
		{id: 20, content: 'Question 20', start: new Date(0,0,0,0,8,10,0)}]);


	  // Configuration for the Timeline
	var options = {
		width: '100%',
		height: '250px',
		timeAxis: {scale: 'second', step: 30}
	};

	options.zoomMax = '420000';
	options.zoomMin = '1000';
	options.min = new Date(0,0,0,0,0,0,0);
	options.max = new Date(0,0,0,0,10,0,0);

	options.format = 
		{
	  minorLabels: {
		millisecond:'',
		second:     'mm:ss',
		minute:     'mm:ss',
		hour:       'HH:mm:ss',
		weekday:    '',
		day:        '',
		week:       '',
		month:      '',
		year:       ''
	  },
	  majorLabels: {
		millisecond:'',
		second:     'mm:ss',
		minute:     'mm:ss',
		hour:       'HH:mm:ss',
		weekday:    '',
		day:        '',
		week:       '',
		month:      '',
		year:       ''
	  }
	};

	  // Create a Timeline
	var timeline = new vis.Timeline(container, items, options);
	timeline.fit(options);

	timeline.on('select', function (properties) {
		var questionId = properties.items;
	  //alert('selected items: ' + properties.items);
	  var question = document.getElementById("displayQuestion");
	  console.log("yo");
	  question.innerHTML = "This is my question: " + questionId;

	});
});