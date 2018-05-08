/*jshint esversion: 6 */
// Waits for DOM to load before running
class Question{
	constructor(id, summary, time, detail, user, isResolved, upvotes, instructorAnswer, studentAnswer){
		this._id = id;
		this._summary = summary;
		this._time = time;
		this._detail = detail;
		this._user = user;
		this._resolved = isResolved;
		this._upvotes = upvotes;
		this._instructorAnswer = instructorAnswer;
		this._studnetAnswer = studentAnswer;
	}
	
	get upvotes(){
		return this._upvotes;
	}
	
	set upvotes(numUpvotes){
		this._upvotes = numUpvotes;
	}
	
	get instructorAnswer(){
		return this._instructorAnswer;
	}
	
	set instructorAnswer(ans){
		this._instructorAnswer = ans;
	}
	
	get studentAnswer(){
		return this._studnetAnswer;
	}
	
	set studentAnswer(ans){
		this._studnetAnswer = ans;
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

class Video{
	constructor(id, linkId){
		this._id = id;
		this._linkId = linkId;
		// this._title = "";
	}
	
	get id(){
		return this._id;
	}
	
	get linkId(){
		return this._linkId;
	}
	
	// get title(){
	// 	return this._title;
	// }

}

const MESSAGE_TYPE = {
	CONNECT: 0,
	NEW_QUESTION: 1,
	NEW_ANSWER: 2,
	UPVOTE: 3,
	instructorAnswer:4,
	studentAnswer: 5,
	ERROR: -1
	
};

let conn;

let questions = new Map();
let questionsOrd = [];
let videoId = parseInt($('#videoId').html());

let questionSel = false;
let expanded = -1;
let refQuestionInt;
let description = "No notes on this course yet.";

let player; //Define a player object, to enable later function calls, without having to create a new class instance again.

let duration = null;

let relVideo = [];
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
	let windowlocation = window.location.href.split('/')[2];
	let ws = "ws://"+windowlocation+"/websocket/"+videoId;
	
	conn = new WebSocket(ws);
	conn.addEventListener('message', function (event) {
		const data = JSON.parse(event.data);
//		console.log("from server: " + data.message);
	    switch (data.type) {
			default:
				console.log('Unknown message type! ' + data.payload.message, data.type);
			break;
			case MESSAGE_TYPE.CONNECT:
				console.log(data.payload.msg);
				break;
			case MESSAGE_TYPE.NEW_QUESTION:
				let id = data.payload.id;
				let time = data.payload.time;
				let summary = data.payload.summary;
				let user = data.payload.user;
				let resolved = data.payload.resolved;
				let detail = data.payload.detail;
				let upvotes = data.payload.upvotes;
				let instructorAnswer = data.payload.instructorAnswer;
				let studentAnswer = data.payload.studentAnswer;
				let obj = new Question(id, summary, time, detail, user, resolved, upvotes, instructorAnswer, studentAnswer);
				questions.set(obj.id, obj);
				questionsOrd.push(obj);
				questionsOrd.sort(compare);
				let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "", false, 0, "", "");
				let index = questionsOrd.binarySearch(questionStub, compare);
				// console.log("index" + index);
				$("#questionBtn").click();
				break;
			case MESSAGE_TYPE.UPVOTE:
				let questionId = data.payload.id;
				let type = data.payload.upvoteType;
				let num = data.payload.num;
				console.log("id:" + questionId + " type " + type + " num: " + num + "original: " + questions.get(questionId).upvotes);
				if(type === 0){
					if(num === 1){
						let old = questions.get(questionId).upvotes;
						questions.get(questionId).upvotes = old + 1;
						let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "",false, 0,"","");
						let index = questionsOrd.binarySearch(questionStub, compare);
					//	console.log("INDEX: " + index);
						$("#questionBtn").click();
						
//						console.log("id:" + questionId + " type " + type + " num: " + num + "new: " + questions.get(questionId).upvotes);
					}
				}
				break;
			case MESSAGE_TYPE.instructorAnswer:
				questions.get(data.payload.questionId).instructorAnswer = data.payload;
				break;
			case MESSAGE_TYPE.studentAnswer:
				questions.get(data.payload.questionId).studentAnswer = data.payload;
				break;
			case MESSAGE_TYPE.ERROR:
				alert("You have already upvoted this question!");
				break;
					
	    }
	});

	document.getElementById('noteBtn').onclick = noteClick;
	document.getElementById('questionBtn').onclick = questionClick;
	document.getElementById('relBtn').onclick = relClick;
	document.getElementById('allQuestionsBtn').onclick = allClick;
	document.getElementById('submitBtn').onclick = postClick;

	// Add function to execute when the API is ready
	YT_ready(function(){
		let frameID = getFrameID("video");
		if (frameID) { //If the frame exists
			player = new YT.Player(frameID, {
				events: {
					"onStateChange": stateChangeFunc
				}
			});	
		}	
	});
	
	loadQuestions();
	setupSearchBar();
	$("#summaryInput").focus(function() {
		if(player.getDuration() >= 3600){
			$("#timeInput").val(convertSeconds(Math.floor(player.getCurrentTime())));
		}else{
			$("#timeInput").val("00:" + convertSeconds(Math.floor(player.getCurrentTime())));
		}
	});
	$("#answerInput").keyup(function(ev) {
	    // 13 is ENTER
	    if (ev.which === 13) {
	     	answerSubmit();
		}
	});	
	
	document.getElementById('relQuestion').onclick = relQuestionClick;
	
	document.getElementById('container').onclick = function(ev){
		if(ev.target.id !== "searchBtn" && ev.target.className !== "searchItem"){
			if(document.getElementById('searchResults') !== null){
				let toRemove = document.getElementById('searchResults');
				toRemove.parentNode.removeChild(toRemove);
			}
		}
	};
	
	init();
	
});

