<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Install app</title>
</head>
<body>
	<form action="addApp" method="POST">
		install info:<textarea name="config"><?xml version="1.0" encoding="UTF-8"?>
		<Application>
		<Name>Test</Name>
		<Listeners>
			<HttpListener>
					<URL>http://localhost:8080/DemoApp/listener</URL>
					<MatchRule type="urlregex">
						.*/signal/send.*
					</MatchRule>
			</HttpListener>
		</Listeners>
	</Application>
		
		</textarea>
		<input type="submit"/>
	</form>
</body>
</html>