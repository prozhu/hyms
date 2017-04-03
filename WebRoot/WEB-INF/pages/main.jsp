<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/pages/common/tag.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_js.jsp"%>
 <%@ include file="/WEB-INF/pages/common/common_css.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理系统</title>

</head>
<body class="easyui-layout" style="background-color: #C7EDCC">

    <!-- begin of header -->
    <div class="wu-header" data-options="region:'north',border:false,split:true">
        <div class="wu-header-left">
            <h1>会员管理系统</h1>
        </div>
        <div class="wu-header-right">
            <p style = "margin:9px; color:#ffa8a8;"><span>在线人数: ${count} &nbsp &nbsp &nbsp&nbsp&nbsp</span><strong title="">${member.loginname}</strong>，欢迎您！</p>
            <p><a href="#" onclick="changeTheme()">主题切换</a>|<a href="${baseurl}logout.action">网站首页</a>|<a href=javascript:help()>帮助中心</a>|<a id="loginOut" href=javascript:logout()>退出系统</a></p>
        </div>

    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
    <div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
        <div class="easyui-accordion" data-options="border:false,fit:true" > 
            <div title="会员管理" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">   
                <ul class="easyui-tree wu-side-tree"  >
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}member.action">会员管理</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}memberCard.action">会员卡管理</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}cardRechargeRecord.action">充值记录</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}cardRecord.action">挂失/激活记录</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}pointRecord.action">积分记录</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}consumeRecord.action">消费记录</a></li>
                </ul>
            </div>
            <c:if test="${member.membertype == 0 }">
            <div title="图表管理" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">   
                <ul class="easyui-tree wu-side-tree"  >
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}memberChart.action">会员图表</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}memberCardChart.action">会员卡图表</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}cardRechargeRecordChart.action">充值图表</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}pointRecordChart.action">积分图表</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-tip"  iframe="0" data-link="${baseurl}consumeRecordChart.action">消费图表</a></li>
                </ul>
            </div>
            </c:if>
        </div>
    </div>   
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true" style="background:url(images/div.jpg)">  
           <%--  <div title="会员管理" data-options="href:'${baseurl}member.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div>
            <div title="会员卡管理" data-options="href:'${baseurl}memberCard.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div>
            <div title="充值记录" data-options="href:'${baseurl}cardRechargeRecord.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div>
            <div title="挂失/激活记录" data-options="href:'${baseurl}cardRecord.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div>
            <div title="积分记录" data-options="href:'${baseurl}pointRecord.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div>
            <div title="积分记录" data-options="href:'${baseurl}consumeRecord.action',closable:true,iconCls:'icon-tip',cls:'pd3'"></div> --%>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
    <div class="wu-footer" data-options="region:'south',border:true,split:true">
        &copy; 2017 Prozhu All Rights Reserved
    </div>
    <!-- end of footer -->  
    <script type="text/javascript">
    
				  /*主题切换
				  */
					function changeTheme() {
						var $easyuiTheme = $('#easyuiTheme');
						var count = $easyuiTheme.attr('count');
						if (count == undefined) {
							count =0;
							$easyuiTheme.attr('count', count);
						}
						var themes = [ "black", "bootstrap", "default", "gray",
								"metro", "material" , "metro-blue", "metro-gray", "metro-green",
                                "metro-red", "metro-orange"];
						count++;
						if (count == themes.length - 1) {
							count = 0;
						}
						
						var url = $easyuiTheme.attr('href');
						var href = url.substring(0, url.indexOf('themes'))
								+ 'themes/' + themes[count] + '/easyui.css';
						$easyuiTheme.attr('href', href);
						$easyuiTheme.attr('count', count);
					}
					
					

					$(function() {

						//为tree 绑定单击事件，跳转到指定的tab
						$('.wu-side-tree a')
								.bind(
										"click",
										function() {
											//标题
											var title = $(this).text();
											//url路径
											var url = $(this).attr('data-link');
											//图标
											var iconCls = $(this).attr(
													'data-icon');
											//判断是否是使用 iframe或者 href 的方式打开页面
											var iframe = $(this).attr('iframe') == 1 ? true
													: false;
											addTab(title, url, iconCls, iframe);
										});
					})

					/**
					 * Name 选项卡初始化
					 */
					$('#wu-tabs').tabs({
						tools : [ {
							iconCls : 'icon-reload',
							border : false,
							handler : function() {
								$('#wu-datagrid').datagrid('reload');
							}
						} ]
					});

					/**
					 * Name 添加菜单选项
					 * Param title 名称
					 * Param href 链接
					 * Param iconCls 图标样式
					 * Param iframe 链接跳转方式（true为iframe，false为href）
					 */
					function addTab(title, href, iconCls, iframe) {
						//删除当前已经打开的tab 页面（保证每次只有一个页面被打开）
						//removeTab();
						var tabPanel = $('#wu-tabs');
						if (!tabPanel.tabs('exists', title)) {
							var content = '<iframe scrolling="auto" frameborder="0"  src="'
									+ href
									+ '" style="width:100%;height:100%;"></iframe>';
							if (iframe) {
								tabPanel.tabs('add', {
									title : title,
									content : content,
									iconCls : iconCls,
									fit : true,
									cls : 'pd3',
									closable : true
								});
							} else {
								tabPanel
										.tabs(
												'add',
												{
													id : title,
													title : title,
													href : href,
													iconCls : iconCls,
													fit : true,
													cls : 'pd3',
													closable : true,
													extractor : function(data) {
														//抽取body中的内容  
														var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
														var matches = pattern
																.exec(data);
														if (matches) {
															data = matches[1];
														}
														var tmp = $('<div/>')
																.html(data);

														var divs = $(tmp).find(
																'[id]');
														var ids = [];
														for (var i = 0; i < divs.length; i++) {
															ids
																	.push(divs[i]
																			.getAttribute("id"));
														}
														//记录本tab中的所有带ID的DIV  
														tabPanel.tabs('getTab',
																title).divs = ids;
														return data;
													}
												});
							}
						} else {
							tabPanel.tabs('select', title);
							// 重新加载已经存在的Tab内容  
							/*  var currTab = tabPanel.tabs('getTab', title);  
							 tabPanel.tabs('update', {tab: currTab, options: {href: href, closable: true}}); */
						}
					}

					/**
					 * Name 移除菜单选项
					 */
					function removeTab() {
						var tabPanel = $('#wu-tabs');
						var tab = tabPanel.tabs('getSelected');
						if (tab) {
							var index = tabPanel.tabs('getTabIndex', tab);
							tabPanel.tabs('close', index);
						}
					}

					/**
					   解决表单重复提交的问题：
					   解决方案查询网址：http://blog.csdn.net/orrinzeng/article/details/51663928
					 */
					$('#wu-tabs').tabs({
						autoWidth : true,
						onBeforeClose : function(title, index) {//关闭面板前把此功能的资源释放  
							var tab = $(this).tabs('getTab', index);
							//准备删除的DIV内容  
							$('#wu-tabs').attr("rmdiv", tab.divs);
						},
						onClose : function() {//删除被关闭tab中用到的DOM对象  
							var divs = $('#wu-tabs').attr("rmdiv").split(",");
							for (var i = 0; i < divs.length; i++) {
								var divTarget = $('#' + divs[i]);
								if (divTarget) {
									divTarget.remove();
								}
							}
						}
					});

					//退出系统方法
					function logout() {
						_confirm('您确定要退出本系统吗?', null, function() {
							location.href = '${baseurl}logout.action';
						})
					}

					//帮助中心
					function help() {
						/* _confirm("帮助中心正在建设中。。。", null, function() {

						}) */
						$.messager
								.show({
									title : '使用说明',
									msg : '一、会员卡的激活、挂失、挂失解除说明：<br>    1. 会员卡只有在禁用的状态下允许激活  <br>2.会员卡挂失只有在正常的状态允许挂失<br>3.会员卡只有在挂失的状态下允许挂失解除<br><br>'
											+ '二、会员卡的消费、充值、积分调整说明：<br> 1. 会员卡消费金额只能是整数<br>2.会员卡在禁用或者挂失的状态下不允许消费！<br><br> '
											+ '3.会员卡的充值金额只能是正整数<br>4.会员卡在禁用或者挂失的状态下不允许充值！<br><br>'
											+ '5.会员卡的积分调整只能是正负整数<br>6.会员卡在禁用或者挂失的状态下不允许进行积分调整！<br><br>'
											+ '三、会员卡的积分和折扣说明<br>1.会员卡每消费一元钱就获得一积分,不足一元，还是获取一积分<br>'
											+ '2.会员卡的初始级别为青铜，折扣为：不打折<br>3.会员卡的累计消费在1万-2万之间，会员级别为：白银， 折扣为：8折<br><br>'
											+ '4.会员卡的累计消费在2万以上，会员级别为：黄金， 折扣为：5折<br><br>',
									showType : 'slide',
									width : '310',
									height : '450',
									style : {
										right : '',
										top : document.body.scrollTop
												+ document.documentElement.scrollTop,
										bottom : ''
									}
								});
					}
				</script>
</body>
</html>
