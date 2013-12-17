$().ready(function () {
    $("#date_selector").validate({
        errorPlacement: function(error, element) {
            error.insertBefore($('#label-for-' + element.attr('id')));
        },
        onkeyup: false,
        onfocusout: false,
        focusInvalid: false,
        rules: {
            startDate: {
                required: true
            },
            endDate: {
                required: true
            },
            accrualRate: {
                required: true,
                min: 10
            },
            rolloverdays: {
                range: [-5, 30]
            },
            messages: {
                accrualRate: {
                    range: "Please enter a value between 10 and 30"
                }
            }
        }
    });
});

$(function () {
    $("#start-date-picker").datepicker({
        changeMonth: true,
        changeYear: true,
        defaultDate: new Date(),
        maxDate: 0,
        yearRange: "1993:+0",
        onSelect: function() {
            var minDate = $(this).datepicker('getDate');
            $("#end-date-picker").datepicker( "option", "minDate", minDate);
        }
    });

    $("#end-date-picker").datepicker({
        changeMonth: true,
        changeYear: true,
        defaultDate: new Date(),
        minDate: "-1Y",
        maxDate: "+3Y",
        yearRange: "+0:+3"
    });
});

function checkdate(input){
    var dateFormat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
    var returnVal=false;
    if (!dateFormat.test(input.value))
        alert(input);
    return returnVal;
}

function showSalesForceHelpBox() {
    $('.sales-force-help-box').toggle();

    if ($('#messages').css('display') == 'none')
    {
        $('#messages').css('display', 'block');
    }
    else
    {
        $('#messages').css('display', 'none');
    }
}

function showFirstSalesForcePage() {
    document.getElementById('sales-force-help-box-1').style.zIndex = "-1";
    document.getElementById('sales-force-help-box-2').style.zIndex = "-2";
    document.getElementById('messages').style.display = "none";
}

function showSecondSalesForcePage() {
    document.getElementById('sales-force-help-box-1').style.zIndex = "-2";
    document.getElementById('sales-force-help-box-2').style.zIndex = "-1";
    document.getElementById('messages').style.display = "none";
}
