<!doctype html>
<html>

<script src="/static/js/userViewStub.js"></script>

<p>Video ${id}</p>


<head>
<meta charset="utf-8">
<title>student_view</title>
<link href="../static/css/main.css" rel="stylesheet" type="text/css">
</head>
<div id = "container">
<body id = "mapBody">
<div id = "left">
  
  	<div class = "videoWrapper">
		<iframe src="https://www.youtube.com/embed/kjBOesZCoqc?rel=0" frameborder="0" allowfullscreen></iframe>	
  	</div>
	
  	<div id = "summaryDiv">
		<label for="summary" class="inputBoxLabel">Summary:</label>
		<input class = "inputBox" id = "summaryInput" placeholder="Summary">
	  	<label for="time" class="inputBoxLabel" id = "timeLabel">Time:</label>
	 	 <input class = "inputBox" id = "timeInput" placeholder="00:00">
	</div>

	
	<div id = "detailDiv">
		<label for="time" class="inputBoxLabel" id = "detailLabel">Detail:</label>
		<input class = "inputBox" id = "detailInput">
	</div>
</div>

<div id = "right">
  	 
	<div class="tab">
	  <button class="tablinks" id = "noteBtn">Notes</button>
	  <button class="tablinks" id = "questionBtn">Questions</button>
	  <button class="tablinks" id = "relBtn">Related</button>
	</div>

	<!-- Tab content -->
	<div id="sideContentDiv" class="tabcontent">
	  <ul id = "sideContent"></ul>
	</div>
      

</div>
      

</body>

</div>

</html>