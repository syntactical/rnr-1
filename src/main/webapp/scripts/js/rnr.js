$(function addCalendar() {
    $('#datepicker').datepicker({
        inline: true,
        showOtherMonths: true,
        dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        buttonImage: "img/calendar-blue.png"
    });


    $(function autoFillDate() {
        $('#datepicker').click(function () {
            var selectedDate = $("#datepicker").datepicker("getDate");
            if (selectedDate.getDate().toString().length == 2) {
                var day = selectedDate.getDate();
            } else {
                var day = "0" + selectedDate.getDate();
            }
            if (selectedDate.getMonth().toString().length == 2) {
                var month = (selectedDate.getMonth() + 1);
            } else {
                var month = "0" + (selectedDate.getMonth() + 1);
            }
            var date = month + "/" + day + "/" + selectedDate.getFullYear();
            $('#date_form').val(date);
        });
    });
});
