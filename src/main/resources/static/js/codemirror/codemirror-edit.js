$(document).ready(function(){
    var code =$(".codemirror-textarea")[0];
    var editor = CodeMirror.fromTextArea(code,{
        lineNumbers : true,         // 라인넘버 표시
        mode: "text/x-csrc",
        theme:"mbo"
        // scrollbarStyle: "simple",    // 스크롤바 스타일
        // keyMap: "sublime",           // 괄호강조
        // matchBrackets: true,         // 괄호강조
        // theme: "bespin",            // 테마
        // tabSize: 2,                  // 탭키 간격
        // lineWrapping: true,           // 가로 스크롤바 숨김, 너비에 맞게 줄바꿈.
        // highlightSelectionMatches: {showToken: /\w/, annotateScrollbar: true}, // 같은단어강조
    });
});