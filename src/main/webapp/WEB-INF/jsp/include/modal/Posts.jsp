<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="modal-title"></h4>
                <button class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <table class="table">
                    <tr>
                        <td>게시글 제목</td>
                        <td><textarea class="form-control" id="title" rows="1"></textarea></td>
                    </tr>
                    <tr>
                        <td>게시글 내용</td>
                        <td><textarea class="form-control" id="contents" rows="10"></textarea></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" id="modalSubmit" class="btn btn-success">Submit</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>