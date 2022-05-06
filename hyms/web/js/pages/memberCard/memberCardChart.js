
    //解决异步加载js文件，在浏览器开发工具source目录中找不到js文件的问题
    //@ sourceURL=memberChart.js
    /**
     * 充值记录的js文件
     */
    /*保存图片
     */
    function saveImageInfo4() {
        var mycanvas = document.getElementById("JSChart_memberCardChart_container");
        var image = mycanvas.toDataURL('image/png');
        var w = window.open('about:blank', 'image from canvas');
        w.document.write("<img src='" + image + "' alt='from canvas'/>");
    }


    /**下载图片到本地
     */
    function saveAsLocalImage4() {
        var myCanvas = document.getElementById("JSChart_memberCardChart_container");
        var image = myCanvas.toDataURL("image/png").replace("image/png",
                "image/octet-stream");
        window.location.href = image; // it will save locally
    }



    /**
     * 会员卡统计报表(多柱形图)
     */
    function memberCardChartBar() {
        var mark = $("#mark4").val() == "undefined" ? "year" : $("#mark4").val() ;
        var year = $("#year4").val();
        $.ajax({
            url: baseurl + "memberCard/memberCardChart.action",
            type: 'get',
            data: {
                mark: mark,
                markYear: year
            },
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    $.messager.alert('信息提示', '查询数据为空！', 'info');
                }
                var data = new Array();
                if (result.data != null) {
                    $(result.data).each(function (i) {
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
                        data[i][1] = this.balance;
                        data[i][2] = this.totalPoint;
                        data[i][3] = this.totalConsumption;
                        data[i][4] = this.totalRecharge;
                    });
                }
                var myChart = new JSChart('memberCardChart_container', "bar");
                if (result.data != null) {
                    myChart.setDataArray(data);
                }
                //myChart.set3D(true);
                //设置 X轴名称, 对饼图无效。
                myChart.setAxisNameX("会员卡统计");
                //设置 y轴名称, 对饼图无效。
                myChart.setAxisNameY('额度');
                //设置图表的大小
                myChart.setSize(1000, 500);
                //设置多柱形图的颜色
                myChart.setBarColor('#2D6B96', 1);
                myChart.setBarColor('#9CCEF0', 2);
                myChart.setBarColor('#FFEC8B', 3);
                myChart.setBarColor('#FA8072', 4);
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
                myChart.setLegendForBar(1, '卡余额(元)');
                myChart.setLegendForBar(2, '累计积分');
                myChart.setLegendForBar(3, '累计消费(元)');
                myChart.setLegendForBar(4, '累计充值(元)');

                //myChart.setGraphExtend(true);//设置是否开启图表延伸功能
                myChart.setTitlePosition("center");//设置标题位置，取值范围（center, left ， right.）
                myChart.setTitle(mark == "year" ? year + "年 年度会员卡统计表"
                        : mark == "quarter" ? year + "年 季度会员卡统计表"
                                : year + "年 月度会员卡统计表");
                if (result.data != null) {
                    myChart.draw();
                }
            }
        });
    }


    /**
     * 会员卡统计报表(多线形图)
     */
    function memberCardChartLine() {
        var mark = $("#mark4").val() == "undefined" ? "year" : $("#mark4").val() ;
        var year = $("#year4").val();
        $.ajax({
            url: baseurl + "memberCard/memberCardChart.action",
            type: 'get',
            data: {
                mark: mark,
                markYear: year
            },
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    $.messager.alert('信息提示', '查询数据为空！', 'info');
                }
                //分别为卡余额、累计积分、累计消费总额、累计充值数组
                var data = new Array(); //用来存放卡余额
                var data1 = new Array(); //用来存放累计积分
                var data2 = new Array(); //用来存放累计消费总额
                var data3 = new Array(); //用来存放累计充值
                if (result.data != null) {
                    //对数组进行遍历
                    $(result.data).each(function (i) {
                    		 data[i] = new Array(); // data[i] 声明为数组, 临时数组
                    		 data1[i] = new Array(); // data[i] 声明为数组, 临时数组
                    		 data2[i] = new Array(); // data[i] 声明为数组, 临时数组
                    		 data3[i] = new Array(); // data[i] 声明为数组, 临时数组
                    		 if (mark === "year") {
                                 data[i][0] = "" + this.year + "年";
                                 data1[i][0] = "" + this.year + "年";
                                 data2[i][0] = "" + this.year + "年";
                                 data3[i][0] = "" + this.year + "年";
                             } else if (mark === "quarter") {
                                 data[i][0] = "第" + this.quarter + "季度";
                                 data1[i][0] = "第" + this.quarter + "季度";
                                 data2[i][0] = "第" + this.quarter + "季度";
                                 data3[i][0] = "第" + this.quarter + "季度";
                             } else if (mark === "month") {
                                 data[i][0] = "" + this.month + "月";
                                 data1[i][0] = "" + this.month + "月";
                                 data2[i][0] = "" + this.month + "月";
                                 data3[i][0] = "" + this.month + "月";
                             }
                    		 data[i][1] = this.balance;
                    		 data1[i][1] = this.totalPoint;
                    		 data2[i][1] = this.totalConsumption;
                    		 data3[i][1] = this.totalRecharge;
                    });
                }

                //支持js二维数组、json格式、xml格式数据源
                /* var myData = new Array([ "商品1", 20 ], [  "商品2", 10 ], [  "商品3", 30 ], [  "商品4", 10 ],
                 [  "商品5", 5 ]); */
                //第二个参数值有：line,bar,pie分别表示用线形图、柱状图、饼图来展示报表

                var myChart = new JSChart('memberCardChart_container', $(
                        "#type4").val());
                if (result.data != null) {
                    myChart.setDataArray(data, '卡余额(元)');
                    myChart.setDataArray(data1, '累计积分');
                    myChart.setDataArray(data2, '累计消费(元)');
                    myChart.setDataArray(data3, '累计充值(元)');
                }
                //myChart.set3D(true);
                //设置 X轴名称, 对饼图无效。
                myChart.setAxisNameX(mark === "year" ? "年度会员卡相关记录"
                        : mark === "quarter" ? "季度会员卡相关记录"
                                 : "周度会员卡相关记录");
                //设置 y轴名称, 对饼图无效。
                myChart.setAxisNameY('额度');
                //设置图表的大小
                myChart.setSize(900, 450);
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
                myChart.setLineColor('#2D6B96', '卡余额(元)');
                myChart.setLineColor('#CD919E', '累计积分');
                myChart.setLineColor('#FFEC8B', '累计消费(元)');
                myChart.setLineColor('#FA8072', '累计充值(元)');
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
                myChart.setTitle(mark == "year" ? year + "年 年度会员卡统计表"
                        : mark == "quarter" ? year + "年 季度会员卡统计表"
                                : year + "年 月度会员卡统计表");
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
         * 选择 ：柱形图、线形图的chang事件
         */
        $("#type4").change(function () {
              var type = $("#type4").val();
              //调用柱形图方法
              if (type == "bar") {
              	memberCardChartBar();
              } else {
              	//调用线形图方法
              	memberCardChartLine();
              }
        });
        
        
        /**
         * 选择 ：柱形图、线形图的chang事件
         */
        $("#mark4").change(function () {
              var type = $("#type4").val();
              //调用柱形图方法
              if (type == "bar") {
              	memberCardChartBar();
              } else {
              	//调用线形图方法
              	memberCardChartLine();
              }
        });

        /**
         * 选择年份的时候，触发事件自动加载图表
         */
        $("#year4").change(function () {
              var type = $("#type4").val();
              //调用柱形图方法
              if (type == "bar") {
              	memberCardChartBar();
              } else {
              	//调用线形图方法
              	memberCardChartLine();
              }
        });


    }); //自动加载函数的尾部
   
    
    
    
    //easyui 框架加载完毕之后，在调用执行方法(可以有效避免元素没有构建成功，就执行方法)
    $.parser.onComplete = function(){  
    	//调用普通的加载图表事件
    	memberCardChartBar();
        
    	  /**
         * 动态的加载年份
         */
        $.ajax({
            url: baseurl + "memberCard/findMemberCardYears.action",
            type: 'get',
            async: false,
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    for (var i = 0; i < result.data.length; i++) {
                        $("#year4").append("<option value = " + result.data[i] + ">" + result.data[i] + "</option>");
                    }
                }
            }
        });
       
        
    }