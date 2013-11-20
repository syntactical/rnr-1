<!DOCTYPE html>
<html>
<head>
    <title>RnR at ThoughtWorks</title>

    <link href="../../scripts/css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="../../scripts/css/datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="../../scripts/css/rnr.css" rel="stylesheet" type="text/css"/>

    <%--<script type="text/javascript" src="../../scripts/js/jquery-1.7.1.min.js"></script>--%>

    <script type="text/javascript" src="../../scripts/js/jquery-1.7.2.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script type="text/javascript" src="../../scripts/js/jquery-ui-1.8.18.custom.min.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script type="text/javascript" src="../../scripts/js/rnr.js"></script>


</head>
<body>
<div id="wrap">
    <script src="../../scripts/js/bootstrap.js"></script>

    <table colspan=3 id="banner">
        <td><p class="text-info"><strong>R<br>n<br>R</strong></p></td>
        <td><p class="text-warning">@</p></td>
        <td><img class="headerimg" src="/images/tw-logo.png" class="img-rounded"/></td>
    </table>


    <table>
        <tr>
            <form name="startDate" action="/vacationDays" method="POST" id="date_selector">
                Start Date:  <input id="start-date-picker" type="text"
                                                        name="startDate" size="15.5"><br>

                Initial Accrual Rate in days per year (If different than 10 days): <input id="accrual_rate" type="text"
                                                                                          name="accrualRate"
                                                                                          size='22'><br>

                Vacation Balance as of 7/1/2013: <input id="rolloverdays_field" type="text" name="rolloverdays"
                                                        size='20'><br>

                Text From SalesForce Time Off: <textarea ROWS=10 COLS=80 id="salesForceText" type="text"
                                                        name="salesForceText"></textarea><br>

                Calculate my vacation days as of: <input id='end-date-picker' type="text"
                                                         name="endDate" size="15.5"><br>

                <input type="submit" value="Submit" id="submit_button">
            </form>
        </tr>
    </table>

</div>
</body>
</html>