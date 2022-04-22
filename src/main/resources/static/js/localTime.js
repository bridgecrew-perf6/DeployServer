function onCheck() {
    let year = document.getElementById("year").value;
    let month = document.getElementById("month").value;
    var y = localStorage.getItem("year");
    localStorage.setItem("year", year);
    var x = localStorage.getItem("month");
    localStorage.setItem("month", month);
    $(".yes").prop("href", "/admin/statistic/revenueByCategory/"+month+"/"+year)
}
$(function() {
    $('#month').change(function() {
        localStorage.setItem('month', month);
    });
    if(localStorage.getItem('month')){
        $('#month').val(localStorage.getItem('month'));
    }
});
$(function() {
    $('#year').change(function() {
        localStorage.setItem('year', year);
    });
    if(localStorage.getItem('year')){
        $('#year').val(localStorage.getItem('year'));
    }
});
