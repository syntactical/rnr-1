<!DOCTYPE html>
<html>
<title>RnR at ThoughtWorks</title>
<link href="../../scripts/css/bootstrap.css" rel="stylesheet" media="screen">
<link href="../../scripts/css/datepicker.css" rel="stylesheet" type="text/css"/>
<link href="../../scripts/css/rnr.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../../scripts/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../../scripts/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="../../scripts/js/rnr.js"></script>
<script type="text/javascript"></script>


<body>
<div id="wrap">
    <script src="../../scripts/js/bootstrap.js"></script>

    <table colspan=3 id="banner">
        <td><p class="text-info"><strong>R<br>n<br>R</strong></p></td>
        <td><p class="text-warning">@</p></td>
        <td><img class="headerimg" src="/images/tw-logo.png" class="img-rounded"/></td>
    </table>

    <p id="vacationDays" class="lead">Hey, your balance is 0.00</p>

    <p class="lead">Please select your <strong>start date.</strong> Or you can manually enter the date in the format
        mm/dd/yyyy</p>


    <table colspan=2>
        <td>
            <form action="" name="dateform" id="date_selector">
                <select id="monthdropdown">
                </select>
                <select id="daydropdown">
                </select>
                <select id="yeardropdown">
                </select>
            </form>
        </td>

        <td>
            <form name="startDate" action="/vacationDays" method="POST">
                <input id="date_form" type="text" name="user">
                <input type="submit" value="Submit">
            </form>
        </td>

    </table>

</div>
</body>
</html>