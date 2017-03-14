<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<html>
<head>
<TITLE>会员管理系统</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script> -->
<LINK rel="stylesheet" type="text/css" href="${baseurl}styles/style.css">
<LINK rel="stylesheet" type="text/css" href="${baseurl}styles/login.css">
<link rel="stylesheet" type="text/css" href="easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="css/wu.css" />
<link rel="stylesheet" type="text/css" href="css/icon.css" />

<STYLE type="text/css">
.btnalink {
	cursor: hand;
	display: block;
	width: 80px;
	height: 29px;
	float: left;
	margin: 12px 28px 12px auto;
	line-height: 30px;
	background: url('${baseurl}images/login/btnbg.jpg') no-repeat;
	font-size: 14px;
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}
</STYLE>


</HEAD>
<BODY 
	style="background: #f6fdff url(${baseurl}images/login/bg1.jpg) repeat-x;">
	<FORM id="loginform" name="loginform"  method="post">
		
		<DIV class="logincon">

			<DIV class="title">
				<%-- <IMG alt="" src="${baseurl}images/login/logo.png"> --%>
			</DIV>

			<DIV class="cen_con">
			     <h1><font size=4" >会员管理系统</font></h1>
				<IMG alt="" src="${baseurl}images/login/bg2.png">
			</DIV>

			<DIV class="tab_con">

				<TABLE class="tab" border="0" cellSpacing="6" cellPadding="8">
					<TBODY>
						<TR>
							<TD>用户名：</TD>
							<TD colSpan="2">
								<input type="text" id="loginName" name="loginName" style="WIDTH: 130px"  />
							</TD>
						</TR>
						<TR>
							<TD>密 码：</TD>
							<TD>
								<input type="password" id="loginPwd" name="loginPwd"  style="WIDTH: 130px"/>
							</TD>
						</TR>
						<TR>
							<TD>验证码：</TD>
							<TD><input id="randomcode" name="randomcode" size="8"/> 
								<img id="randomcode_img" src="${baseurl}validatecode.jsp" alt="" width="56" height="20" align='absMiddle' /> 
								<a href=javascript:randomcode_refresh()>刷新</a>
							</TD>
						</TR>

						<TR>
							<TD colSpan="2" align="center"><input type="button"
								class="btnalink" onclick="loginsubmit()" value="登&nbsp;&nbsp;录"  
								id = "login"/>
								<input type="reset" class="btnalink" value="重&nbsp;&nbsp;置" />
							</TD>
						</TR>
						
						<TR>
							<TD colSpan="2" align="center">
		                         <span >还没有账号，点击</span>
	                            <a href="${baseurl}toRegister.action" style="color:red">这里</a>
	                            <span >进行注册！</span>
	                         </TD>
						</TR>
					</TBODY>
				</TABLE>

			</DIV>
		</DIV>
	</FORM>
</BODY>

<script type="text/javascript">
          //登录方法
       function loginsubmit() {
       	  if($("#loginform").form('validate')) {
       		var myString = $('#loginform').serialize();
            $.ajax({
                url : '${baseurl}login.action',
                data : myString,
                type : 'post',
                //dataType : 'json',
                success : function(data) {
                    var json = eval("("+data+")");
                    if (json.success) {
                        window.location = '${baseurl}main.action';
                    } else {
                        $.messager.alert('信息提示', json.msg, 'info');
                    }
                }
            });  
       	}
       }

        //回车键登录
        $(window).keydown(function(e){
             var ev = window.event || e;
                console.log(ev);
                if (ev.keyCode == 13) {
                    $("#login").click();
                }
        }) 

        //验证码刷新
        function randomcode_refresh() {
            document.getElementById("randomcode_img").src = document
                    .getElementById("randomcode_img").src
                    + "?nocache=" + new Date().getTime();
        }
        
         //登录表单的校验
         $("#loginName").validatebox({
             required : true
         });
         $("#loginPwd").validatebox({
             required : true
         });
         $("#randomcode").validatebox({
             required : true
         });
        
</SCRIPT>
</HTML>
