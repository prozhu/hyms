<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="refresh()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="print()" plain="true">打印</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportMemberInfoExcel()" plain="true">导出报表</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help" onclick="help()" plain="true">帮助</a>
                <c:if test="${member.membertype == 0 }">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="consume()" plain="true">消费</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="recharge()" plain="true">充值</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="adjustPoint()" plain="true">积分调整</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick=" activate()" plain="true">会员卡激活</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="loss()" plain="true">会员卡挂失</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="unloss()" plain="true">会员卡挂失解除</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-cut" onclick="cancel()" plain="true">会员卡注销</a>
                </c:if>
            </div>
            <c:if test="${member.membertype == 0 }">
	            <div class="wu-toolbar-search">
		                <label>起始时间：</label><input class="easyui-datebox"  editable = "false" style="width:100px" name="startTime" id = "startTime">
		                <label>结束时间：</label><input class="easyui-datebox" editable = "false" style="width:100px" name="endTime" id = "endTime">
		                <label>关键词：</label><input class="wu-text easyui-tooltip"    style="width:100px"  title = "可以通过会员名称搜索...." name="keyword"  id = "keyword">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAllByCondition()">开始检索</a>
	            </div>
            </c:if>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid4" toolbar="#wu-toolbar2"></table>
    </div>
</div>
<script type="text/javascript">
/**
 * 会员卡注销
 */
 function cancel() {
	    //获取勾选的行信息
	    var member = $('#wu-datagrid4').datagrid('getSelections');
	    if (member == null || member.length == 0) {
	        $.messager.alert('信息提示', '请选择一条用户信息！');
	        return false;
	    }
	    //修改信息，只能选择一条数据
	    if (member.length != 1) {
	        $.messager.alert("温馨提示", "只能选择一个用户进行注销操作！");
	        return false;
	    }
	    //会员卡在禁用(1)状态下：不允许注销
	    if (member[0].cardstatus == 1) {
	        $.messager.alert("温馨提示", "会员卡在禁用状态下不允许注销！");
	        return  false;
	    }
	    
	    var ids = [];
	    //收集id信息
	    $(member).each(function() {
	        ids.push(this.id);
	    }); 
	    $.messager.confirm('信息提示', '确定注销该会员卡吗？', function(result) {
	        if (result) {
	            $.ajax({
	                url : '${baseurl}memberCard/memberCardActivate.action',
	                type : 'post',
	                data : {
	                    ids : ids.join(","),
	                    flag : 'cancel'
	                },
	                dataType: 'json',
	                success : function(data) {
	                    if (data.success) {
	                        $.messager.alert('信息提示', '注销成功！', 'info');
	                        $('#wu-datagrid4').datagrid('reload');
	                    } else {
	                        $.messager.alert('信息提示', data.msg, 'info');
	                    }
	                }
	            });
	        }
	    });
}

/**
 * 会员卡挂失解除
 */
function unloss() {
    //获取勾选的行信息
    var member = $('#wu-datagrid4').datagrid('getSelections');
    if (member == null || member.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    //修改信息，只能选择一条数据
    if (member.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户解除会员卡挂失状态！");
        return false;
    }
    //会员卡在禁用(1)和挂失(2)状态下：不允许挂失
    if (member[0].cardstatus == 1 || member[0].cardstatus == 0) {
        $.messager.alert("温馨提示", "会员卡在禁用或者正常的状态下不允许解除挂失！");
        return  false;
    }
    var ids = [];
    //收集id信息
    $(member).each(function() {
        ids.push(this.id);
    }); 
    $.messager.confirm('信息提示', '确定解除该会员卡的挂失状态吗？', function(result) {
        if (result) {
            $.ajax({
                url : '${baseurl}memberCard/memberCardActivate.action',
                type : 'post',
                data : {
                    ids : ids.join(","),
                    flag : 'unloss'
                },
                success : function(data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '解除挂失成功！', 'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', json.msg, 'info');
                    }
                }
            });
        }
    });
}


/**
 * 会员卡挂失
 */
