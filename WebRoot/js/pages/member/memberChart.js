
    //解决异步加载js文件，在浏览器开发工具source目录中找不到js文件的问题
    //@ sourceURL=memberChart.js
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
    function memberChart() {
        var mark = $("#mark3").val() == "undefined" ? "year" : $("#mark3").val() ;
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
     * 按照年龄分布统计报表(多柱形图)
     */
    function memberChartByAgeBar() {
        $.ajax({
            url: baseurl + "member/memberChartByAgeBar.action",
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    $.messager.alert('信息提示', '查询数据为空！', 'info');
                }
                var data = new Array();
                if (result.data != null) {
                    $(result.data).each(function (i) {
                        data[i] = new Array(); // data[i] 声明为数组
                        data[i][0] = "" + this.age + "岁";
                        data[i][1] = this.totalMan;
                        data[i][2] = this.totalLady;
                    });
                }
                var myChart = new JSChart('memberChart_container', "bar");
                if (result.data != null) {
                    myChart.setDataArray(data);
                }
                //myChart.set3D(true);
                //设置 X轴名称, 对饼图无效。
                myChart.setAxisNameX("年龄分布");
                //设置 y轴名称, 对饼图无效。
                myChart.setAxisNameY('人数');
                //设置图表的大小
                myChart.setSize(100 * data.length, 400);
                //设置多柱形图的颜色
                myChart.setBarColor('#2D6B96', 1);
                myChart.setBarColor('#9CCEF0', 2);
                myChart.setBarBorderWidth(0);
                myChart.setBarSpacingRatio(50)
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
                //为提示标志设置颜色
                myChart.setFlagColor('#9D16FC');
                //设置是否显示图例
                myChart.setLegendShow(true);
                //设置不自动创建图例
                //myChart.setLegendDetect(false);
                //设置图例显示信息
                /*               myChart.setLegend('#2D6B96', '男性');
                 myChart.setLegend('#9CCEF0', '女性');*/
                //设置图例显示的位置，可以是相对位置
                myChart.setLegendPosition('right top');
                myChart.setLegendForBar(1, '男性');
                myChart.setLegendForBar(2, '女性');

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
     * 按照年龄分布统计报表(多线形图)
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
                var data = new Array(); //用来存放男年龄段
                var data1 = new Array(); //用来存放女年龄段
                if (result.data != null) {
                    //对数组进行遍历(由于要区分男女年龄段，所以需要放到两个不同数组中)
                    $(result.data).each(function (i) {
                        //判断前缀是 “男” 或者 “女”
                        if (this.age.toString().substr(0, 1) == '男') {
                            data[i] = new Array(); // data[i] 声明为数组
                            data[i][0] = "" + this.age + "岁";
                            data[i][1] = this.total;
                        } else {
                            data1[i] = new Array(); // data[i] 声明为数组
                            data1[i][0] = "" + this.age + "岁";
                            data1[i][1] = this.total;
                        }
                    });
                    //数组去除空值
                    for (var i = 0; i < data.length; i++) {
                        if (data[i] == "" || typeof(data[i]) == "undefined") {
                            data.splice(i, 1);
                            i = i - 1;
                        }
                    }

                    //去除"年龄段前面的男女标记"
                    for (var i = 0; i < data.length; i++) {
                        data[i][0] = data[i][0].substring(1);
                    }
                    //去除"年龄段前面的男女标记"
                    for (var i = 0; i < data1.length; i++) {
                        data1[i][0] = data1[i][0].substring(1);
                    }

                }

                //支持js二维数组、json格式、xml格式数据源
                /* var myData = new Array([ "商品1", 20 ], [  "商品2", 10 ], [  "商品3", 30 ], [  "商品4", 10 ],
                 [  "商品5", 5 ]); */
                //第二个参数值有：line,bar,pie分别表示用线形图、柱状图、饼图来展示报表

                var myChart = new JSChart('memberChart_container', $(
                        "#type3").val());
                if (result.data != null) {
                    myChart.setDataArray(data, '男性');
                    myChart.setDataArray(data1, '女性');
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
                //设置曲线图中曲线的颜色,第一个参数为颜色，第二个参数为数组（数据源）的最后一个字符串元素
                myChart.setLineColor('#A4D314', '男性');
                myChart.setLineColor('#BBBBBB', '女性');
                //为提示标志设置颜色
                myChart.setFlagColor('#9D16FC');
                //设置是否显示图例
                myChart.setLegendShow(true);
                //设置图例显示信息
                // myChart.setLegend('#3366FB', 'Papers which cite from other articles');
                // myChart.setLegend('#0000FA', 'Papers which cite from news');
                //设置图例显示的位置，可以是相对位置
                myChart.setLegendPosition('right top');
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
            var temp = $("#mark3").val();
            var type = $("#type3").val();
            if (temp == "week") {
                //显示选择时间的框进行显示
                $("#week3").css("display", "inline");
                //将选择年份的框进行隐藏
                $("#chooseYear3").css("display", "none");
                //显示饼图
                $("#pie").css("display", "inline");
            } else {
                //显示选择时间的框进行隐藏
                $("#week3").css("display", "none");
                //将选择年份的框进行显示
                $("#chooseYear3").css("display", "inline");
                //显示饼图
                $("#pie").css("display", "inline");
            }
            //如果是年龄图表(只显示 "line", "bar"， 不显示 "pie")
            if (temp == "ageChart") {
                //隐藏饼图
                $("#pie").css("display", "none");
            }
            //如果是按照年龄区分图表的话，(ageChart 和 多柱形图)
            if (temp == "ageChart" && type == "bar") {
                memberChartByAgeBar();
            }
            //如果是按照年龄区分图表的话，(ageChart 和 多线形图)
            if (temp == "ageChart" && type == "line") {
                //调用按照年龄加载图表信息
                memberChartByAge();
            }
            if (temp != "ageChart") {
                //调用普通的加载图表事件
                memberChart();
            }
        });


        /**
         * 选择 ：柱形图、饼图、线形图的chang事件
         */
        $("#type3").change(function () {
            var temp = $("#mark3").val();
            var type = $("#type3").val();
            //年龄图表，线形图
            if (temp == "ageChart" && type == "line") {
                //调用按照年龄加载图表信息
                memberChartByAge();
            }
            //年龄图表，柱形图
            if (temp == "ageChart" && type == "bar") {
                memberChartByAgeBar();
            }
            if (temp != "ageChart") {
                //调用普通的加载图表事件
                memberChart();
            }
        });

        /**
         * 选择年份的时候，触发事件自动加载图表
         */
        $("#year3").change(function () {
            memberChart();
        });

       
       


        /**
         * 图表信息
         */
        $("#search11").click(function () {
                    var temp = $("#mark3").val();
                    var type = $("#type3").val();
                    //年龄图表，线形图
                    if (temp == "aggChart" && type == "line") {
                        //调用按照年龄加载图表信息
                        memberChartByAge();
                    }
                    //年龄图表，柱形图
                    if (temp == "aggChart" && type == "bar") {
                        memberChartByAgeBar();
                    } else {
                        //调用普通的加载图表事件
                        memberChart();
                    }
                }
        );

    });
    //自动加载函数的尾部
    
    
    
    //easyui 框架加载完毕之后，在调用执行方法(可以有效避免元素没有构建成功，就执行方法)
    $.parser.onComplete = function(){  
    	//调用普通的加载图表事件
        memberChart();
        
        
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
 
    }