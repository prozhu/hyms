<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
 <%@ include file="/WEB-INF/jsp/common/common_js.jsp"%>
 <%@ include file="/WEB-INF/jsp/common/common_css.jsp"%>
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
                <a href="#" class="easyui-linkbutton" iconCls="icon-page-excel" onclick="exportMemberInfoExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
                <c:if test="${member.membertype == 0 }">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-add" onclick="addCard()" plain="true">办卡</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-server-compressed" onclick="reCard()" plain="true">补卡</a>
                </c:if>
            </div>
            <c:if test="${member.membertype == 0 }">
	            <div class="wu-toolbar-search">
		                <label>起始时间：</label><input class="easyui-datebox" editable = "false" style="width:100px" name="startTime5" id = "startTime5">
		                <label>结束时间：</label><input class="easyui-datebox " editable = "false"  style="width:100px" name="endTime5" id = "endTime5">
		                <label>关键词：</label><input class="wu-text easyui-tooltip"  style="width:100px"  title = "可以通过会员名称搜索...." name="keyword5"  id = "keyword5">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAllByCondition()">开始检索</a>
	            </div>
            </c:if>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar1"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="wu-dialog" class="easyui-dialog" data-options=" closable: false,closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
	<!-- 表单 -->
    <form id="wu-form" method="post"  >
        <input type="hidden" name="id" class="wu-text"/>
        <table>
            <tr>
                <td width="60" align="right">登录名称:</td>
                <td><input type="text" name="loginname" class="wu-text" id="loginname" /></td>
            </tr>
            <tr>
                <td align="right">登录密码:</td>
                <td><input type="password" name="loginpwd" class="wu-text easyui-validatebox" id="loginpwd" required = "true"   validtype="length[5, 32]"/></td>
            </tr>
            <tr>
                <td align="right">会员名称:</td>
                <td><input type="text" name="membername" class="wu-text easyui-validatebox" id="membername" required = "true"  /></td>
            </tr>
             <tr>
                <td align="right">性别:</td>
                <td>
                	<input type="radio" name="sex"  id="sex" value="男">男</input>
                	<input type="radio" name="sex"  id="sex" value="女">女</input>
                </td>
            </tr>
             <tr>
                <td align="right">年龄:</td>
                <td><input type="text" name="age" class="wu-text easyui-validatebox"  id="age" required = "true" validtype="age" /></td>
            </tr>
             <tr>
                <td align="right">电话号码:</td>
                <td><input type="text" name="phone" class="wu-text easyui-validatebox" required = "true" validtype="mobile"  id="phone"  /></td>
            </tr>
             <tr>
                <td align="right">电子邮件:</td>
                <td><input type="text" name="email" class="wu-text easyui-validatebox"  validtype="email"  id="email"  /></td>
            </tr>
            <tr>
                <td valign="top" align="right">备 注:</td>
                <td>
                	<textarea name="remark" rows="6" class="wu-textarea" style="width:260px" id ="remark"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->

<script type="text/javascript">

$.extend($.fn.validatebox.defaults.rules, {
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    }
});


	//回车键查询所有会员信息
	$(window).keydown(function(e){
	     var ev = window.event || e;
	        console.log(ev);
	        if (ev.keyCode == 13) {
	            $("#begin").click();
	        }
	}) 

	/**
	 * 有条件的查询所有会员信息
	 */
	function findAllByCondition() {
		var startTime = $.trim($('input[name="startTime5"]').val());
		var endTime = $.trim($('input[name="endTime5"]').val());
		$('#wu-datagrid').datagrid('load', {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
			endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
			keyword : $.trim($("#keyword5").val())
		});
	}
	
	/**
	补办会员卡
	*/
	function reCard() {
	       //获取勾选的数据
        var members = $('#wu-datagrid').datagrid('getSelections');
        var ids = [];
        if (members == null || members.length == 0) {
            $.messager.alert('信息提示', '请选择一条用户信息！');
            return false;
        }
        if (members.length != 1) {
            $.messager.alert("温馨提示", "只能选择一个用户补办会员卡！");
            return false;
        }
        //收集id信息
        $(members).each(function() {
            ids.push(this.id);
        }); 
        $.messager.confirm('信息提示', '确定补办会员卡吗？', function(result) {
            if (result) {
                $.ajax({
                    url : '${baseurl}memberCard/addMemberCard.action',
                    async : true,
                    type : 'post',
                    data : {
                        ids : ids.join(",")
                    },
                    success : function(data) {
                        var json = eval("(" + data + ")");
                        if (json.success) {
                            $.messager.alert('信息提示', '补办成功！', 'info');
                            $('#wu-datagrid').datagrid('reload');
                        } else {
                            $.messager.alert('信息提示', json.msg, 'info');
                        }
                    }
                });
            }
        });
	}
	
	/**
	 * 办理会员卡
	 */
	function addCard() {
		 //获取勾选的数据
        var ms = $('#wu-datagrid').datagrid('getSelections');
        var ids = [];
        if (ms == null || ms.length == 0) {
            $.messager.alert('信息提示', '请选择一条用户信息！');
            return false;
        }
        //收集id信息
        $(ms).each(function() {
            ids.push(this.id);
        }); 
		$.messager.confirm('信息提示', '确定办理会员卡吗？', function(result) {
			if (result) {
				$.ajax({
					url : '${baseurl}memberCard/addMemberCard.action',
					async : true,
					type : 'post',
					data : {
						ids : ids.join(",")
					},
					success : function(data) {
						var json = eval("(" + data + ")");
						if (json.success) {
							$.messager.alert('信息提示', '办卡成功！', 'info');
							$('#wu-datagrid').datagrid('reload');
						} else {
							$.messager.alert('信息提示', json.msg, 'info');
						}
					}
				});
			}
		});
	}

	/**
	 * 用户注册表单的校验
	 */
	//用户名校验
