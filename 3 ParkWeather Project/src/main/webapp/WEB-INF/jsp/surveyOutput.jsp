<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/header.jsp"/>
<c:url value="/css/Survey.css" var="cssHref" />
<link rel="stylesheet" href="${cssHref}">

<div id="main-content">
	<div class="top-content">
		<h2>Survey Results</h2>
		<p>See how other users have rated our fabulous parks!</p>
	</div>


<c:forEach var="item" items="${surveyResultList}">
	<section>
	<div><img class="image" src="img/parks/${item.parkCode.toLowerCase()}.jpg"></div>
	
	<div class="result-display-park">${item.parkName}</div>
	<div class="result-display-num">${item.surveySum}</div>
	</section>
</c:forEach>


</div>
<c:import url="/WEB-INF/jsp/footer.jsp" />