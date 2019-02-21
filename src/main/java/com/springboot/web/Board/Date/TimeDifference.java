package com.springboot.web.Board.Date;

import com.springboot.web.Board.domain.Board;
import com.springboot.web.Board.domain.BoardReply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//TODO : 중복되는 코드 줄일수 있도록..!
public class TimeDifference {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    long seconds;
    long minutes;
    long hours;
    long days;
    long month;
    long year;


    //리스트 반환
    public void getBoardListTimeDifference(List<Board> boardList) throws ParseException {

        for (int i = 0; i < boardList.size(); i++) {
            //현재시간
            Date EndDate = new Date();
            //등록한 시간
            Date StartDate = format.parse(boardList.get(i).getDateTime());

            //밀리세컨즈 단위 시간 차
            Long diff = EndDate.getTime() - StartDate.getTime();

            setClassify(diff);

            if (year <= 0) {
                if (month <= 0) {
                    if (days <= 0) {
                        if (hours <= 0) {
                            if (minutes <= 0) {
                                boardList.get(i).setTimeDifference(seconds + "초 전");
                            } else {
                                boardList.get(i).setTimeDifference(minutes + "분 " + seconds + "초 전");
                            }
                        } else {
                            boardList.get(i).setTimeDifference(hours + "시간 " + minutes + "분 전");
                        }
                    } else {
                        boardList.get(i).setTimeDifference(days + "일 " + hours + "시간 전");
                    }
                } else {
                    boardList.get(i).setTimeDifference(month + "개월 " + days % 30 + "일 전");
                }
            } else {
                //continue 덕에 위에 조건 성립하면 이 아래 코드는 실행 안함
                boardList.get(i).setTimeDifference(year + "년 " + year % 12 + "개월 전");
            }
        }//for

    }

    //단일 객체 반환
    public void getBoardTimeDifference(Board board) throws ParseException {

        //현재시간
        Date EndDate = new Date();

        //등록한 시간
        Date StartDate = format.parse(board.getDateTime());

        //밀리세컨즈 단위 시간 차
        Long diff = EndDate.getTime() - StartDate.getTime();

        setClassify(diff);

        if (year <= 0) {
            if (month <= 0) {
                if (days <= 0) {
                    if (hours <= 0) {
                        if (minutes <= 0) {
                            board.setTimeDifference(seconds + "초 전");
                        } else {
                            board.setTimeDifference(minutes + "분 " + seconds + "초 전");
                        }
                    } else {
                        board.setTimeDifference(hours + "시간 " + minutes + "분 전");
                    }
                } else {
                    board.setTimeDifference(days + "일 " + hours + "시간 전");
                }
            }//month <= 0
            else {
                board.setTimeDifference(month + "개월 " + days % 30 + "일 전");
            }
        }//year <= 0
        else {
            board.setTimeDifference(year + "년 " + year % 12 + "개월 전");
        }
    }//getBoardTimeDifference

    //리스트 객체 반환 - 게시글에 따른 모든 댓글 작성일
    public void getReplyTimeDifference(List<BoardReply> replyList) throws ParseException {

        for (int i = 0; i < replyList.size(); i++) {
            //현재시간
            Date EndDate = new Date();

            //등록한 시간
            Date StartDate = format.parse(replyList.get(i).getDateTime());

            //밀리세컨즈 단위 시간 차
            Long diff = EndDate.getTime() - StartDate.getTime();

            setClassify(diff);

            if (year <= 0) {
                if (month <= 0) {
                    if (days <= 0) {
                        if (hours <= 0) {
                            if (minutes <= 0) {
                                replyList.get(i).setTimeDifference(seconds + "초 전");
                            } else {
                                replyList.get(i).setTimeDifference(minutes + "분 " + seconds + "초 전");
                            }
                        } else {
                            replyList.get(i).setTimeDifference(hours + "시간 " + minutes + "분 전");
                        }
                    } else {
                        replyList.get(i).setTimeDifference(days + "일 " + hours + "시간 전");
                    }
                } else {
                    replyList.get(i).setTimeDifference(month + "개월 " + days % 30 + "일 전");
                }
            } else {
                //continue 덕에 위에 조건 성립하면 이 아래 코드는 실행 안함
                replyList.get(i).setTimeDifference(year + "년 " + year % 12 + "개월 전");
            }
        }//for

    }//getReplyTimeDifference

    //realTime -> Ajax 1초단위 시간 갱신을 위한 함수
    public void listRealTimeDifference(List<Map> list) throws ParseException {
        Map<String, String> tempMap;

        for (int i = 0; i < list.size(); i++) {

            tempMap = list.get(i);
            //현재시간
            Date EndDate = new Date();

            //등록한 시간
            Date StartDate = format.parse(tempMap.get("dateTime"));

            //밀리세컨즈 단위 시간 차
            Long diff = EndDate.getTime() - StartDate.getTime();

            setClassify(diff);

            if (year <= 0) {
                if (month <= 0) {
                    if (days <= 0) {
                        if (hours <= 0) {
                            if (minutes <= 0) {
                                tempMap.put("timeDifference", seconds + "초 전");
                            } else {
                                tempMap.put("timeDifference", minutes + "분 " + seconds + "초 전");
                            }
                        } else {
                            tempMap.put("timeDifference", hours + "시간 " + minutes + "분 전");
                        }
                    } else {
                        tempMap.put("timeDifference", days + "일 " + hours + "시간 전");
                    }
                } else {
                    tempMap.put("timeDifference", month + "개월 " + days % 30 + "일 전");
                }
            } else {
                //continue 덕에 위에 조건 성립하면 이 아래 코드는 실행 안함
                tempMap.put("timeDifference", year + "년 " + year % 12 + "개월 전");
            }
        }//for
    }

    //realTime -> Ajax 1초단위 시간 갱신을 위한 함수
    public void readRealTimeDifference(List<Map> list) throws ParseException {
        Map<String, String> tempMap;

        for (int i = 0; i < list.size(); i++) {

            tempMap = list.get(i);
            //현재시간
            Date EndDate = new Date();

            //등록한 시간
            Date StartDate = format.parse(tempMap.get("dateTime"));

            //밀리세컨즈 단위 시간 차
            Long diff = EndDate.getTime() - StartDate.getTime();

            setClassify(diff);

            if (year <= 0) {
                if (month <= 0) {
                    if (days <= 0) {
                        if (hours <= 0) {
                            if (minutes <= 0) {
                                tempMap.put("timeDifference", seconds + "초 전");
                            } else {
                                tempMap.put("timeDifference", minutes + "분 " + seconds + "초 전");
                            }
                        } else {
                            tempMap.put("timeDifference", hours + "시간 " + minutes + "분 전");
                        }
                    } else {
                        tempMap.put("timeDifference", days + "일 " + hours + "시간 전");
                    }
                } else {
                    tempMap.put("timeDifference", month + "개월 " + days % 30 + "일 전");
                }
            } else {
                //continue 덕에 위에 조건 성립하면 이 아래 코드는 실행 안함
                tempMap.put("timeDifference", year + "년 " + year % 12 + "개월 전");
            }
        }//for
    }

    public void setClassify(Long diff) {

        seconds = diff / 1000 % 60;
        minutes = diff / (60 * 1000) % 60;
        hours = diff / (60 * 60 * 1000) % 24;
        days = diff / (24 * 60 * 60 * 1000);

        month = days / 30;
        year = days / 365;
    }
}
