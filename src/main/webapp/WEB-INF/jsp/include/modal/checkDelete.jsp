<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Modal -->
<div class="modal fade" id="infoDelete" role="dialog">
    <div class="modal-dialog">

        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="modal-deleteTitle"></h4>
                <button class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p style="text-align: center">정말로 삭제하시겠습니까?</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="modalDeleteButton" class="btn btn-warning">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>