/* 	$('#loginname').validatebox({
		required : true,
		validType :"remote['${baseurl}member/checkLoginName.action',  'loginname']",
		invalidMessage : '该用户已被注册！'
	}); */
	//性别校验
    $('#sex').validatebox({
        required : true,
        missingMessage : '性别必须选择'
    });

	//导出会员信息表格
	function exportMemberInfoExcel() {
		var startTime = $.trim($('input[name="startTime5"]').val());
		var endTime = $.trim($('input[name="endTime5"]').val());
		download("${baseurl}member/exportMemberInfoExcel.action", {
			startTime : startTime.length > 0?startTime + ' 00:00:00':startTime,
	        endTime : endTime.length > 0?endTime + ' 23:59:59':endTime,
	        keyword : $("#keyword5").val()
		});
	}

	//打印报表
	function print() {
		CreateFormPage("会员信息", $('#wu-datagrid'));
	}

	//刷新表格数据
	function refresh() {
		$('#wu-datagrid').datagrid('reload');
	}

	//删除会员信息
	function del() {
		var members = $('#wu-datagrid').datagrid('getSelections');
		var ids = [];
		if (members == null || members.length == 0) {
	           $.messager.alert('信息提示', '请选择一条用户信息！');
	           return false;
	    }
		$.messager.confirm('信息提示', '确定要删除该记录？', function(result) {
			if (result) {
				$(members).each(function() {
					if (this.membertype == 0) {
						$.messager.alert('信息提示','管理员账号不允许删除！','info');  
						return false;
					}
					ids.push(this.id);
				});
				$.ajax({
					traditional : true,
					url : '${baseurl}member/delMember.action',
					type: 'post',
					async : true,
					dataType : 'json',
					data : {
						ids : ids.join(",")
					},
					success : function(data) {
						if (data.success) {
							$.messager.alert('信息提示','删除成功！','info');     
							$('#wu-datagrid').datagrid('reload');
						} else {
							$.messager.alert('信息提示', data.msg, 'info');
						}
					}
				});
			}
		});
	}

	/**
	 * 修改记录
	 */
	function edit() {
		if($("#wu-form").form('validate')) {
			$.ajax({
				url : '${baseurl}member/register.action',
				data : $('#wu-form').serialize(),
				async : true,
				type : 'post',
				success : function(data) {
					var json = eval("(" + data + ")");
					if (json.success) {
						$('#wu-datagrid').datagrid('reload');
						$('#wu-dialog').dialog('close');
						if (json.total == 2) {
							$.messager.alert('信息提示', json.msg + '<span id = "layer">4</span>秒之后，自动跳转到登录页面', 'info', function() {
						 });
							returnUrlByTime(json.msg);
						} else {
							$.messager.alert('信息提示', json.msg, 'info');
						}
					} else {
						$.messager.alert('信息提示', '修改失败！', 'info');
					}
				}
			});
		}

	}
