/**
 * 会员卡消费记录的js文件
 */
 
 
    /**
     * 有条件的查询所有会员卡消费记录
     */
    function findConsumeRecordByCondition() {
        var startTime = $.trim($('input[name="startTime3"]').val());
        var endTime = $.trim($('input[name="endTime3"]').val());
        $('#wu-datagrid3').datagrid('load', {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $.trim($("#keyword3").val())
        });
    }


    /**
     * 导出所有会员卡消费记录
     */
    function exportConsumeRecordExcel() {
        var startTime = $.trim($('input[name="startTime3"]').val());
        var endTime = $.trim($('input[name="endTime3"]').val());
        download(baseurl + "consumeRecord/exportExcel.action", {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $("#keyword3").val()
        });
    }

    /**
     * 打印报表
     */
    function printConsumeRecord() {
        CreateFormPage("会员信息", $('#wu-datagrid3'));
    }

    /**
     * 刷新表格数据
     */
    function refreshConsumeRecord() {
        $('#wu-datagrid3').datagrid('reload');
    }

    /**
     *  载入数据
     */
    $('#wu-datagrid3').datagrid({
        url: baseurl + 'consumeRecord/findAllByCondition.action',
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
                    field: 'membername',
                    title: '会员名称',
                    width: 40
                },
                {
                    field: 'memberid',
                    title: '会员编号',
                    width: 80,
                },
                {
                    field: 'membercardid',
                    title: '会员卡号',
                    width: 55
                },
                {
                    field: 'consumemoney',
                    title: '消费金额(元)',
                    sortable: true,
                    width: 50
                },
                {
                    field: 'consumetime',
                    title: '消费时间',
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
