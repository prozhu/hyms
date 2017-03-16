<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=0.5, minimum-scale=0.5, maximum-scale=0.5, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员注册</title>

</head>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<script type="text/javascript">

	$(document).ready(function() {
		   //初始化表单验证
	    $.formValidator.initConfig({formID:"memberForm",debug:true,
	    	onSuccess:function(){
	             $.ajax({
	                    url : '${pageContext.request.contextPath }/member/register.action',
	                    type : 'post',
	                    data : $('#memberForm').serialize(),
	                    success : function(data) {
	                        if (data) {
	                            window.location = "${pageContext.request.contextPath }/success.action";
	                        } else {
	                            $.messager.alert('信息提示', '注册失败！', 'info');
	                        }
	                    }
	                });
	    },onError:function(){
	        return false;
	    }});
		   
	  //ajax验证
        $("#loginname").formValidator({ // 验证：模块名称
            onfocus: "在5-20个字符之间",
            onShow : "请输入登录用户名",
            onCorrect : "正确"
        }).inputValidator({
            min: 5,
            max: 20,
           onError : "用户名在5-20个字符之间"
        }).ajaxValidator({
            type: "get",
            dataType: "json",
        	async : true,
            url: "${baseurl}member/checkLoginName.action",
            success: function (data) {
                if (data) {
                    return true;
                } else {
                    return false;
                }
            },
            onCorrect : "正确",
            onError: "该账号已被占用，请更换！"
        });

		jQuery("#loginpwd").formValidator({
			onShow : "请输入登录密码",
			onCorrect : "正确"
		}).regexValidator({
			regExp : "^([A-Za-z0-9]{6,40})$",
			onError : "密码只能输入6-40个字符的字母或者数字"
		});
		
		jQuery("#membername").formValidator({
			onShow : "请输入会员名称",
			onCorrect : "正确"
		}).inputValidator({
	       min: 2,
            max: 20,
           onError : "会员名称在2-20个字符之间"
		});
		
		jQuery("#age").formValidator({
			onShow : "请输入年龄",
			onCorrect : "正确"
		}).regexValidator({
			regExp : "^(?:[1-9][0-9]?|1[01][0-9]|120)$",
		    onError : "年龄只能输入1-120之间的整数"
		});
		
		jQuery("#phone").formValidator({
			onShow : "请输入电话号码",
			onCorrect : "正确"
		}).regexValidator({
			regExp : "(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)",
			onError : "电话号码格式不正确"
		});
		
		jQuery("#email").formValidator({
			onShow : "请输入电子邮箱",
			onCorrect : "正确"
		}).regexValidator({
			regExp : "(^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$)",
			onError : "电话邮箱格式不正确"
		});
	});
</script>
<body>
	<form id="memberForm"
		action="${pageContext.request.contextPath }/member/register.action"
		method="post" >
		<br />
		<br />
		<table width="50%" border=0 align="center">

			<tr>
				<td>登录用户名：</td>
				<td style="width: 100px"><input type="text" name="loginname" id="loginname"
					value="" /></td>
				<td><div id="loginnameTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>登录密码：</td>
				<td style="width: 100px"><input type="password" name="loginpwd" id="loginpwd"
					value="" /></td>
				<td><div id="loginpwdTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>会员名称：</td>
				<td style="width: 100px"><input type="text" name="membername" id="membername"
					value="" /></td>
				<td><div id="membernameTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>性别：</td>
				<td style="width: 100px"><input type="radio" name="sex" id="sex"
					value="男">男</input> <input type="radio" name="sex" value="女" id="sex">女</input>
				</td>
				<td><div id="sexTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>年龄：</td>
				<td style="width: 100px"><input type="text" name="age" id="age"
					value="${member.age}" /></td>
				<td><div id="ageTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>电话号码：</td>
				<td style="width: 100px"><input type="text" name="phone"  id="phone"
					value="" /></td>
				<td><div id="phoneTip" style="width: 200px"></div></td>
			</tr>
			
			<tr>
				<td>电子邮箱：</td>
				<td style="width: 100px"><input type="text" name="email"  id="email"
					value="" /></td>
				<td><div id="emailTip" style="width: 200px"></div></td>
			</tr>

			<tr>
				<td>备注：</td>
				<td style="width: 100px"><textarea rows="3" cols="30"
						name="remark">${member.remark }</textarea></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit" value="注册" />
				</td>
			</tr>

		</table>

	</form>
</body>

</html>