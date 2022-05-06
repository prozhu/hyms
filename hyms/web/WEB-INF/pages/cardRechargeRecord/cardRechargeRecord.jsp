<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	        <div id="wu-toolbar4">
	            <div class="wu-toolbar-button">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshRechargeRecord()"
	                   plain="true">刷新</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printRechargeRecord()" plain="true">打印</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportRechargeRecordExcel()"
	                   plain="true">导出报表</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
	            </div>
	            <div class="wu-toolbar-search">
	                <label>起始时间：</label><input class="easyui-datebox" editable="false" style="width:100px" name="startTime1"
	                                           id="startTime1">
	                <label>结束时间：</label><input class="easyui-datebox" editable="false" style="width:100px" name="endTime1"
	                                           id="endTime1">
	                <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px" title="可以通过会员名称搜索...."
	                                          name="keyword1" id="keyword1">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search"
	                   onclick="findMemberCardRechargeByCondition()">开始检索</a>
	            </div>
	        </div>
	        <!-- End of toolbar -->
	        <table id="wu-datagrid2" toolbar="#wu-toolbar4"></table>
	    </div>
	</div>
	
	<script type="text/javascript" src="${baseurl}js/pages/cardRechargeRecord/cardRechargeRecord.js"></script>

</body>
</html>