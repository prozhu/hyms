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
	        <div id="wu-toolbar8">
	            <div class="wu-toolbar-search">
	                <div id="chooseYear" style="display:inline;">
	                    <label>选择年份：</label>
	                    <select panelHeight="auto" id="year" style="margin-right:10px">
	                        <option value="">请选择一个年份</option>
	                    </select>
	                </div>
	
	                <div id="week" style="display:none;">
	                    <label>选择时间：</label><input class="easyui-datebox" style="width:80px" id="time"
	                                               style="margin-right:10px"/>
	                </div>
	
	                <label>选择图表形式：</label>
	                <select panelHeight="auto" id="mark" style="margin-right:10px">
	                    <option value="year">年度图表</option>
	                    <option value="quarter">季度图表</option>
	                    <option value="month">月度图表</option>
	                    <option value="week">周度图表</option>
	                </select>
	
	                <label>选择图表类型：</label>
	                <select panelHeight="auto" id="type" style="margin-right:10px">
	                    <option value="bar">柱状图</option>
	                    <option value="line">线形图</option>
	                    <option value="pie">饼状图</option>
	                </select>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="search8" >生成图表</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveImageInfo()" plain="true"
	                   id="saveChart" style="display:none">保存图表图片</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-arrow-down" onclick="saveAsLocalImage()"
	                   plain="true" id="downloadChart" style="display:none">下载图表图片</a>
	            </div>
	        </div>
	        <!-- End of toolbar -->
	        <div id="consumeChart_container" style="margin-left: 10%; margin-top: 20px; ">Loading chart...ing</div>
	    </div>
	</div>
	<script type="text/javascript" src="${baseurl}js/pages/consumeRecord/consumeRecordChart.js"></script>
</body>
</html>