function loss() {
    //获取勾选的行信息
    var member = $('#wu-datagrid4').datagrid('getSelections');
    if (member == null || member.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    //修改信息，只能选择一条数据
    if (member.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户挂失会员卡！");
        return false;
    }
    //会员卡在禁用(1)和挂失(2)状态下：不允许挂失
    if (member[0].cardstatus == 1 || member[0].cardstatus == 2) {
    	$.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许挂失！");
    	return  false;
    }
    var ids = [];
    //收集id信息
    $(member).each(function() {
        ids.push(this.id);
    }); 
    $.messager.confirm('信息提示', '确定挂失该会员卡吗？', function(result) {
        if (result) {
            $.ajax({
                url : '${baseurl}memberCard/memberCardActivate.action',
                type : 'post',
                data : {
                    ids : ids.join(","),
                    flag : 'loss'
                },
                success : function(data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '挂失成功！', 'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', json.msg, 'info');
                    }
                }
            });
        }
    });
}

	/**
	 * 会员卡激活
	 */
	function activate() {
        //获取勾选的数据
        var members = $('#wu-datagrid4').datagrid('getSelections');
        var ids = [];
        if (members == null || members.length == 0) {
            $.messager.alert('信息提示', '请选择一条用户信息！');
            return false;
        }
        //收集id信息
        $(members).each(function() {
            ids.push(this.id);
        }); 
        $.messager.confirm('信息提示', '确定激活该会员卡吗？', function(result) {
            if (result) {
                $.ajax({
                    url : '${baseurl}memberCard/memberCardActivate.action',
                    async : true,
                    type : 'post',
                    data : {
                        ids : ids.join(","),
                        flag : 'activate'
                    },
                    success : function(data) {
                        var json = eval("(" + data + ")");
                        if (json.success) {
                            $.messager.alert('信息提示', '激活成功！', 'info');
                            $('#wu-datagrid4').datagrid('reload');
                        } else {
                            $.messager.alert('信息提示', json.msg, 'info');
                        }
                    }
                });
            }
        });
	}

	
	/**
	 * 会员卡的积分调整
	 */
	function adjustPoint() {
		//获取勾选的行信息
		var member = $('#wu-datagrid4').datagrid('getSelections');
		//alert(item.productid);return;
		if (member == null || member.length == 0) {
			$.messager.alert('信息提示', '请选择一条用户信息！');
			return false;
		}
		//修改信息，只能选择一条数据
		if (member.length != 1) {
			$.messager.alert("温馨提示", "只能选择一条用户信息进行积分调整!");
			return false;
		}
		//会员卡在禁用(1)和挂失(2)状态下：不允许消费
		if (member[0].cardstatus == 1 || member[0].cardstatus == 2) {
			$.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许进行积分调整！");
			return false;
		}
		$.messager.prompt('积分调整','请输入你的积分数:',function(result) {
			//正负整数和0
			var s = /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/;
			if (!s.test(result)) {
				$.messager.alert("温馨提示", "请输入整数!");
				return false;
			} else {
				if (result) {
				$.ajax({
					url : '${baseurl}memberCard/updateMemberCardInfo.action',
					data : {
						point : result,
						id : member[0].id
					},
					async : true,
					type : 'post',
					dataType:'json',
					success : function(data) {
						if (data.success) {
							$.messager.alert('信息提示','积分调整成功！','info');
							$('#wu-datagrid4').datagrid('reload');
						} else {
							$.messager.alert('信息提示',data.msg,'info');
						}
					}
				});
	}
}
	});
				
	}

	/**
	 * 会员卡消费功能
	 */
	function consume() {
		//获取勾选的行信息
		var member = $('#wu-datagrid4').datagrid('getSelections');
		//alert(item.productid);return;
		if (member == null || member.length == 0) {
			$.messager.alert('信息提示', '请选择一条用户信息！');
			return false;
		}
		//修改信息，只能选择一条数据
		if (member.length != 1) {
			$.messager.alert("温馨提示", "只能选择一条用户信息进行消费!");
			return false;
		}
		//会员卡在禁用(1)和挂失(2)状态下：不允许消费
		if (member[0].cardstatus == 1 || member[0].cardstatus == 2) {
			$.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许消费！");
			return false;
		}
		$.messager.prompt('消费界面', '请输入你的消费金额:', function(result) {
			if (result) {
				var reg = /^[0-9]+([.]{1}[0-9]+){0,1}/;
				if (!reg.test(result)) {
					$.messager.alert("温馨提示", "消费金额错误!");
					return false;
				}
				$.ajax({
					url : '${baseurl}memberCard/updateMemberCardInfo.action',
					data : {
						consume : result,
						id : member[0].id
					},
					async : true,
					type : 'post',
					dataType:'json',
					success : function(data) {
						if (data.success) {
							$.messager.alert('信息提示', '消费成功！', 'info');
							$('#wu-datagrid4').datagrid('reload');
						} else {
							$.messager.alert('信息提示', data.msg, 'info');
						}
					}
				});
			}
		});
	}

	/**
	 * 会员卡充值功能
	 */
	function recharge() {
		//获取勾选的行信息
		var member = $('#wu-datagrid4').datagrid('getSelections');
		//alert(item.productid);return;
		if (member == null || member.length == 0) {
			$.messager.alert('信息提示', '请选择一条数据！');
			return false;
		}
		//修改信息，只能选择一条数据
		if (member.length != 1) {
			$.messager.alert("温馨提示", "只能选择一个用户进行充值!");
			return false;
		}

		//会员卡在禁用(1)和挂失(2)状态下：不允许充值
		if (member[0].cardstatus == 1 || member[0].cardstatus == 2) {
			$.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许充值！");
			return false;
		}

		$.messager.prompt('充值界面', '请输入你的充值金额:', function(result) {
			if (result) {
				var s = /^[0-9]*[1-9][0-9]*$/;
				if (!s.test(result)) {
					$.messager.alert("温馨提示", "请输入整数!");
					return false;
				}
				$.ajax({
					url : '${baseurl}memberCard/updateMemberCardInfo.action',
					data : {
						recharge : result,
						id : member[0].id
					},
					async : true,
					type : 'post',
					success : function(data) {
						var json = eval("(" + data + ")");
						if (json.success) {
							$.messager.alert('信息提示', '充值成功！', 'info');
							$('#wu-datagrid4').datagrid('reload');
						} else {
							$.messager.alert('信息提示', "充值失败", 'info');
						}
					}
				});
			}
		});
	}

	   //回车键查询所有会员卡信息
    $(window).keydown(function(e){
         var ev = window.event || e;
            console.log(ev);
            if (ev.keyCode == 13) {
                $("#begin").click();
            }
    }) 
	
	/**
	 * 有条件的查询所有会员卡信息
	 */
	function findAllByCondition() {
		$('#wu-datagrid4').datagrid('load', {
			startTime : $.trim($('input[name="startTime"]').val()),
			endTime : $.trim($('input[name="endTime"]').val()),
			keyword : $.trim($("#keyword").val())
		});
	}

	//导出会员信息表格
	function exportMemberInfoExcel() {
		download("${baseurl}memberCard/exportExcel.action", {
			startTime : $('input[name="startTime"]').val(),
			endTime : $('input[name="endTime"]').val(),
			keyword : $("#keyword").val()
		});
	}

	//打印报表
	function print() {
		CreateFormPage("会员信息", $('#wu-datagrid4'));
	}

	//刷新表格数据
	function refresh() {
		$('#wu-datagrid4').datagrid('reload');
	}

	/**
	 * 修改记录
	 */
	function edit() {
		$.ajax({
			url : '${baseurl}member/register.action',
			data : $('#wu-form').serialize(),
			async : true,
			type : 'post',
			success : function(data) {
				if (data) {
					//$.messager.alert('信息提示','修改成功！','info');
					$('#wu-datagrid4').datagrid('reload');
					$('#wu-dialog').dialog('close');
				} else {
					$.messager.alert('信息提示', '修改失败！', 'info');
				}
			}
		});

	}

	/**
	 * 打开修改窗口
	 */
	function openEdit() {
		$('#wu-form').form('clear');
		//获取勾选的行信息
		var member = $('#wu-datagrid4').datagrid('getSelections');
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
		$('#wu-form').form('load', member[0]);
		$("#loginname").attr('disabled', true);
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
					$('#wu-dialog').dialog('close');
				}
			} ]
		});
	}

	/**
	 *  载入数据
	 */
	$('#wu-datagrid4').datagrid({
		url : '${baseurl}memberCard/findAllByCondition.action',
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
			width : 60,
			sortable : true,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		}/* , {
			field : 'memberid',
			title : '会员编号',
			align : 'center',
			width : 180
		} */, {
			field : 'membercardid',
			title : '会员卡编号',
			width : 130,
			align : 'center'
		}, {
			field : 'cardstatus',
			title : '状态',
			align : 'center',
			width : 60,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "正常";
				}
				if (value == 1) {
					return "禁用";
				}
				if (value == 2) {
					return "挂失";
				}
				if (value == 3) {
                    return "注销";
                }
			},
			editor : {
				type : 'combobox',
				options : {
					valueField : 'cardstatus',
					textField : '23',
					required : true
				}
			}
		}, {
			field : 'cardgrade',
			title : '会员卡级别',
			align : 'center',
			width : 50
		}, {
			field : 'discount',
			title : '折扣',
			align : 'center',
			width : 50
		}, {
			field : 'balance',
			title : '余额',
			sortable : true,
			align : 'center',
			width : 70
		}, {
			field : 'totalpoint',
			title : '累计积分',
			sortable : true,
			align : 'center',
			width : 80
		}, {
			field : 'totalconsumption',
			title : '累计消费总额',
			sortable : true,
			align : 'center',
			width : 80
		}, {
			field : 'totalrecharge',
			title : '累计充值总额',
			sortable : true,
			align : 'center',
			width : 80
		}, {
			field : 'opendate',
			title : '开卡日期',
			sortable : true,
			align : 'center',
			width : 100,
			formatter : function(value, row, index) {
				var unixTimestamp = new Date(value);
				return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
			}
		} ] ]
	/* ,
			onAfterEdit : function(rowIndex, rowData, changes) {
				//endEdit该方法触发此事件
				console.info(rowData);
				editRow = undefined;
			},
			onDblClickRow : function(rowIndex, rowData) {
				//双击开启编辑行
				console.log(rowIndex);
				console.log(rowData);
				$('#wu-datagrid4').datagrid("beginEdit", rowIndex);
				editRow = rowIndex;
			} */
	});
</script>
</body>
</html>



