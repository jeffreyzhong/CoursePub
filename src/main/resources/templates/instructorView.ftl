<!doctype html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>coursepub</title>
<link href="../css/instructorView.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->
<script>var __adobewebfontsappname__="dreamweaver"</script>
<script src="http://use.edgefonts.net/source-sans-pro:n2:default.js" type="text/javascript"></script>
<script src="../node_modules/vis/dist/vis.js"></script>
<link href="../node_modules/vis/dist/vis.css" rel="stylesheet" type="text/css"/>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Main Container -->

<div class="container"> 
  <!-- Navigation -->
  <header> <a href="">
    <h4 class="logo">coursepub</h4>
  </a>
    <nav>
      <ul>
        <li><a href="#hero">home</a></li>
        <li><a href="#about">courses</a></li>
        <li> <a href="#contact">about</a></li>
      </ul>
    </nav>
  </header>
	<br>
	<div id="videoName">Essence of Linear Algebra</div>
<!--
	<section id="interactiveSection"> 
	<canvas id="interactiveCanvas"></canvas>
	</section>
-->
	<span id="videoId" style="display:none">${videoId}</span>
	<div id="visualization">
	</div>
	<br>
	<div id="clickedOnQuestion">
		<div id="fullQuestion">
			<text id="displayQuestion"></text><br>
			<textarea id="instructorResponse"></textarea><br>
			<input type="submit" id="submitResponseButton">
		</div>
	</div>
	<div id="videoSection">
		<iframe width="560" height="315" src="https://www.youtube.com/embed/kjBOesZCoqc?rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
	</div>
</div>
<!-- Main Container Ends -->

<script src="../static/node_modules/moment/moment.js"></script>
<script>
	moment().format();
	moment().format("ss, mm, kk");
</script>
<script src="../static/js/jquery-3.1.1.js"></script>
<script src="../static/js/instructorViewTimeline.js"></script>
	
</body>
</html>
