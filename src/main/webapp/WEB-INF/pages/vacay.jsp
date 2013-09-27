<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>RnR at ThoughtWorks</title>
    <link rel="stylesheet" href="rnr.css">
    <link href="../../scripts/css/rnr.css" rel="stylesheet" type="text/css"/>
    <link href="scripts/css/bootstrap.css" rel="stylesheet" media="screen">

</head>
<body>
<div id="wrap">
    <table colspan=3>
        <td><p class="text-info"><strong>R<br>n<br>R</strong></p></td>
        <td><p class="text-warning">@</p></td>
        <td><img class="headerimg" src="/images/tw-logo.png" class="img-rounded"/></td>
    </table>
    <br>
    <%--<p>You started at ThoughtWorks on ${postedValues.date} so you have ${postedValues.months} vacation days based on a monthly accrual</p>--%>
    <%--<p>You started at ThoughtWorks on ${postedValues.date} so you have ${postedValues.days} vacation days based on a daily accrual</p>--%>
    <p id="vacationDays">Hey, your balance is ${postedValues.days}</p>
</div>
</body>
</html>