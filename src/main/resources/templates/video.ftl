<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script>var __adobewebfontsappname__="dreamweaver"</script>
<script src="http://use.edgefonts.net/source-sans-pro:n2:default.js" type="text/javascript"></script>
<title>student_view</title>
<link href="../../static/css/student_view.css" rel="stylesheet" type="text/css">
</head>
<div id = "container">

<body id = "mapBody">
<div id = "header">
	<h4 class = "logo"> coursepub</h4>
</div>
<div class = "videoName">Essense of Linear Algebra</div>	
<div id = "left" >		
	<div class = "videoWrapper" id = "videoWrapper">
		<iframe src="https://www.youtube.com/embed/kjBOesZCoqc?rel=0&enablejsapi=1" frameborder="0" allowfullscreen id = "video"></iframe>	
	</div>
		
	<div id = "summaryDiv">
		<label for="summary" class="inputBoxLabel">Summary:</label>
		<input class = "inputBox" id = "summaryInput" placeholder="Summary">
	</div>

	<div id = "timeDiv">
		<label for="time" class="inputBoxLabel" id = "timeLabel">Time:</label>
	 	<input class = "inputBox" id = "timeInput" placeholder="00:00:00">
	</div>  
	
	<div id = "detailDiv">
		<label for="time" class="inputBoxLabel" id = "detailLabel">Detail:</label>
		<textarea class = "inputBox" id = "detailInput" placeholder="Input question here"></textarea>
	</div>
	<button type="submit" name = "submit" value = "Post!" id = "submitBtn">Post!</button><br><br>
</div>

<div id = "right" class = "col">
  	 
	<div class="tab">
	  <button class="tablinks" id = "noteBtn">Notes</button>
	  <button class="tablinks" id = "questionBtn">Questions</button>
	  <button class="tablinks" id = "relBtn">Related</button>
	</div>

	<!-- Tab content -->
	<div id="sideContentDiv" class="tabcontent" >
		<div id="question0Div" class = "questionDiv">
			<p id = "question0" class = "questions">asdfasdf</p>
		</div>
		<div id="question1Div" class = "questionDiv">
			<p id = "question1" class = "questions">asdfasdf</p>
		</div>
		<div id="question2Div" class = "questionDiv">
			<p id = "question2" class = "questions">asdfasdf</p>
		</div>
		<div id="question3Div" class = "questionDiv">
			<p id = "question3" class = "questions">asdfasdf</p>
		</div>
		<div id="question4Div" class = "questionDiv">
			<p id = "question4" class = "questions">asdfasdf</p>
		</div>	

	</div>
	
	
      

</div>
      

</body>

</div>
<script src="../../static/js/jquery-2.1.1.js"></script>
<script src="../../static/js/student_view.js"></script>
</html>
