<!doctype html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>coursepub</title>
<link href="/static/css/homePage.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->
<script>var __adobewebfontsappname__="dreamweaver"</script>
<script src="http://use.edgefonts.net/source-sans-pro:n2:default.js" type="text/javascript"></script>
<!--<script src="../static/js/homePage.js" type="text/javascript"></script>-->
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
        <li><a href="">home</a></li>
        <li><a href="#about">courses</a></li>
        <li> <a href="#contact">about</a></li>
        <li> <a id="account" href="/login/google">sign in/sign up</a></li>
      </ul>
    </nav>
  </header>
	<br><br><br>
	<div class="search" style="list-style-type: none">
		<li><a id="learningStartsHere">learning starts here.</a></li><br>
		<li><textarea class="searchCourses" id="searchCourses"></textarea></li>
		<ul id="searchSuggestions"></ul>
	</div>
	<div class="submit" style="list-style-type: none">
	<input type="submit" id="submitCourseButton" value="Search">
	</div>
    <div class="suggestions" style="list-style-type: none">
    <li id="suggestedVideos"></li>
    </div>
<!-- Hero Section -->
  <!-- About Section -->
  <!-- Stats Gallery Section -->
  <!-- Parallax Section -->
<!-- More Info Section -->
  <footer> </footer>
  <!-- Footer Section -->
  
  <!-- Copyrights Section -->
</div>
<!-- Main Container Ends -->
</body>
        <script>

	let searchCourses = document.getElementById("searchCourses");	
	let searchButton = document.getElementById("submitCourseButton");
	let suggestionLost = document.getElementById("searchSuggestions");
	searchCourses.addEventListener("keydown",searchSuggest);
	searchButton.addEventListener("click", submitSearch);
	
	function searchSuggest() {
		console.log(searchCourses.value);
		postParams = {input : searchCourses.val, suggestion : true};
//		$.post("/homePageSearchSuggestions",postParams, responseJSON => {
//			let responseObject = JSON.parse(responseJSON);
//			
//		});
	}
	
	function submitSearch() {
		console.log(searchCourses.value);
		postParams = {input : searchCourses.val, suggestion : false};
        $.post("/homePageSearchSuggestions",postParams, responseJSON => {
            let responseObject = JSON.parse(responseJSON);
            for (let i = 0; i < responseObject.length; i++) {
                   
            }
        });
	}
            
	
	function extractVideoIdFromYouTubeUrl (url) {

    	let stepOne = url.split('?')[0];
    	let stepTwo = stepOne.split('/');
    	let videoId = stepTwo[stepTwo.length-1];

    return videoId;
	}
	
	let linkId = extractVideoIdFromYouTubeUrl(link);
	let url = 'https://img.youtube.com/vi/'+linkId+'/0.jpg';
	videoThumbnail.src = url;
	videoThumbnail.style.width = "50px";
	videoThumbnail.style.height = "50px";
	$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + linkId + "&key=" + "AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
		videoName.innerHTML = data.items[0].snippet.title;
		//alert(data.items[0].snippet.title);
	});
    
    </script>
</html>
