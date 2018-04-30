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

const MESSAGE_TYPE = {
	CONNECT: 0,
	NEW_QUESTION: 1,
	NEW_ANSWER: 2,
	UPVOTE: 3
};

let conn;

let questions = new Map();
let questionsOrd = [];
let videoId = parseInt($('#videoId').html());

let questionSel = false;
let viewState = 0;
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
	let url = window.location.href;
	let string = url.split("/");
	let connection = "ws://localhost:4567/websocket/" + string[string.length-1];

	conn = new WebSocket(connection);
	conn.addEventListener('message', function (event) {
		const data = JSON(event.data);
	    switch (data.type) {
	      default:
	        console.log('Unknown message type!', data.type);
	        break;
	      case MESSAGE_TYPE.CONNECT:
	        let payload = JSON.parse(data.payload);
	        console.log(payload.msg);
	        break;
	      case MESSAGE_TYPE.NEW_QUESTION:
	        let payload = JSON.parse(msg.payload);
			let id = payload.id;
			let time = payload.time;
			let summary = payload.summary;
			let detail = payload.detail;
			let user = payload.user;
			let resolved = payload.resolved;
			let obj = new Question(id, summary, time, detail, user, resolved);
			questions.set(obj.id, obj);
		  	questionsOrd = questionsOrd.splice(locationOf(obj,questionsOrd,compare) + 1, 0, obj);
			console.log("id: " + id + " summary: " + summary + " time: " + time);
		  	questionDisplay();
	        break;
	    }
	});

	document.getElementById('noteBtn').onclick = noteClick;
	document.getElementById('questionBtn').onclick = questionClick	;
	document.getElementById('relBtn').onclick = relClick;
	document.getElementById('allQuestionsBtn').onclick = allClick;

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
			console.log(player.videoId);
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
			let resolved = question.resolved;
			let detail = question.detail
			let obj = new Question(id, summary, time, detail, user, resolved);
			questions.set(obj.id, obj);
		  	questionsOrd.push(obj);
		}   
		
		questionsOrd = questionsOrd.sort(compare);
		event.target.playVideo();
	});	
	
}



//============================================================================
//Below are code for controls sideContentDiv (e.x. click on different tabs)

function noteClick(){
	hideContent();
	$("#question0").html("Hi Class, welcome to MATH 520 Linear Algebra. In this class, I will give a brief introduction to what linear algebra is and the basic concepts that would be taught in this course. Please take a second to watch this short video and get excited for a semester long journey exploring the power of linear algebra!");
}

function relClick(){
	hideContent();
	$("#question0").html("No related video available at the moment");	
}

function allClick(){
	questionSel = false;
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	if(document.getElementById('questionsList') === null){
		let ul = document.createElement('ul');
		ul.setAttribute('id','questionsList');
		ul.style.listStyleType = "none";
		ul.style.lineHeight = "22px";
		document.getElementById("sideContentDiv").appendChild(ul);
		for(let i = 0; i < questionsOrd.length; i++){
			let curr = questionsOrd[i];
			let text = convertSeconds(curr.time) + " " + curr.summary + " user: " + curr.user;
			renderQuestionList(text,ul);
		}
	}else{
		let ul = document.getElementById('questionsList').style.display = "block";
		while (ul.firstChild) {
			ul.removeChild(ul.firstChild);
		}
		for(let i = 0; i < questionsOrd.length; i++){
			let curr = questionsOrd[i];
			let text = convertSeconds(curr.time) + " " + curr.summary + " user: " + curr.user;
			renderQuestionList(text,ul);
		}
	}
}

function renderQuestionList(text, ul){
	let li = document.createElement('li');
	li.setAttribute('class', 'item');
	li.style.color = 'white';
	ul.appendChild(li);
	li.innerHTML = li.innerHTML + text;
}



//============================================================================
//Below are code for automatic question display (e.x. click on different tabs)


