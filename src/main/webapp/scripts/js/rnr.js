$(function () {
    $("#start-date-picker").datepicker({
        changeMonth: true,
        changeYear: true,
        defaultDate: new Date(),
        maxDate: 0,
        yearRange: "1993:+0"
    });

    $("#end-date-picker").datepicker({
        changeMonth: true,
        changeYear: true,
        defaultDate: new Date(),
        minDate: 0,
        maxDate: "+3Y",
        yearRange: "+0:+3"
    });
});

function showFirstSalesForcePage() {
    document.getElementById('sales-force-help-box-1').style.zIndex = "-1";
    document.getElementById('sales-force-help-box-2').style.zIndex = "-2";
}

function showSecondSalesForcePage() {
    document.getElementById('sales-force-help-box-1').style.zIndex = "-2";
    document.getElementById('sales-force-help-box-2').style.zIndex = "-1";
}
