<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Java Database Updater</title>
	<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
 	<form action="UpdaterServlet" method = "GET">
 		<div class="center">
 			<h1>Java Database Updater</h1>
 			<br></br>
 			<label for="database">Database File Path: </label>
			<input type="text" id="database" name="database">
			<br></br>
			<label for="updater">Updater File Path: </label>
			<input type="text" id="updater" name="updater">
			<br></br>
			<label for="download">Download Location: </label>
			<input type="text" id="download" name="download" />			
			<br></br>
			<input  class="marginButton" type="submit" value="Update Database"/>
		</div>			
 	</form>
</body>
