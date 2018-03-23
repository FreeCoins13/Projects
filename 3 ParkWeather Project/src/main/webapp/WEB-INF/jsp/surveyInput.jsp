<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<c:url value="/css/homePage.css" var="cssHref" />
    <link rel="stylesheet" href="${cssHref}">
<c:url value="/css/Survey.css" var="cssHref" />
    <link rel="stylesheet" href="${cssHref}">



<br><br><br>
<div id="main-content">
		<h2 style="margin: 3%">Give us your feedback</h2>

		<form:form class="line" method="POST" action="surveyInput" modelAttribute="survey">
	
			<label class=input for="parkCode">Favorite National Park</label>
			<form:select path="parkCode">
					<option value=""> </option>
				<c:forEach var="item" items="${parks}">
					<option value="${item.parkCode}">${item.parkName}</option>
				</c:forEach>
			</form:select>
			<form:errors style="color: red" path="parkCode" cssClass="error"/><br>
		
			<label class="input" for="email">Your email</label>
			<form:input path="email" />
			<form:errors style="color: red" path="email" /><br>
		
			<label class="input" for="state">State of Residence</label>
			<form:select path="state" id="state">
				<c:forEach var="item" items="${states}">
					<option value="${item}">${item}</option>
				</c:forEach>
			</form:select>
			<form:errors style="color: red" path="state" /><br>
		
			<label class="checkbox" for="activityLevel">Activity Level</label><br>
			<form:checkbox id="crossfit" path="activityLevel" value="CrossFit Junkie" /><small>CrossFit Junkie</small>
			<form:checkbox path="activityLevel" value="Perpetual Trekker" /><small>Perpetual Trekker</small>
			<form:checkbox path="activityLevel" value="Opportunist Wanderer" /><small>Opportunist Wanderer</small>
			<form:checkbox path="activityLevel" value="Couch Potato" /><small>Couch Potato</small>
			<form:errors style="color: red" path="activityLevel" /><br><br>
			
			<input class="submit" type="submit" value="Submit">
		</form:form>
	
   
</div>
<c:import url="/WEB-INF/jsp/footer.jsp" />