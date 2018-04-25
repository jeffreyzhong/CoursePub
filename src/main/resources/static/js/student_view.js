/*jshint esversion: 6 */
// Waits for DOM to load before running
class Question{
	constructor(id, summary, time, detail, user){
		this.id = id;
		this.summar = summary;
		this.time = time;
		this.detail = detail;
		this.user = user;
	}
	
	get id(){
		return this._id;
	}
	
	get summary(){
		return this._summary;
	}
	
	get time(){
		return this._time;
	}
	
	get detail(){
		return this._detail;
	}
	
	get user(){
		return this._user;
	}
}


const $sideContent = $("#sideContent");

//let conn = new WebSocket("ws://localhost:4567/student_view")

let questions = new map();

let questionSel = false;
let refQuestionInt;

let player; //Define a player object, to enable later function calls, without having to create a new class instance again.

let displayQuestions = [];
let duration = null;

// Define YT_ready function.
let YT_ready = (function() {
    let onReady_funcs = [], api_isReady = false;
    /* @param func function     Function to execute on ready
     * @param func Boolean      If true, all qeued functions are executed
     * @param b_before Boolean  If true, the func will added to the first
                                 position in the queue*/
    return function(func, b_before) {
        if (func === true) {
            api_isReady = true;
            while (onReady_funcs.length) {
                // Removes the first func from the array, and execute func
                onReady_funcs.shift()();
            }
        } else if (typeof func === "function") {
            if (api_isReady){
				func();
			} 
            else{
				onReady_funcs[b_before?"unshift":"push"](func); 
			} 
        }
    };
})();


$(document).ready(() => {

	document.getElementById('noteBtn').onclick = noteClick;
	document.getElementById('questionBtn').onclick = questionClick;
	document.getElementById('relBtn').onclick = relClick;
	
//	document
	
	

	// Add function to execute when the API is ready
	YT_ready(function(){
		let frameID = getFrameID("video");
		if (frameID) { //If the frame exists
			player = new YT.Player(frameID, {
				events: {
					"onStateChange": stateChangeFunc
				}
			});
			console.log(frameID);		
		}	
	});
	
});


//============================================================================
//Below are code for controls sideContentDiv (e.x. click on different tabs)

function noteClick(){
	questionSel = false;
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 1; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs[0].style.border = "none";
	$("#question0").html("Hi Class, welcome to MATH 520 Linear Algebra. In this class, I will give a brief introduction to what linear algebra is and the basic concepts that would be taught in this course. Please take a second to watch this short video and get excited for a semester long journey exploring the power of linear algebra!");
}

function relClick(){
	questionSel = false;
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 1; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs[0].style.border = "none";
	$("#question0").html("No related video available at the moment");	
}

function questionClick(){
	questionSel = true;
	questionDisplay();
}


function refQuestion(){
//	console.log("execute");
	if(player.getPlayerState() === 1){
		let currTime = Math.floor(player.getCurrentTime());
		displayQuestions = [];
		for(let i = 0; i < 5; i++){
			let listItem = "The current time of the video is: " + currTime ;
			displayQuestions.push(listItem);
		}
	}
	if(questionSel){
		questionDisplay();
	}
}


//display questions in each div
function questionDisplay(){
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "block";
	}
	let questions = document.getElementsByClassName("questions");
	for(let i = 0; i < questions.length; i++){
		let id = "#question" + i;
		$(id).html(displayQuestions[i]);
	}
}

// Example: function stopCycle, bound to onStateChange
function stateChangeFunc(event) {
	if(event.data === 0 || event.data === 2){
		clearInterval(refQuestionInt);
	}else if(event.data === 1){
		refQuestionInt = setInterval(refQuestion, 1000);
		if(duration === null){
			duration = player.getDuration();
			alert(duration);
		}
	}
}



//============================================================================
//Below are code for action when post button is clicked



// check if the user input time is valid.
function isValidTime(time){
	let timeArray = time.split(":");
	if(timeArray.length !== 3){
		return false;
	}
	if(isNaN(timeArray[0])||isNaN(timeArray[1])||isNaN(timeArray[2])){
		return false;
	}
	let sec = parseInt(timeArray[0])*3600 + parseInt(timeArray[1])*60 + parseInt(timeArray[2]);
	if(sec > Math.floor(+duration)){
		return false;
	}
	return true;
}




//============================================================================
//Below are helper methods to get iFrame element from html

function getFrameID(id){
    let elem = document.getElementById(id);
    if (elem) {
        if(/^iframe$/i.test(elem.tagName)){
			return id; //Frame, OK
		}
        // else: Look for frame
        let elems = elem.getElementsByTagName("iframe");
        if (!elems.length){
			return null; //No iframe found, FAILURE
		} 
		let i = 0;
        for (i=0; i<elems.length; i++) {
           if (/^https?:\/\/(?:www\.)?youtube(?:-nocookie)?\.com(\/|$)/i.test(elems[i].src)){
			   break;
		   }
        }
        elem = elems[i]; //The only, or the best iFrame
        if (elem.id){
			return elem.id; //Existing ID, return it
		} 
        // else: Create a new ID
        do { //Keep postfixing `-frame` until the ID is unique
            id += "-frame";
        } while (document.getElementById(id));
        elem.id = id;
        return id;
    }
    // If no element, return null.
    return null;
}


// This function will be called when the API is fully loaded
function onYouTubePlayerAPIReady() {YT_ready(true)}

// Load YouTube Frame API
(function() { // Closure, to not leak to the scope
  let s = document.createElement("script");
  s.src = (location.protocol === 'https:' ? 'https' : 'http') + "://www.youtube.com/player_api";
  let before = document.getElementsByTagName("script")[0];
  before.parentNode.insertBefore(s, before);
})();