function init(){
	const postParameters = {id: videoId};
	$.post("/setup", postParameters, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		document.getElementById('video').setAttribute('src', responseObject[0]);
		let url = responseObject[0].split("/");
		let temp = url[url.length-1];
		let linkId = temp.split("?")[0];
		$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + linkId + "&key=AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
				document.getElementById('title').innerHTML = data.items[0].snippet.title;
				description = data.items[0].snippet.description;
				$("#noteBtn").click();
		});	
	});	
}

function loadQuestions(){
	setupRelatedVideo();
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
			let detail = question.detail;
			let upvotes = question.upvotes;
			let instructorAnswer = question.instructorAnswer;
			let studentAnswer = question.studentAnswer;
			let obj = new Question(id, summary, time, detail, user, resolved, upvotes, instructorAnswer, studentAnswer);
			questions.set(obj.id, obj);
		  	questionsOrd.push(obj);
		}   
		
		questionsOrd = questionsOrd.sort(compare);

//		event.target.playVideo();
	});	
}

function setupRelatedVideo(){
	const postParameters = {};
	$.post("/related", postParameters, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		for (let i = 0; i < responseObject.length; i+=2) {
			let id = responseObject[i];
			let url = responseObject[i+1].split("/");
			let temp = url[url.length-1];
			let linkId = temp.split("?")[0];
			let obj = new Video(id, linkId);
			relVideo.push(obj);
		}   
	});	
}

// Example: function stopCycle, bound to onStateChange
function stateChangeFunc(event) {
	if(event.data === 0 || event.data === 2){
		console.log("here")
		clearInterval(refQuestionInt);
	}else if(event.data === 1){
		console.log("playing");
		refQuestionInt = setInterval(refQuestion, 100);
		if(!duration){
			duration = player.getDuration();
		}
	}
}

