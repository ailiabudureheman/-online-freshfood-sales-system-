
var req;
function getSubType() {
	var id;
	var superType = document.getElementById("s1");
	for(var i=0;i<superType.options.length;i++) {
		if(superType.options[i].selected) {
			id = superType.options[i].value;
		}
	}
	var url = "getSubTypeBySuperTypeId?superTypeId="+id;
	sendRequest(url);
}
function sendRequest(url) {
	if(window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	req.onreadystatechange = showSub;
	req.open("get",url,true);
	req.send(null);
}
function clearSubType() {
	 var subType = document.getElementById("s2");
	 for(var i = subType.options.length - 1;i>=0;i--) {
	 	subType.options[i] = null;
	 }
}
function showSub() {
	if(req.readyState == 4) {
		if(req.status == 200) {
			var subTypeXml = req.responseXML;
			clearSubType();
			var subTypes = subTypeXml.getElementsByTagName("subType");
			var subType = document.getElementById("s2");
			for(var i=0;i<subTypes.length;i++) {
				var subTypeId = subTypes[i].getElementsByTagName("subTypeId").item(0).firstChild.data;
				var subTypeName = subTypes[i].getElementsByTagName("subTypeName").item(0).firstChild.data;
				var op = document.createElement("option");
				op.setAttribute("value",subTypeId);
				var txt = document.createTextNode(subTypeName);
				op.appendChild(txt);
				subType.appendChild(op);
			}
		}
	}
}