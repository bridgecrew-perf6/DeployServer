function addAccount() {
    $.ajax({
        cache: false,
        url: "/addAccount",
        type: "POST",
        data: {
            username: $('#name').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            fullname: $('#fullname').val(),
            phone: $('#phone').val(),
            gender: $('#gender').val(),
            address: $('#address').val(),
        },
        success: function (res) {
            console.log(res['message']);
            if (res['success'] === true){
                window.location.replace('/login');
            } else if(res['message']=== "Tên đăng nhập đã được sủ dụng!") {
                $('#namefail').text("Tên đăng nhập đã được sủ dụng!");
            } else if(res['message'] === "Email đã được sủ dụng!") {
                $('#namefail').text("");
                $('#emailfail').text("Email đã được sủ dụng!");
            } else if(res['message'] === "Số điện thoại đã được sủ dụng!") {
                $('#emailfail').text("");
                $('#phonefail').text("Số điện thoại đã được sủ dụng!");
            }
        },
        error: function (err) {
            console.error(err.responseText);
        }
    });
}

