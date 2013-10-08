$(function () {
    $('.date').live("change click", function(){
        var month = ($('#monthdropdown').val() < 10 ? '0' : '') + ($('#monthdropdown').val());
        var day = ($('#daydropdown').val() < 10 ? '0' : '') + $('#daydropdown').val();
        var year = ($('#yeardropdown').val());
        $('#startdate_field').val(month + "/" + day + "/" + year);
    })
});

$(function loadDays() {
    var selection = document.getElementById('daydropdown');
    populateDropDown(1, 31, selection);
});

$(function loadMonths() {
    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var sel = document.getElementById('monthdropdown');
    for (var i = 0; i < months.length; i++) {
        var opt = document.createElement('option');
        opt.innerHTML = months[i];
        opt.value = i + 1;
        sel.appendChild(opt);
    }
});

$(function loadYears() {
    var selection = document.getElementById('yeardropdown');
    populateDropDown(1993, 2025, selection);
});

function populateDropDown(startIndex, endIndex, selection) {
    for (var i = startIndex; i <= endIndex; i++) {
        var option = document.createElement('option');
        option.innerHTML = i;
        option.value = i;
        selection.appendChild(option);
    }
};

