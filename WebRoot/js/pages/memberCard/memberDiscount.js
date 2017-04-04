/**
 * 会员信息的js文件
 */
 
 
/**
 * 拓展easyui表单的校验规则
 */
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


/**
 * 有条件的查询所有会员信息
 */
function findAllByCondition() {
    var startTime = $.trim($('input[name="startTime5"]').val());
    var endTime = $.trim($('input[name="endTime5"]').val());
    $('#wu-datagrid').datagrid('load', {
        startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
        endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
        keyword: $.trim($("#keyword5").val())
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
/**
 * 性别校验
 */
$('#sex').validatebox({
    required: true,
    missingMessage: '性别必须选择'
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
function del() {
    var members = $('#wu-datagrid11').datagrid('getSelections');
    var ids = [];
    if (members == null || members.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
        if (result) {
            $(members).each(function () {
                if (this.membertype == 0) {
                    $.messager.alert('信息提示', '管理员账号不允许删除！', 'info');
                    return false;
                }
                ids.push(this.id);
            });
            $.ajax({
                traditional: true,
                url: baseurl + 'member/delMember.action',
                type: 'post',
                async: true,
                dataType: 'json',
                data: {
                    ids: ids.join(",")
                },
                success: function (data) {
                    if (data.success) {
                        $.messager.alert('信息提示', '删除成功！', 'info');
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
                width: 80,
                sortable: true
            },
            {
            field: 'grade',
            title: '会员级别',
            width: 50
        },
            {
                field: 'discount',
                title: '会员折扣',
                sortable: true,
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
