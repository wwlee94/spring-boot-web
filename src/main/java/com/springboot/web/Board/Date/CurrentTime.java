package com.springboot.web.Board.Date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentTime {

    //현재 시간 추가하기
    SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.KOREA );

    public String getStringCurrentTime(){
        Date currentTime = new Date();
        String date=formatter.format(currentTime);
        return date;
    }
}