function questionClick(){
	questionSel = true;
	if(document.getElementById('questionsList') !== null){
		document.getElementById('questionsList').style.display = "none";
	}
	let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "", false);
	index = questionsOrd.binarySearch(questionStub, compare);
	questionDisplay(index);
}


function refQuestion(){
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
	divs = document.getElementsByClassName("questionTimeLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "block";
	}
	divs = document.getElementsByClassName("userLabel");
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
		let questionId = "#question" + i;
		let timeId = "#time" + i;
		let userId = "#user" + i;
		$(questionId).html(questionsOrd[min].summary);
		let time = convertSeconds(parseInt(questionsOrd[min].time));
		$(timeId).html(time);
		$(userId).html("User: " + questionsOrd[min].user);
		min+=1;
	}

}


// Example: function stopCycle, bound to onStateChange
function stateChangeFunc(event) {
	if(event.data === 0 || event.data === 2){
		clearInterval(refQuestionInt);
	}else if(event.data === 1){
		refQuestionInt = setInterval(refQuestion, 100);
		if(duration === null){
			duration = player.getDuration();
		}
	}
}



//============================================================================
//Below are code for action when post button is clicked
// Setup the WebSocket connection for live updating of scores.


const new_question = () => {
  let tmp = $("input[name='board']").val();
  let jsonObject = {type:1, board:tmp, text:guesses.join(" ")};
  // obj.setProperty("type", MESSAGE_TYPE.SCORE);
  // obj.setProperty("board", $("board"));
  // obj.setProperty("text", $("guesses"));
  conn.send(JSON.stringify({type: 1, payload: jsonObject}));
  //conn.send(jsonObject);
}

function

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
//Below are miscellaneous helper functions

//Binary search for the index of the closest question in the list
Array.prototype.binarySearch = function(find, comparator) {
	var low = 0, high = this.length - 1, i, comparison, prev_comparison;  
	while (low <= high) {
	i = Math.floor((low + high) / 2);
	comparison = comparator(this[i], find);
	prev_comparison = comparison;
	if (comparison < 0) { low = i + 1; continue; }
	if (comparison > 0) { high = i - 1; continue; }
	return i;
	}
	var option_low;
	var option_high;
	if (prev_comparison < 0) {
	  option_low = i;
	  option_high = i+1;
	} else {
	  option_low = i-1;
	  option_high = i;
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

function locationOf(element, array, comparer, start, end) {
    if (array.length === 0)
        return -1;

    start = start || 0;
    end = end || array.length;
    var pivot = (start + end) >> 1;  // should be faster than dividing by 2

    var c = comparer(element, array[pivot]);
    if (end - start <= 1) return c == -1 ? pivot - 1 : pivot;

    switch (c) {
        case -1: return locationOf(element, array, comparer, start, pivot);
        case 0: return pivot;
        case 1: return locationOf(element, array, comparer, pivot, end);
    };
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

//Convert seconds to "HH:MM:SS" format
function convertSeconds(seconds){
	let time = 0;
	if(player.getDuration() >= 3600){
		let H = Math.floor(seconds / 3600);
		seconds = seconds % 3600;
		let M = Math.floor(seconds / 60);
		seconds = seconds % 60;
		
		if(H < 10){
			H = "0" + H;
		}
		if(M < 10){
			M = "0" + M;
		}
		if(seconds < 10){
			seconds = "0" + seconds;
		}
		time = H + ":" + M + ":" + seconds;
	}else{
		let M = Math.floor(seconds / 60);
		seconds = seconds % 60;
		
		if(M < 10){
			M = "0" + M;
		}
		if(seconds < 10){
			seconds = "0" + seconds;
		}
		time = M + ":" + seconds;
	}
	return time;
}

function hideContent(){
	questionSel = false;
	document.getElementById("time0").style.display = "none";
	document.getElementById("user0").style.display = "none";
	if(document.getElementById('questionsList') !== null){
		document.getElementById('questionsList').style.display = "none";
	}
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 1; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs[0].style.border = "none";
	divs[0].style.display = "block";
}

