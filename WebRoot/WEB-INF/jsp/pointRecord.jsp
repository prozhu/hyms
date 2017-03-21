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
        <div id="wu-toolbar3">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshPointRecord()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printPointRecord()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportPointRecordExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
            <div class="wu-toolbar-search">
	                <label>起始时间：</label><input class="easyui-datebox"  editable = "false" style="width:100px" name="startTime4" id = "startTime4">
	                <label>结束时间：</label><input class="easyui-datebox" editable = "false" style="width:100px" name="endTime4" id = "endTime4">
	                <label>关键词：</label><input class="wu-text easyui-tooltip"  style="width:100px"  title = "可以通过会员名称搜索...." name="keyword4"  id = "keyword4">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPointRecordByCondition()" >开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid5" toolbar="#wu-toolbar3"></table>
    </div>
</div>

<script type="text/javascript">

	//回车键查询所有会员信息
	$(window).keydown(function(e){
	     var ev = window.event || e;
	        console.log(ev);
	        if (ev.keyCode == 13) {
	            $("#begin").click();
	        }
	}) 

	/**
	 * 有条件的查询所有会员卡的积分记录
	 */
	function findPointRecordByCondition() {
		var startTime = $.trim($('input[name="startTime4"]').val());
		var endTime = $.trim($('input[name="endTime4"]').val());
		$('#wu-datagrid5').datagrid('load', {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
			endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
			keyword : $.trim($("#keyword4").val())
		});
	}

	   //导出所有会员卡的积分记录
    function exportPointRecordExcel() {
		var startTime = $.trim($('input[name="startTime4"]').val());
		var endTime = $.trim($('input[name="endTime4"]').val());
		download("${baseurl}pointRecord/exportExcel.action", {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
	        endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
	        keyword : $("#keyword4").val()
		});
    }

	//打印报表
	function printPointRecord() {
		CreateFormPage("会员信息", $('#wu-datagrid5'));
	}

	//刷新表格数据
	function refreshPointRecord() {
		$('#wu-datagrid5').datagrid('reload');
	}



    /**
     *  载入数据
     */
    $('#wu-datagrid5').datagrid({
        url : '${baseurl}pointRecord/findAllByCondition.action',
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
            width : 90,
        }, {
            field : 'membercardid',
            title : '会员卡号',
            width : 60
        }, {
            field : 'points',
            title : '积分',
            width : 50
        }, {
            field : 'operationtype',
            title : '积分类型',
            sortable : true,
            width : 50
        },  {
            field : 'changetime',
            title : '变更时间',
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