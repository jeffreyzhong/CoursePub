$(document).ready(function() {
	let container = document.getElementById('visualization');
	let instructorResponse = document.getElementById("instructorResponse");
	let videoId = parseInt($('#videoId').html());
	let conn = new WebSocket("ws://localhost:4567/websocket/"+videoId);
	let addData = [];
	let instructorSubmit = document.getElementById("submitResponseButton");
	let videoThumbnail = document.getElementById("videoThumbnail");
	let videoName = document.getElementById("videoName");
	let timeline;
	let linkId;
	$.post("/setup",{id:videoId},responseJSON => {
		let responseObject = JSON.parse(responseJSON);
		console.log("LINK: " + responseObject);
		document.getElementById("videoFrame").setAttribute("src",responseObject[0]);
		console.log("IFRAME: " + document.getElementById("videoFrame").src);
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
		let questionId;
		let mouseoverId;
	
	const MESSAGE_TYPE = {
	  	CONNECT: 0,
		NEW_QUESTION: 1,
		NEW_RESPONSE: 2,
		UPVOTE: 3,
		INSTRUCTOR_ANSWER : 4,
		ERROR: 5
	};
	conn.addEventListener('message', function (event) {
		let data = JSON.parse(event.data);
		console.log('Message from server: ', data);
		let newQuestion = data.payload;
		switch (data.type) {
			default: 
				console.log("Unknown data type");
				break;
			case MESSAGE_TYPE.NEW_QUESTION:
				
				let formattedTime = moment().startOf('day').seconds(newQuestion.time).format('H,mm,ss');
				let colonTime = moment().startOf('day').seconds(newQuestion.time).format('H:mm:ss');
				//console.log("formattedTime: " + formattedTime);
				let timeArray = formattedTime.split(",");
				let currQuestion = {id: newQuestion.id,
					content : setContent(newQuestion.resolved,newQuestion.upvotes,newQuestion.user,colonTime),
					summary : newQuestion.summary,
					colonTime : colonTime,
					start : new Date(0,0,0,parseInt(timeArray[0]),parseInt(timeArray[1]),parseInt(timeArray[2]),0),
					fullQuestion : newQuestion.detail,
					user : newQuestion.user};
				console.log("currQuestion: " + currQuestion);
				addData.push(currQuestion);
				timeline.setItems(addData);	
				console.log("added");
				items = new vis.DataSet(addData);
				
				break;
			case MESSAGE_TYPE.NEW_ANSWER:
				//alert("success");

				toUpdate.content = setContent(2,toUpdate.numVotes,toUpdate.user,toUpdate.colonTime);
				//toUpdate.responses = detail;
				console.log("ITEMS AFTER: " + items);
				timeline.setItems(items);
				break;
			case MESSAGE_TYPE.UPVOTE: 
				
				break;
			case MESSAGE_TYPE.INSTRUCTOR_ANSWER:
				let questionId = newQuestion.questionId;
				let toUpdate = items._data[questionId];
				console.log("ITEMS BEFORE: " + items);
				let detail = [];
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				let tmpStr = "";
				instructorAnswer.innerHTML = items._data[questionId].instructorAnswer.detail;

				break
			case MESSAGE_TYPE.ERROR:
				alert("error");
				break;
		}
	});	
	
	
	
	
	let postParameters = {id:videoId};
	$.post("/question", postParameters, responseJSON => {
		console.log("here");
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
			let instructorAnswer = question['instructorAnswer'];
			let studentAnswer = question['studentAnswer'];
			console.log(question);
			let formattedTime = moment().startOf('day').seconds(time).format('H,mm,ss');
			let colonTime = moment().startOf('day').seconds(time).format('H:mm:ss');
			console.log("formattedTime: " + formattedTime);
			let timeArray = formattedTime.split(",");
			console.log("user id: " + user);
			let currQuestion = {id: id,
			content : setContent(resolved,30,user,colonTime),
			resolved : resolved,
			numVotes : 30,
			summary : summary,
			colonTime : colonTime,
			start : new Date(0,0,0,parseInt(timeArray[0]),parseInt(timeArray[1]),parseInt(timeArray[2]),0),
			fullQuestion : detail,
			instructorAnswer : instructorAnswer,
			studentAnswer : studentAnswer,
			user : user};
			addData.push(currQuestion);
			console.log(addData);
		}
		console.log("creating");
		createDataSet();
		createTimeline();
		
	});
	instructorResponse.addEventListener('keydown', autosize);
	window.addEventListener('resize', resizeSummary);
	
	function resizeSummary() {
		let el = this;
		setTimeout(function() {
			console.log("here");
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
			//let postParameters = {instructorResponse:answer};
			//console.log(answer);
//			$.post("/instructorResponse", postParameters, responseJSON => {
//				let responseObject = JSON.parse(responseJSON);
//			});
			console.log(questionId);
			let payload = {questionId : parseInt(questionId), detail : answer};
			let toSend = JSON.stringify({type : MESSAGE_TYPE.INSTRUCTOR_ANSWER, payload : payload});
			console.log(toSend);
			conn.send(toSend);
			instructorResponse.value = "";
			let toUpdate = items._data[questionId];
			toUpdate.content = setContent(2,toUpdate.numVotes,toUpdate.user,toUpdate.colonTime);
			toUpdate.instructorAnswer = {detail: answer};
			console.log(toUpdate.resonse);
			//toUpdate.responses = detail;
			console.log("ITEMS AFTER: " + items);
			timeline.setItems(items);
			

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
		console.log(seconds);
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
		console.log(linkId);
    	let url = 'https://www.googleapis.com/youtube/v3/videos?id='+linkId+'&part=contentDetails&key=AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0';
    	request.onreadystatechange = function() {
      		if (this.readyState === 4 && this.status === 200) {
        		let response = JSON.parse(this.responseText);
				console.log(response);
        		getElements(response);
			}
    	};

		request.open("GET", url, true);
		request.send();
		getElements = function(response) {
			console.log(response);
			if (!response['items']['0']) {
				console.log("reload");
				window.location.reload();
			}
			let duration = response['items']['0']['contentDetails']['duration'];
			let videoEndTime = parseDuration(duration).split(",");
			console.log(videoEndTime);
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
				height: '320px',
				timeAxis: {scale: 'second', step: step}
			};
			console.log(parseInt(videoEndTime[0])*60*60*1000+parseInt(videoEndTime[1])*60*1000+parseInt(videoEndTime[2])*1000);
			options.zoomMax = parseInt(videoEndTime[0])*60*60*1000+parseInt(videoEndTime[1])*60*1000+parseInt(videoEndTime[2])*1000;
			options.zoomMin = 1000;
			options.min = new Date(0,0,0,0,0,0,0);
			console.log(videoEndTime[0] + " " + videoEndTime[1] + " " + videoEndTime[2]);
			console.log(new Date(0,0,0,parseInt(videoEndTime[0]),parseInt(videoEndTime[1]),parseInt(videoEndTime[2]),0));
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
			console.log(items);
			timeline = new vis.Timeline(container, items, options);
			timeline.fit(options);
			console.log("before select");
			timeline.on('select', function (properties) {
				console.log(properties);
				questionId = properties.items[0];
				let summary = document.getElementById("displaySummary");
				let question = document.getElementById("displayQuestion");
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				let studentAnswer = document.getElementById("displayStudentAnswer");
				let thread = document.getElementById("displayThread");
				let info = items._data[questionId];
				if (info) {
					summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					question.innerHTML = info.fullQuestion;
					console.log(info);
					let postP = {id:questionId};
					let tmpStr = "";
					$.post("/response",postP,responseJSON => {
					let responseObject = JSON.parse(responseJSON);
					console.log("length: " + responseObject.length);
					if (responseObject.length > 0) {
						for (let i = 0; i < responseObject.length; i++) {
							console.log("RESPONSE OBJECT: " + responseObject[i]);
							let currThread = responseObject[i];
							tmpStr += currThread['detail']+"<br>";
							//toUpdate.responses.push({detail:response['detail']});
							//detail.push(response['detail']);


						}
					} else {
						thread.innerHTML = "No follow ups currently in this question thread!";
					}
					thread.innerHTML = tmpStr;
				});
					if (info.instructorAnswer) {
						
					//	if (info.responses.length > 0) {
							let res = info.instructorAnswer.detail;
							//let tmpStr = "";
//							for (let i = 0; i < res.length; i++) {
//								tmpStr += res[i].detail+"<br>";
//							}
							instructorAnswer.innerHTML = res;
					//	} 

					} else {
							instructorAnswer.innerHTML = "No instructor answer yet! :(";
					}
					if (info.studentAnswer) {
						let res = info.studentAnswer.detail;
						studentAnswer.innerHTML = res;
					} else {
						studentAnswer.innerHTML = "No student answer yet! :(";
					}
				} else {
					summary.innerHTML = " ";
					question.innerHTML = " ";
					instructorAnswer.innerHTML = " ";
					studentAnswer.innerHTML = " ";
					thread.innerHTML = " ";
					}
				
			});
			
			timeline.on('mouseOver', function (properties) {
				mouseoverId = properties.item;
				//console.log(properties);
				let summary = document.getElementById("displaySummary");
				let question = document.getElementById("displayQuestion");
				let instructorAnswer = document.getElementById("displayInstructorAnswer");
				if (mouseoverId != null && timeline.getSelection().length === 0) {
				//	console.log(timeline.getSelection());
					let info = items._data[mouseoverId];

					let sum = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					if (sum !== summary.innerHTML) {
						summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
						question.innerHTML = info.fullQuestion;
						console.log(info);
						if (info.instructorAnswer) {
							//if (info.responses.length > 0) {
								let res = info.instructorAnswer.detail;
								
								let currStr = "";
								//for (let i = 0; i < res.length; i++) {
								//	currStr += res[i].detail+"<br>";
							//	}
								instructorAnswer.innerHTML = res;
							//}

						} else {
								instructorAnswer.innerHTML = "No instructor answer yet! :(";
							}
					}
				}
//				else {
//					summary.innerHTML = "";
//					question.innerHTML = "";
//					instructorAnswer.innerHTML = "";
//				}
			});

		};	
	}
	
	function setContent(answered, numUpVotes,user,colonTime) {
		let color = "";
		if (answered === 0) {
			color = "red";
		} else if (answered === 1) {
			color="yellow";
		} else if (answered === 2) {
			color="green";
		}
//		if (answered) {
//			color="green";
//		} else {
//			color="red";
//		}
		let padding = 7 + numUpVotes/5;
		if (color === "red" || color === "green") {
			return '<div style="background-color:'+color+'; color:white; ' + 
			'padding-top:'+padding+'px; padding-left:'+padding+'px; padding-right:'+padding+'px;' +
			'padding-bottom:'+padding+'px; border-radius: 20px;">'+user + ' @ ' + colonTime +'</div>';
		} else {
			return '<div style="background-color:'+color+'; color:black; ' + 
			'padding-top:'+padding+'px; padding-left:'+padding+'px; padding-right:'+padding+'px;' +
			'padding-bottom:'+padding+'px; border-radius: 20px;">'+user + ' @ ' + colonTime +'</div>';
		}
	}
	
	
	
	
	
	
});