//邮箱自动补全
$("#email").mailAutoComplete();//使用方法
	
    // 用户密码修改之后，强制其重新登录
    function returnUrlByTime(messages) {
    	var time = $("#layer").text();
        time = time - 1;
        $("#layer").text(time);
        if (time == 0) {
            window.location.href = "${baseurl}first.action";
        }
        window.setTimeout('returnUrlByTime()', 1000);
    }

	/**
	 * 添加记录
	 */
	function add() {
		$('#wu-form').form('submit', {
			url : '${baseurl}member/register.action',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				if (data) {
					$('#wu-dialog').dialog('close');
					$.messager.alert('信息提示', '添加成功！', 'info');
					$('#wu-datagrid').datagrid('reload');
				} else {
					$.messager.alert('信息提示', '添加失败！', 'info');
				}
			}
		});
	}

	/**
	 *  删除记录
	 */
	function remove() {
		$.messager.confirm('信息提示', '确定要删除该记录？', function(result) {
			if (result) {
				var members = $('#wu-datagrid').datagrid('getSelections');
				var ids = [];
				$(members).each(function() {
					ids.push(this.productid);
				});
				//alert(ids);return;
				alert(ids);
				$.ajax({
					url : '',
					data : '',
					success : function(data) {
						if (data) {
							$.messager.alert('信息提示', '删除成功！', 'info');
						} else {
							$.messager.alert('信息提示', '删除失败！', 'info');
						}
					}
				});
			}
		});
	}

	/**
	 *  打开添加窗口
	 */
	function openAdd() {
		$('#loginname').validatebox({
	        required : true,
	        validType : "remote['${baseurl}member/checkLoginName.action',  'loginname']",
	        invalidMessage : '该用户已被注册！'
	    });
		$('#wu-form').form('clear');
		$("#loginname").attr('disabled', false);
		$('#wu-dialog').dialog({
			closed : false,
			modal : true,
			title : "添加信息",
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : add
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					//解决弹出窗口关闭后，验证消息还显示在页面的最上面
					$('.tooltip.tooltip-right').hide(); 
					$('#wu-dialog').dialog('close');
				}
			} ]
		});
	}
	
    $.extend($.fn.validatebox.methods, {    
        remove: function(jq, newposition){    
            return jq.each(function(){    
                $(this).removeClass(" tooltip-content validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');  
            });    
        },  
        reduce: function(jq, newposition){    
            return jq.each(function(){    
               var opt = $(this).data().validatebox.options;  
               $(this).addClass("validatebox-text").validatebox(opt);  
            });    
        }     
    });   

	/**
	 * 打开修改窗口
	 */
	function openEdit() {
		//获取勾选的行信息
		var member = $('#wu-datagrid').datagrid('getSelections');
		//alert(item.productid);return;
		if (member == null || member.length == 0) {
			$.messager.alert('信息提示', '请选择一条数据！');
			return false;
		}
		//修改信息，只能选择一条数据
		if (member.length != 1) {
			$.messager.alert("温馨提示", "只能选择一条数据进行修改!");
			return false;
		}
		
	   /*    $('#loginname').validatebox({
	            required : false,
	            validType :"remote['${baseurl}member/checkLoginName.action',  'loginname']",
	            invalidMessage : '该用户已被注册！'
	      }); */
		$('#wu-form').form('clear');
		
		//加载选中行的数据到表单中填充
		$('#wu-form').form('load', member[0]);
		//将loginname输入框给禁用掉，禁用用户修改
		$("#loginname").attr('disabled', true);
		$("#loginname").validatebox('remove');  
		//$("#loginname").validatebox('reduce'); 
		$('#wu-dialog').dialog({
			closed : false,
			modal : true,
			cache : false,
			title : "修改信息",
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : edit
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					//解决弹出窗口关闭后，验证消息还显示在页面的最上面
                    $('.tooltip.tooltip-right').hide(); 
					$('#wu-dialog').dialog('close');
				}
			} ]
		});
	}

	/**
	 *  载入数据
	 */
	$('#wu-datagrid').datagrid({
		url : '${baseurl}member/findAllByCondition.action',
		rownumbers : true,
		singleSelect : false,
		total : 10,
		pageSize : 5,
		pageList : [ 10, 15, 20 ],
		pagination : true,
		multiSort : true,
		fitColumns : true,
		fit : true,
		columns : [ [ {
			checkbox : true
		}, {
			field : 'membername',
			title : '会员名称',
			width : 70
		}, {
			field : 'loginname',
			title : '登录名',
			width : 80,
			sortable : true
		},/*  {
			field : 'loginpwd',
			title : '登录密码',
			width : 200,
		 formatter : function(value, row, index) {
			return "***********************";
		} 
		}, */ {
			field : 'sex',
			title : '性别',
			width : 50
		}, {
			field : 'age',
			title : '年龄',
			sortable : true,
			width : 50
		}, {
			field : 'phone',
			title : '电话号码',
			width : 100
		}, {
            field : 'email',
            title : '邮箱',
            width : 150
        }, {
			field : 'membertype',
			title : '会员类型',
			width : 80,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "普通会员";
				}
				if (value == 0) {
					return "管理员";
				}
			}
		}, {
			field : 'registertime',
			title : '注册时间',
			sortable : true,
			width : 100,
			formatter : function(value, row, index) {
				var unixTimestamp = new Date(value);
				return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
			}
		} ] ]
	});
</script>

</body>

</html>