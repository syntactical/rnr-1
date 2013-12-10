<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RnR at ThoughtWorks</title>
    <link href="../../scripts/css/rnr.css" rel="stylesheet" type="text/css"/>
    <link href='http://fonts.googleapis.com/css?family=Average+Sans' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="../../scripts/js/rnr.js"></script>
</head>
<body>
<div id="page">
    <div>
        <img class="header-img" src="/images/logo.png"/>
    </div>
    <div id="centered-section">
        <span id="vacation-days">Your vacation balance is ${postedValues.days} days.</span>
        <br>
        <span id="personal-days">Your personal day balance is ${postedValues.personalDays} days.</span>
    </div>
</div>
</body>
</html>