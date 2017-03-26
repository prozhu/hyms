
    /**
     * 充值记录的js文件
     */
    /*保存图片
     */
    function saveImageInfo3() {
        var mycanvas = document.getElementById("JSChart_memberChart_container");
        var image = mycanvas.toDataURL('image/png');
        var w = window.open('about:blank', 'image from canvas');
        w.document.write("<img src='" + image + "' alt='from canvas'/>");
    }


    /**下载图片到本地
     */
    function saveAsLocalImage3() {
        var myCanvas = document.getElementById("JSChart_memberChart_container");
        var image = myCanvas.toDataURL("image/png").replace("image/png",
                "image/octet-stream");
        window.location.href = image; // it will save locally
    }


    /**
     * 生成充值记录图表信息
     */
    function memberChart(url) {
        var mark = $("#mark3").val();
        var year = $("#year3").val();
        var time = $('#time3').datebox('getValue');
        
        $.ajax({
            url: baseurl + "member/memberChart.action",
            type: 'get',
            data: {
                mark: mark,
                markYear: year,
                time: time
            },
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    $.messager.alert('信息提示', '查询数据为空！', 'info');
                }
                var data = new Array(); //data是一个数组
                if (result.data != null) {
                    $(result.data).each(function (i) {
                        //alert("i:" + i + "," + this.month + "," + this.money);
                        data[i] = new Array(); // data[i] 声明为数组
                        if (mark === "year") {
                            data[i][0] = "" + this.year + "年";
                        } else if (mark === "quarter") {
                            data[i][0] = "第" + this.quarter + "季度";
                        } else if (mark === "month") {
                            data[i][0] = "" + this.month + "月";
                        } else {
                            data[i][0] = "" + this.times + "日";
                        }
                        data[i][1] = this.total;
                    });
                }
                //支持js二维数组、json格式、xml格式数据源
                /* var myData = new Array([ "商品1", 20 ], [  "商品2", 10 ], [  "商品3", 30 ], [  "商品4", 10 ],
                 [  "商品5", 5 ]); */
                //第二个参数值有：line,bar,pie分别表示用线形图、柱状图、饼图来展示报表

                var myChart = new JSChart('memberChart_container', $(
                        "#type3").val());
                if (result.data != null) {
                    myChart.setDataArray(data);
                }
                //myChart.set3D(true);
                //设置 X轴名称, 对饼图无效。
                myChart.setAxisNameX(mark === "year" ? "年度"
                        : mark === "quarter" ? "季度"
                        : mark === "month" ? "月度" : "周度");
                //设置 y轴名称, 对饼图无效。
                myChart.setAxisNameY('人数');
                //设置图表的大小
                myChart.setSize(700, 400);
                //设置轴线名字体大小，对饼图无效。默认是11。
                myChart.setAxisNameFontSize(13);
                //设置x轴和容器底部的距离，默认30
                myChart.setAxisPaddingBottom(45);
                //设置y轴和容器左边距的距离，默认40
                myChart.setAxisPaddingLeft(70);
                //设置图表右边和容器的距离，默认30。
                myChart.setAxisPaddingRight(30);
                //设置图表上边和容器的距离，默认30
                myChart.setAxisPaddingTop(30);
                //设置柱状图矩形间距，由此来控制柱状图的宽度，值为0～100之间的整数，默认是10。
                myChart.setBarSpacingRatio(10);
                //设置是否显示背景网格，默认是显示的
                myChart.setGrid(false);
                //设置图表的背景颜色
                myChart.setBackgroundColor('#EEE');
                //设置x轴上提示信息
                for (var i = 0; i < data.length; i++) {
                    myChart.setTooltip([i]);
                }
                //myChart.setGraphExtend(true);//设置是否开启图表延伸功能
                myChart.setTitlePosition("center");//设置标题位置，取值范围（center, left ， right.）
                myChart.setTitle(mark == "year" ? "年度会员人数统计图表"
                        : mark == "quarter" ? "季度会员人数统计图表"
                        : mark == "month" ? "月度会员人数统计图表"
                        : "周度会员人数统计图表");
                if (result.data != null) {
                    myChart.draw();
                }
            }
        });

        //这里控制显示保存图表图片和下载图表图片按钮
        $("#saveChart3").css("display", "inline");
        $("#downloadChart3").css("display", "inline");
    }

    /**
     * 按照年龄分布统计报表
     */
    function memberChartByAge() {
    	$.ajax({
            url: baseurl + "member/memberChartByAge.action",
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    $.messager.alert('信息提示', '查询数据为空！', 'info');
                }
                var data = new Array(); //data是一个数组
                if (result.data != null) {
                    $(result.data).each(function (i) {
                        //alert("i:" + i + "," + this.month + "," + this.money);
                        data[i] = new Array(); // data[i] 声明为数组
                        data[i][0] = "" + this.age + "岁";
                        data[i][1] = this.total;
                    });
                }
                //支持js二维数组、json格式、xml格式数据源
                /* var myData = new Array([ "商品1", 20 ], [  "商品2", 10 ], [  "商品3", 30 ], [  "商品4", 10 ],
                 [  "商品5", 5 ]); */
                //第二个参数值有：line,bar,pie分别表示用线形图、柱状图、饼图来展示报表

                var myChart = new JSChart('memberChart_container', $(
                        "#type3").val());
                if (result.data != null) {
                    myChart.setDataArray(data);
                }
                //myChart.set3D(true);
                //设置 X轴名称, 对饼图无效。
                myChart.setAxisNameX("年龄分布");
                //设置 y轴名称, 对饼图无效。
                myChart.setAxisNameY('人数');
                //设置图表的大小
                myChart.setSize(700, 400);
                //设置轴线名字体大小，对饼图无效。默认是11。
                myChart.setAxisNameFontSize(13);
                //设置x轴和容器底部的距离，默认30
                myChart.setAxisPaddingBottom(45);
                //设置y轴和容器左边距的距离，默认40
                myChart.setAxisPaddingLeft(70);
                //设置图表右边和容器的距离，默认30。
                myChart.setAxisPaddingRight(30);
                //设置图表上边和容器的距离，默认30
                myChart.setAxisPaddingTop(30);
                //设置柱状图矩形间距，由此来控制柱状图的宽度，值为0～100之间的整数，默认是10。
                myChart.setBarSpacingRatio(10);
                //设置是否显示背景网格，默认是显示的
                myChart.setGrid(false);
                //设置图表的背景颜色
                myChart.setBackgroundColor('#EEE');
                //设置x轴上提示信息
                for (var i = 0; i < data.length; i++) {
                    myChart.setTooltip([i]);
                }
                //myChart.setGraphExtend(true);//设置是否开启图表延伸功能
                myChart.setTitlePosition("center");//设置标题位置，取值范围（center, left ， right.）
                myChart.setTitle("会员年龄分布统计表");
                if (result.data != null) {
                    myChart.draw();
                }
            }
        });
    }

 
    /**
     * 页面加载完毕之后，自动执行的函数
     */
    $(function () {
    	
    	
        /**
         * 选择时间和选择年份的显示和隐藏
         */
        $("#mark3").change(function () {
            if ($("#mark3").val() == "week") {
                //显示选择时间的框进行显示
                $("#week3").css("display", "inline");
                //将选择年份的框进行隐藏
                $("#chooseYear3").css("display", "none");
            } else {
                //显示选择时间的框进行隐藏
                $("#week3").css("display", "none");
                //将选择年份的框进行显示
                $("#chooseYear3").css("display", "inline");
            }
            //触发加载图表的事件
            memberChart();
        });


        /**
         * 选择 ：柱形图、饼图、线形图的chang事件
         */
        $("#type3").change(function () {
            memberChart();
            memberChartByAge();
        });
        
        /**
         * 选择年份的时候，触发事件自动加载图表
         */
        $("#year3").change(function(){
        	memberChart();
        });

        /**
         * 动态的加载年份
         */
        $.ajax({
            url: baseurl + "member/findMemberYears.action",
            type: 'get',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    for (var i = 0; i < result.data.length; i++) {
                        $("#year3").append("<option value = " + result.data[i] + ">" + result.data[i] + "</option>");
                    }
                }
            }
        });


        /**
         * 图表信息
         */
        $("#search11").click(function () {
                    memberChart();
                }
        );
        
        /**
         * 按照年龄分布统计报表
         */
        $("#ageChart").click(function(){
        	memberChartByAge();
       });


    });
    //自动加载函数的尾部