var xmlHttp
function suggestion(name){ 
if (name.length==0){ 
 document.getElementById("livesearch").innerHTML="";
 document.getElementById("livesearch").style.border="0px";
 return
 }
xmlHttp=GetXmlHttpObject()
if (xmlHttp==null){
 alert ("Browser does not support HTTP Request");
 return;
 }
 
var url="suggest.php";
url=url+"?unos="+name;
url=url+"&sid="+Math.random();
xmlHttp.onreadystatechange=stateChanged; 
xmlHttp.open("GET",url,true);
xmlHttp.send(null);
}
function stateChanged(){ 

if (xmlHttp.readyState==4){ 
 document.getElementById("livesearch").innerHTML=xmlHttp.responseText;
 document.getElementById("livesearch").style.border="1px solid";
 document.getElementById("livesearch").style.display="block";
 } 
}
function GetXmlHttpObject(){
var xmlHttp=null;
try {
 // Firefox, Opera 8.0+, Safari
 xmlHttp=new XMLHttpRequest();
 } catch (e) {
 //Internet Explorer
 try {
  
  xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e) {
  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 }
return xmlHttp;
}
