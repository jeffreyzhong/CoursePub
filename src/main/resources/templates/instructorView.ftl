<!doctype html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CoursePub Instructor</title>
<link href="/static/css/instructorView.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->
<script>var __adobewebfontsappname__="dreamweaver"</script>
<script src="//use.edgefonts.net/old-standard:n4,i4,n7.js"></script>
<#include "requestHeader.ftl">
<script src="/static/node_modules/vis/dist/vis.js"></script>
<script src="https://apis.google.com/js/client.js?onload=onClientLoad" type="text/javascript"></script>
<script src="/static/js/instructorViewTimeline.js"></script>
<link href="/static/node_modules/vis/dist/vis.css" rel="stylesheet" type="text/css"/>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style>
		.vis-item {
            background-color: transparent;
            border-color: transparent;
		}

		.vis-item.vis-selected {
			border-radius: 20px;
			background-color: transparent;
            box-shadow: inset 0 0 0 1px #fff,
            0 0 0 1px #fff,
            3px -3px 30px #1beabd, 
            -3px 3px 30px #10abff;
		}
        .vis-even {
            background-color: transparent;
        }
        .vis-odd {
            background-color: transparent;
        }
	</style>

  <script src="/static/node_modules/moment/moment.js"></script>
  <script>
    moment().format();
    moment().format("ss, mm, kk");
  </script>
</head>
<body>
<!-- Main Container -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="container" id="container"> 
  <!-- Navigation -->
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
	<br>
    <div id="thumbnailDiv">
        <img id="videoThumbnail">
        <div id="videoName"></div>
        <br>
    </div>
	
<!--
	<section id="interactiveSection"> 
	<canvas id="interactiveCanvas"></canvas>
	</section>
-->
	<span id="videoId" style="display:none">${videoId}</span>
	<div id="visualization">
	</div>
	<br>
    <div id="wrapper">
	<div id="clickedOnQuestion">
		<div id="fullQuestion">
            Question Summary:<br>
            <div class="displaySection" id="displaySummary"></div><br>
            Question Details:<br>
			<div class="displaySection" id="displayQuestion"></div><br>
            Student Answer:<br>
            <div class="displaySection" id="displayStudentAnswer"></div><br>
            Instructor Answer:<br>
            <div class="displaySection" id="displayInstructorAnswer"></div><br>
            Answer:<br>
			<textarea class="instructorResponse" id="instructorResponse"></textarea><br>
			<input type="submit" id="submitResponseButton" value="Post"><br> 
		</div>
	</div>
	<div id="videoSection">
		<iframe id="videoFrame" width="560" height="315" frameborder="0"
        src="" allow="autoplay; encrypted-media" allowfullscreen></iframe><br>
        Follow-Up Thread:<br>
        <div class="displaySection" id="displayThread"></div><br>
        Reply Here:<br>
        <textarea class="instructorResponse" id="threadResponse"></textarea>
	</div>
    </div>
</div>
<!-- Main Container Ends -->
	
</body>
</html>
