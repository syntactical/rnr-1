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



