// JavaScript Document
$(document).ready(function() {
	let searchCourses = document.getElementById("searchCourses");	
	let searchButton = document.getElementById("submitCourseButton");
	let suggestionList = document.getElementById("searchSuggestions");
	let $sugg = $("#searchSuggestions");
	let $suggestedVideos = $("#suggestedVideos");
	searchCourses.addEventListener("keydown",searchSuggest);
	searchButton.addEventListener("click", submitSearch);
	
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
					$sugg.append($("<li id=\"whichSugg"+i+"\"onClick=\"getValue("+i+")\"style=\"list-style-type:none; font-size:25px; \" value=\""+responseObject[i]+"\">").text(responseObject[i]));
                  //  $suggList.append($("<li>").text(responseObject[i]));
                }
            } else {
                let elem = document.createElement("li");
				elem.style.listStyleType = "none";
				elem.style.fontSize = "25px";
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
		while (suggestionList.firstChild) {
			suggestionList.removeChild(suggestionList.firstChild);
		}
        $.post("/homePageSearchSubmit",postParams, responseJSON => {
			
            let responseObject = JSON.parse(responseJSON);
			$suggestedVideos.html("");
            for (let i = 1; i < responseObject.length; i++) {
				let url = 'https://img.youtube.com/vi/'+extractVideoIdFromYouTubeUrl(responseObject[i])+'/0.jpg';
				$suggestedVideos.append($("<img id=\"courseImage"+i+"\"src=\""+url+"\" style=\"width:200px; height:200px;\">"));
				$suggestedVideos.append($("<li id=\"courseName\"style=\"list-style-type:none; font-size:50px; \"value=\""+responseObject[0]+"\">").text(responseObject[0]));
				document.getElementById("courseImage"+i).addEventListener("click",loadPage);
            }
        });
	}
	
	function loadPage() {
		
	}
	
	
 });