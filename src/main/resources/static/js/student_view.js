/*jshint esversion: 6 */
// Waits for DOM to load before running
class Question{
	constructor(id, summary, time, detail, user, isResolved){
		this._id = id;
		this._summary = summary;
		this._time = time;
		this._detail = detail;
		this._user = user;
		this._resolved = isResolved;
	}
	
	get id(){
		return this._id;
	}
	
	set id(id){
		this._id = id;
	}
	
	get summary(){
		return this._summary;
	}
	
	set summary(summary){
		this._summar = summary;
	}
	
	get time(){
		return this._time;
	}
	
	set time(time){
		this._time = time;
	}
	
	get detail(){
		return this._detail;
	}
	
	set detail(detail){
		this._detail = detail;
	}
	
	get user(){
		return this._user;
	}
	
	set user(user){
		this._user = user;
	}
	
	get resolved(){
		return this._resolved;
	}
	
	set resolved(resolved){
		this._resolved = resolved;
	}
}


//let conn = new WebSocket("ws://localhost:4567/student_view")

let questions = new Map();
let questionsOrd = [];
let videoId = parseInt($('#videoId').html());

let questionSel = false;
let refQuestionInt;

let player; //Define a player object, to enable later function calls, without having to create a new class instance again.

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

	// Add function to execute when the API is ready
	YT_ready(function(){
		let frameID = getFrameID("video");
		if (frameID) { //If the frame exists
			player = new YT.Player(frameID, {
				events: {
					"onReady": loadQuestions,
					"onStateChange": stateChangeFunc
				}
			});	
		}	
	});

	$("#noteBtn").click();
	
});


function loadQuestions(event){
	event.target.pauseVideo();
	
	const postParameters = {id: videoId};
	$.post("/question", postParameters, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		
		for (let i = 0; i < responseObject.length; ++i) {
			let question = responseObject[i];
			let id = question.id;
			let time = question.time;
			let summary = question.summary;
			let user = question.user;
			let resolved = question.isResolved;
			let obj = new Question(id, summary, time, "", user, resolved);
			questions.set(obj.id, obj);
		  	questionsOrd.push(obj);
		}   
		
		questionsOrd = questionsOrd.sort(compare);
		for(let i = 0; i < questionsOrd.length; ++i){
			alert("question (id " + questionsOrd[i].id + " with summary " + questionsOrd[i].summary + " at second " + questionsOrd[i].time);
		}
		event.target.playVideo();
	});
	
}

function compare(a,b) {
  if (a.time < b.time){
    return -1;
  }
  if (a.time > b.time){
    return 1;
  }
  return 0;
}


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

	let index = 0;
	if(player.getPlayerState() === 1){
		let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "", false);
		index = questionsOrd.binarySearch(questionStub, compare);
		if(questionSel && index !== null){
			questionDisplay(index);
		}
	}
}


//display questions in each div
function questionDisplay(index){
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "block";
	}
	
	let min = Math.max(0, index-1);
	if(min - 1 >= 0){
		min -= 1;
	}
	let max = Math.min(questionsOrd.length-1, index+1);
	if(max + 1 < questionsOrd.length){
		max += 1;
	}
	
	let range = max - min;

	for(let i = 0; i <= range; i++){
		console.log("questionsOrd index is: " + min);
		let questionId = "#question" + i;
		let timeId = "#time" + i;
		let userId = "#user" + i;
		$(questionId).html(questionsOrd[min].summary);
		// let time = 0;
		// if(player.getDuration() > 3600){
		// 	time = moment(questionsOrd[min].time).format('H:mm:ss');
		// }else{
		// 	time = moment(questionsOrd[min].time).format('mm:ss');
		// }
		$(timeId).html(questionsOrd[min].time);
		$(userId).html(questionsOrd[min].user);
		min+=1;
	}


}

// Example: function stopCycle, bound to onStateChange
function stateChangeFunc(event) {
	if(event.data === 0 || event.data === 2){
		clearInterval(refQuestionInt);
	}else if(event.data === 1){
		refQuestionInt = setInterval(refQuestion, 250);
		if(duration === null){
			duration = player.getDuration();
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


//============================================================================
//Binary search for the index of the closest question in the list
Array.prototype.binarySearch = function(find, comparator) {
  var low = 0, high = this.length - 1, i, comparison, prev_comparison;  
  while (low <= high) {
    i = Math.floor((low + high) / 2);
    comparison = comparator(this[i], find);
    prev_comparison = comparison
    if (comparison < 0) { low = i + 1; continue; };
    if (comparison > 0) { high = i - 1; continue; };
    return i;
  }
  if (prev_comparison < 0) {
      var option_low = i;
      var option_high = i+1;
  } else {
      var option_low = i-1;
      var option_high = i;
  }
  var dist_a = find - this[option_low];
  var dist_b = this[option_high] - find;
  if (dist_a < dist_b) {
      return option_low;
  } else {
      return option_high;
  }
  return null;
};

