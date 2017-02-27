<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- Viewport metatags -->
<meta name="HandheldFriendly" content="true" />
<meta name="MobileOptimized" content="320" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<link rel="stylesheet" type="text/css" href="css/dandelion.css"  media="screen" />

<title>404</title>
</head>
<body>
    <!-- Main Wrapper. Set this to 'fixed' for fixed layout and 'fluid' for fluid layout' -->
    <div id="da-wrapper" class="fluid">
    
        <!-- Content -->
        <div id="da-content">
            
            <!-- Container -->
            <div class="da-container clearfix">
            
                <div id="da-error-wrapper">
                    
                    <div id="da-error-pin"></div>
                    <div id="da-error-code">
                        error <span>404</span>                    </div>
                
                    <h1 class="da-error-heading">哎哟喂！页面让狗狗叼走了！</h1>
                    <p>大家可以到狗狗没有叼过的地方看看！<br> 
                    <a href="#" onclick ="javascript:history.go(-1);">返回继续</a>
                    <a href="${baseurl }welcome.action" >系统首页</a>
                    <a href="http://www.baidu.com" >百度溜一下</a>
                    </p>
                </div>
            </div>
        </div>
        
        <!-- Footer -->
        <div id="da-footer">
            <div class="da-container clearfix">
            <p> 一起好愉快。。。。。</div>
        </div>
    </div>
    
</body>
</html>