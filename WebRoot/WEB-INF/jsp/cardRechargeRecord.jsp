<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<%@ include file="/WEB-INF/jsp/common_css.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshRechargeRecord()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printRechargeRecord()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportRechargeRecordExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
	            <div class="wu-toolbar-search">
		                <label>起始时间：</label><input class="easyui-datebox" editable = "false"  style="width:100px" name="startTime1" id = "startTime1">
		                <label>结束时间：</label><input class="easyui-datebox"  editable = "false" style="width:100px" name="endTime1" id = "endTime1">
		                <label>关键词：</label><input class="wu-text easyui-tooltip"    style="width:100px"  title = "可以通过会员名称搜索...." name="keyword1"  id = "keyword1">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMemberCardRechargeByCondition()">开始检索</a>
	            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid2" toolbar="#wu-toolbar4"></table>
    </div>
</div>

<script type="text/javascript">

	/**
	 * 有条件的查询所有的会员卡充值记录信息
	 */
	function findMemberCardRechargeByCondition() {
		var startTime = $.trim($('input[name="startTime1"]').val());
		var endTime = $.trim($('input[name="endTime1"]').val());
		$('#wu-datagrid2').datagrid('load', {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
			endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
			keyword : $.trim($("#keyword1").val())
		});
	}


	//导出会员卡充值记录报表
	function exportRechargeRecordExcel() {
		var startTime = $.trim($('input[name="startTime1"]').val());
		var endTime = $.trim($('input[name="endTime1"]').val());
		download("${baseurl}cardRechargeRecord/exportExcel.action", {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
	        endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
	        keyword : $("#keyword1").val()
		});
	}

	//打印报表
	function printRechargeRecord() {
		CreateFormPage("会员信息", $('#wu-datagrid2'));
	}

	//刷新表格数据
	function refreshRechargeRecord() {
		$('#wu-datagrid2').datagrid('reload');
	}


	/**
	 *  载入数据
	 */
	$('#wu-datagrid2').datagrid({
		url : '${baseurl}cardRechargeRecord/findAllByCondition.action',
		rownumbers : true,
		singleSelect : false,
		total : 10,
		pageSize : 5,
		pageList : [ 10, 15, 20 ],
		pagination : true,
		multiSort : true,
		fitColumns : true,
		fit : true,
		columns : [ [ {
			checkbox : true
		}, {
			field : 'membername',
			title : '会员名称',
			width : 40
		}, {
			field : 'memberid',
			title : '会员编号',
			width : 80,
			sortable : true
		}, {
			field : 'membercardid',
			title : '会员卡号',
			width : 55
		}, {
			field : 'rechargemoney',
			title : '充值金额',
			sortable : true,
			width : 50
		},  {
			field : 'changetime',
			title : '充值时间',
			sortable : true,
			width : 100,
			formatter : function(value, row, index) {
				var unixTimestamp = new Date(value);
				return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
			}
		} ] ]
	});
</script>
</body>
</html>