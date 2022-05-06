/**
 * 会员卡激活、挂失记录的js文件
 */
 
 
    /**
     * 有条件的查询所有会员卡激活、挂失记录
     */
    function findCardRecordByCondition() {
        var startTime = $.trim($('input[name="startTime2"]').val());
        var endTime = $.trim($('input[name="endTime2"]').val());
        $('#wu-datagrid1').datagrid('load', {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $.trim($("#keyword2").val())
        });
    }


    /**
     * 导出所有会员卡激活、挂失记录
     */
    function exportCardRecordExcel() {
        var startTime = $.trim($('input[name="startTime2"]').val());
        var endTime = $.trim($('input[name="endTime2"]').val());
        download(baseurl + "cardRecord/exportExcel.action", {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $("#keyword2").val()
        });
    }

    /**
     * 打印报表
     */
    function printCardRecord() {
        CreateFormPage("会员信息", $('#wu-datagrid1'));
    }

    /**
     * 刷新表格数据
     */
    function refreshCardRecord() {
        $('#wu-datagrid1').datagrid('reload');
    }


    /**
     *  载入数据
     */
    $('#wu-datagrid1').datagrid({
        url: baseurl + 'cardRecord/findAllByCondition.action',
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
                },
                {
                    field: 'memberCardId',
                    title: '会员卡号',
                    width: 55
                },
                {
                    field: 'operationType',
                    title: '操作类型',
                    sortable: true,
                    width: 50
                },
                {
                    field: 'changeTime',
                    title: '激活/挂失时间',
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