//============================================================================
//Below are code for searching transcripts within video
function setupSearchBar(){
	$("searchBar").keyup(event =>{
		let item = document.getElementById("item");
		let ul = document.createElement("ul");
		ul.setAttribute("id", "searchResults");
		ul.style.position = "absolute";
		ul.style.overflow = "hidden";
		ul.style.overflowY = "auto";
		ul.style.zIndex = "999";
		ul.style.listStyle = "none";
		ul.style.margin = "0px";
		ul.style.marginLeft = "112.58px";
		ul.style.padding = "0px";
		ul.style.background = "rgba(0,0,0,0.25)";
		ul.style.width = "224px";
		ul.style.height = "300px";
		const postParameters = {id:videoId, word : $("searchBar").val()};
		$.post("/autocorrect", postParameters, responseJSON => {
			// TODO: Parse the JSON response into a JavaScript object.
			const responseObject = JSON.parse(responseJSON);  

			for (let i =  0; i < responseObject.length; i++) {
				let text = responseObject[i];
				let li = document.createElement('li');
				li.setAttribute('class', 'searchItem');
				li.style.margin = "0px";
				li.style.padding = "0px";
				li.style.color = "white";
				li.style.border = "2px solid #000000";
				if(i === 0){
					li.style.borderTop = "none";
				}
				if(i+1 !== responseObject.length - 1){
					li.style.borderBottom = "none";
				}
				li.style.fontSize = "15px";
				li.style.display = "block";
				ul.appendChild(li);
				li.innerHTML = li.innerHTML + text;
				li.onclick = function(){
					$("searchBar").val(text);
					item.removeChild(ul);
				};
			}
			item.appendChild(ul);	
		});
	});
	document.getElementById('searchBtn').onclick = function() {
	    // 13 is ENTER
		if ($("#searchBar").val() !== "" && document.getElementById("searchResults") === null && isValidTime($("#searchTimeInput1").val()) && isValidTime($("#searchTimeInput2").val())){
			let item = document.getElementById("item");
			let ul = document.createElement("ul");
			ul.setAttribute("id", "searchResults");
			ul.style.position = "absolute";
			ul.style.overflow = "hidden";
			ul.style.overflowY = "auto";
			ul.style.zIndex = "999";
			ul.style.listStyle = "none";
			ul.style.margin = "0px";
			ul.style.marginLeft = "112.58px";
			ul.style.padding = "0px";
			ul.style.background = "rgba(0,0,0,0.25)";	
			ul.style.width = "224px";
			ul.style.height = "300px";

			const postParameters = {id: videoId, word: $("#searchBar").val(), start: convertTime($("#searchTimeInput1").val()), end: convertTime($("#searchTimeInput2").val())};

			$.post("/searchTranscript", postParameters, responseJSON => {
				const responseObject = JSON.parse(responseJSON);
				if(responseObject.length === 0){
					alert("Keyword does not appear in this video");
				}else{
					for(let i = 0; i < responseObject.length; i+=2){
						let text = convertSeconds(responseObject[i]) + " \"" + responseObject[i+1] + "\"";
						let li = document.createElement('li');
						li.setAttribute('class', 'searchItem');
						li.style.margin = "0px";
						li.style.padding = "0px";
						li.style.color = "white";
						li.style.border = "2px solid #000000";
						if(i === 0){
							li.style.borderTop = "none";
						}
						if(i+1 !== responseObject.length - 1){
							li.style.borderBottom = "none";
						}
						li.style.fontSize = "15px";
						li.style.display = "block";
						ul.appendChild(li);
						li.innerHTML = li.innerHTML + text;
						li.onclick = function(){
							player.seekTo(parseFloat(responseObject[i]));
							item.removeChild(ul);
						};
					}
					item.appendChild(ul);	
				}
			});
		}else{
			alert("please check your input!");
		}
				
	};
}

//============================================================================
//Below are code for controls sideContentDiv (e.x. click on different tabs)

function noteClick(){
	hideContent();
	expanded = -1;
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].onclick = null;
	}
	$("#question0").html(description);
}

