var dm="mdc"
$(function() {
    validateKickout();
    validateRule();

    if ($.cookie(dm+'rememberme') == 'true') {
        $("input[name='rememberme']").attr("checked",true);
        $(".uname").val(decodeURIComponent($.cookie(dm+'usrname')));
        $(".pword").val(decodeURIComponent($.cookie(dm+'pwd'))).focus();

    } else {
        $(".uname").val('').focus();
        $(".pword").val('');
        $(".uname").focus(); //读取cookie中rememberme(记住我)的值为false;用户名获取焦点
    }
    $("#btnSubmit").on("click",function () {
        if ($.validate.form()) {
            login();
        }
    });

    common.initOnkeydown(function () {
        if ($.validate.form()) {
            login();
        }
    })

});


$.validator.setDefaults({
    submitHandler: function() {
        if ($.validate.form()) {
            login();
        }
    }
});

function login() {
    $.modal.loading($("#btnSubmit").data("loading"));
    var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');
    $.ajax({
        type: "post",
        url: ctx + "sys/user/doLogin",
        data: {
            "account": username,
            "password": password,
            "validateCode": validateCode,
            "rememberMe": rememberMe
        },
        success: function(r) {
            if (r.code == web_status.SUCCESS) {
                setcookie();
                location.href = ctx + 'index';
            } else {
            	$.modal.closeLoading();
            	// $('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#loginForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的账号",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

function validateKickout() {
    /*if (getParam("kickout") == 1) {
        layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
            icon: 0,
            title: "系统提示"
        },
        function(index) {
            //关闭弹窗
            layer.close(index);
            if (top != self) {
                top.location = self.location;
            } else {
                var url  =  location.search;
                if (url) {
                    var oldUrl  = window.location.href;
                    var newUrl  = oldUrl.substring(0,  oldUrl.indexOf('?'));
                    self.location  = newUrl;
                }
            }
        });
    }*/
}

var setcookie=function(){
    var rememberMe = $("input[name='rememberme']").is(':checked');
    if(rememberMe){
        $.cookie(dm+'rememberme',"true",{expires:60}); //创建cookie 记住我; 值为true ;有效期为60天
        $.cookie(dm+'usrname',encodeURIComponent($(".uname").val()),{expires:60}); //usrname 值为 用户名  有效期60天
        $.cookie(dm+'pwd',encodeURIComponent($(".pword").val()),{expires:60}); //pwd 值为密码  有效期60天
    }else{
        $.cookie(dm+'rememberme',"false");
        $.cookie(dm+'usrname',''); //usrname 值为 用户名  有效期60天
        $.cookie(dm+'pwd',''); //pwd 值为密码  有效期60天
    }
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}