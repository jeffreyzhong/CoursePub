// JavaScript Document
$(document).ready(function() {
	var signInUpLink = document.getElementById("account");
	signInUpLink.addEventListener("click", sendRequest);
	
	var searchButton = document.getElementById("submitCourseButton");
	searchButton.addEventListener("click", searchCourses);
	
	
	
});