//view related video
function relClick(){
//	hideContent();
	questionSel = false;
	expanded = -1;
	hideTimeandUser();
	if(document.getElementById('questionsList') !== null){
		document.getElementById('questionsList').style.display = "none";
	}
	let divs = document.getElementsByClassName("questionDiv");
	if(relVideo.length === 0){
		hideContent();
		$("#question0").html("No suggested video at this time.");
	}
	
	for(let i = 0; i < relVideo.length; i++){
		divs[i].style.display = "block";
		divs[i].onclick = null;	
		let vid = relVideo[i];
		let relVideoPic = document.createElement("IMG");
		let videoId = "relVideo" + i;
		if(document.getElementById(videoId) === null){
			relVideoPic.setAttribute('id',videoId);
			relVideoPic.setAttribute('src', 'https://img.youtube.com/vi/'+vid.linkId +'/0.jpg');
			relVideoPic.setAttribute('href', "http://localhost:4567/video/" + vid.id);
			relVideoPic.style.width = "65px";
			relVideoPic.style.height = "55px";
			relVideoPic.onclick = function() { 
				window.open("http://localhost:4567/video/" + vid.id, '_blank');
			};
			// console.log("linkId: " + vid.linkId + " title " + vid.title);
			$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + vid.linkId + "&key=AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
				let temp = "question" + i;
				let currQuestion = document.getElementById(temp);
				// relVideo.innerHTML = data.items[0].snippet.title;
				currQuestion.innerHTML = data.items[0].snippet.title;
				currQuestion.parentNode.insertBefore(relVideoPic, currQuestion);
			});	
		}
		
	}
	for(let j = 4; j >= relVideo.length; j--){
		let questionId = "#question" + j;
		let timeId = "#time" + j;
		let userId = "#user" + j;
		let idLabel = "#questionId" + j;
		$(questionId).html("");
		$(timeId).html("");
		$(userId).html("");
		$(idLabel).html("");
	}
}

function hideTimeandUser(){
	document.getElementById('responseList').style.height = "0px";
	document.getElementById('responseList').style.display = "none";
	let divs = document.getElementsByClassName("questionTimeLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs = document.getElementsByClassName("userLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs = document.getElementsByClassName("upvotes");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs = document.getElementsByClassName("upvoteLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
}

//view all questions
function allClick(){
	questionSel = false;
	expanded = -1;
	deleteThumbNails();
	document.getElementById('responseList').style.height = "0px";
	document.getElementById('responseList').style.display = "none";
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	let ul = null;
	if(document.getElementById('questionsList') === null){
		ul = document.createElement('ul');
		ul.setAttribute('id','questionsList');
		ul.style.listStyleType = "none";
		ul.style.lineHeight = "30px";
		ul.style.marginTop = "-5px";
		ul.style.paddingRight = "20px";
		document.getElementById("sideContentDiv").appendChild(ul);
		
	}else{
		ul = document.getElementById('questionsList');
		while (ul.firstChild) {
			ul.removeChild(ul.firstChild);
		}
		ul.style.display = "block";
	}
	for(let i = 0; i < questionsOrd.length; i++){
			let curr = questionsOrd[i];
			let text = convertSeconds(curr.time) + " " + curr.summary + " user: " + curr.user;
			let li = document.createElement('li');
			li.setAttribute('class', 'item');
			li.style.color = 'white';
			li.style.borderBottom = "2px solid #FFFFFF";
			li.style.fontSize = "15px";
			ul.appendChild(li);
			li.innerHTML = li.innerHTML + text;
			li.onclick = function(){
				player.seekTo(parseFloat(curr.time));
				$("#questionBtn").click();
			};
	}
}

function relQuestionClick(){
	questionSel = false;
	expanded = -1;
	deleteThumbNails();
	document.getElementById('responseList').style.height = "0px";
	document.getElementById('responseList').style.display = "none";
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	let ul = null;
	if(document.getElementById('questionsList') === null){
		ul = document.createElement('ul');
		ul.setAttribute('id','questionsList');
		ul.style.listStyleType = "none";
		ul.style.lineHeight = "30px";
		ul.style.marginTop = "-5px";
		ul.style.paddingRight = "20px";
		document.getElementById("sideContentDiv").appendChild(ul);
		
	}else{
		ul = document.getElementById('questionsList');
		while (ul.firstChild) {
			ul.removeChild(ul.firstChild);
		}
		ul.style.display = "block";
	}
	
	const postParameters = {id: parseInt(videoId), input:$("#summaryInput").val()};
	console.log(postParameters);
	$.post("/relatedStudent", postParameters, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		console.log(responseObject);
		for (let i = 0; i < responseObject.length; ++i) {
			let question = responseObject[i];
			let text = convertSeconds(question.time) + " " + question.summary + " " + " User: " + question.user;
			console.log(text);
			let li = document.createElement('li');
			li.setAttribute('class', 'item');
			li.style.color = 'white';
			li.style.borderBottom = "2px solid #FFFFFF";
			li.style.fontSize = "15px";
			ul.appendChild(li);
			li.innerHTML = li.innerHTML + text;
			li.onclick = function(){
				player.seekTo(parseFloat(question.time));
				$("#questionBtn").click();
			};
		}
	});		
}

function renderList(text, ul){
	let li = document.createElement('li');
	li.setAttribute('class', 'item');
	li.style.color = 'white';
	li.style.borderBottom = "2px solid #FFFFFF";
	li.style.fontSize = "15px";
	ul.appendChild(li);
	li.innerHTML = li.innerHTML + text;
}



//============================================================================
//Below are code for automatic question display (e.x. click on different tabs)


function questionClick(){
	questionSel = true;
	expanded = -1;
	deleteThumbNails();
	document.getElementById('responseList').style.height = "0px";
	document.getElementById('responseList').style.display = "none";
	if(document.getElementById('questionsList') !== null){
		document.getElementById('questionsList').style.display = "none";
	}
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		divs[i].onclick = openQuestion;
	}
	let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "",false, 0,"","");
	let index = questionsOrd.binarySearch(questionStub, compare);
