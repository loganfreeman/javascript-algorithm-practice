function loadstars(){
	$.getJSON(Root+'index.php?s=gold-vod-id-'+Id+''+'-m'+Math.round(Math.random()*10000),function(r){
		stars(r);
	})
}
var hadpingfen = 0;
function stars(r) {
	var curstars = parseInt(r.vod_gold/2);
	$("#pa").html(r['data'].vod_gold_5 + "人");
	$("#pb").html(r['data'].vod_gold_4 + "人");
	$("#pc").html(r['data'].vod_gold_3 + "人");
	$("#pd").html(r['data'].vod_gold_2 + "人");
	$("#pe").html(r['data'].vod_gold_1 + "人");
	//$("#commnum").html(r['curpingfen'].num);
	var totalnum = parseInt(r['data'].vod_gold_1) + parseInt(r['data'].vod_gold_2) + parseInt(r['data'].vod_gold_3) + parseInt(r['data'].vod_gold_4) + parseInt(r['data'].vod_gold_5);
	if (totalnum > 0) {
		$("#pam").css("width", ((parseInt(r['data'].vod_gold_5) / totalnum) * 100) + "%");
		$("#pbm").css("width", ((parseInt(r['data'].vod_gold_4) / totalnum) * 100) + "%");
		$("#pcm").css("width", ((parseInt(r['data'].vod_gold_3) / totalnum) * 100) + "%");
		$("#pdm").css("width", ((parseInt(r['data'].vod_gold_2) / totalnum) * 100) + "%");
		$("#pem").css("width", ((parseInt(r['data'].vod_gold_1) / totalnum) * 100) + "%")
	};
	if (r['hadpingfen'] != undefined && r['hadpingfen'] != null) {
		hadpingfen = 1
	};
	//$("#addtime").html(r['time']);
	var PFbai = r['data'].vod_gold*10;
	if (PFbai > 0) {
		$("#rating-main").show();
		$("#rating-kong").hide();
		//		$("#fenshu").css('width', parseInt(PFbai) + "%");
		//		$("#total").css('width', parseInt(PFbai) + "%");
		$("#fenshu").animate({'width': parseInt(PFbai) + "%"});
		$("#total").animate({'width': parseInt(PFbai) + "%"});
		$("#pingfen").html(r['data'].vod_gold);
		$("#pingfen2").html(r['data'].vod_gold)
	} else {
		$("#rating-main").hide();
		$("#rating-kong").show();
		$(".loading").addClass('nopingfen').html('暂时没有人评分，赶快从左边打分吧！');
	};
	//$("#golder").html(r['curpingfen'].golder);
	if (curstars > 0) {
		var curnum = curstars - 1;
		$("ul.rating li:lt(" + curnum + ")").addClass("current");
		$("ul.rating li:eq(" + curnum + ")").addClass("current");
		$("ul.rating li:gt(" + curnum + ")").removeClass("current");
		var arr = new Array('很差', '较差', '还行', '推荐', '力荐');
		$("#ratewords").html(arr[curnum])
	}
};
			
loadstars();
function init(){
	$("ul.rating li").each(function(i){
		var $title = $(this).attr("title");
		var $lis = $("ul.rating li");
		var num = $(this).index();
		var n = num+1;
		$(this).click(function(){
			if(hadpingfen>0){
			$.showfloatdiv({txt:'已经评分,请务重复评分'});
			$.hidediv({});
			}else{
			$.showfloatdiv({txt:'数据提交中...',cssname:'loading'});
			$lis.removeClass("active");	
			$("ul.rating li:lt("+n+")").addClass("active");	 
			$("#ratewords").html($title);
			$.getJSON(Root+'index.php?s=gold-vod-id-'+Id+'-type-'+$(this).attr('val')+''+'-m'+Math.round(Math.random()*10000),function(r){
				if(parseInt(r.status)>0){
				stars(r);
				$.showfloatdiv({txt:r.info});
			    $.hidediv({});
				hadpingfen=1;
				}else{
					if(parseInt(r.status)==0){
						hadpingfen=1;
						$.showfloatdiv({txt:'已经评分,请务重复评分'});
			            $.hidediv({});
					}else{
					$.closefloatdiv();
					//$("#innermsg").trigger('click');	
					}
					
				}
			});
		}
		}
		).hover(function(){
			this.myTitle=this.title;
			this.title="";
			$(this).nextAll().removeClass("active");
			$(this).prevAll().addClass("active");
			$(this).addClass("active");
			$("#ratewords").html($title);
		},function(){
			this.title=this.myTitle;	
			$("ul.rating li:lt("+n+")").removeClass("hover");
		});	
		
	});
	$(".rating-panle").hover(function(){
		$(this).find(".rating-show").show();
	},function(){
		$(this).find(".rating-show").hide();
	});
}init();



