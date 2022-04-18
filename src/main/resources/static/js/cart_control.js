$(function (){
    initQuantity(false, null);
});

function getValues() {
    var quantityList = document.getElementsByName('quantity');
}

function minusHandler(element){
    var id = $(element).attr("data-id");
    var index = $(element).attr("data-index");
    var price = $(element).attr("data-price");
    var qtyInput = $("#quantity" + id);
    var newQty= parseInt(qtyInput.val())-1;
    var v = parseInt($("#point").val());
    var discount = (v * 1000);
    if(newQty == 0)
        newQty = 1;
    var total = parseFloat(price) * newQty;

    $("#total-"+id).text(total);

    qtyInput.val(newQty);

    $("#total-money").text(onSetTotalMoney());
    $("#total-money").text(onSetTotalMoney() - discount);

    var carts = localStorage.getItem("carts");
    if (carts!==null && carts!=="" && carts!== undefined){
        var jsonObject = JSON.parse(carts);
        jsonObject.forEach(item =>{
            if (item.id === parseInt(id)){
                item.value = newQty;
            }
        })
        initQuantity(true, jsonObject);
        localStorage.setItem("carts", JSON.stringify(jsonObject));
    }

}

function plusHandler(element){
    var id = $(element).attr("data-id");
    var index = $(element).attr("data-index");
    var price = $(element).attr("data-price");
    var qtyInput = $("#quantity" + id);
    var qtyMax = $("#instock" + id).val();
    var newQty= parseInt(qtyInput.val())+1;
    var v = parseInt($("#point").val());
    var discount = (v * 1000);

    if (newQty <= qtyMax){
        qtyInput.val(newQty);
        var total = parseFloat(price) * newQty;
        $("#total-"+id).text(total);
    }

    $("#subtotal-money").text(onSetTotalMoney());
    $("#total-money").text((onSetTotalMoney() - discount).toString());

    var carts = localStorage.getItem("carts");
    if (carts!==null && carts!=="" && carts!== undefined){
        var jsonObject = JSON.parse(carts);
        jsonObject.forEach(item =>{
            if (item.id === parseInt(id)){
                item.value = newQty;
            }
        })
        initQuantity(true, jsonObject);
        localStorage.setItem("carts", JSON.stringify(jsonObject));
    }
}


var onSetTotalMoney =  function () {
    var total = 0;
    $(".total-money").each(function(){
        total+= parseFloat($(this).text());
    })

    return total;
}

document.getElementById("point").addEventListener("change", function() {
    var v = parseInt(this.value);
    var cusPoint = $('#pointMax').val();
    var total = parseInt(onSetTotalMoney()) / 1000;
    var max = 0;
    if(cusPoint > total) max = total;
    else max = cusPoint;
    if (v > max) {
        v = max;
        $("#point").val(max);
    }
    if (v < 0) v = 0;
    var discount = v * 1000;
    $("#discount").text(discount);
    $("#total-money").text(onSetTotalMoney() - discount);
});



var onSubmitCart = function () {
    var listValue = [];

    $("input[name='quantity']").each(function(){
        listValue.push(parseInt($(this).val()));
    })

    var listString = listValue.join(',');

    $.ajax({
        cache: false,
        url: "/cart/addOrder/",
        data: {
            customerPhone: $("#customerPhone").val(),
            listItems: listValue,
            point: $("#point").val(),
        },
        type: "POST",
        success: function (res) {
            if (res.success){
                console.log(res.message)
            }else{
                console.log(res.message)
            }
        },
        error: function (err) {
            console.error(err.responseText);
        }
    });
}

function addToCart(id) {
    $.ajax({
        cache: false,
        url: "/addToCart/" + id,
        type: "GET",
        success: function (res) {
            if (res==="success"){
                var list = [];
                var carts = localStorage.getItem("carts");
                if (carts!==null && carts!=="" && carts!== undefined){
                    var jsonObject = JSON.parse(carts);
                    jsonObject.push({
                        id: id,
                        value: 1
                    })
                    localStorage.setItem("carts", JSON.stringify(jsonObject));
                }else{
                    list.push({
                        id: id,
                        value: 1
                    });

                    localStorage.setItem("carts", JSON.stringify(list));
                }
            }
        },
        error: function (err) {
            console.error(err.responseText);
        }
    });
}

function removeFromCart(subId) {
    var id = subId.split("|")[0];
    var prodId = subId.split("|")[1];
    var v = parseInt($("#point").val());
    var discount = (v * 1000);
    console.log(this);
    $.ajax({
        cache: false,
        url: "/cart/removeItem/" + parseInt(prodId),
        type: "GET",
        success: function (res) {
            if (res==="success"){
                var div = "#instock" + prodId;
                $(div).parent('div').remove();
                $("#subtotal-money").text(onSetTotalMoney());
                $("#total-money").text((onSetTotalMoney() - discount).toString());

                var carts = localStorage.getItem("carts");
                if (carts!==null && carts!=="" && carts!== undefined){
                    var jsonObject = JSON.parse(carts);
                    var list = jsonObject.filter(f => f.id!==parseInt(prodId))
                    initQuantity(true, list);
                    localStorage.setItem("carts", JSON.stringify(list));
                }
            }
        },
        error: function (err) {
            console.error(err.responseText);
        }
    });
}

$("#customerPhone").on('change', function(){
    var phone = $(this).val();

    $.ajax({
        cache: false,
        url: "/cart/get-account/" + phone,
        type: "POST",
        success: function (res) {
            console.log(res)
            $("#customerName").text(res['fullname'] != null ? res['fullname'] : "");
            $("#customerPoint").text(res['point'] != null ? res['point'] : "");
            $('#pointMax').val(res['point'] != null ? res['point'] : 0);
            $('#point').val(0);
        },
        error: function (err) {
            console.error(err.responseText);
            $("#customerName").text("");
            $("#customerPoint").text("");
            $('#pointMax').val(0);
            $('#point').val(0);
        }
    });
})

function initQuantity(isObject, jsonObject) {
    var carts = isObject ? jsonObject : JSON.parse(localStorage.getItem("carts"));
    console.log(carts);
    if (carts!==null && carts!=="" && carts!== undefined){
        carts.forEach(item=>{
            var id = "#quantity" + item.id;
            var value = item.value;

            $(id).val(value);
        })
    }

}