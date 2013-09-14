
    $(function addCalendar () {
        $('#datepicker').datepicker({
            inline: true,
            showOtherMonths: true,
            dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            buttonImage: "img/calendar-blue.png"
        });


        $(function autoFillDate() {
            $('#datepicker').click(function () {
                var selectedDate = $("#datepicker").datepicker("getDate");
                var date = (selectedDate.getMonth() + 1) + "/" + selectedDate.getDate() + "/" + selectedDate.getFullYear();
                $('#date_form').val(date);
            });
        });
});
