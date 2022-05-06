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
        <div id="wu-toolbar2">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refreshMemberCard()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printMemberCard()"
                   plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportMemberCardExcel()"
                   plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-style" onclick="consume()" plain="true">消费</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-style-add" onclick="recharge()"
                       plain="true">充值</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-style-edit" onclick="adjustPoint()"
                       plain="true">积分调整</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-start" onclick=" activate()"
                       plain="true">会员卡激活</a>
                </c:if>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-key" onclick="loss()"
                       plain="true" id = "loss">会员卡挂失</a>
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-lightning" onclick="unloss()"
                       plain="true">会员卡挂失解除</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-stop" onclick="cancel()" plain="true">会员卡注销</a>
                </c:if>
            </div>
            <c:if test="${member.membertype == 0 }">
                <div class="wu-toolbar-search">
                    <label>起始时间：</label><input class="easyui-datebox" editable="false" style="width:100px; padding: 5px"
                                               name="startTime" id="startTime">
                    <label>结束时间：</label><input class="easyui-datebox" editable="false" style="width:100px; padding: 5px"
                                               name="endTime" id="endTime">
                    <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px" title="可以通过会员名称搜索...."
                                              name="keyword" id="keyword">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMemberCardByCondition()">开始检索</a>
                </div>
            </c:if>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid4" toolbar="#wu-toolbar2"></table>
    </div>
</div>
<script type="text/javascript">
	var memberGrade = ${member.membertype};
</script>
<script type="text/javascript" src="${baseurl}js/pages/memberCard/MemberCard.js"></script>

</body>

</html>



