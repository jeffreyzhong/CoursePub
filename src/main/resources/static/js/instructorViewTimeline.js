$(document).ready(function() {
	let container = document.getElementById('visualization');
	let instructorResponse = document.getElementById("instructorResponse");
	let videoId = parseInt($('#videoId').html());
	let windowlocation = window.location.href.split('/')[2];
	let ws = "ws://"+windowlocation+"/websocket/"+videoId;
	let conn = new WebSocket(ws);
	let addData = [];
	let instructorSubmit = document.getElementById("submitResponseButton");
	let videoThumbnail = document.getElementById("videoThumbnail");
	let displayThread = document.getElementById("displayThread");
	let videoName = document.getElementById("videoName");
	let timeline;
	let linkId;
	let questionToSimilarity = {};
	let ctrlCount = 0;
	$.post("/setup",{id:videoId},responseJSON => {
		let responseObject = JSON.parse(responseJSON);
		document.getElementById("videoFrame").setAttribute("src",responseObject[0]);
		let link = document.getElementById("videoFrame").src;
		linkId = extractVideoIdFromYouTubeUrl(link);
		let url = 'https://img.youtube.com/vi/'+linkId+'/0.jpg';

		videoThumbnail.src = url;
		videoThumbnail.style.width = "50px";
		videoThumbnail.style.height = "50px";
		$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + linkId + "&key=" + "AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
			videoName.innerHTML = data.items[0].snippet.title;
			//alert(data.items[0].snippet.title);
		});
	});
	
