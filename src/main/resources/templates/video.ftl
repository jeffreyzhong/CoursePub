<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script>var __adobewebfontsappname__="dreamweaver"</script>
<script src="http://use.edgefonts.net/source-sans-pro:n2:default.js" type="text/javascript"></script>
<title>CoursePub Student</title>
<link href="/static/css/student_view.css" rel="stylesheet" type="text/css">
</head>
<div id = "container">

<body id = "mapBody">  
  <header> <a href="">
    <h4 class="logo">CoursePub</h4>
  </a>
    <nav>
      <ul>
        <li><a href="/home">Home</a></li>
        <li><a href="/courses">Courses</a></li>
      </ul>
    </nav>
  </header>   
<div class = "videoName"><label id = "title">Essense of Linear Algebra</label>
</div>	
<div id = "searchArea">
	<div id = "item">
	<label id = "searchBarTag" class = "searchTags">Keep Exploring:</label><input class = "inputBox" id = "searchBar" placeholder="Learning begins here">
	</div>
	<label id = "timeTag1" class = "searchTags">Between :</label><input class = "inputBox" id = "searchTimeInput1" placeholder="00:00:00">
	<label id = "timeTag2" class = "searchTags">and</label><input class = "inputBox" id = "searchTimeInput2" placeholder="00:00:00">
	<button type="submit" value = "Go!" id = "searchBtn">Go</button>
</div>
<div id = "left" >		
	<div class = "videoWrapper" id = "videoWrapper">
		<iframe src="https://www.youtube.com/embed/kjBOesZCoqc?rel=0&enablejsapi=1" frameborder="0" allowfullscreen id = "video"></iframe>	
	</div>
		
	<div id = "summaryDiv">
		<label for="summary" class="inputBoxLabel" id="summaryLabel">Summary:</label>
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
	<button type="submit" name = "submit" value = "Post!" id = "submitBtn">Post!</button>
	<button type="submit" name = "submit" value = "Post!" id = "relQuestion">Related Question</button>
	<span id = "videoId" style = "display:none">${videoId}</span>
</div>

<div id = "right" class = "col">
  	 
	<div class="tab">
	  <button class="tablinks" id = "noteBtn">Notes</button>
	  <button class="tablinks" id = "questionBtn">Questions</button>
	  <button class="tablinks" id = "relBtn">Suggested</button>
	  <button class="tablinks" id = "allQuestionsBtn">All Questions</button>
	</div>

	<!-- Tab content -->
	<div id="sideContentDiv" class="tabcontent" >
		<div id="questionDiv0" class = "questionDiv">
			<label id = "time0" class = "questionTimeLabel"></label>
			<button class="upvotes" id = "upvote0">Upvote!</button>
			<p id = "question0" class = "questions"></p>
			<label id = "user0" class = "userLabel"></label>
			<label id = "upvoteLabel0" class = "upvoteLabel"></label>
			<label id = "questionId0" class = "questionIdLabel"></label>
		</div>
		<div id = "responseList"></div>
		<div id="questionDiv1" class = "questionDiv">
			<label id = "time1" class = "questionTimeLabel"></label>
			<button class="upvotes" id = "upvote1">Upvote!</button>
			<p id = "question1" class = "questions"></p>
			<label id = "user1" class = "userLabel"></label>
			<label id = "upvoteLabel1" class = "upvoteLabel"></label>
			<label id = "questionId1" class = "questionIdLabel"></label>
		</div>
		<div id="questionDiv2" class = "questionDiv">
			<label id = "time2" class = "questionTimeLabel"></label>
			<button class="upvotes" id = "upvote2">Upvote!</button>
			<p id = "question2" class = "questions"></p>
			<label id = "user2" class = "userLabel"></label>
			<label id = "upvoteLabel2" class = "upvoteLabel"></label>
			<label id = "questionId2" class = "questionIdLabel"></label>
		</div>
		<div id="questionDiv3" class = "questionDiv">
			<label id = "time3" class = "questionTimeLabel"></label>
			<button class="upvotes" id = "upvote3">Upvote!</button>
			<p id = "question3" class = "questions"></p>
			<label id = "user3" class = "userLabel"></label>
			<label id = "upvoteLabel3" class = "upvoteLabel"></label>
			<label id = "questionId3" class = "questionIdLabel"></label>
		</div>
		<div id="questionDiv4" class = "questionDiv">
			<label id = "time4" class = "questionTimeLabel"></label>
			<button class="upvotes" id = "upvote4">Upvote!</button>
			<p id = "question4" class = "questions"></p>
			<label id = "user4" class = "userLabel"></label>
			<label id = "upvoteLabel4" class = "upvoteLabel"></label>
			<label id = "questionId4" class = "questionIdLabel"></label>
		</div>	
	</div>
	
	<div id = "answerDiv">
		<label id = "answerLabel">Answer:</label>
		<input type = "radio"
                 value = "byXYZ"
			   	 id = "followUp"
                 checked = "checked" />
		<label for = "sizeSmall">Answer</label>
		<input type = "radio"
		     value = "byName"
		     id = "studentAnswer"/>
		<label for = "sizeSmall">Follow-up Question</label><br>
		<textarea class = "inputBox" id = "answerInput" placeholder="Answer a question!"></textarea>
	</div>

</div>
</body>

<script type="text/javascript" src="../static/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="../static/js/student_view.js"></script>

<#include "requestHeader.ftl">

</html>
