var pw_passed =true;
function idcheck(){
    var id = document.getElementById("uid").value; // 아이디

    if(id.length == 0) {
        document.getElementById("uidCheck").innerText="아이디를 입력해주세요";
    } else {
        document.getElementById("uidCheck").innerText="";
    }
}
function pwcheck(){
    var pw = document.getElementById("password").value; //비밀번호

    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;     // 원하는 특수문자 추가 제거

    if(pw.length == 0) {
        document.getElementById("passwordCheck").innerText="비밀번호를 입력해주세요.";
    }
    else if(!pattern1.test(pw)||!pattern2.test(pw)||!pattern3.test(pw)||pw.length<8||pw.length>16){
        document.getElementById("passwordCheck").innerText="영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.";
    }
    else{
        document.getElementById("passwordCheck").innerText="";
    }


}

function pw2check() {
    var pw = document.getElementById("password").value; //비밀번호
    var pw2 = document.getElementById("password2").value; // 확인 비밀번호

    if( pw != pw2) {
        document.getElementById("password2Check").innerText = "비밀번호가 일치하지 않습니다.";
    }else{
        document.getElementById("password2Check").innerText = "";
    }
}

function registerCheck(){
    pw_passed = true;

    var id = document.getElementById("uid").value; // 아이디
    var pw = document.getElementById("password").value; //비밀번호
    var pw2 = document.getElementById("password2").value; // 확인 비밀번호
    var name = document.getElementById("name").value; // 이름
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;     // 원하는 특수문자 추가 제거

    if(id.length == 0) {
        document.getElementById("uidCheck").innerText="아이디를 입력해주세요";
        return false;
    } else {
        document.getElementById("uidCheck").innerText="";
        pw_passed = true;
    }

    if(pw.length == 0) {
        document.getElementById("passwordCheck").innerText="비밀번호를 입력해주세요.";
        return false;
    }
    else if(!pattern1.test(pw)||!pattern2.test(pw)||!pattern3.test(pw)||pw.length<8||pw.length>16){
        document.getElementById("passwordCheck").innerText="영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.";
        return false;
    }
    else{
        document.getElementById("passwordCheck").innerText="";
        pw_passed = true;
    }

    if( pw != pw2) {
        document.getElementById("password2Check").innerText = "비밀번호가 일치하지 않습니다.";
        return false;
    }else{
        document.getElementById("password2Check").innerText = "";
        pw_passed = true;
    }

    if(name.length == 0){
        document.getElementById("nameCheck").innerText = "이름을 입력해주세요.";
        return false;
    }else{
        document.getElementById("nameCheck").innerText = "";
        pw_passed = true;
    }

    return pw_passed;
}

window.onload = function() {
    if (getCookie("id")) { // getCookie함수로 id라는 이름의 쿠키를 불러와서 있을경우
        document.getElementById("userID").value = getCookie("id"); //input 이름이 id인곳에 getCookie("id")값을 넣어줌
        document.getElementById("remember").checked = true; // 체크는 체크됨으로
    }
}

function setCookie(name, value, expiredays) //쿠키 저장함수
{
    var todayDate = new Date();
    todayDate.setDate(todayDate.getDate() + expiredays);
    document.cookie = name + "=" + escape(value) + "; path=/; expires="
        + todayDate.toGMTString() + ";"
}

function getCookie(Name) { // 쿠키 불러오는 함수
    var search = Name + "=";
    if (document.cookie.length > 0) { // if there are any cookies
        offset = document.cookie.indexOf(search);
        if (offset != -1) { // if cookie exists
            offset += search.length; // set index of beginning of value
            end = document.cookie.indexOf(";", offset); // set index of end of cookie value
            if (end == -1)
                end = document.cookie.length;
            return unescape(document.cookie.substring(offset, end));
        }
    }
}

function sendit() {
    if (document.getElementById("remember").value == "on") { // 아이디 저장을 체크 하였을때
        setCookie("id", document.getElementById("userID").value, 7); //쿠키이름을 id로 아이디입력필드값을 7일동안 저장
    } else { // 아이디 저장을 체크 하지 않았을때
        setCookie("id",document.getElementById("userID").value, 0); //날짜를 0으로 저장하여 쿠키삭제
    }
}

