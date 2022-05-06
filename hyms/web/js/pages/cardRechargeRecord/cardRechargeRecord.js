/**
 * 会员卡充值记录的js文件
 */
 
 
    /**
     * 有条件的查询所有的会员卡充值记录信息
     */
    function findMemberCardRechargeByCondition() {
        var startTime = $.trim($('input[name="startTime1"]').val());
        var endTime = $.trim($('input[name="endTime1"]').val());
        $('#wu-datagrid2').datagrid('load', {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $.trim($("#keyword1").val())
        });
    }


    /**
     * 导出会员卡充值记录报表
     */
    function exportRechargeRecordExcel() {
        var startTime = $.trim($('input[name="startTime1"]').val());
        var endTime = $.trim($('input[name="endTime1"]').val());
        download(baseurl + "cardRechargeRecord/exportExcel.action", {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $("#keyword1").val()
        });
    }

    /**
     * 打印报表
     */
    function printRechargeRecord() {
        CreateFormPage("会员信息", $('#wu-datagrid2'));
    }

    /**
     * 刷新表格数据
     */
    function refreshRechargeRecord() {
        $('#wu-datagrid2').datagrid('reload');
    }


    /**
     *  载入数据
     */
    $('#wu-datagrid2').datagrid({
        url: baseurl + 'cardRechargeRecord/findAllByCondition.action',
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
                    field: 'memberName',
                    title: '会员名称',
                    width: 40
                },
                {
                    field: 'memberId',
                    title: '会员编号',
                    width: 80,
                    sortable: true
                },
                {
                    field: 'memberCardId',
                    title: '会员卡号',
                    width: 55
                },
                {
                    field: 'rechargeMoney',
                    title: '充值金额(元)',
                    sortable: true,
                    width: 50
                },
                {
                    field: 'changeTime',
                    title: '充值时间',
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

