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

    <p id="vacationDays" class="lead">Hey, your balance is 0.00 enter any rollover days from the previous year.</p>

    <%--<p class="lead">Please select your <strong>start date.</strong> Or you can manually enter the date in the format--%>
        <%--mm/dd/yyyy</p>--%>


    <table>
        <tr>
            <form name="startDate" action="/vacationDays" method="POST" id="date_selector">
                <select id="monthdropdown" class="date">
                </select>
                <select id="daydropdown" class="date">
                </select>
                <select id="yeardropdown" class="date">
                </select>

                <input id="startdate_field" type="text" name="startdate"><br>

                Rollover Days (as of January 1st)<input id="rolloverdays_field" type="text" name="rolloverdays"><br>

                <input type="submit" value="Submit" id="submit_button">
            </form>
        </tr>
    </table>

</div>
</body>
</html>