jQuery.showfloatdiv = function(ox) {
    var oxdefaults = {
        txt: '数据加载中,请稍后...',
        classname: 'progressBar',
        left: 410,
        top: 210,
        wantclose: 1,
        suredo: function(e) {
            return false;
        },
        succdo: function(r) {},
        completetxt: '操作成功!',
        autoclose: 1,
        ispost: 0,
        cssname: 'succ',
        isajax: 0,
        intvaltime: 1000,
        redirurl: '/'
    };
    ox = ox || {};
    $.extend(oxdefaults, ox);
    $("#tbmovbox_overlay").remove();
    $("#tbmovbox").remove();
    if (oxdefaults.wantclose == 1) {
        var floatdiv = $('<div class="tbmovbox-overlayBG" id="tbmovbox_overlay"></div><div id="tbmovbox" class="tbmovbox png-img"><iframe frameborder="0" class="ui-iframe"></iframe><table class="ui-dialog-box"><tr><td><div class="ui-dialog"><div class="ui-dialog-cnt" id="ui-dialog-cnt"><div class="ui-dialog-tip alert" id="ui-cnt"><span id="xtip">' + oxdefaults.txt + '</span></div></div><div class="ui-dialog-close"><span class="close">关闭</span></div></div></td></tr></table></div>');
        $("body").append(floatdiv);
        $("#tbmovbox_overlay").fadeIn(500);
        $("#tbmovbox").fadeIn(500);
        $("#ui-cnt").removeClass('succ error alert loading').addClass(oxdefaults.cssname);
        $(".ui-dialog-close").click(function() {
            $.closefloatdiv();
        });
        if (oxdefaults.isajax == 1) {
            objEvent = getEvent();
            if (objEvent.srcElement) id = objEvent.srcElement;
            else id = objEvent.target;
            var idval = (id.attributes["data"].nodeValue != null && id.attributes["data"].nodeValue != undefined) ? id.attributes["data"].nodeValue: id.data;
            $.ajax({
                url: idval,
                async: true,
                type: 'get',
                cache: true,
                dataType: 'json',
                success: function(data, textStatus) {
                    if (data.msg != null && data.msg != undefined) {
                        $("#xtip").html(data.msg);
                    } else {
                        $("#xtip").html(oxdefaults.completetxt);
                    }
                    oxdefaults.succdo(data);
                    if (data.wantclose != null && data.wantclose != undefined) {
                        $.hidediv(data);
                    } else if (oxdefaults.autoclose == 1) {
                        $.hidediv(data);
                    }
                    if (data.wantredir != undefined || data.wantredir != null) {
                        if (data.redir != undefined || data.redir != null) {
                            setTimeout("$.refresh('" + data.redir + "')", oxdefaults.intvaltime);
                        } else {
                            setTimeout("$.refresh('" + oxdefaults.redirurl + "')", oxdefaults.intvaltime);
                        }
                    }
                },
                error: function(e) {
                    $("#xtip").html('系统繁忙,请稍后再试...');
                }
            });
        }
    } else if (oxdefaults.wantclose == 2) {
        objEvent = getEvent();
        if (objEvent.srcElement) id = objEvent.srcElement;
        else id = objEvent.target;
        var idval = (id.attributes["data"].nodeValue != null && id.attributes["data"].nodeValue != undefined) ? id.attributes["data"].nodeValue: id.data;
        var floatdiv = $('<div class="tbmovbox-overlayBG" id="tbmovbox_overlay"></div><div id="tbmovbox" class="tbmovbox png-img"><iframe frameborder="0" class="ui-iframe"></iframe><table class="ui-dialog-box"><tr><td><div class="ui-dialog"><div class="ui-dialog-cnt" id="ui-dialog-cnt"><div class="ui-dialog-tip alert" id="ui-cnt"><span id="xtip">' + oxdefaults.txt + '</span></div></div><div class="ui-dialog-todo"><a class="ui-link ui-link-small" href="javascript:void(0);" id="surebt">确定</a><a class="ui-link ui-link-small cancelbt"  id="cancelbt">取消</a><input type="hidden" id="hideval" value=""/></div><div class="ui-dialog-close"><span class="close">关闭</span></div></div></td></tr></table></div>');
        $("body").append(floatdiv);
        $("#tbmovbox_overlay").fadeIn(500);
        $("#tbmovbox").fadeIn(500);
        $(".ui-dialog-close").click(function() {
            $.closefloatdiv();
        });
        $(".cancelbt").click(function() {
            $.closefloatdiv();
        });
        $("#surebt").click(function(e) {
            if (!oxdefaults.suredo(e)) {
                $(".ui-dialog-todo").remove();
                $("#ui-cnt").removeClass('succ error alert').addClass('loading');
                if (oxdefaults.ispost == 0) {
                    $.ajax({
                        url: idval,
                        async: true,
                        type: 'get',
                        cache: true,
                        dataType: 'json',
                        success: function(data, textStatus) {
                            if (data.msg != null && data.msg != undefined) {
                                $("#xtip").html(data.msg);
                            } else {
                                $("#xtip").html(oxdefaults.completetxt);
                            }
                            oxdefaults.succdo(data);
                            if (data.wantclose != null && data.wantclose != undefined) {
                                $.hidediv(data);
                            } else if (oxdefaults.autoclose == 1) {
                                $.hidediv(data);
                            }
                        },
                        error: function(e) {
                            $("#xtip").html('系统繁忙,请稍后再试...');
                        }
                    });
                } else {
                    $("#" + oxdefaults.formid).tbmovsub({
                        curobj: $("#surebt"),
                        txt: '数据提交中,请稍后...',
                        onsucc: function(result) {
                            oxdefaults.succdo(result);
                            $.hidediv(result);
                        }
                    }).post({
                        url: oxdefaults.url
                    });
                }
            } else {
                oxdefaults.succdo(e);
            }
        });
    } else {
        var floatdiv = $('<div class="tbmovbox_overlayBG" id="tbmovbox_overlay"></div><div id="tbmovbox" class="tbmovbox"><iframe frameborder="0" class="ui-iframe"></iframe><div class="ui-dialog"><div class="ui-dialog-cnt" id="ui-dialog-cnt"><div class="ui-dialog-box"<div class="ui-cnt" id="ui-cnt">' + oxdefaults.txt + '</div></div></div></div></div>');
        $("body").append(floatdiv);
        $("#tbmovbox_overlay").fadeIn(500);
        $("#tbmovbox").fadeIn(500);
    }
    $('#tbmovbox_overlay').bind('click',
    function(e) {
        $.closefloatdiv(e);
        if (pp != null) {
            clearTimeout(pp);
        }
    });
};
jQuery.closefloatdiv = function(e) {
    $("#tbmovbox_overlay").remove();
    $("#tbmovbox").remove();
};
jQuery.hidediv = function(e) {
    var oxdefaults = {
        intvaltime: 1000
    };
    e = e || {};
    $.extend(oxdefaults, e);
    if (e.msg != null && e.msg != undefined) {
        $("#ui-cnt").html(e.msg);
    }
    if (parseInt(e.rcode) == 1) {
        $("#ui-cnt").removeClass('loading error alert').addClass('succ');
    } else if (parseInt(e.rcode) < 1) {
        $("#ui-cnt").removeClass('loading alert succ').addClass('error');
    }
    pp = setTimeout("$.closefloatdiv()", oxdefaults.intvaltime);
}; (function($) {
    $.fn.tbmovsub = function(options) {
        var defaults = {
            txt: '数据提交中,请稍后...',
            redirurl: window.location.href,
            dataType: 'json',
            onsucc: function(e) {},
            onerr: function() {
                $.hidediv({
                    msg: '系统繁忙'
                });
            },
            oncomplete: function() {},
            intvaltime: 1000
        };
        options.curobj.attr('disabled', true);
        var ox = options.curobj.offset();
        var options = $.extend(defaults, options);
        $.showfloatdiv({
            offset: ox,
            txt: defaults.txt
        });
        var obj = $(this);
        var id = obj.attr('id');
        return {
            post: function(e) {
                $("#ui-cnt").removeClass('succ error alert').addClass('loading');
                $.post(e.url, obj.serializeArray(),
                function(result) {
                    options.curobj.attr('disabled', false);
                    defaults.onsucc(result);
                    if (result.closediv != undefined || result.closediv != null) {
                        $.closefloatdiv();
                    }
                    if (result.wantredir != undefined || result.wantredir != null) {
                        if (result.redir != undefined || result.redir != null) {
                            setTimeout("$.refresh('" + result.redir + "')", options.intvaltime);
                        } else {
                            setTimeout("$.refresh('" + options.redirurl + "')", options.intvaltime);
                        }
                    }
                },
                options.dataType).error(function() {
                    options.curobj.attr('disabled', false);
                    defaults.onerr();
                }).complete(function() {
                    defaults.oncomplete();
                    options.curobj.attr('disabled', false);
                });
            },
            implodeval: function(e) {
                val = $("#" + id + " :input").map(function() {
                    if ($(this).attr('name') != '' && $(this).attr('name') != undefined) {
                        return $(this).attr('name') + "-" + $(this).val();
                    }
                }).get().join("-");
                return val;
            },
            get: function(e) {
                $(".ui-dialog-todo").remove();
                $("#ui-cnt").removeClass('succ error alert').addClass('loading');
                var val = this.implodeval();
                $.get(e.url + "-" + val, '',
                function(result) {
                    options.curobj.attr('disabled', false);
                    defaults.onsucc(result);
                    if (result.wantredir != undefined || result.wantredir != null) {
                        if (result.redir != undefined || result.redir != null) {
                            setTimeout("$.refresh(" + result.redir + ")", options.intvaltime);
                        } else {
                            setTimeout("$.refresh(" + options.redirurl + ")", options.intvaltime);
                        }
                    }
                },
                options.dataType).error(function() {
                    options.curobj.attr('disabled', false);
                    defaults.onerr();
                }).complete(function() {
                    defaults.oncomplete();
                    options.curobj.attr('disabled', false);
                });
            }
        };
    };
    $.fn.ajaxdel = function(options) {
        var defaults = {
            txt: '数据提交中,请稍后...',
            redirurl: window.location.href,
            dataType: 'json',
            onsucc: function(e) {},
            onerr: function() {},
            oncomplete: function() {},
            intvaltime: 3000
        };
        $(".ui-dialog-todo").remove();
        $("#ui-cnt").removeClass('succ error alert').addClass('loading');
        var options = $.extend(defaults, options);
        var ajurl = $(this).attr('url');
        $.ajax({
            url: ajurl,
            success: function(data) {
                options.onsucc(data);
            },
            error: function() {
                options.onerr();
            },
            complete: function() {
                options.oncomplete();
            },
            dataType: 'json'
        });
    };
})(jQuery);