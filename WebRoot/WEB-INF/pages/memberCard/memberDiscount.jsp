<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/pages/common/tag.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_js.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_css.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${baseurl}css/email.css"/> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="wu-toolbar11">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddDiscount()" plain="true">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditDiscount()" plain="true">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()" plain="true">删除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refresh()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="print()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportMemberInfoExcel()"
                   plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid11" toolbar="#wu-toolbar11"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="wu-dialog11" class="easyui-dialog" data-options=" closable: false,closed:true,iconCls:'icon-save'"
     style="width:400px; padding:10px;">
    <!-- 表单 -->
    <form id="wu-form1" method="post">
        <input type="hidden" name="id" class="wu-text"/>
        <table>
            <tr>
                <td width="60" align="right">累计消费区间(低):</td>
                <td><input type="text" name="lowConsume" class="wu-text" id="lowConsume"/></td>
            </tr>
            <tr>
               <td width="60" align="right">累计消费区间(高):</td>
                <td><input type="text" name="highConsume" class="wu-text" id="highConsume"/></td>
            </tr>
            <tr>
                <td align="right">会员级别:</td>
                <td><input type="text" name="grade" class="wu-text easyui-validatebox" id="grade"
                           required="true"/></td>
            </tr>
            <tr>
                <td align="right">折扣:</td>
                <td><input type="text" name="discount" class="wu-text easyui-validatebox" id="discount" required="true"
                           /></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript" src="${baseurl}js/pages/memberCard/memberDiscount.js"></script>


</body>

</html>