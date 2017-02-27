<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作成功</title>
</head>
<script type="text/javascript">
    //几秒之后跳转到指定的页面
	var time = 4;
	function returnUrlByTime() {
		window.setTimeout('returnUrlByTime()', 1000);
		time = time - 1;
		document.getElementById("layer").innerHTML = time;
		if (time == 0) {
			window.location.href = "${baseurl}first.action";
		}
	}
</script>

<body onload="returnUrlByTime()">
    <br/>
    <h4 align="center">注册成功！</h4>
    <br/> <br/>
    <h4 align="center">
	    <span id = "layer">3</span>秒之后，跳转到系统登录页面！如果没有跳转，请点击
	    <a href="${baseurl}first.action" style="color:red">这里</a>进行跳转!
    </h4>
  
</body>
</html>