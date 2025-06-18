$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault(); //기본 제출 방지

        let formData = {
            loginId: $("#loginId").val(),
            password: $("#password").val()
        };

        $.ajax({
            url: "/login",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            dataType: "json",
            success: function (response) {
                if(response.success) {
                    alert("로그인 성공")
                    window.location.href="/";
                }  else{
                    alert(response.message);
                 }
            },
               error: function () {
               alert("서버 오류 발생")
            }
        });
    });
});
