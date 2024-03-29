
/**
 * 会员卡信息的js文件
 */

/**
 * 会员卡注销
 */
function cancel() {
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户进行注销操作！");
        return false;
    }
    // 会员卡在禁用(1)状态下：不允许注销
    if (memberCard[0].cardstatus == 1) {
        $.messager.alert("温馨提示", "会员卡在禁用状态下不允许注销！");
        return false;
    }

    var ids = [];
    // 收集id信息
    $(memberCard).each(function () {
        ids.push(this.id);
    });
    $.messager.confirm('信息提示', '确定注销该会员卡吗？', function (result) {
        if (result) {
            $.ajax({
                url: baseurl
                        + 'memberCard/memberCardActivate.action',
                type: 'post',
                data: {
                    ids: ids.join(","),
                    flag: 'cancel'
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert('信息提示', '注销成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', data.msg,
                                'info');
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
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户解除会员卡挂失状态！");
        return false;
    }
    // 会员卡在禁用(1)和挂失(2)状态下：不允许挂失
    if (memberCard[0].cardstatus == 1 || memberCard[0].cardstatus == 0) {
        $.messager.alert("温馨提示", "会员卡在禁用或者正常的状态下不允许解除挂失！");
        return false;
    }
    var ids = [];
    // 收集id信息
    $(memberCard).each(function () {
        ids.push(this.id);
    });
    $.messager.confirm('信息提示', '确定解除该会员卡的挂失状态吗？', function (result) {
        if (result) {
            $.ajax({
                url: baseurl
                        + 'memberCard/memberCardActivate.action',
                type: 'post',
                data: {
                    ids: ids.join(","),
                    flag: 'unloss'
                },
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '解除挂失成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', json.msg,
                                'info');
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
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户挂失会员卡！");
        return false;
    }
    // 会员卡在禁用(1)和挂失(2)状态下：不允许挂失
    if (memberCard[0].cardstatus == 1 || memberCard[0].cardstatus == 2) {
        $.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许挂失！");
        return false;
    }
    var ids = [];
    // 收集id信息
    $(memberCard).each(function () {
        ids.push(this.id);
    });
    $.messager.confirm('信息提示', '确定挂失该会员卡吗？', function (result) {
        if (result) {
            $.ajax({
                url: baseurl
                        + 'memberCard/memberCardActivate.action',
                type: 'post',
                data: {
                    ids: ids.join(","),
                    flag: 'loss'
                },
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '挂失成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                        // 挂失成功之后，普通会员将挂失按钮进行隐藏，管理员进行显示
                        if (memberGrade == 1) {
                            $("#loss").css("display", "none");
                        }
                    } else {
                        $.messager.alert('信息提示', json.msg,
                                'info');
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
    // 获取勾选的数据
    var memberCards = $('#wu-datagrid4').datagrid('getSelections');
    var ids = [];
    if (memberCards == null || memberCards.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 收集id信息
    $(memberCards).each(function () {
        ids.push(this.id);
    });
    $.messager.confirm('信息提示', '确定激活该会员卡吗？', function (result) {
        if (result) {
            $.ajax({
                url: baseurl
                        + 'memberCard/memberCardActivate.action',
                async: true,
                type: 'post',
                data: {
                    ids: ids.join(","),
                    flag: 'activate'
                },
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '激活成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', json.msg,
                                'info');
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
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    // alert(item.productid);return;
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一条用户信息进行积分调整!");
        return false;
    }
    // 会员卡在禁用(1)和挂失(2)状态下：不允许消费
    if (memberCard[0].cardstatus == 1 || memberCard[0].cardstatus == 2) {
        $.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许进行积分调整！");
        return false;
    }
    $.messager.prompt('积分调整', '请输入你的积分数:', function (result) {
        // 正负整数和0
        var s = /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/;
        if (!s.test(result)) {
            $.messager.alert("温馨提示", "请输入正负整数!");
            return false;
        }
        if ((memberCard[0].totalpoint + result) < 0) {
            $.messager.alert("温馨提示", "积分余额不足!");
            return false;
        } else {
            if (result) {
                $.ajax({
                    url: baseurl
                            + 'memberCard/updateMemberCardInfo.action',
                    data: {
                        point: result,
                        id: memberCard[0].id
                    },
                    async: true,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('信息提示', '积分调整成功!', 'info');
                            $('#wu-datagrid4').datagrid('reload');
                        } else {
                            $.messager.alert('信息提示', data.msg, 'info');
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
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    // alert(item.productid);return;
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条用户信息！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一条用户信息进行消费!");
        return false;
    }
    // 会员卡在禁用(1)和挂失(2)状态下：不允许消费
    if (memberCard[0].cardstatus == 1 || memberCard[0].cardstatus == 2) {
        $.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许消费！");
        return false;
    }
    $.messager.prompt('消费界面', '请输入你的消费金额:', function (result) {
        if (result) {
            var reg = /^[0-9]+([.]{1}[0-9]+){0,1}/;
            if (!reg.test(result)) {
                $.messager.alert("温馨提示", "消费金额错误!");
                return false;
            }
            if (memberCard[0].balance < result) {
                $.messager.alert("温馨提示", "余额不足!");
                return false;
            }
            $.ajax({
                url: baseurl
                        + 'memberCard/updateMemberCardInfo.action',
                data: {
                    consume: result,
                    id: memberCard[0].id
                },
                async: true,
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert('信息提示', '消费成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager.alert('信息提示', data.msg,
                                'info');
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
    // 获取勾选的行信息
    var memberCard = $('#wu-datagrid4').datagrid('getSelections');
    // alert(item.productid);return;
    if (memberCard == null || memberCard.length == 0) {
        $.messager.alert('信息提示', '请选择一条数据！');
        return false;
    }
    // 修改信息，只能选择一条数据
    if (memberCard.length != 1) {
        $.messager.alert("温馨提示", "只能选择一个用户进行充值!");
        return false;
    }

    /**
     * 会员卡在禁用(1)和挂失(2)状态下：不允许充值
     */
    if (memberCard[0].cardstatus == 1 || memberCard[0].cardstatus == 2) {
        $.messager.alert("温馨提示", "会员卡在禁用或者挂失的状态下不允许充值！");
        return false;
    }

    $.messager.prompt('充值界面', '请输入你的充值金额:', function (result) {
        if (result) {
            var s = /^[0-9]*[1-9][0-9]*$/;
            if (!s.test(result)) {
                $.messager.alert("温馨提示", "请输入整数!");
                return false;
            }
            $.ajax({
                url: baseurl
                        + 'memberCard/updateMemberCardInfo.action',
                data: {
                    recharge: result,
                    id: memberCard[0].id
                },
                async: true,
                type: 'post',
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.success) {
                        $.messager.alert('信息提示', '充值成功！',
                                'info');
                        $('#wu-datagrid4').datagrid('reload');
                    } else {
                        $.messager
                                .alert('信息提示', "充值失败", 'info');
                    }
                }
            });
        }
    });
}

/**
 * 有条件的查询所有会员卡信息
 */
function findMemberCardByCondition() {
    var startTime = $.trim($('input[name="startTime"]').val());
    var endTime = $.trim($('input[name="endTime"]').val());
    $('#wu-datagrid4').datagrid('load', {
        startTime: startTime.length > 0
                ? startTime + ' 00:00:00'
                : startTime,
        endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
        keyword: $.trim($("#keyword").val())
    });
}

/**
 * 导出会员信息表格
 */
function exportMemberCardExcel() {
    var startTime = $.trim($('input[name="startTime"]').val());
    var endTime = $.trim($('input[name="endTime"]').val());
    download(baseurl + "memberCard/exportExcel.action", {
        startTime: startTime.length > 0
                ? startTime + ' 00:00:00'
                : startTime,
        endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
        keyword: $("#keyword").val()
    });
}

/**
 * 打印报表
 */
function printMemberCard() {
    CreateFormPage("会员信息", $('#wu-datagrid4'));
}

/**
 * 刷新表格数据
 */
function refreshMemberCard() {
    $('#wu-datagrid4').datagrid('reload');
}

/**
 * 载入数据
 */
$('#wu-datagrid4').datagrid({
    url: baseurl + 'memberCard/findAllByCondition.action',
    rownumbers: true,
    singleSelect: false,
    total: 10,
    pageSize: 5,
    pageList: [10, 15, 20],
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
                field: 'memberName',
                title: '会员名称',
                width: 60,
                sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
                    }
                }
            }/*
         * , { field : 'memberid', title : '会员编号', align : 'center',
         * width : 180 }
         */,
            {
                field: 'memberCardId',
                title: '会员卡编号',
                width: 130,
                align: 'center'
            },
            {
                field: 'cardStatus',
                title: '状态',
                align: 'center',
                width: 60,
                formatter: function (value, row, index) {
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
                editor: {
                    type: 'combobox',
                    options: {
                        valueField: 'cardStatus',
                        textField: '23',
                        required: true
                    }
                }
            },
            {
                field: 'cardGrade',
                title: '会员卡级别',
                align: 'center',
                width: 50
            },
            {
                field: 'discount',
                title: '折扣',
                align: 'center',
                width: 50
            },
            {
                field: 'balance',
                title: '余额(元)',
                sortable: true,
                align: 'center',
                width: 70
            },
            {
                field: 'totalPoint',
                title: '累计积分',
                sortable: true,
                align: 'center',
                width: 80
            },
            {
                field: 'totalConsumption',
                title: '累计消费总额(元)',
                sortable: true,
                align: 'center',
                width: 80
            },
            {
                field: 'totalRecharge',
                title: '累计充值总额(元)',
                sortable: true,
                align: 'center',
                width: 80
            },
            {
                field: 'openDate',
                title: '开卡日期',
                sortable: true,
                align: 'center',
                width: 100,
                formatter: function (value, row, index) {
                    var unixTimestamp = new Date(value);
                    return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
                }
            }
        ]
    ]
    /*
     * , onAfterEdit : function(rowIndex, rowData, changes) {
     * //endEdit该方法触发此事件 console.info(rowData); editRow = undefined; },
     * onDblClickRow : function(rowIndex, rowData) { //双击开启编辑行
     * console.log(rowIndex); console.log(rowData);
     * $('#wu-datagrid4').datagrid("beginEdit", rowIndex); editRow =
     * rowIndex; }
     */
});
