<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/header.jsp" />

<c:url value="/css/homePage.css" var="cssHref" />
    <link rel="stylesheet" href="${cssHref}">
    
<div id="main-content">
	<div class="survey-main">
   	  <h2 style="text-align: center">Our Parks</h2>
    </div>
    <c:forEach var="item" items="${parks}">
        <section>
            <div>
            		<c:url var="parkDetailLink" value="/parkDetail">
            			<c:param name="parkCode" value="${item.parkCode}"/>
            		</c:url>
            		<a href="${parkDetailLink}"><img src="img/parks/${item.parkCode.toLowerCase()}.jpg"/></a>
            </div>
            <div id="park-text"><h3 id="park-name"><a href="${parkDetailLink}">${item.parkName}</a></h3>
            <p id="park-description">${item.description}</p></div>
            <br/>
        </section>
    </c:forEach>
</div>

<c:import url="/WEB-INF/jsp/footer.jsp" />