<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
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
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refresh()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="print()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportMemberInfoExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
            <div class="wu-toolbar-search">
	                <label>起始时间：</label><input class="easyui-datebox" editable = "false" style="width:100px" name="startTime" id = "startTime">
	                <label>结束时间：</label><input class="easyui-datebox"editable = "false"  style="width:100px" name="endTime" id = "endTime">
	                <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px"   title = "可以通过会员名称搜索...." name="keyword"  id = "keyword">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAllByCondition()" >开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid1" toolbar="#wu-toolbar5"></table>
    </div>
</div>
<script type="text/javascript">

	/**
	 * 有条件的查询所有会员信息
	 */
	function findAllByCondition() {
	    $('#wu-datagrid1').datagrid('load', {
	        startTime : $.trim($('input[name="startTime"]').val()) + ' 00:00:00',
	        endTime : $.trim($('input[name="endTime"]').val()) + ' 23:59:59',
	        keyword : $.trim($("#keyword").val())
	    });
	}


	

	//导出会员信息表格
	function exportMemberInfoExcel() {
		download("${baseurl}cardRecord/exportExcel.action", {
			startTime : $('input[name="startTime"]').val(),
	        endTime : $('input[name="endTime"]').val(),
	        keyword : $("#keyword").val()
		});
	}

	//打印报表
	function print() {
		CreateFormPage("会员信息", $('#wu-datagrid1'));
	}

	//刷新表格数据
	function refresh() {
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
            width : 50
        }, {
            field : 'memberid',
            title : '会员编号',
            width : 80,
        }, {
            field : 'membercardid',
            title : '会员卡号',
            width : 50
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