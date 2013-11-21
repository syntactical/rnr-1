<!DOCTYPE html>
<html>
<head>
    <title>RnR at ThoughtWorks</title>
    <link href="../../scripts/css/datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="../../scripts/css/rnr.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link href='http://fonts.googleapis.com/css?family=Average+Sans' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="../../scripts/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../../scripts/js/jquery-ui-1.8.18.custom.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script type="text/javascript" src="../../scripts/js/rnr.js"></script>
</head>
<body>
<div id="page">
    <div>
        <img class="header-img" src="/images/logo.png"/>
    </div>
    <form name="startDate" action="/vacationDays" method="POST" id="date_selector">
        <div id="main-section">
            <div>
                <label for="start-date-picker">Start Date:</label>
                <input id="start-date-picker" class="banner" type="text" name="startDate">
            </div>
            <div>
                <label for="accrual-rate">Initial Accrual Rate:</label>
                <input id="accrual-rate" type="text" name="accrualRate">
                <span class="annotation">days per year</span>
            </div>
            <div>
                <label for="rolloverdays-field">Vacation Balance:</label>
                <input id="rolloverdays-field" type="text" name="rolloverdays">
                <span class="annotation">(as of 7/1/2013)</span>
            </div>
            <div>
                <label for="sales-force-text">Text from SalesForce Time Off:</label>
                <textarea id="sales-force-text" type="text" name="salesForceText"></textarea>
            </div>
        </div>
        <div id="right-bar">
            <div>
                <label for="end-date-picker">Calculate my vacation days as of:</label>
                <input id='end-date-picker' type="text" name="endDate">
            </div>
            <div>
                <input type="submit" value="Submit" id="submit_button">
            </div>
        </div>
    </form>
</div>
</body>
</html>