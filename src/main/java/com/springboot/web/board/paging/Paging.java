package com.springboot.web.board.paging;

public class Paging {

    //게시글의 총 갯수
    int totalCount;

    //한 페이지에 몇개의 게시글을 보여줄 것인지 -> 10개씩
    int listCount = 5;

    //총 페이지의 개수
    int totalPage;

    //현재 페이지
    int page;

    //보여줄 시작페이지
    int startPage;

    //보여줄 마지막 페이지
    int endPage;

    public void init(int totalCount, int page) {
        this.totalCount = totalCount;
        this.page = page;

        //개수가 안떨어지면 여분의 페이지
        totalPage = totalCount / listCount;
        if (totalCount % listCount > 0) {
            totalPage++;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        //시작페이지 -> 마지막 +1 은 0~9가 아닌 1~10을 보여주기 위해
        startPage = ((page - 1) / listCount) * listCount + 1;

        //endPage = (((page - 1) / listCount) + 1) * listCount;
        //마지막페이지 -> 1~10 / 11~20으로 보여주기 위함
        endPage = startPage + listCount - 1;

        if (endPage > totalPage) {
            endPage = totalPage;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    //총 페이지의 개수를 구해주는 함수
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
