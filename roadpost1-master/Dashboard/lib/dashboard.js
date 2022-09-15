Array.prototype.unique = function () {
    var r = new Array();
    o:for(var i = 0, n = this.length; i < n; i++) {
    	for(var x = 0, y = r.length; x < y; x++) 	{
    		if(r[x]==this[i])
    		{
                //alert('this is a DUPE!');
    			continue o;
    		}
    	}
    	r[r.length] = this[i];
    }
    return r;
}
function runtimereport(){
	headers();
	var arr={};
	for(j=1;j<90;j++){
		var data=readFile(j+".properties").split("\n");
		for(i=0;i<data.length-1;i++){
			displayScriptStatus(data[i],i+1);
		}
	}
	
	var passed=document.getElementsByClassName("PASSED").length-1;
	var failed=document.getElementsByClassName("failed").length-1;
	var skipped=document.getElementsByClassName("Skipped").length-1;
	
	displayExecutionStatus(passed,failed,skipped);
	var total=document.getElementById("TOTAL_TEST_SCRIPTS").innerHTML;
	var elements = document.querySelectorAll('td[class*=")"]');
	var txt="";
	for(i=0;i<elements.length;i++){
		if(elements[i].innerHTML.indexOf("(")>-1){
			 var tmp=elements[i].innerHTML.split("(");
			 txt = txt+","+tmp[0].trim();
			}
	}
	if(txt.length>0){
	var arr= txt.substr(1).split(",");
var unique = arr.unique();
//alert(unique);
	total=+total + +elements.length - +unique.length;
	}
	document.getElementById("TOTAL_TEST_SCRIPTS").innerHTML=total
	if(total == passed+failed+skipped){
		if(document.getElementById("SUITE_STATUS").innerHTML=="COMPLETED"){
			alert("Execution completed refer to Automation Reports\\LatestResults for report overview");
		}
	}
	
	
	
	}


function headers(){
	var d=readFile("suiteInfo.properties").split("\n");
	for(i=0; i < d.length; i++){
		if(d[i].indexOf("=") != -1)
		{
		var key=d[i].split("=")[0].trim();
		var value=d[i].split("=")[1].trim();
		document.getElementById(key).innerHTML=value;
		}
	}
}

function displayScriptStatus(script,i){
	
	var element = document.getElementById("testcasesheader")
	var row = document.createElement("tr");
	var scriptName = script.split("=")[0];
	var temp = script.split(":-")[0];
	var scriptStatus = temp.split("=")[1];
	var ErrorMessage = script.split(":-")[1];
	var ExecutionTime = script.split(":-")[3];
	var ScreenShotPath = script.split(":-")[2];

	
	var number = document.createElement("td");
	number.id = i;
	number.className = i;
	element.appendChild(row).appendChild(number).appendChild(
			document.createTextNode(i));


	var eleScriptName = document.createElement("td");
	eleScriptName.id = scriptName;
	eleScriptName.className = scriptName;
	element.appendChild(row).appendChild(eleScriptName).appendChild(
			document.createTextNode(scriptName));

	var eleScriptStatus = document.createElement("td");
	eleScriptStatus.id = scriptStatus;
	eleScriptStatus.className = scriptStatus;
	element.appendChild(row).appendChild(eleScriptStatus).appendChild(
			document.createTextNode(scriptStatus));

	var ErrorMessageElement = document.createElement("td");
	ErrorMessageElement.id = ErrorMessage;
	ErrorMessageElement.className = ErrorMessage;

	if (ErrorMessage == "--------") {
		element.appendChild(row).appendChild(ErrorMessageElement).appendChild(
				document.createTextNode(ErrorMessage));
	} else {
		var n = ScreenShotPath.includes("null");
		if (n) {
		
			var ScreenShotElement = document.createElement("td");
			ScreenShotElement.id = ScreenShotPath;
			element.appendChild(row).appendChild(ErrorMessageElement)
					.appendChild(ScreenShotElement).appendChild(
							document.createTextNode(ErrorMessage));
		} else {
			var ScreenShotElement = document.createElement("a");
			ScreenShotElement.href = ScreenShotPath;
			element.appendChild(row).appendChild(ErrorMessageElement)
					.appendChild(ScreenShotElement).appendChild(
							document.createTextNode(ErrorMessage));
		}
	}
	var executionTime = document.createElement("td");
	executionTime.id = ExecutionTime;
	executionTime.className = ExecutionTime;
	element.appendChild(row).appendChild(executionTime).appendChild(
			document.createTextNode(ExecutionTime));
}
function displayExecutionStatus(tp,tf,ts){
	document.getElementById("passed").innerHTML=tp;
	document.getElementById("failed").innerHTML=tf;
	document.getElementById("skipped").innerHTML=ts;
	document.getElementById("executed").innerHTML=tp+ts+tf;
}	

function readFile(file){

	var rawFile = new XMLHttpRequest();
	rawFile.open("GET",file,false);
	var allText="";
	rawFile.onreadystatechange = function()
	{
		if(rawFile.readyState == 4)
		{
			if(rawFile.status === 200 || rawFile.status == 0)
			{
				allText = rawFile.responseText;
			}
		}
	}
	try{
	rawFile.send(null);
	}
	catch(err){
		//document.getElementById("abc").innerHTML=err+file;
	}
	return allText
}