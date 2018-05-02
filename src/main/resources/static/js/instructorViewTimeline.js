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
	let link = document.getElementById("videoFrame").src;
	let linkId = extractVideoIdFromYouTubeUrl(link);
	let url = 'https://img.youtube.com/vi/'+linkId+'/0.jpg';
	let questionId;
	let mouseoverId;
	videoThumbnail.src = url;
	videoThumbnail.style.width = "50px";
	videoThumbnail.style.height = "50px";
	$.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + linkId + "&key=" + "AIzaSyC20skOqfx9zQmQ6eNhZi-bqTNis5teoX0", function(data) {
		videoName.innerHTML = data.items[0].snippet.title;
		//alert(data.items[0].snippet.title);
	});
	
	const MESSAGE_TYPE = {
	  	CONNECT: 0,
		NEW_QUESTION: 1,
		NEW_ANSWER: 2,
		UPVOTE: 3, 
		ERROR: 4
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
				let questionId = newQuestion.questionId;
				let toUpdate = items._data[questionId];
				console.log("ITEMS BEFORE: " + items);
				let postParameters = {id:questionId};
				let detail = [];
				let displayResponse = document.getElementById("displayResponse");
				let tmpStr = "";
				$.post("/response",postParameters,responseJSON => {
					let responseObject = JSON.parse(responseJSON);
					for (let i = 0; i < responseObject.length; i++) {
						console.log("RESPONSE OBJECT: " + responseObject[i]);
						let response = responseObject[i];
						tmpStr += response['detail']+"<br>";
						detail.push(response['detail']);
						
						
					}
					displayResponse.innerHTML = tmpStr;
				});
				toUpdate.content = setContent(2,toUpdate.numVotes,toUpdate.user,toUpdate.colonTime);
				toUpdate.response = detail;
				console.log("ITEMS AFTER: " + items);
				timeline.setItems(items);
				break;
			case MESSAGE_TYPE.UPVOTE: 
				
				break;
			case MESSAGE_TYPE.ERROR:
				alert("error");
				break;
		}
	});	
	
	
	
	
	let postParameters = {id:videoId responses:true};
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
			let responses = question['responses'];
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
			responses : responses,
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
			let toSend = JSON.stringify({type : MESSAGE_TYPE.NEW_ANSWER, payload : payload});
			console.log(toSend);
			conn.send(toSend);
			instructorResponse.value = "";



			
			
			// TELL JERRY TO AUTOMATICALLY SET THIS QUESTION TO RESOLVED
			

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
			let duration = response['items']['0']['contentDetails']['duration'];
			let videoEndTime = parseDuration(duration).split(",");
			console.log(videoEndTime);
			let options = {
				width: '100%',
				height: '320px',
				timeAxis: {scale: 'second', step: 60}
			};
			options.zoomMax = 420000;
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
				let response = document.getElementById("displayResponse");
				let info = items._data[questionId];
				if (info) {
					summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					question.innerHTML = info.fullQuestion;
					if (info.response.length > 0) {
						let res = info.response;
						let tmpStr = "";
						for (let i = 0; i < res.length; i++) {
							tmpStr += res[i]+"<br>";
						}
						response.innerHTML = tmpStr;
					} else {
						response.innerHTML = "No response yet! :(";
					}
				} else {
					summary.innerHTML = "";
					question.innerHTML = "";
					response.innerHTML = "";
				}
				
			});
			
			timeline.on('mouseOver', function (properties) {
				mouseoverId = properties.item;
				//console.log(properties);
				if (mouseoverId != null && timeline.getSelection().length === 0) {
				//	console.log(timeline.getSelection());
					let info = items._data[mouseoverId];
					let summary = document.getElementById("displaySummary");
					let question = document.getElementById("displayQuestion");
					let response = document.getElementById("displayResponse");
					let sum = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
					if (sum !== summary.innerHTML) {
						summary.innerHTML = info.user + " had a question @ " + info.colonTime + " | " + info.summary;
						question.innerHTML = info.fullQuestion;
						if (info.response.length > 0) {
							let res = info.response;
							let currStr = ""
							for (let i = 0; i < res.length; i++) {
								currStr += res[i]+"<br>";
							}
							response.innerHTML = currStr;
						} else {
							response.innerHTML = "No response yet! :(";
						}
					}
				}
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
		let padding = 7 + numUpVotes/5;
		return '<div style="background-color:'+color+'; color:white; ' + 
		'padding-top:'+padding+'px; padding-left:'+padding+'px; padding-right:'+padding+'px;' +
		'padding-bottom:'+padding+'px; border-radius: 20px;">'+user + ' @ ' + colonTime +'</div>';
	}
	
	
	
	
	
	
});