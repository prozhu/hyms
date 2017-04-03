/**
 * 会员卡积分记录的js文件
 */
 
 

    /**
     * 有条件的查询所有会员卡的积分记录
     */
    function findPointRecordByCondition() {
        var startTime = $.trim($('input[name="startTime4"]').val());
        var endTime = $.trim($('input[name="endTime4"]').val());
        $('#wu-datagrid5').datagrid('load', {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $.trim($("#keyword4").val())
        });
    }

    /**
     * 导出所有会员卡的积分记录
     */
    function exportPointRecordExcel() {
        var startTime = $.trim($('input[name="startTime4"]').val());
        var endTime = $.trim($('input[name="endTime4"]').val());
        download(baseurl + "pointRecord/exportExcel.action", {
            startTime: startTime.length > 0 ? startTime + ' 00:00:00' : startTime,
            endTime: endTime.length > 0 ? endTime + ' 23:59:59' : endTime,
            keyword: $("#keyword4").val()
        });
    }

    /**
     * 打印报表
     */
    function printPointRecord() {
        CreateFormPage("会积分记录明细", $('#wu-datagrid5'));
    }

    /**
     * 刷新表格数据
     */
    function refreshPointRecord() {
        $('#wu-datagrid5').datagrid('reload');
    }


    /**
     *  载入数据
     */
    $('#wu-datagrid5').datagrid({
        url: baseurl + 'pointRecord/findAllByCondition.action',
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
                    width: 90,
                },
                {
                    field: 'membercardid',
                    title: '会员卡号',
                    width: 60
                },
                {
                    field: 'points',
                    title: '积分',
                    width: 50
                },
                {
                    field: 'operationtype',
                    title: '积分类型',
                    sortable: true,
                    width: 50
                },
                {
                    field: 'changetime',
                    title: '变更时间',
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