$("#threadResponse").keyup(function(event) {
	if (event.which === 13) {
		let response = $("#threadResponse").val();
		//let update = items._data[questionId];
		let payload = {questionId : parseInt(questionId), detail : response};
		let toSend = JSON.stringify({type : MESSAGE_TYPE.NEW_RESPONSE, payload : payload});
		if (confirm("Are you sure you want to send this follow-up?")){
			conn.send(toSend);
		} 
		document.getElementById("threadResponse").value = "";
	}	
});
		let questionId = null;
		let mouseoverId;
	
	const MESSAGE_TYPE = {
		ERROR: -1,
	  	CONNECT: 0,
		NEW_QUESTION: 1,
		NEW_RESPONSE: 2,
		UPVOTE: 3,
		INSTRUCTOR_ANSWER : 4,
		STUDENT_ANSWER: 5,
	};
	conn.addEventListener('message', function (event) {
		let data = JSON.parse(event.data);
		let newMessage = data.payload;
		let questionId = newMessage.questionId;
		let toUpdate = items._data[questionId];
		switch (data.type) {
			default: 
				break;
			case MESSAGE_TYPE.NEW_QUESTION:
				
				let formattedTime = moment().startOf('day').seconds(newMessage.time).format('H,mm,ss');
				let colonTime = moment().startOf('day').seconds(newMessage.time).format('H:mm:ss');
				let timeArray = formattedTime.split(",");
				let currQuestion = {id: newMessage.id,
					content : setContent(newMessage.resolved,newMessage.upvotes,newMessage.user,colonTime,'1.0'),
					summary : newMessage.summary,
					colonTime : colonTime,
					start : new Date(0,0,0,parseInt(timeArray[0]),parseInt(timeArray[1]),parseInt(timeArray[2]),0),
					thread : [],
					fullQuestion : newMessage.detail,
					instructorAnswer : newMessage.instructorAnswer,
					studentAnswer : newMessage.studentAnswer,
					numVotes : newMessage.upvotes,
					resolved : newMessage.resolved,
					user : "Anon "+newMessage.user};
				addData.push(currQuestion);
				timeline.setItems(addData);	
				items = new vis.DataSet(addData);
				
				break;
			case MESSAGE_TYPE.NEW_RESPONSE:
				toUpdate.thread.push(newMessage);
				if (displayThread.innerHTML === "No follow ups currently in this question thread!") {
					displayThread.innerHTML = "Anon " + newMessage.userId + ": "+ newMessage.detail +"<br>";
					//	"    "+newMessage.upvotes+"UP <br>"
				} else {
					displayThread.innerHTML += "Anon " + newMessage.userId + ": "+ newMessage.detail +"<br>";
						//"    "+newMessage.upvotes+"UP <br>";
				}
				break;
			case MESSAGE_TYPE.INSTRUCTOR_ANSWER:
				let detail = [];
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				let tmpStr = "";
				instructorAnswer.innerHTML = items._data[questionId].instructorAnswer.detail;

				break
			case MESSAGE_TYPE.STUDENT_ANSWER:
				toUpdate.studentAnswer = {detail:newMessage.detail};
				toUpdate.content = setContent(1,toUpdate.numVotes,toUpdate.user,toUpdate.colonTime,'1.0');
				toUpdate.resolved = 1;
				timeline.setItems(items);
				break;
			
			case MESSAGE_TYPE.UPVOTE:
				let update = items._data[newMessage.id];
				update.numVotes = update.numVotes + newMessage.num;
				update.content = setContent(update.resolved,update.numVotes,update.user,update.colonTime,'1.0');
				timeline.setItems(items);
				break;
			case MESSAGE_TYPE.ERROR:
				alert("There was a websocket error!: Check console.");
				console.log("Message from Server: " + data);
				break;
		}
	});	
	
	
	
	
	let postParameters = {id:videoId};
	$.post("/question", postParameters, responseJSON => {
		let responseObject = JSON.parse(responseJSON);
		allData = [];
		for (i = 0; i < responseObject.length; i++) {
			let question = responseObject[i];
			let id = question['id'];
			let time = question['time'];
			let summary = question['summary'];
			let user = question['user'];
			let resolved = question['resolved'];
			let detail = question['detail'];
			let numUpVotes = question['upvotes'];
			let instructorAnswer = question['instructorAnswer'];
			let studentAnswer = question['studentAnswer'];
			let formattedTime = moment().startOf('day').seconds(time).format('H,mm,ss');
			let colonTime = moment().startOf('day').seconds(time).format('H:mm:ss');
			let timeArray = formattedTime.split(",");
			let currQuestion = {id: id,
			content : setContent(resolved,numUpVotes,user,colonTime,'1.0'),
			resolved : resolved,
			numVotes : numUpVotes,
			thread : [],
			summary : summary,
			colonTime : colonTime,
			start : new Date(0,0,0,parseInt(timeArray[0]),parseInt(timeArray[1]),parseInt(timeArray[2]),0),
			fullQuestion : detail,
			instructorAnswer : instructorAnswer,
			studentAnswer : studentAnswer,
			user : "Anon "+user};
			addData.push(currQuestion);
		}
		createDataSet();
		createTimeline();
		
	});
	instructorResponse.addEventListener('keydown', autosize);
	window.addEventListener('resize', resizeSummary);
	
	function resizeSummary() {
		let el = this;
		setTimeout(function() {
			document.getElementById("displaySummary").style.cssText = 'height:; padding:0';
			document.getElementById("displaySummary").style.cssText = 'height:' + el.scrollHeight + 'px';
		},0);
	}

	function autosize(){
	  let el = this;
	  setTimeout(function(){
		el.style.cssText = 'height:auto; padding:0';
		el.style.cssText = 'height:' + el.scrollHeight + 'px';
	  },0);
	}
	
	instructorSubmit.addEventListener('click', sendInstructorResponse);
	
	function sendInstructorResponse() {
		let answer = instructorResponse.value;
		if (answer !== "") {
			if (confirm("Are you sure you want to post this response?")) {
				if (questionId) {
				let payload = {questionId : parseInt(questionId), detail : answer};
				let toSend = JSON.stringify({type : MESSAGE_TYPE.INSTRUCTOR_ANSWER, payload : payload});
				conn.send(toSend);
				instructorResponse.value = "";
				let toUpdate = items._data[questionId];
				toUpdate.content = setContent(2,toUpdate.numVotes,toUpdate.user,toUpdate.colonTime,'1.0');
				toUpdate.resolved = 2;
				toUpdate.instructorAnswer = {detail: answer};
				//toUpdate.responses = detail;
				timeline.setItems(items);
				} else {
					alert("Not sure which question you are tryng to answer. Please select a question in the timeline to answer.");
				}
			} else {
				instructorResponse.value = "";
			}
			

		}

	}
	
	let items;
	function createDataSet() {
	  	items = new vis.DataSet(addData);
	}
	
	function parseDuration(duration) {
		let matches = duration.match(/[0-9]+[HMS]/g);

		let seconds = 0;

		matches.forEach(function (part) {
			let unit = part.charAt(part.length-1);
			let amount = parseInt(part.slice(0,-1));

			switch (unit) {
				case 'H':
					seconds += amount*60*60;
					break;
				case 'M':
					seconds += amount*60;
					break;
				case 'S':
					seconds += amount;
					break;
				default:
			}
		});
		let formattedTime = moment().startOf('day').seconds(seconds).format('H,mm,ss');
		return formattedTime;
	}
	
	function extractVideoIdFromYouTubeUrl (url) {

    	let stepOne = url.split('?')[0];
    	let stepTwo = stepOne.split('/');
    	let videoId = stepTwo[stepTwo.length-1];

    return videoId;

	}
	
	function createTimeline() {
		let request = new XMLHttpRequest();
		let link = document.getElementById("videoFrame").src;
		let linkId = extractVideoIdFromYouTubeUrl(link);
    	let url = 'https://www.googleapis.com/youtube/v3/videos?id='+linkId+'&part=contentDetails&key=AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0';
    	request.onreadystatechange = function() {
      		if (this.readyState === 4 && this.status === 200) {
        		let response = JSON.parse(this.responseText);
        		getElements(response);
			}
    	};

		request.open("GET", url, true);
		request.send();
		getElements = function(response) {
			if (!response['items']['0']) {
				window.location.reload();
			}
			let duration = response['items']['0']['contentDetails']['duration'];
			let videoEndTime = parseDuration(duration).split(",");
			let step;
			if (parseInt(videoEndTime[1]) > 50) {
				step = 300;
			} else if (parseInt(videoEndTime[1]) > 40) {
				step = 240;
			} else if (parseInt(videoEndTime[2]) > 30) {
				step = 180;
			} else if (parseInt(videoEndTime) > 20) {
				step = 120;
			} else if (parseInt(videoEndTime) > 10 || parseInt(videoEndTime) < 10) {
				step = 60;
			}
			let options = {
				width: '100%',
				height: '380px',
				align: 'center',
				timeAxis: {scale: 'second', step: step}
			};
			options.zoomMax = parseInt(videoEndTime[0])*60*60*1000+parseInt(videoEndTime[1])*60*1000+parseInt(videoEndTime[2])*1000;
			options.zoomMin = 1000;
			options.min = new Date(0,0,0,0,0,0,0);
			options.max = new Date(0,0,0,parseInt(videoEndTime[0]),parseInt(videoEndTime[1]),parseInt(videoEndTime[2]),0);

			options.format = {
				minorLabels: {
					millisecond:'',
					second:     'mm:ss',
					minute:     'mm:ss',
					hour:       'HH:mm:ss',
					weekday:    '',
					day:        '',
					week:       '',
					month:      '',
					year:       ''
				},
				majorLabels: {
					millisecond:'',
					second:     'mm:ss',
					minute:     'mm:ss',
					hour:       'HH:mm:ss',
					weekday:    '',
					day:        '',
					week:       '',
					month:      '',
					year:       ''
				}};
			timeline = new vis.Timeline(container, items, options);
			timeline.fit(options);
			timeline.on('click', function (properties) {
				console.log(properties);
				questionId = properties.item;
				let summary = document.getElementById("displaySummary");
				let question = document.getElementById("displayQuestion");
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				let studentAnswer = document.getElementById("displayStudentAnswer");
				let thread = document.getElementById("displayThread");
				let info = items._data[questionId];
				thread.innerHTML = "";
				if (questionId) {
					summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					question.innerHTML = info.fullQuestion;
					let postP = {id:questionId};
					let tmpStr = "";
					$.post("/response",postP,responseJSON => {
					let responseObject = JSON.parse(responseJSON);
					if (responseObject.length > 0) {
						if (responseObject.length > info.thread.length) {
							for (let i = 0; i < responseObject.length; i++) {
								let currThread = responseObject[i];
								info.thread.push(currThread);
							}
						timeline.setItems(items);
							
						}
						for (let i = 0; i < info.thread.length; i++) {

							thread.innerHTML += "Anon " + info.thread[i].userId + ": " + info.thread[i].detail+"<br>";
						}
					} else {
						thread.innerHTML = "No follow ups currently in this question thread!";
					}
				});
					if (info.instructorAnswer) {
							let res = info.instructorAnswer.detail;
							instructorAnswer.innerHTML = res;

					} else {
							instructorAnswer.innerHTML = "No instructor answer yet! :(";
					}
					if (info.studentAnswer) {
						let res = info.studentAnswer.detail;
						studentAnswer.innerHTML = res;
					} else {
						studentAnswer.innerHTML = "No student answer yet! :(";
					}
					
			info.content = setContent(info.resolved,info.numVotes,info.user,info.colonTime,'1.0');	
			if (!questionToSimilarity[questionId]) {
				$.post("/relatedInstructor",{id:questionId},responseJSON => {
						let responseObject = JSON.parse(responseJSON);
						for (let i = 0; i < responseObject.length; i++) {
							let currQuest = responseObject[i];
							let currQuestId = currQuest['id'];
							let currQuestSimilarity = currQuest['similarity'];
							let dataSetQuest = items._data[currQuestId];
							dataSetQuest.content = setContent(dataSetQuest.resolved,dataSetQuest.numVotes,dataSetQuest.user,dataSetQuest.colonTime,currQuestSimilarity);
							if (!questionToSimilarity[questionId]) {
								questionToSimilarity[questionId] = [{id:currQuestId,similarity:currQuestSimilarity}];
							} else {
								questionToSimilarity[questionId].push({id:currQuestId,similarity:currQuestSimilarity});
							}


						}
						console.log("1");
						timeline.setItems(items);
						timeline.setSelection(properties.item);
					});
				} else {
					for (let i = 0; i < questionToSimilarity[questionId].length; i++) {
						let currQuest = questionToSimilarity[questionId][i];
						let currQuestId = currQuest['id'];
						let currQuestSimilarity = currQuest['similarity'];
						let dataSetQuest = items._data[currQuestId];
						dataSetQuest.content = setContent(dataSetQuest.resolved,dataSetQuest.numVotes,dataSetQuest.user,dataSetQuest.colonTime,currQuestSimilarity);
					}
					console.log("2");
					timeline.setItems(items);
					timeline.setSelection(properties.item);
				}
				} else {
					summary.innerHTML = " ";
					question.innerHTML = " ";
					instructorAnswer.innerHTML = " ";
					studentAnswer.innerHTML = " ";
					thread.innerHTML = " ";
							for (let i = 0; i < addData.length; i++) {
						let currQuest = addData[i];
						let currQuestId = currQuest['id'];
						let dataSetQuest = items._data[currQuestId];
						dataSetQuest.content = setContent(dataSetQuest.resolved,dataSetQuest.numVotes,dataSetQuest.user,dataSetQuest.colonTime,'1.0');
					}
					console.log("items: " + items);
					timeline.setItems(items);
					//timeline.setItems(items);
				}
				
				//timeline.setItems(items);
			});
			
			timeline.on('mouseOver', function (properties) {
				mouseoverId = properties.item;
				let summary = document.getElementById("displaySummary");
				let question = document.getElementById("displayQuestion");
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				if (mouseoverId != null && timeline.getSelection().length === 0) {
					let info = items._data[mouseoverId];

					let sum = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					if (sum !== summary.innerHTML) {
						summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
						question.innerHTML = info.fullQuestion;
						if (info.instructorAnswer) {
								let res = info.instructorAnswer.detail;
								let currStr = "";
								instructorAnswer.innerHTML = res;
						} else {
								instructorAnswer.innerHTML = "No instructor answer yet! :(";
							}
					}
				}
			});
		};	
	}
	
	
	function setContent(answered,numUpVotes,user,colonTime,opacity) {
		let color = "";
		if (answered === 0) {
			color = "red";
		} else if (answered === 1) {
			color="yellow";
		} else if (answered === 2) {
			color="green";
		}
		let padding = 4 + numUpVotes*3;
		if (color === "red" || color === "green") {
			return '<div style="background-color:'+color+'; color:white; ' + 
			'padding-top:'+padding+'px; padding-left:'+padding+'px; padding-right:'+padding+'px;' +
			'padding-bottom:'+padding+'px; border-radius: 20px; opacity:'+opacity+';">'+numUpVotes + 'UP' + ' @ ' + colonTime +'</div>';
		} else {
			return '<div style="background-color:'+color+'; color:black; ' + 
			'padding-top:'+padding+'px; padding-left:'+padding+'px; padding-right:'+padding+'px;' +
			'padding-bottom:'+padding+'px; border-radius: 20px; opacity:'+opacity+';">'+numUpVotes + 'UP' + ' @ ' + colonTime +'</div>';
		}
	}

});