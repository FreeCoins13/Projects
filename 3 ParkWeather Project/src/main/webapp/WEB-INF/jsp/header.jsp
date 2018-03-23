<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css?family=Lato|Lobster|Shrikhand|Special+Elite" rel="stylesheet">
    <%--
	font-family: 'Lobster', cursive;
	font-family: 'Special Elite', cursive;
	font-family: 'Shrikhand', cursive;
	font-family: 'Lato', sans-serif;
	 --%>
    <title> National Park Geek</title>
    <%-- <c:url value="/css/parkWeather.css" var="cssHref" /> --%>
    <link rel="stylesheet" href="/css/parkWeather.css">
    
</head>

<body>
   <div id="header-div">
     <header>
    		<c:url value="/" var="homePageHref" />
    		<c:url value="/img/logo.png" var="logoSrc" />
    		
    		<div >
        		<a href="${homePageHref}">
        			<img id="logo" src="${logoSrc}" alt="National Park Geek logo" />
        		</a>
        </div>
    </header>
 	 <nav>
        <ul>
            <li><a href="/m3-java-capstone">HOME</a></li>
            <li><a href="/m3-java-capstone/surveyInput">SURVEY</a></li>
            <li><a href="/m3-java-capstone/surveyOutput">TOP PARKS</a></li>
        </ul>
     </nav>
    </div>