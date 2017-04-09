
//解决异步加载js文件，在浏览器开发工具source目录中找不到js文件的问题
//@ sourceURL=memberDiscount.js
/**
 * 会员信息的js文件
 */
 
 
/**
 * 拓展easyui表单的校验规则
 */
$.extend($.fn.validatebox.defaults.rules, {
    discount: {// 验证手机号码
        validator: function (value) {
            return value > 0 && value <= 1;
        },
        message: '折扣金额必须在 (0 - 1]之间的小数'
    },
    checkNum: {// 验证年龄
        validator: function (value) {
            return /^\d+$/.test(value);
        },
        message: '金额必须为整数'
    },
    compareDiscount: {// 验证年龄
        validator: function (value, param) {
        	if (/^\d+$/.test(value)) {
        		return $(param[0]).val() < value;
        	} else {
        		return false;
        	}
        },
        message: '累计消费区间(高) 必须大于 累计消费区间(低)'
    }
});



/**
 * 导出会员信息表格
 */
function exportMemberInfoExcel() {
    var startTime = $.trim($('input[name="startTime5"]').val());
    var endTime = $.trim($('input[name="endTime5"]').val());
    download(baseurl + "member/exportMemberInfoExcel.action", {
        startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
        endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
        keyword: $("#keyword5").val()
    });
}

/**
 * 打印报表
 */
function print() {
    CreateFormPage("会员折扣信息", $('#wu-datagrid11'));
}

/**
 * 刷新表格数据
 */
function refresh() {
    $('#wu-datagrid11').datagrid('reload');
}


/**
 * 删除会员信息
 */
function delDiscount() {
    var discounts = $('#wu-datagrid11').datagrid('getSelections');
    var ids = [];
    if (discounts == null || discounts.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    if (discounts.length > 1) {
        $.messager.alert('信息提示', '只能选择一条用户信息进行删除！');
        return false;
    }
    $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
        if (result) {
            $(discounts).each(function () {
                ids.push(this.id);
            });
            
            $.ajax({
                traditional: true,
                url: baseurl + 'delDiscount.action',
                type: 'post',
                async: true,
                dataType: 'json',
                data: {
                    id: ids.join(",")
                },
                success: function (data) {
                    if (data.success) {
                        $.messager.alert('信息提示', data.msg, 'info');
                        $('#wu-datagrid11').datagrid('reload');
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
function editDiscount() {
    if ($("#wu-form1").form('validate')) {
        $.ajax({
            url: baseurl + 'editDiscount.action',
            data: $('#wu-form1').serialize(),
            async: true,
            type: 'post',
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.success) {
                    $('#wu-datagrid11').datagrid('reload');
                    $('#wu-dialog11').dialog('close');
                    $.messager.alert('信息提示', json.msg, 'info');
                } else {
                    $.messager.alert('信息提示', '修改失败！', 'info');
                }
            }
        });
    }
}



/**
 * 添加记录
 */
function addDiscount() {
    $('#wu-form1').form('submit', {
        url: baseurl + 'addDiscount.action',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            if (data) {
                $('#wu-dialog11').dialog('close');
                $.messager.alert('信息提示', '添加成功！', 'info');
                $('#wu-datagrid11').datagrid('reload');
            } else {
                $.messager.alert('信息提示', '添加失败！', 'info');
            }
        }
    });
}


/**
 *  打开添加窗口
 */
function openAddDiscount() {
    $('#wu-form1').form('clear');
    $("#grade").attr('disabled', false);
    $('#wu-dialog11').dialog({
        closed: false,
        modal: true,
        title: "添加折扣信息",
        buttons: [
            {
                text: '确定',
                iconCls: 'icon-ok',
                handler: addDiscount
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //解决弹出窗口关闭后，验证消息还显示在页面的最上面
                    $('.tooltip.tooltip-right').hide();
                    $('#wu-dialog11').dialog('close');
                }
            }
        ]
    });
}

$.extend($.fn.validatebox.methods, {
    remove: function (jq, newposition) {
        return jq.each(function () {
            $(this).removeClass(" tooltip-content validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
        });
    },
    reduce: function (jq, newposition) {
        return jq.each(function () {
            var opt = $(this).data().validatebox.options;
            $(this).addClass("validatebox-text").validatebox(opt);
        });
    }
});

/**
 * 打开修改窗口
 */
function openEditDiscount() {
    //获取勾选的行信息
    var discount = $('#wu-datagrid11').datagrid('getSelections');
    if (discount == null || discount.length == 0) {
        $.messager.alert('信息提示', '请选择一条数据！');
        return false;
    }
    //修改信息，只能选择一条数据
    if (discount.length != 1) {
        $.messager.alert("温馨提示", "只能选择一条数据进行修改!");
        return false;
    }
    $('#wu-form1').form('clear');

    //加载选中行的数据到表单中填充
    $('#wu-form1').form('load', discount[0]);
    //将grade输入框给禁用掉，禁用用户修改
    $("#grade").attr('disabled', true);
    $("#grade").validatebox('remove');
    $('#wu-dialog11').dialog({
        closed: false,
        modal: true,
        cache: false,
        title: "修改信息",
        buttons: [
            {
                text: '确定',
                iconCls: 'icon-ok',
                handler: editDiscount
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //解决弹出窗口关闭后，验证消息还显示在页面的最上面
                    $('.tooltip.tooltip-right').hide();
                    $('#wu-dialog11').dialog('close');
                }
            }
        ]
    });
}

/**
 *  载入数据
 */
$('#wu-datagrid11').datagrid({
    url: baseurl + 'queryAllDiscount.action',
    rownumbers: true,
    singleSelect: false,
    total: 10,
    pageSize: 5,
    pageList: [ 10, 15, 20 ],
    pagination: true,
    multiSort: true,
    fitColumns: true,
    fit: true,
    columns: [
        [
            {
                checkbox: true
            },
            {
                field: 'lowConsume',
                title: '累计消费区间(低)',
                width: 70
            },
            {
                field: 'highConsume',
                title: '累计消费区间(高)',
                width: 80
            },
            {
            field: 'grade',
            title: '会员级别',
            width: 50
        },
            {
                field: 'discount',
                title: '会员折扣',
                width: 50
            },
            {
                field: 'createTime',
                title: '创建时间',
                sortable: true,
                width: 100,
                formatter: function (value, row, index) {
                    var unixTimestamp = new Date(value);
                    return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
                }
            }
        ]
    ]
});
