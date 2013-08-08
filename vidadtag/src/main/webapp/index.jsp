<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<header>
	<script type="text/javascript"><jsp:include page="js/vidadtag.js" />
		function pause(){
			var vid=document.getElementById("myvid");
			vid.pause();
			alert(vid.currentTime);
			vid.play();

		}
	</script>	
</header>

<body>
<h2>Vidtag server is running</h2>
<%= "Server time is " + new Date() + " local address:" + request.getLocalAddr() + " remote address:" + request.getRemoteAddr()%>
<br/>
<video width="320" height="240"	autoplay id="myvid" >
  <source src="movie.mp4" type="video/mp4">
  <source src="movie.ogg" type="video/ogg">
  <source src="movie.webm" type="video/webm">
Your browser does not support the video tag.
</video>
<br/>
<button onclick="pause()">click me</button>
</body>
</html>
