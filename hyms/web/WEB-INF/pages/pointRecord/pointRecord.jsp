<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/tag.jsp"%>
<%@ include file="/WEB-INF/pages/common/common_js.jsp"%>
<%@ include file="/WEB-INF/pages/common/common_css.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="wu-toolbar3">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshPointRecord()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printPointRecord()"
                   plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportPointRecordExcel()"
                   plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
            <div class="wu-toolbar-search">
                <label>起始时间：</label><input class="easyui-datebox" editable="false" style="width:100px" name="startTime4"
                                           id="startTime4">
                <label>结束时间：</label><input class="easyui-datebox" editable="false" style="width:100px" name="endTime4"
                                           id="endTime4">
                <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px" title="可以通过会员名称搜索...."
                                          name="keyword4" id="keyword4">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                   onclick="findPointRecordByCondition()">开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid5" toolbar="#wu-toolbar3"></table>
    </div>
</div>
<script type="text/javascript" src="${baseurl}js/pages/pointRecord/pointRecord.js"></script>

</body>
</html>