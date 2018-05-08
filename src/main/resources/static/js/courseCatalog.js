// JavaScript Document

$(document).ready(function() {
	
	let urlToId = {};
	let srcToId = {};
	let $courseList = $("#courseList");
	let isInstructor;
	let videoToClicked = {};
	
	$.post("/courseCatalog",{},responseJSON => {
		let responseObject = JSON.parse(responseJSON);
		console.log(responseObject);
		for (let i = 0; i < responseObject.length; i++) {
			let curr = responseObject[i];
			let thumbnail = curr['courseThumbnail'];
			let courseName = curr['courseName'];
			let courseId = curr['courseId'];
			let url = 'https://img.youtube.com/vi/'+extractVideoIdFromYouTubeUrl(thumbnail)+'/0.jpg';
			$courseList.append($("<p id=\"para"+i+"\"style=\"font-size:30px; color:white;\"><img id=\"courseImage"+i+"\"src=\""+url+"\" style=\"width:200px; height:200px; align:left;\">"+courseName+"</p>"));
//			$courseList.append($("<li id=\"courseName\"style=\"list-style-type:none; font-size:50px; color:white; \"value=\""+courseName+"\">").text(courseName));
			document.getElementById("courseImage"+i).addEventListener("click",loadPage);
			urlToId[url] = courseId;
		}
	});
	
	function extractVideoIdFromYouTubeUrl (url) {

    	let stepOne = url.split('?')[0];
    	let stepTwo = stepOne.split('/');
    	let videoId = stepTwo[stepTwo.length-1];

    return videoId;

	}
	
	
	function loadPage() {
		let el = this;
		console.log(urlToId);
		console.log(el.src);
		console.log(String(el.src));
		if (!videoToClicked[String(el.src)]) {
			videoToClicked[String(el.src)] = true;
			let courseId = urlToId[String(el.src)];
			console.log(courseId);
			console.log(el.id.slice(-1));
			let num = parseInt(el.id.slice(-1))+1;
			let numString = num.toString();
			console.log("NumString: " + numString);
			document.removeE
			$.post("/getCourseVideos",{courseId:courseId}, responseJSON => {
				let responseObject = JSON.parse(responseJSON);
				console.log(responseObject);
				let elBefore = document.getElementById("para"+numString);
				if (!elBefore) {
					elBefore = document.getElementById("lastOne");
				}
				isInstructor = responseObject[responseObject.length-1]['isInstructor'];
				for (let i = 0; i < responseObject.length; i++) {
					let elem = document.createElement("img");

					let elemName = document.createElement("p");
					let videoId = responseObject[i]['videoId'];
					let videoUrl = responseObject[i]['videoUrl'];
					let youtubeId = extractVideoIdFromYouTubeUrl(videoUrl);
					let imageUrl = 'https://img.youtube.com/vi/'+youtubeId+'/0.jpg';
					let videoName = "";
					$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + youtubeId + "&key=AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
						elemName.innerHTML = data.items[0].snippet.title;
						//alert(data.items[0].snippet.title);
					});
					elemName.style.fontSize = "25px";
					elemName.style.color = "white";
					elem.src = imageUrl;
					srcToId[imageUrl] = videoId;
					elem.addEventListener("click",goToPage);
					elem.style.height = "100px";
					elem.style.width = "100px";
					elBefore.parentNode.insertBefore(elem,elBefore);
					elBefore.parentNode.insertBefore(elemName,elBefore);
				}
				console.log(responseObject);
			});
			//console.log("http://localhost:4567/instructor/video/"+videoId);
			//window.open("http://localhost:4567/instructor/video/"+videoId,"_blank");
			//window.location.href = 'localhost:4567/instructor/video/'+videoId;
		}
	}
	
	function goToPage() {
		let el = this;
		console.log(el.src);
		let videoId = srcToId[String(el.src)];
		if (isInstructor) {
			window.open("http://localhost:4567/instructor/video/"+videoId,"_blank");
		} else {
			window.open("http://localhost:4567/video/"+videoId,"_blank");
		}
		
	}
	
});