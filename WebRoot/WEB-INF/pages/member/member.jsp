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
        <div id="wu-toolbar1">
            <div class="wu-toolbar-button">
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
                </c:if>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()" plain="true">删除</a>
                </c:if>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refresh()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="print()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportMemberInfoExcel()"
                   plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-add" onclick="addCard()"
                       plain="true">办卡</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-compressed" onclick="reCard()"
                       plain="true">补卡</a>
                </c:if>
            </div>
            <c:if test="${member.membertype == 0 }">
                <div class="wu-toolbar-search">
                    <label>起始时间：</label><input class="easyui-datebox" editable="false" style="width:100px"
                                               name="startTime5" id="startTime5">
                    <label>结束时间：</label><input class="easyui-datebox " editable="false" style="width:100px"
                                               name="endTime5" id="endTime5">
                    <label>关键词：</label><input class="wu-text easyui-tooltip" style="width:100px" title="可以通过会员名称搜索...."
                                              name="keyword5" id="keyword5">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAllByCondition()">开始检索</a>
                </div>
            </c:if>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar1"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="wu-dialog" class="easyui-dialog" data-options=" closable: false,closed:true,iconCls:'icon-save'"
     style="width:400px; padding:10px;">
    <!-- 表单 -->
    <form id="wu-form" method="post">
        <input type="hidden" name="id" class="wu-text"/>
        <table>
            <tr>
                <td width="60" align="right">登录名称:</td>
                <td><input type="text" name="loginname" class="wu-text" id="loginname"/></td>
            </tr>
            <tr>
                <td align="right">登录密码:</td>
                <td><input type="password" name="loginpwd" class="wu-text easyui-validatebox" id="loginpwd"
                           required="true" validtype="length[5, 32]"/></td>
            </tr>
            <tr>
                <td align="right">会员名称:</td>
                <td><input type="text" name="membername" class="wu-text easyui-validatebox" id="membername"
                           required="true"/></td>
            </tr>
            <tr>
                <td align="right">性别:</td>
                <td>
                    <input type="radio" name="sex" id="sex" value="男">男</input>
                    <input type="radio" name="sex" id="sex" value="女">女</input>
                </td>
            </tr>
            <tr>
                <td align="right">年龄:</td>
                <td><input type="text" name="age" class="wu-text easyui-validatebox" id="age" required="true"
                           validtype="age"/></td>
            </tr>
            <tr>
                <td align="right">电话号码:</td>
                <td><input type="text" name="phone" class="wu-text easyui-validatebox" required="true"
                           validtype="mobile" id="phone"/></td>
            </tr>
            <tr>
                <td align="right">电子邮件:</td>
                <td><input type="text" name="email" class="wu-text easyui-validatebox" validtype="email" id="email"/>
                </td>
            </tr>
            <tr>
                <td valign="top" align="right">备 注:</td>
                <td>
                    <textarea name="remark" rows="6" class="wu-textarea" style="width:260px" id="remark"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript" src="${baseurl}js/pages/member/member.js"></script>

</body>

</html>