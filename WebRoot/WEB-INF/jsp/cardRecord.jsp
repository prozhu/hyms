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
        <div id="wu-toolbar5">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshCardRecord()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printCardRecord()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportCardRecordExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
            <div class="wu-toolbar-search">
	                <label>起始时间：</label><input class="easyui-datebox" editable = "false" style="width:100px" name="startTime2" id = "startTime2">
	                <label>结束时间：</label><input class="easyui-datebox"editable = "false"  style="width:100px" name="endTime2" id = "endTime2">
	                <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px"   title = "可以通过会员名称搜索...." name="keyword2"  id = "keyword2">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCardRecordByCondition()" >开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid1" toolbar="#wu-toolbar5"></table>
    </div>
</div>
<script type="text/javascript">

	/**
	 * 有条件的查询所有会员卡激活、挂失记录
	 */
	function findCardRecordByCondition() {
		var startTime = $.trim($('input[name="startTime2"]').val());
		var endTime = $.trim($('input[name="endTime2"]').val());
		$('#wu-datagrid1').datagrid('load', {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
			endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
			keyword : $.trim($("#keyword2").val())
		});
	}


	

	//导出所有会员卡激活、挂失记录
	function exportCardRecordExcel() {
		var startTime = $.trim($('input[name="startTime2"]').val());
		var endTime = $.trim($('input[name="endTime2"]').val());
		download("${baseurl}cardRecord/exportExcel.action", {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
	        endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
	        keyword : $("#keyword2").val()
		});
	}

	//打印报表
	function printCardRecord() {
		CreateFormPage("会员信息", $('#wu-datagrid1'));
	}

	//刷新表格数据
	function refreshCardRecord() {
		$('#wu-datagrid1').datagrid('reload');
	}


	   /**
     *  载入数据
     */
    $('#wu-datagrid1').datagrid({
        url : '${baseurl}cardRecord/findAllByCondition.action',
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
        }, {
            field : 'membercardid',
            title : '会员卡号',
            width : 55
        }, {
            field : 'operationtype',
            title : '操作类型',
            sortable : true,
            width : 50
        },  {
            field : 'changetime',
            title : '激活/挂失时间',
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