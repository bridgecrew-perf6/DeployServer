
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

