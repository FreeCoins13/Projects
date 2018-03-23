<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:import url="/WEB-INF/jsp/header.jsp"/>
<c:url value="/css/parkDetail.css" var="cssHref" />
<link rel="stylesheet" href="${cssHref}">

<div id="main-content">
    
    <section id="parkDescription">
    		
    		<div >
    			<img class="parallax" src="img/parks/${park.parkCode.toLowerCase()}.jpg"/>
    			<div id="quoteContainer">
    			<div id="quote">"${park.quote}"</div>
       		<div id="quoteSource">-${park.quoteSource}</div>
        </div>
    		</div>
    		<div id="header">
    			<div><h1 id="parkName">${park.parkName}</h1></div>
    			<%-- <div style="font-size: 14px">, ${park.state}</div>--%>
    		</div>
    		
    		
        <div class="card" id="description">${park.description}</div>
        <h3>Interesting Facts</h3>
        <div id="facts">
        		
        		<div id="wrap">
        			<div id="fact-box">
        				<div class="table"><div class="left-column fact-box">Park Area: </div><div class="fact-box">${park.acreage} acres</div></div>
        				<div class="table"><div class="left-column">Elevation: </div><div>${park.elevation} feet</div></div>
        				<div class="table"><div class="left-column">Total Length of Park Trails: </div><div>${park.miles} miles</div></div>        
       				<div class="table"><div class="left-column">Climate: </div><div>${park.climate}</div></div>
        				<div class="table"><div class="left-column">Number of animal species: </div><div>${park.animals}</div></div>
        				<div class="table"><div class="left-column">Founded in: </div><div>${park.year}</div></div>
        				<div class="table"><div class="left-column">Number of Annual Visitors: </div><div>${park.visitors}</div></div>
        				<div class="table"><div class="left-column">Number of campsites: </div><div>${park.campNum}</div></div>
        				<div class="table"><div class="left-column">Entry Fee: </div><div>$${park.fee}</div></div>
        			</div>
        		</div>
        </div>
     </section>
    
     <section id="parkWeather">
     	<h2>5-Day Weather Forecast</h2>
     	<div id="scaleChoice">
     		<c:url var="temperatureLink" value="/parkDetail">
     			<c:param name="temperature" value="celcius" />
     			<c:param name="parkCode" value="${park.parkCode}" />
     		</c:url>
     		<a href="${temperatureLink}">°C | </a>
     		<c:url var="temperatureLink" value="/parkDetail">
     			<c:param name="temperature" value="fahrenheit" />
     			<c:param name="parkCode" value="${park.parkCode}" />
     		</c:url>
     		<a href="${temperatureLink}">°F</a>
     	</div>
     	<div id="forecast">
     	<c:forEach var="item" items="${weatherList}">
     		<c:choose>
     			<c:when test="${item.dayNum==1}">
     				<div class="today">
     					<div><img style="opacity: 0.9" width="90%" src="img/weather/${item.forecast}.png"></div>
     					<div>High ${item.high} ${temp}</div>
     					<div class="low">Low ${item.low} ${temp}</div>
     					<div class="forecastWord">${item.forecast}</div>
     					<div>${advisoryList[0].advisory}</div>
     				</div>
     			</c:when>
     			<c:otherwise>
     				<div class="future">
     					<div><img style="opacity: 0.9" width="90%" src="img/weather/${item.forecast}.png"></div>
     					<div>High ${item.high} ${temp}</div>
     					<div class="low">Low ${item.low} ${temp}</div>
     					<div class="forecastWord">${item.forecast}</div>
     					<div>${advisoryList[item.dayNum-1].advisory}</div>
     				</div>
     			</c:otherwise>
     		</c:choose>
     	</c:forEach>
     	</div>
     </section>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp" />