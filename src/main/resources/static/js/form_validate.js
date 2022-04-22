function Validator(option)  {
    var selectorRules = {};
    function validate(inputElement, rule) {
        var errorMessage;
        var errorElement = inputElement.parentElement.querySelector(option.errorSelector);
        var rules = selectorRules[rule.selector];
        for (var i = 0; i < rules.length; i++) {
            errorMessage = rules[i](inputElement.value);
            if(errorMessage)
                break;
        }
        if(errorMessage) {
            errorElement.innerHTML = errorMessage;
            inputElement.parentElement.classList.add("invalid");
        }
        else {
            errorElement.innerHTML = "";
            inputElement.parentElement.classList.remove("invalid");
        }

        return !errorMessage;
    }

    var formElement = document.querySelector(option.form);
    if (formElement) {
        formElement.onsubmit = function (e) {
            var isFormValid = true;
            option.rules.forEach(function (rule) {
                var inputElement = formElement.querySelector(rule.selector);
                var isValid = validate(inputElement, rule);
                if(!isValid) isFormValid = false;
            });
            if(!isFormValid) e.preventDefault();
        }
        option.rules.forEach(function (rule) {
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }
            var inputElement = formElement.querySelector(rule.selector);
            if (inputElement) {
                inputElement.onblur = function () {
                    validate(inputElement, rule);
                }
            }
        });


    }
}

Validator.isRequired = function (selector) {
    return {
        selector : selector,
        test : function (value) {
            return value.trim() ? undefined : 'Vui lòng nhập trường này!'
        }
    }
}

Validator.isEmail = function (selector) {
    return {
        selector : selector,
        test : function (value) {
            var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
            return value.trim().match(emailRegex) ? undefined : 'Email không hợp lệ!'
        }
    }
}

Validator.minLength = function (selector, min) {
    return {
        selector : selector,
        test : function (value) {
            return value.length >= min ? undefined : 'Mật khẩu phải chứa ít nhất ' + min + ' kí tự!'
        }
    }
}

Validator.isConfirmed = function (selector, getPassword) {
    return {
        selector : selector,
        test : function (value) {
            return value === getPassword() ? undefined : 'Xác nhận mật khẩu không chính xác!'
        }
    }
}

Validator.isPhone = function (selector) {
    return {
        selector : selector,
        test : function (value) {
            var phoneRegex = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g
            return value.trim().match(phoneRegex) ? undefined : 'Số điện thoại không hợp lệ!'
        }
    }
}

Validator.isActivePrice = function (selector) {
    return {
        selector: selector,
        test: function (value) {
            var priceRegex = /(^[0-9]+$)/
            return value.trim().match(priceRegex) ? undefined : 'Giá sản phẩm không hợp lệ!'
        }
    }
}

Validator.isActiveInt = function (selector) {
    return {
        selector : selector,
        test : function (value) {
            var priceRegex = /(^[0-9]+$)/
            return value.trim().match(priceRegex) ? undefined : 'Số lượng không hợp lệ!'
        }
    }
}