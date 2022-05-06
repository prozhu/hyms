/**
 * window.open 新窗口，参数以post方式提交
 * @param url
 * @param data
 */
function openPostWindow(url,data1){

    var tempForm = document.createElement("form");
    tempForm.id = "tempForm1";
    tempForm.method = "post";
    tempForm.action = url;
    tempForm.target="_blank"; //打开新页面
    var hideInput1 = document.createElement("input");
    hideInput1.type = "hidden";
    hideInput1.name="opid"; //后台要接受这个参数来取值
    hideInput1.value = data1; //后台实际取到的值
    /*var hideInput2 = document.createElement("input");
     hideInput2.type = "hidden";
     hideInput2.name="xtmc";
     hideInput2.value = data2;*/  //这里就是如果需要第二个参数的时候可以自己再设置
    tempForm.appendChild(hideInput1);
    //tempForm.appendChild(hideInput2);
    if(document.all){
        tempForm.attachEvent("onsubmit",function(){});        //IE
    }else{
        var subObj = tempForm.addEventListener("submit",function(){},false);    //firefox
    }
    document.body.appendChild(tempForm);
    if(document.all){
        tempForm.fireEvent("onsubmit");
    }else{
        tempForm.dispatchEvent(new Event("submit"));
    }
    tempForm.submit();
    document.body.removeChild(tempForm);
}

//document.write("<script language=javascript src=’/js/common.js’></script>");
// strPrintName 打印任务名
// printDatagrid 要打印的datagrid
function CreateFormPage(strPrintName, printDatagrid) {
    var tableString = '<table cellspacing="0" class="pb">';
    var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
    var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象
    var nameList = '';

    // 载入title
    if (typeof columns != 'undefined' && columns != '') {
        $(columns).each(function (index) {
            tableString += '\n<tr>';
            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; i++) {
                    if (!frozenColumns[index][i].hidden) {
                        tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }//columns[index].length
            for (var i = 1; i < columns[index].length; i++) {
                if (!columns[index][i].hidden) {
                    tableString += '\n<th width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
                        nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        });
    }
    // 载入内容
    var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
    var nl = eval('([' + nameList.substring(1) + '])');
    for (var i = 0; i < rows.length; ++i) {
        tableString += '\n<tr>';
        $(nl).each(function (j) {
            var e = nl[j].f.lastIndexOf('_0');

            tableString += '\n<td';
            if (nl[j].a != 'undefined' && nl[j].a != '') {
                tableString += ' style="text-align:' + nl[j].a + ';"';
            }
            tableString += '>';
            if (e + 2 == nl[j].f.length) {
                tableString += rows[i][nl[j].f.substring(0, e)];
            }
            else {
                //将json格式的时间转为正常可观的时间格式
                if (('' + rows[i][nl[j].f]).length == 13) {
                    var unixTimestamp = new Date(rows[i][nl[j].f]);
                    rows[i][nl[j].f] = unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
                }
                tableString += rows[i][nl[j].f];
                tableString += '</td>';
            }
        });
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';

  //解决浏览器兼容问题
    if (navigator.userAgent.indexOf("Chrome") > 0) {
        window.open("print.html?tableString=" + tableString, strPrintName,
            "height=600,width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no, help=no");
        //openPostWindow("print.html",tableString);
    } else {
        window.showModalDialog("print.html", tableString,
            "location:No;status:No;help:No;dialogWidth:1000px;dialogHeight:600px;scroll:auto;");
    }


}