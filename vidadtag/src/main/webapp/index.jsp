<%@ page import="java.util.*" %>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
	
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
<h2>Vidadtag server is running</h2>
<%= "Server time is " + new Date() + " local address:" + request.getLocalAddr() + " remote address:" + request.getRemoteAddr()%>
<br/>
<video width="320" height="240"	autoplay id="myvid" >
  <source src="movie.mp4" type="video/mp4"></source>
  <source src="movie.ogg" type="video/ogg"></source>
  <source src="movie.webm" type="video/webm"></source>
	Your browser does not support the video tag.
</video>
<br/>
<button onclick="pause()">click me</button>
<%-- <f:view>
  <h:form>
    <h:panelGrid columns="2">
      <h:outputLabel value="Add"></h:outputLabel>
      <h:inputText  value="#{vidoeTagCollector.taxonomy}"></h:inputText>
    </h:panelGrid>
    <h:commandButton action="#{vidoeTagCollector.add}" value="Add"></h:commandButton>
    <h:commandButton action="#{temperatureConvertor.reset}" value="Reset"></h:commandButton>
    <h:messages layout="table"></h:messages>
  </h:form>
  
  
  <h:panelGroup rendered="#{vidoeTagCollector.initial!=true}">
  <h3> Result </h3>
  <h:outputLabel value="Status "></h:outputLabel>
  <h:outputLabel value="#{vidoeTagCollector.status}"></h:outputLabel>
  </h:panelGroup>
</f:view>
 --%>
</body>
</html>
