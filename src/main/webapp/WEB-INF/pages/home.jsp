<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<script>
    function showFirstSalesForcePage(){
        document.getElementById('sales-force-help-box-1').style.zIndex="3";
        document.getElementById('sales-force-help-box-2').style.zIndex="2";
    }
    function showSecondSalesForcePage(){
        document.getElementById('sales-force-help-box-1').style.zIndex="2";
        document.getElementById('sales-force-help-box-2').style.zIndex="3";
    }
</script>
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
                <span id="sales-force-text-help">
                    <div>
                        <button type="button" class="square-button" id="sales-force-help-button" onclick="$('.sales-force-help-box').toggle()">
                        ?
                        </button>
                        <div class="sales-force-help-box" id="sales-force-help-box-1">
                            <span>Navigate to <a href="https://our.thoughtworks.com" target="_new">our.thoughtworks.com</a> and click the "Time Off" link in the left sidebar.</span>
                            <img id="sales-force-image-1" src="../../images/SFinctructions1.jpg"/>
                            <button type="button" class="square-button sales-force-button" id="sales-force-next-button" onclick="showSecondSalesForcePage()">
                                &#9658;
                            </button>
                        </div>
                        <div class="sales-force-help-box" id="sales-force-help-box-2">
                            <span>Copy and paste everything under the "Project Name" header.</span>
                            <img id="sales-force-image-2" src="../../images/SFinctructions2.jpg"/>
                            <button type="button" class="square-button sales-force-button" id="sales-force-back-button" onclick="showFirstSalesForcePage()">
                                &#9668;
                            </button>
                        </div>
                    </div>
                </span>
            </div>
        </div>
        <div id="right-bar">
            <div>
                <label for="end-date-picker">Calculate my vacation days as of:</label>
                <input id='end-date-picker' type="text" name="endDate">
            </div>
            <div>
                <input type="submit" value="Submit" class="button" id="submit-button">
            </div>
        </div>
    </form>
</div>
</body>
</html>