//	console.log("INDEX: " + index);
	questionDisplay(index);
}


function refQuestion(){
	let index = 0;
	if(player.getPlayerState() === 1){
		let questionStub = new Question("", "", Math.floor(player.getCurrentTime()), "", "", false, 0,"","");
		index = questionsOrd.binarySearch(questionStub, compare);
//		console.log("INDEX: " + index);
		if(questionSel && expanded === -1 && index !== null){
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
	divs[0].style.borderBottom = "none";
	divs = document.getElementsByClassName("questionTimeLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "inline-block";
	}
	divs = document.getElementsByClassName("userLabel");
	for(let i = 0; i < divs.length; i++){
		divs[i].style.display = "block";
	}
	
	divs = document.getElementsByClassName("upvoteLabel");
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
	let i;
	for(i = 0; i <= range; i++){
		let questionId = "#question" + i;
		let timeId = "#time" + i;
		let userId = "#user" + i;
		let upvoteId = "#upvoteLabel" + i;
		let idLabel = "#questionId" + i;
		$(questionId).html(questionsOrd[min].summary);
		let time = convertSeconds(parseInt(questionsOrd[min].time));
		$(timeId).html(time);
		$(userId).html("User: " + questionsOrd[min].user);
		$(upvoteId).html(questionsOrd[min].upvotes + " Upvotes");
		$(idLabel).html(questionsOrd[min].id);
		document.getElementById("upvote" + i).style.display = "inline-block";
		min+=1;
		document.getElementById("upvote" + i).onclick = function(){
			let jsonObject = {upvoteType: 0, id:parseInt($(idLabel).html())};
			console.log("upvote websocket set " + parseInt($(idLabel).html()));
			conn.send(JSON.stringify({type: 3, payload: jsonObject}));
		};
	}
	for(let j = 4; j >= i; j--){
		let questionId = "#question" + j;
		let timeId = "#time" + j;
		let userId = "#user" + j;
		let upvoteId = "#upvoteLabel" + j;
		let idLabel = "#questionId" + j;
		$(questionId).html("");
		$(timeId).html("");
		$(userId).html("");
		$(upvoteId).html("");
		$(idLabel).html("");
		document.getElementById("upvote" + j).style.display = "none";
	}
	
}

//============================================================================
//Below are code for display a particular question
function openQuestion(ev){
	if(ev.target.className !== "upvotes"){
		var id = this.id.substring(this.id.length-1);
		let idLabel = "#questionId" + id;
		expanded = parseInt($(idLabel).html());
		document.getElementById("questionDiv0").style.borderBottom = "2px solid #FFFFFF";
		let divs = document.getElementsByClassName("questionDiv");
		divs[0].onclick = null;
		let questionId = "#question" + id;
		let timeId = "#time" + id;
		let userId = "#user" + id;
		let upvoteId = "#upvoteLabel" + id;

		$("#question0").html($(questionId).html());
		$("#time0").html($(timeId).html());
		$("#user0").html($(userId).html());
		$("#upvoteLabel0").html($(upvoteId).html());
		$("#questionId0").html(expanded);

		let div = document.getElementById("responseList");

		div.style.height = $('#questionDiv0').height()*4+"px";
		div.style.overflow = 'hidden';
		div.style.overflowY = 'scroll';
		div.style.display = 'block';
		div.style.color = 'white';

		while (div.firstChild) {
				div.removeChild(div.firstChild);
		}

		let p = document.createElement("p");
		p.setAttribute('id', 'detail');
		p.style.textAlign = "left";
		p.style.display = "block";
		p.style.paddingLeft = "10px";
		p.style.paddingRight = "15px";
		p.style.paddingBottom = "10px";
		p.style.color = "white";
		p.style.borderBottom = "2px solid #FFFFFF";
		let detail = questions.get(parseInt(expanded)).detail;
		p.innerHTML = "Question detail: " + detail;
		div.appendChild(p);

		let instructorAns = questions.get(parseInt(expanded)).instructorAnswer;
		let studentAns = questions.get(parseInt(expanded)).studentAnswer;

		let ul = document.createElement('ul');
		ul.setAttribute('id','remarksList');
		ul.style.listStyleType = "none";
		ul.style.lineHeight = "30px";
		ul.style.marginTop = "-5px";
		ul.style.paddingRight = "15px";
		ul.style.textAlign = "right";
		div.appendChild(ul);

		if(instructorAns){
			let id = instructorAns.id;
	//			let questionId = response.questionId;
			let detail = instructorAns.detail;
			let userId = instructorAns.userId;
			let postDate = instructorAns.postDate;
			let postTime = instructorAns.postTime;
			let text = 'Instructor Answer (id ' + id + ')' + postDate + " " + postTime + ':' + "<br>" + detail + "<br>" + '	Instructor: ' + userId + "<br>";
			let li = document.createElement('li');
			li.setAttribute('class', 'item');
			li.style.color = 'white';
			li.style.borderBottom = "2px solid #FFFFFF";
			li.style.fontWeight = "bold";
			li.style.fontSize = "16px";
			ul.appendChild(li);
			li.innerHTML = li.innerHTML + text;
		}

		if(studentAns){
			let id = studentAns.id;
	//			let questionId = response.questionId;
			let detail = studentAns.detail;
			let userId = studentAns.userId;
			let postDate = studentAns.postDate;
			let postTime = studentAns.postTime;
			let text = 'Student Answer (id ' + id + ')' + postDate + " " + postTime + ':' + "<br>" + detail + "<br>" + '	Student: ' + userId + "<br>";
			let li = document.createElement('li');
			li.setAttribute('class', 'item');
			li.style.color = 'white';
			li.style.borderBottom = "2px solid #FFFFFF";
			li.style.fontSize = "15px";
			ul.appendChild(li);
			li.innerHTML = li.innerHTML + text;
		}

		const postParameters = {id: expanded};
		$.post("/response", postParameters, responseJSON => {
			const responseObject = JSON.parse(responseJSON);
			for (let i = 0; i < responseObject.length; ++i) {
				let response = responseObject[i];
				let id = response.id;
	//			let questionId = response.questionId;
				let detail = response.detail;
				let userId = response.userId;
				let postDate = response.postDate;
				let postTime = response.postTime;
				let upvotes = response.upvotes;
				let text = 'response (id ' + id + ')' + postDate + " " + postTime + ':' + "<br>" + detail + "<br>" + '	user: ' + userId;
				renderList(text,ul);
			}
			player.seekTo(parseFloat(convertTime($("#time0").html())));
		});		
	}
	
}

//============================================================================
//Below are code for action when post button is clicked
// Setup the WebSocket connection for live updating of scores.

function postClick(){
	let summary = $("#summaryInput").val();
	let time = $("#timeInput").val();
	let detail = $("#detailInput").val();
	console.log(time);
	if(summary === ""){
		alert("Please input a summary!");
		return;
	}
	if(!isValidTime(time)){
		alert("Please input a correct time!");
		return;
	}
	if(detail === ""){
		alert("Please put in some explanation for your question!");
		return;
	}
	
	if (confirm("Are you sure you don't want to see similar question?")) {
		let jsonObject = {videoId: videoId, summary:summary, questionTimestamp:time, detail:detail};
	
		conn.send(JSON.stringify({type: 1, payload: jsonObject}));

		$("#summaryInput").val("");
		$("#timeInput").val("");
		$("#detailInput").val("");
	}
	
}

// check if the user input time is valid.
function isValidTime(time){
	let timeArray = time.split(":");
	if(timeArray.length !== 3){
		console.log("1");
		return false;
	}
	if(isNaN(timeArray[0])||isNaN(timeArray[1])||isNaN(timeArray[2])){
				console.log("2");

		return false;
	}
	if(parseInt(timeArray[1]) >= 60 || parseInt(timeArray[2]) >= 60){
				console.log("3");

		return false;
	}
	let sec = parseInt(timeArray[0])*3600 + parseInt(timeArray[1])*60 + parseInt(timeArray[2]);
	if(sec > Math.floor(parseInt(duration))){
		return false;
	}
	return true;
}

//============================================================================
//Below is method to submit answer to a question to backend
function answerSubmit(){
	let questionId = expanded;
	let detail = $("#answerInput").val();
	if(questionId === -1){
		alert("Please select a question before submitting your answer");
		return;
	}
	if(detail === ""){
		alert("Please write something down for your answer");
		return;
	}
	
	let answerType;
	if(document.getElementById('followUp').checked){
		answerType = 0;
		document.getElementById('studentAnswer').checked = false;
	}else if(document.getElementById('studentAnswer').checked){
		answerType = 1;
		document.getElementById('followUp').checked = true;
	}
	
	if (confirm("Are you sure you want to post this message?")) {
		let jsonObject = {questionId: questionId, detail:detail, answerType: answerType};
	
		conn.send(JSON.stringify({type: 2, payload: jsonObject}));

		$("#answerInput").val("");
	}
	
	
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
	if(duration >= 3600){
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

//Convert seconds to "HH:MM:SS" format
function convertTime(time){
	let timeArr = time.split(":");
	let seconds = 0;
	if(duration >= 3600){
		seconds = parseFloat(timeArr[0])*3600 + parseFloat(timeArr[1])*60 + parseFloat(timeArr[2]);
	}else{
		if(timeArr.length === 3){
			seconds = parseFloat(timeArr[1])*60 + parseFloat(timeArr[2]);
		}else{
			seconds = parseFloat(timeArr[0])*60 + parseFloat(timeArr[1]);
		}
		
	}
	return seconds;
}

function hideContent(){
	questionSel = false;
	document.getElementById("time0").style.display = "none";
	document.getElementById("user0").style.display = "none";
	document.getElementById("upvote0").style.display = "none";
	document.getElementById("upvoteLabel0").style.display = "none";
	document.getElementById('responseList').style.height = "0px";
	document.getElementById('responseList').style.display = "none";
	if(document.getElementById('questionsList') !== null){
		document.getElementById('questionsList').style.display = "none";
	}
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 1; i < divs.length; i++){
		divs[i].style.display = "none";
	}
	divs[0].style.border = "none";
	divs[0].style.display = "block";
	deleteThumbNails();
}

function deleteThumbNails(){
	let divs = document.getElementsByClassName("questionDiv");
	for(let i = 0; i < divs.length; i++){
		let relId = "relVideo" + i;
		if(document.getElementById(relId) !== null){
			let divId = "questionDiv" + i;
			document.getElementById(divId).removeChild(document.getElementById(relId));
		}
	}
}

