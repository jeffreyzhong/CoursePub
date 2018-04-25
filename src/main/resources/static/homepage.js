$(document).ready(function() {

	let $searchCourses = $(document).getElementById("searchCourses");
	let $searchSuggestions = $(document).getElementById("searchSuggestions");
	$searchCourses.keyup(event => {
		
		const postParameters = {searchCourses:$searchCourses.val()};
		if ($intersectionName.val() === "") {
			$suggestion1.html("");
		} else {
			$.post("/coursepub/searchCourses", postParameters, responseJSON => {
				const responseObject = JSON.parse(responseJSON);
				$suggestion1.html("");
				if (responseObject != null) {
					for (i = 0; i < responseObject.content.length; i++) {
						$searchSuggestions.append($("<li id=\"whichSugg"+i+
							"\"onClick=\"getValue("+i+")\"value=\""+
							responseObject.content[i]+"\">").text(responseObject.content[i]));
					}
				} else {
					$searchSuggestions.append($("<li id=\"whichSugg"+i+
							"\"onClick=\"getValue("+i+")\"value=\""+noCourses
							+"\">").text("No courses at this moment :("));
				}
			});
		}
	});
})