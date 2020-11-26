package com.springboot.web.board.date;

import com.springboot.web.board.domain.Board;
import com.springboot.web.board.domain.BoardReply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConverterTime {

    //Datetime 형 변환
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    //Date 형 변환
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

    //현재시간을 Datetime 형식으로 가져오기
    public String getStringDateTime() {
        Date currentTime = new Date();
        String date = formatter.format(currentTime);
        return date;
    }

    //Datetime형식을 Date형식 String으로 가져오기
    public String getStringDateByBoard(Board board) throws ParseException {
        Date date = formatter.parse(board.getDateTime());
        String stringDate = dateFormat.format(date);
        System.out.println(stringDate);
        return stringDate;
    }

    //Datetime형식을 Date형식 String으로 가져오기 (reply)
    public void getStringDateByReply(List<BoardReply> replyList) throws ParseException {

        for (int i = 0; i < replyList.size(); i++) {
            Date date = formatter.parse(replyList.get(i).getDateTime());
            String stringDate = dateFormat.format(date);
            replyList.get(i).setDate(stringDate);
        }
    }
}
