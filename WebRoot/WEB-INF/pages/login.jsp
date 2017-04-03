<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
  <%@ include file="/WEB-INF/pages/common/tag.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_js.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_css.jsp"%>
<html>
<head>
<title>会员管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${baseurl}styles/style.css">
<link rel="stylesheet" type="text/css" href="${baseurl}styles/login.css">

<style type="text/css">
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
</style>


</head>
<body 
	style="background: #f6fdff url(${baseurl}images/login/bg1.jpg) repeat-x; background-size:cover">
	<form id="loginform" name="loginform"  method="post">
		
		<div class="logincon">

			<div class="title">
				<%-- <IMG alt="" src="${baseurl}images/login/logo.png"> --%>
			</div>

			<div class="cen_con">
			     <h1><font size=4" >会员管理系统</font></h1>
				<img alt="" src="${baseurl}images/login/bg2.png">
			</div>

			<div class="tab_con">

				<table class="tab" border="0" cellSpacing="6" cellPadding="8">
					<tbody>
						<tr>
							<td>用户名：</td>
							<td colSpan="2">
								<input type="text" id="loginName" name="loginName" style="width: 130px;margin-left:-6px"  />
							</td>
						</tr>
						<tr>
							<td>密&nbsp;&nbsp;&nbsp;码：</td>
							<td>
								<input type="password" id="loginPwd" name="loginPwd"  style="width: 130px"/>
							</td>
						</tr>
						<tr>
							<td >验证码：</td>
							<td><input id="randomcode" name="randomcode" size="8" style = "margin-left: 16px"/> 
								<img id="randomcode_img" src="${baseurl}validatecode.jsp" alt="" width="56" height="20" align='absMiddle' /> 
								<a href=javascript:randomcode_refresh()>刷新</a>
							</td>
						</tr>

						<tr>
							<td colSpan="2" align="center"><input type="button"
								class="btnalink" onclick="loginsubmit()" value="登&nbsp;&nbsp;录"  
								id = "login"/>
								<input type="reset" class="btnalink" value="重&nbsp;&nbsp;置" />
							</td>
						</tr>
						
						<tr>
							<td colSpan="2" align="center">
		                         <span >还没有账号，点击</span>
	                            <a href="${baseurl}toRegister.action" style="color:red">这里</a>
	                            <span >进行注册！</span>
	                         </td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</form>
</body>

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
