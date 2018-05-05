// JavaScript Document
//$(document).ready(function() {
	let searchCourses = document.getElementById("searchCourses");	
	let searchButton = document.getElementById("submitCourseButton");
	let suggestionLost = document.getElementById("searchSuggestions");
	searchCourses.addEventListener("keydown",searchSuggest);
	searchButton.addEventListener("click", submitSearch);
	
	function searchSuggest() {
		console.log(searchCourses.val);
		postParams = {input : searchCourses.val, suggestion : true};
//		$.post("/homePageSearchSuggestions",postParams, responseJSON => {
//			let responseObject = JSON.parse(responseJSON);
//			
//		});
	}
	
	function submitSearch() {
		console.log(searchCourses.val);
		postParams = {input : searchCourses.val, suggestion : false};
	}
	
	
	
//});