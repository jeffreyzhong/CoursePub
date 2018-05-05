// JavaScript Document
$(document).ready(function() {
	let searchCourses = document.getElementById("searchCourses");	
	let searchButton = document.getElementById("submitCourseButton");
	let suggestionList = document.getElementById("searchSuggestions");
	let $sugg = $("#searchSuggestions");
	let $suggestedVideos = $("#suggestedVideos");
	let urlToId = {};
	let srcToId = {};
	let videoToClicked = {};
	let isInstructor;
	searchCourses.addEventListener("keydown",searchSuggest);
	searchButton.addEventListener("click", submitSearch);
	let invisible = document.getElementById("invisible");
	
	function searchSuggest() {
		console.log(searchCourses.value);
		let postParams = {input : searchCourses.value};
		$.post("/homePageSearchSuggestions",postParams,responseJSON => {
			while (suggestionList.firstChild) {
    			suggestionList.removeChild(suggestionList.firstChild);
			}
			let responseObject = JSON.parse(responseJSON);
            if (responseObject.length > 0) {
                for (let i = 0; i < responseObject.length; i++) {
//                    let elem = document.createElement("li");
//					elem.style.listStyleType = "none";
//                    elem.innerHTML = responseObject[i];
//                    suggestionList.appendChild(elem);
					$sugg.append($("<li id=\"whichSugg"+i+"\"onClick=\"getValue("+i+")\"style=\"list-style-type:none; font-size:25px; color:white;\" value=\""+responseObject[i]+"\">").text(responseObject[i]));
                  //  $suggList.append($("<li>").text(responseObject[i]));
                }
            } else {
                let elem = document.createElement("li");
				elem.style.listStyleType = "none";
				elem.style.fontSize = "25px";
				elem.style.color = "white";
                elem.innerHTML = "Not sure which course you're talking about :/";
                suggestionList.appendChild(elem); 
            }
		});
	}
	
	function extractVideoIdFromYouTubeUrl (url) {

    	let stepOne = url.split('?')[0];
    	let stepTwo = stepOne.split('/');
    	let videoId = stepTwo[stepTwo.length-1];

    return videoId;

	}
	
	function submitSearch() {
		console.log(searchCourses.value);
		let postParams = {input : searchCourses.value};
		while (invisible.firstChild) {
			invisible.removeChild(invisible.firstChild);
		}
        $.post("/homePageSearchSubmit",postParams, responseJSON => {
			
            let responseObject = JSON.parse(responseJSON);
			let courseInfo = responseObject['courseInfo'];
			let courseName = responseObject['courseName'];
			isInstructor = responseObject['isInstructor'];
			console.log(responseObject);
			console.log(responseObject['courseName']);
			console.log(responseObject['courseInfo']);
			console.log(responseObject.courseName);
			$suggestedVideos.html("");
            for (let i = 0; i < courseInfo.length; i++) {
				let url = 'https://img.youtube.com/vi/'+extractVideoIdFromYouTubeUrl(courseInfo[i]['0'])+'/0.jpg';
//				$suggestedVideos.append($("<img id=\"courseImage"+i+"\"src=\""+url+"\" style=\"width:200px; height:200px;\">"));
//				$suggestedVideos.append($("<li id=\"courseName\"style=\"list-style-type:none; font-size:50px; color:white; \"value=\""+courseName+"\">").text(courseName));
//				document.getElementById("courseImage"+i).addEventListener("click",loadPage);
			$suggestedVideos.append($("<p id=\"para"+i+"\"style=\"font-size:50px; color:white;\"><img id=\"courseImage"+i+"\"src=\""+url+"\" style=\"width:300px; height:300px; align:left;\">"+courseName+"</p>"));
//			$courseList.append($("<li id=\"courseName\"style=\"list-style-type:none; font-size:50px; color:white; \"value=\""+courseName+"\">").text(courseName));
			document.getElementById("courseImage"+i).addEventListener("click",loadPage);
				urlToId[url] = courseInfo[i]['1'];
            }
        });
	}
	
//	function loadPage() {
//		let el = this;
//		console.log(urlToId);
//		console.log(el.src);
//		console.log(String(el.src));
//		let videoId = urlToId[String(el.src)];
//		console.log(videoId);
//		console.log("http://localhost:4567/instructor/video/"+videoId);
//		if (isInstructor) {
//			window.open("http://localhost:4567/instructor/video/"+videoId,"_blank");
//		} else {
//			window.open("http://localhost:4567/video/"+videoId,"_blank");
//		}
//		//window.location.href = 'localhost:4567/instructor/video/'+videoId;
//		
//	}
	
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
				//let elBefore = document.getElementById("para"+numString);
				//if (!elBefore) {
				//	elBefore = document.getElementById("invisible");
				//}
				elBefore = document.getElementById("invisible");
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
					elBefore.appendChild(elem);
					elBefore.appendChild(elemName);
//					elBefore.parentNode.insertBefore(elem,elBefore);
//					elBefore.parentNode.insertBefore(elemName,elBefore);
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