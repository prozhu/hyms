(function($){
    $.fn.emailMatch= function(settings){
        var defaultSettings = {
            emailTip:"请输入邮箱地址",
            aEmail: ["163.com", "qq.com", "gmail.com", "126.com", "hotmail.com", "yahoo.com", "yahoo.com.cn", "live.com", "sohu.com", "sina.com"], //邮件数组
            wrapLayer:"body",
            className:"mailListBox",
            emailRemember:true,
            autoCursor:false,
            position:"bottom" // bottom, left , right
        };
        /* 合并默认参数和用户自定义参数 */
        settings = settings ? $.extend(defaultSettings,settings):defaultSettings;
        return this.each(function(){
            var elem = $(this),t=0,l=0,
            w = elem.outerWidth(), 
            h = elem.outerHeight(),
            selectVal = sMail = inputVal = "",arrayNum = 0,
            isIndex = -1;
            
            t = elem.position().top;
            l = elem.position().left;
            
            var mailWrap = document.createElement("div");
            $(mailWrap).attr({"id":elem.attr("id"),"class":settings.className})
            $(settings.wrapLayer).append(mailWrap);
            if($.trim(elem.val()) == ""){elem.val(settings.emailTip);};
            elem.focus(function(){
                arrayNum = 0;
                if($.trim(elem.val()) == settings.emailTip){elem.val('');}; // 清空 输入框 提示内容
                if($.trim(elem.val()) !=""){
                    inputVal = $.trim(elem.val());
                    isIndex = inputVal.indexOf("@");
                    if(isIndex >= 0 ){
                        sMail = inputVal.substr(isIndex + 1);
                        inputVal = inputVal.substring(0,isIndex);
                        if(sMail !=""){
                            arrayNum = parseInt(!position(settings.aEmail,sMail)?0:position(settings.aEmail,sMail));
                        }
                    }
                    if(settings.autoCursor){
                        elem.val(inputVal);
                        if($.browser.msie ){
                            setCaretAtEnd(elem.attr("id"));
                        }
                    }
                    showList($(mailWrap),w,h,t,l);
                    createMailList(mailWrap,inputVal,sMail,settings.aEmail,arrayNum);
                };
            }).blur(function(){
                if(elem.val() == ''){
                    elem.val(settings.emailTip);
                    hideList($(mailWrap));
                    return false;
                }; // 还原 输入框 提示内容
                enterVal(mailWrap,elem);
                hideList($(mailWrap));
            });
            elem.keyup(function(e){
                var suffixArray = [], eKey = e && (e.which || e.keyCode);
                //console.log(eKey);
                switch(eKey){
                    case 9: // tab 按键
                        return;
                        break;
                    case 13: { // 回车 
                            enterVal(mailWrap,elem);
                            hideList($(mailWrap));
                    }break;
                    case 38:{ // 方向键 上
                        showList($(mailWrap),w,h,t,l);
                        cursorMove(mailWrap,-1);
                    }break;
                    case 40: {// 方向键 下
                        showList($(mailWrap),w,h,t,l);
                        cursorMove(mailWrap,+1);
                    }break;
                    default:{
                            inputVal = $.trim(elem.val());
                        var    keyIndex = inputVal.indexOf("@");
                        var suffix = "",suffixState = true;        
                        if(keyIndex >= 0){
                            suffix = inputVal.substr(keyIndex + 1);
                            inputVal = inputVal.substring(0,keyIndex);
                            $("#t2").text("BBB" + inputVal);
                            if(suffix != '' && settings.emailRemember){ // 过滤数组
                                for (var i = 0; i < settings.aEmail.length; i++) {
                                    if (settings.aEmail[i].indexOf(suffix) == 0) {
                                        suffixArray.push(settings.aEmail[i]);
                                        suffixState = false;
                                    }
                                }                
                            }
                            if(suffix != '' && !settings.emailRemember){ // 当前高亮 选项 
                                for (var i = 0; i < settings.aEmail.length; i++) {
                                    if (settings.aEmail[i].indexOf(suffix) == 0) {
                                        arrayNum = i;
                                        suffixState = false;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        suffixArray = suffixArray.length > 0 ? suffixArray:settings.aEmail;
                        if(inputVal=="" && suffix == ""){
                            hideList($(mailWrap));
                            arrayNum = 0;
                            createMailList(mailWrap,inputVal,suffix,settings.aEmail,arrayNum);
                        }else{
                            showList($(mailWrap),w,h,t,l);
                            createMailList(mailWrap,inputVal,suffix,suffixArray,arrayNum);
                        }
                    }
                }
                
            });
            
            $(mailWrap).find("li:not('.first')").live('mouseover',function(){
                $(this).addClass("hover").siblings().removeClass("hover"); 
            });
            $(mailWrap).find("li:not('.first')").live('mousedown',function(){
                $(this).addClass("current").siblings().removeClass("current");
                enterVal(mailWrap,elem);
                hideList($(mailWrap));
            });
            $(mailWrap).bind("mouseout",function(){
                $(mailWrap).find("li:not('.first')").removeClass("hover");
            });
        });
    };
    
    function cursorMove(o,n){
        var cursorList = $(o).find("li:not('.first')"),k = new Number();
        for(i=0;i<cursorList.length;i++){
            if(cursorList[i].className == "current"){
                k = i+n
                cursorList[i].className = "";
            };
        }
        if(k < 0) k =0;
        if(k >= cursorList.length - 1 ) k = cursorList.length - 1;
        cursorList[k].className = "current";
    }
    
    function setCaretAtEnd(field){ // IE 系列浏览器 在自动光标跳回文档首问题
        var b = document.getElementById(field);
        if (b.createTextRange){
            var r = b.createTextRange(); 
            r.moveStart('character',  b.value.length); 
            r.collapse(); 
            r.select();
        } 
    }
    
    function position(array,value){  // 取得 元素在数组中的位置
        for(var i in array){
            if(array[i]==value){
                return i;break;
            }
        }
    };    
    function enterVal(oWrap,elem){
        elem.val($(oWrap).find("li.current").text()); // 获取高亮内容 到 输入框
    };
    
    function showList(oElem,w,h,t,l){ // 显示 邮箱框架 并定位.
        oElem.css({"display":"block","top":h + t,"left":l,"width":w-2});
    };
    
    function hideList(oElem){ // 显示 邮箱框架 
        oElem.css({"display":"none"});
    };
    
    function createMailList(oWrap,sVal,suffix,aEail,nK){ // 创建 候选列表
        if(nK < 0 ) {nK = 0;}
        if(nK > aEail.length-1) {nK = aEail.length - 1;}
        var mailList = "<li class='first'><span>请选择邮箱类型</span></li>";
        var State = true; // 用户键入 后缀 是否匹配候选后缀 的状态
        
        for(k=0; k<aEail.length;k++){
            if(suffix!= '' && aEail[k].indexOf(suffix) == 0){
                State = false;
            }
        }
        
        nK = parseInt(suffix!= '' && State && !position(aEail,suffix)?0:nK);
        
        if(suffix !='' && State ){
            if(nK == 0){
                mailList += '<li class="current"><span>'+sVal+'</span>@'+suffix+'</li>';
            } else {
                mailList += '<li><span>'+sVal+'</span>@'+suffix+'</li>';
            }
        }
        if($.isArray(aEail)){
            $.each(aEail, function(i){
                if(State && suffix !=''){
                    if(i == (nK-1) ){
                        mailList += '<li class="current"><span>'+sVal+'</span>@'+aEail[i]+'</li>';    
                    } else {
                        mailList += '<li><span>'+sVal+'</span>@'+aEail[i]+'</li>';    
                    }
                } else{
                    if(i == nK){
                        mailList += '<li class="current"><span>'+sVal+'</span>@'+aEail[i]+'</li>';    
                    } else {
                        mailList += '<li><span>'+sVal+'</span>@'+aEail[i]+'</li>';    
                    }
                }
            });
        }
        mailList = "<ul>" + mailList + "</ul>";
        $(oWrap).html(mailList);
    };
})(jQuery);









$.extend($.fn.validatebox.defaults.rules, {
            idcard: {// 验证身份证
                validator: function (value) {
                    return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
                },
                message: '身份证号码格式不正确'
            },
            minLength: {
                validator: function (value, param) {
                    return value.length >= param[0];
                },
                message: '请输入至少（2）个字符.'
            },
            length: { validator: function (value, param) {
                var len = $.trim(value).length;
                return len >= param[0] && len <= param[1];
            },
                message: "输入内容长度必须介于{0}和{1}之间."
            },
            phone: {// 验证电话号码
                validator: function (value) {
                    return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '格式不正确,请使用下面格式:020-88888888'
            },
            mobile: {// 验证手机号码
                validator: function (value) {
                    return /^(13|15|18)\d{9}$/i.test(value);
                },
                message: '手机号码格式不正确'
            },
            intOrFloat: {// 验证整数或小数
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '请输入数字，并确保格式正确'
            },
            currency: {// 验证货币
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '货币格式不正确'
            },
            qq: {// 验证QQ,从10000开始
                validator: function (value) {
                    return /^[1-9]\d{4,9}$/i.test(value);
                },
                message: 'QQ号码格式不正确'
            },
            integer: {// 验证整数 可正负数
                validator: function (value) {
                    //return /^[+]?[1-9]+\d*$/i.test(value);

                    return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
                },
                message: '请输入整数'
            },
            age: {// 验证年龄
                validator: function (value) {
                    return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
                },
                message: '年龄必须是0到120之间的整数'
            },

            chinese: {// 验证中文
                validator: function (value) {
                    return /^[\Α-\￥]+$/i.test(value);
                },
                message: '请输入中文'
            },
            english: {// 验证英语
                validator: function (value) {
                    return /^[A-Za-z]+$/i.test(value);
                },
                message: '请输入英文'
            },
            unnormal: {// 验证是否包含空格和非法字符
                validator: function (value) {
                    return /.+/i.test(value);
                },
                message: '输入值不能为空和包含其他非法字符'
            },
            username: {// 验证用户名
                validator: function (value) {
                    return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
                },
                message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
            },
            faxno: {// 验证传真
                validator: function (value) {
                    //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                    return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '传真号码不正确'
            },
            zip: {// 验证邮政编码
                validator: function (value) {
                    return /^[1-9]\d{5}$/i.test(value);
                },
                message: '邮政编码格式不正确'
            },
            ip: {// 验证IP地址
                validator: function (value) {
                    return /d+.d+.d+.d+/i.test(value);
                },
                message: 'IP地址格式不正确'
            },
            name: {// 验证姓名，可以是中文或英文
                validator: function (value) {
                    return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
                },
                message: '请输入姓名'
            },
            date: {// 验证姓名，可以是中文或英文
                validator: function (value) {
                    //格式yyyy-MM-dd或yyyy-M-d
                    return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
                },
                message: '清输入合适的日期格式'
            },
            msn: {
                validator: function (value) {
                    return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
                },
                message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
            },
            same: {
                validator: function (value, param) {
                    if ($("#" + param[0]).val() != "" && value != "") {
                        return $("#" + param[0]).val() == value;
                    } else {
                        return true;
                    }
                },
                message: '两次输入的密码不一致！'
            }
        }); 




  /**
    js 时间格式化
    */
    Date.prototype.pattern=function(fmt) {           
        var o = {           
        "M+" : this.getMonth()+1, //月份           
        "d+" : this.getDate(), //日           
        "h+" : this.getHours()%24 == 0 ? 24 : this.getHours()%24, //小时           
        "H+" : this.getHours(), //小时           
        "m+" : this.getMinutes(), //分           
        "s+" : this.getSeconds(), //秒           
        "q+" : Math.floor((this.getMonth()+3)/3), //季度           
        "S" : this.getMilliseconds() //毫秒           
        };           
        var week = {           
        "0" : "/u65e5",           
        "1" : "/u4e00",           
        "2" : "/u4e8c",           
        "3" : "/u4e09",           
        "4" : "/u56db",           
        "5" : "/u4e94",           
        "6" : "/u516d"          
        };           
        if(/(y+)/.test(fmt)){           
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
        }           
        if(/(E+)/.test(fmt)){           
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
        }           
        for(var k in o){           
            if(new RegExp("("+ k +")").test(fmt)){           
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
            }           
        }           
        return fmt;           
    }  
    
    
    /**
 * 页面无刷新方式下载文件
 */
function download(action, params) {
	var form = $("<form>"); // 定义一个form表单
	form.attr('style', 'display:none'); // 在form表单中添加查询参数
	form.attr('target', 'hiddenFream');
	form.attr('method', 'post');
	form.attr('action', action);

	if (params != undefined) {
		if($.isArray(params)){
			for (i in params) {
				var input = $("<input>");
				input.attr('name', params[i].name); // 在form表单中添加查询参数
				input.attr('value', params[i].value);
				input.attr('type', 'hidden');
				form.append(input); // 将查询参数控件提交到表单上
			}
		} else {
			for (name in params) {
				var input = $("<input>");
				input.attr('name', name); // 在form表单中添加查询参数
				input.attr('value', params[name]);
				input.attr('type', 'hidden');
				form.append(input); // 将查询参数控件提交到表单上
			}
		}
	}

	$('body').append(form); // 将表单放置在web中
	form.submit(); // 表单提交
};
    
    