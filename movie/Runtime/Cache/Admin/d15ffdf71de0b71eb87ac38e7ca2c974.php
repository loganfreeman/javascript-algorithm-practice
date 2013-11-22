<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>网站信息配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='__PUBLIC__/css/admin-style.css' />
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/jquery/jquery-1.7.2.min.js"></script>
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/js/admin.js"></script>
<script language="javascript">
$(document).ready(function(){
	$("#myform").submit(function(){
		if($feifeicms.form.empty('myform','site_name') == false){
			return false;
		}
		if($feifeicms.form.empty('myform','site_url') == false){
			return false;
		}
		if($feifeicms.form.empty('myform','site_path') == false){
			return false;
		}	
	});
	$("#tabs>a").click(function(){
		var no = $(this).attr('id');
		var n = $("#tabs>a").length;
		showtab('tabs',no,n);
		$("#tabs>a").removeClass("on");
		$(this).addClass("on");
		return false;
	});
	<?php if(($url_rewrite)  ==  "1"): ?>showtab('urlrewrite',1,1);
	showtab('urlrewrites',1,1);<?php endif; ?>	
	<?php if(($url_html)  ==  "1"): ?>showtab('htmlfilesuffix',1,1);
	showtab('urlhtml',1,1);<?php endif; ?>
	<?php if(($url_html_list)  ==  "1"): ?>showtab('listhtml',1,1);<?php endif; ?>
	<?php if(($url_html_play)  >  "0"): ?>showtab('playhtml',1,1);<?php endif; ?>	
	<?php if(($html_cache_on)  ==  "1"): ?>showtab('htmlcache',1,1);<?php endif; ?>
	<?php if(($upload_thumb)  ==  "1"): ?>showtab('thumb',1,1);<?php endif; ?>
	<?php if(($upload_water)  ==  "1"): ?>showtab('water',1,1);<?php endif; ?>
	<?php if(($upload_ftp)  ==  "1"): ?>showtab('ftptab',1,1);<?php endif; ?>
	<?php if(($play_show)  ==  "1"): ?>showtab('play_urllist',1,1);<?php endif; ?>		
	
});
function dir_html(id,value){
	if(value){
	$('#'+id).val(value);
	}
}	
function dir_html_add(id,value){
	//光标处插入指定内容
	$('#'+id).focus();
	var str = document.selection.createRange();
	str.text = value;	
	//$('#'+id).val($('#'+id).val()+value);
}
function playtab(mid,value){
	if(value>0){
		$('#'+mid).show();
	}else{
		$('#'+mid).hide();
	}
}
</script>
<style>
.dir{  color:#006600; font-size:14px;}
.diri{ color:#666666; font-size:14px; }
label{ color:#666666}
#urlhtml1 .left,#urlhtml1 select,#urlrewrites1 .left,#datacache1 .left,#htmlcache1 .left{ color:#444}
</style>
</head>
<body class="body">
<form action="?s=Admin-Admin-Configsave" method="post" name="myform" id="myform">
<div class="title">
	<div class="tabs" id="tabs"><a href="javascript:void(0)" class="on" onfocus="this.blur();" id="1">网站信息</a><a href="javascript:void(0)" onfocus="this.blur();" id="2">系统设置</a><a href="javascript:void(0)" onfocus="this.blur();" id="3">网页静态化</a><a href="javascript:void(0)" onfocus="this.blur();" id="9">URL伪静态</a><a href="javascript:void(0)" onfocus="this.blur();" id="4">性能优化</a><a href="javascript:void(0)" onfocus="this.blur();" id="5">附件设置</a><a href="javascript:void(0)" onfocus="this.blur();" id="6">视频设置</a><a href="javascript:void(0)" onfocus="this.blur();" id="7">采集设置</a><a href="javascript:void(0)" onfocus="this.blur();" id="8">用户中心</a></div>
</div>
<div class="add" id="tabs1">
    <ul><li class="left">网站名称：</li>
    	<li class="right"><input type="text" name="config[site_name]" id="site_name" value="<?php echo ($site_name); ?>" maxlength="50" error="* 网站名称不能为空!"><span id="site_name_error">*</span><label>请填写贵站名字。</label></li>
    </ul>
    <ul><li class="left">网站域名：</li>
    	<li class="right"><input type="text" name="config[site_url]" id="site_url" value="<?php echo ($site_url); ?>" maxlength="50" error="* 网站域名不能为空!"><span id="site_url_error">*</span><label>网站安装地址，结尾必需加斜杆 '/'。</label></li>
    </ul>
    <ul><li class="left">安装路径：</li>
    	<li class="right"><input type="text" name="config[site_path]" id="site_path" value="<?php echo ($site_path); ?>" maxlength="20" error="* 安装路径不能为空!"><span id="site_path_error">*</span><label>网站安装路径，一般不需要修改，结尾必需加斜杆 '/'。</label></li>
    </ul>
    <ul><li class="left">备案信息：</li>
    	<li class="right"><input type="text" name="config[site_icp]" id="site_icp" value="<?php echo ($site_icp); ?>" maxlength="20">&nbsp;</li>
    </ul>
    <ul><li class="left">站长邮箱：</li>
    	<li class="right"><input type="text" name="config[site_email]" id="site_email" value="<?php echo ($site_email); ?>" maxlength="20">&nbsp;</li>
    </ul> 
     <ul><li class="left">模板方案：</li>
    	<li class="right"><select name="config[default_theme]" class="w70"><?php if(is_array($dir)): $i = 0; $__LIST__ = $dir;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$admin): ++$i;$mod = ($i % 2 )?><option value="<?php echo ($admin["filename"]); ?>" <?php if(($admin["filename"])  ==  $default_theme): ?>selected<?php endif; ?>><?php echo ($admin["filename"]); ?></option><?php endforeach; endif; else: echo "" ;endif; ?></select>&nbsp;</li>
    </ul>   
    <ul><li class="left">站点关键字：</li>
    	<li class="right"><input type="text" name="config[site_keywords]" id="site_keywords" value="<?php echo (htmlspecialchars($site_keywords)); ?>" maxlength="100" class="w400">&nbsp;</li>
    </ul> 
    <ul><li class="left">站点描述：</li>
        <li class="right"><input type="text" name="config[site_description]" id="site_description" value="<?php echo (htmlspecialchars($site_description)); ?>" maxlength="150" class="w400">&nbsp;</li>
    </ul>
    <ul><li class="left">热门搜索(一行一个)：<br />带超链接使用"|"来隔开</li>
    	<li class="right" style="height:115px"><textarea name="config[site_hot]" id="site_hot" style="height:100px"><?php echo (htmlspecialchars($site_hot)); ?></textarea></li>
    </ul>    
    <ul><li class="left">统计代码：</li>
        <li class="right" style="height:75px"><textarea name="config[site_tongji]" id="site_tongji" style="height:60px"><?php echo (htmlspecialchars($site_tongji)); ?></textarea></li>
    </ul>         
    <ul><li class="left">版权信息：</li>
    	<li class="right" style="height:75px"><textarea name="config[site_copyright]" id="site_copyright" style="height:60px"><?php echo (htmlspecialchars($site_copyright)); ?></textarea></li>
    </ul>                           
</div>
<!-- -->
<div class="add" id="tabs2" style="display:none;"> 
     <ul><li class="left">后台数据管理排序方式：</li>
    	<li class="right"><select name="config[admin_order_type]" class="w70"><option value="addtime">时间</option><option value="id" <?php if(($admin_order_type)  ==  "id"): ?>selected<?php endif; ?>>ID值</option></select> 倒序</li>
    </ul> 
    <ul><li class="left">后台编辑数据时更新时间：</li>
    	<li class="right"><select name="config[admin_time_edit]" class="w70"><option value=1 <?php if(($admin_time_edit)  ==  "1"): ?>selected<?php endif; ?>>开启</option><option value=0 <?php if(($admin_time_edit)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul> 
    <ul><li class="left">广告文件保存目录：</li>
    	<li class="right"><input type="text" name="config[admin_ads_file]" id="admin_ads_file" value="<?php echo ($admin_ads_file); ?>" maxlength="10" class="w70">&nbsp;</li>
    </ul>                   
     <ul><li class="left">后台分页展示条数：</li>
    	<li class="right"><input type="text" name="config[url_num_admin]" id="url_num_admin" value="<?php echo ($url_num_admin); ?>" maxlength="5" class="w70">&nbsp;</li>
    </ul>
    <ul><li class="left">前台分页左右条数：</li>
    	<li class="right"><input type="text" name="config[home_pagenum]" id="home_pagenum" value="<?php echo ($home_pagenum); ?>" maxlength="5" class="w70">&nbsp;</li>
    </ul>
    <ul><li class="left">MYSQL数据库：</li>
    	<li class="right"><input type="text" name="config[db_name]" id="db_name" value="<?php echo ($db_name); ?>" maxlength="30" error="* MYSQL数据库名称不能为空!" class="w70"><span id="db_name_error">*</span><label>已存在的MYSQL数据库。</label></li>
    </ul>           
</div>
<!-- -->
<div class="add" id="tabs3" style="display:none;">
    <ul><li class="left">网站运行模式：</li>
    	<li class="right"><select name="config[url_html]" onChange="showtab('htmlfilesuffix',this.value,1);showtab('urlhtml',this.value,1);" class="w120"><option value="0" >动态运行</option><option value="1" <?php if(($url_html)  ==  "1"): ?>selected<?php endif; ?>>生成静态</option></select> <span id="htmlfilesuffix1" style="display:none">后缀名：<select name="config[html_file_suffix]"><option value=".html">.html</option><?php if(($html_file_suffix)  ==  ".htm"): ?><option value=".htm" selected>.htm</option><?php else: ?><option value=".htm">.htm</option><?php endif; ?><?php if(($html_file_suffix)  ==  ".shtml"): ?><option value=".shtml" selected>.shtml</option><?php else: ?><option value=".shtml">.shtml</option><?php endif; ?><?php if(($html_file_suffix)  ==  ".shtm"): ?><option value=".shtm" selected>.shtm</option><?php else: ?><option value=".shtm">.shtm</option><?php endif; ?><?php if(($html_file_suffix)  ==  ".aspx"): ?><option value=".aspx" selected>.aspx</option><?php else: ?><option value=".aspx">.aspx</option><?php endif; ?><?php if(($html_file_suffix)  ==  ".asp"): ?><option value=".asp" selected>.asp</option><?php else: ?><option value=".asp">.asp</option><?php endif; ?><?php if(($html_file_suffix)  ==  ".jsp"): ?><option value=".jsp" selected>.jsp</option><?php else: ?><option value=".jsp">.jsp</option><?php endif; ?></select></span>&nbsp;<label>如选择生成静态，则需要站长手动操作生成相关网页，否则网站不能正常运行。</label></li>
    </ul>
    <div id="urlhtml1" style="display:none;">
    <ul><li class="left">栏目分类页是否生成：</li>
    	<li class="right"><select name="config[url_html_list]" id="url_html_list" class="w120" onChange="showtab('listhtml',this.value,1);"><option value="0">动态不生成</option><option value="1" <?php if(($url_html_list)  ==  "1"): ?>selected<?php endif; ?>>生成静态网页</option></select>&nbsp;</li>         
    </ul>
    <div id="listhtml1" style="display:none">
    <ul><li class="left">视频栏目页路径：</li>
    	<li class="right"><input type="text" name="config[url_vodlist]" id="url_vodlist" maxlength="150" value="<?php echo ($url_vodlist); ?>" class="w200 diri"> <select style="width:120px" onChange="dir_html('url_vodlist',this.value);"><option>常用结构</option><option value="list/{listid}/index<?php echo ($html_file_suffix); ?>">1.list/id/</option><option value="list/{md5}/index<?php echo ($html_file_suffix); ?>">2.list/md5值/</option></option><option value="list/{listdir}/index<?php echo ($html_file_suffix); ?>">3.list/listdir/</option><option value="list/{listid}<?php echo ($html_file_suffix); ?>">4.list/id<?php echo ($html_file_suffix); ?></option><option value="list/{md5}<?php echo ($html_file_suffix); ?>">5.list/md5<?php echo ($html_file_suffix); ?></option><option value="list/{listdir}<?php echo ($html_file_suffix); ?>">6.list/listdir<?php echo ($html_file_suffix); ?></option></select> 变量：<a href="javascript:" onClick="dir_html_add('url_vodlist','{listid}');" title="分类ID" class="dir">{listid}</a> <a href="javascript:" onClick="dir_html_add('url_vodlist','{listdir}');" title="分类英文名" class="dir">{listdir}</a> <a href="javascript:" onClick="dir_html_add('url_vodlist','{md5}');" title="影片md5(id)" class="dir">{md5}</a> <a href="javascript:" onClick="dir_html_add('url_vodlist','index<?php echo ($html_file_suffix); ?>');" title="默认首页" class="dir">index<?php echo ($html_file_suffix); ?></a></li>            
    </ul>
    <ul><li class="left">新闻栏目页路径：</li>
    	<li class="right"><input type="text" name="config[url_newslist]" id="url_newslist" maxlength="150" value="<?php echo ($url_newslist); ?>" class="w200 diri"> <select style="width:120px" onChange="dir_html('url_newslist',this.value);"><option>常用结构</option><option value="list/{listid}/index<?php echo ($html_file_suffix); ?>">1.list/id/</option><option value="list/{md5}/index<?php echo ($html_file_suffix); ?>">2.list/md5值/</option></option><option value="list/{listdir}/index<?php echo ($html_file_suffix); ?>">3.list/listdir/</option><option value="list/{listid}<?php echo ($html_file_suffix); ?>">4.list/id<?php echo ($html_file_suffix); ?></option><option value="list/{md5}<?php echo ($html_file_suffix); ?>">5.list/md5<?php echo ($html_file_suffix); ?></option><option value="list/{listdir}<?php echo ($html_file_suffix); ?>">6.list/listdir<?php echo ($html_file_suffix); ?></option></select> 变量：<a href="javascript:" onClick="dir_html_add('url_newslist','{listid}');" title="分类ID" class="dir">{listid}</a> <a href="javascript:" onClick="dir_html_add('url_newslist','{listdir}');" title="分类英文名" class="dir">{listdir}</a> <a href="javascript:" onClick="dir_html_add('url_newslist','{md5}');" title="影片md5(id)" class="dir">{md5}</a> <a href="javascript:" onClick="dir_html_add('url_newslist','index<?php echo ($html_file_suffix); ?>');" title="默认首页" class="dir">index<?php echo ($html_file_suffix); ?></a></li>            
    </ul>     
    </div>      
    <ul><li class="left">视频内容页路径：</li>
    	<li class="right"><input type="text" name="config[url_voddata]" id="url_voddata" maxlength="150" value="<?php echo ($url_voddata); ?>" class="w200 diri"> <select style="width:120px" onChange="dir_html('url_voddata',this.value);"><option>常用结构</option><option value="vod/{id}/index<?php echo ($html_file_suffix); ?>">1.vod/id/</option><option value="vod/{md5}/index<?php echo ($html_file_suffix); ?>">2.vod/md5值/</option><option value="vod/{pinyin}/index<?php echo ($html_file_suffix); ?>">3.vod/拼音/</option><option value="vod/{id}<?php echo ($html_file_suffix); ?>">4.vod/id<?php echo ($html_file_suffix); ?></option><option value="vod/{md5}<?php echo ($html_file_suffix); ?>">5.vod/md5<?php echo ($html_file_suffix); ?></option><option value="vod/{pinyin}<?php echo ($html_file_suffix); ?>">6.vod/拼音<?php echo ($html_file_suffix); ?></option><option value="{listid}/{id}<?php echo ($html_file_suffix); ?>">7.listid/id<?php echo ($html_file_suffix); ?></option><option value="{listdir}/{pinyin}/index<?php echo ($html_file_suffix); ?>">8.listdir/拼音/</option></select> 变量：<a href="javascript:" onClick="dir_html_add('url_voddata','{listid}');" title="分类ID值" class="dir">{listid}</a> <a href="javascript:" onClick="dir_html_add('url_voddata','{listdir}');" title="分类英文名" class="dir">{listdir}</a> <a href="javascript:" onClick="dir_html_add('url_voddata','{pinyin}');" title="影片拼音" class="dir">{pinyin}</a> <a href="javascript:" onClick="dir_html_add('url_voddata','{id}');" title="影片ID" class="dir">{id}</a> <a href="javascript:" onClick="dir_html_add('url_voddata','{md5}');" title="影片md5(id)" class="dir">{md5}</a> <a href="javascript:" onClick="dir_html_add('url_voddata','index<?php echo ($html_file_suffix); ?>');" title="默认首页" class="dir">index<?php echo ($html_file_suffix); ?></a></li>            
    </ul>
    <ul><li class="left">新闻内容页路径：</li>
    	<li class="right"><input type="text" name="config[url_newsdata]" id="url_newsdata" maxlength="150" value="<?php echo ($url_newsdata); ?>" class="w200 diri"> <select style="width:120px" onChange="dir_html('url_newsdata',this.value);"><option>常用结构</option><option value="news/{id}/index<?php echo ($html_file_suffix); ?>">1.news/id/</option><option value="news/{md5}/index<?php echo ($html_file_suffix); ?>">2.news/md5值/</option><option value="news/{pinyin}/index<?php echo ($html_file_suffix); ?>">3.news/拼音/</option><option value="news/{id}<?php echo ($html_file_suffix); ?>">4.news/id<?php echo ($html_file_suffix); ?></option><option value="news/{md5}<?php echo ($html_file_suffix); ?>">5.news/md5<?php echo ($html_file_suffix); ?></option><option value="news/{pinyin}<?php echo ($html_file_suffix); ?>">6.news/拼音<?php echo ($html_file_suffix); ?></option><option value="{listid}/{id}<?php echo ($html_file_suffix); ?>">7.listid/id<?php echo ($html_file_suffix); ?></option><option value="{listdir}/{pinyin}/index<?php echo ($html_file_suffix); ?>">8.listdir/拼音/</option></select> 变量：<a href="javascript:" onClick="dir_html_add('url_newsdata','{listid}');" title="分类ID值" class="dir">{listid}</a> <a href="javascript:" onClick="dir_html_add('url_newsdata','{listdir}');" title="分类英文名" class="dir">{listdir}</a> <a href="javascript:" onClick="dir_html_add('url_newsdata','{pinyin}');" title="影片拼音" class="dir">{pinyin}</a> <a href="javascript:" onClick="dir_html_add('url_newsdata','{id}');" title="影片ID" class="dir">{id}</a> <a href="javascript:" onClick="dir_html_add('url_newsdata','{md5}');" title="影片md5(id)" class="dir">{md5}</a> <a href="javascript:" onClick="dir_html_add('url_newsdata','index<?php echo ($html_file_suffix); ?>');" title="默认首页" class="dir">index<?php echo ($html_file_suffix); ?></a></li>            
    </ul>
    <ul><li class="left">是否生成播放页：</li>
    	<li class="right"><select name="config[url_html_play]" id="url_html_play" class="w120" onChange="playtab('playhtml1',this.value);"><option value="0" >动态不生成</option><option value="1" <?php if(($url_html_play)  ==  "1"): ?>selected<?php endif; ?>>生成静态网页</option><option value="2" <?php if(($url_html_play)  ==  "2"): ?>selected<?php endif; ?>>每集生成单页</option></select> (每集生成单页的情况建议不使用{id}这个变量)</li>        
    </ul>
	<ul id="playhtml1" style="display:none"><li class="left">播放页路径：</li>
    	<li class="right"><input type="text" name="config[url_play]" id="url_play" maxlength="150" value="<?php echo ($url_play); ?>" class="w200 diri"> <select style="width:120px" onChange="dir_html('url_play',this.value);"><option>常用结构</option><option value="vod/{id}/play<?php echo ($html_file_suffix); ?>">1.vod/id/play<?php echo ($html_file_suffix); ?></option><option value="vod/{md5}/play<?php echo ($html_file_suffix); ?>">2.vod/md5值/play<?php echo ($html_file_suffix); ?></option><option value="vod/{pinyin}/play<?php echo ($html_file_suffix); ?>">3.vod/拼音/play<?php echo ($html_file_suffix); ?></option><option value="vod/{id}-play<?php echo ($html_file_suffix); ?>">4.vod/id-play<?php echo ($html_file_suffix); ?></option><option value="vod/{md5}-play<?php echo ($html_file_suffix); ?>">5.vod/md5-play<?php echo ($html_file_suffix); ?></option><option value="vod/{pinyin}-play<?php echo ($html_file_suffix); ?>">6.vod/拼音-play<?php echo ($html_file_suffix); ?></option><option value="{listid}/{id}-play<?php echo ($html_file_suffix); ?>">7.listid/id-play<?php echo ($html_file_suffix); ?></option><option value="{listdir}/{pinyin}/play<?php echo ($html_file_suffix); ?>">8.listdir/拼音/play<?php echo ($html_file_suffix); ?></option></select> 变量：<a href="javascript:" onClick="dir_html_add('url_play','{listid}');" title="分类ID值" class="dir">{listid}</a> <a href="javascript:" onClick="dir_html_add('url_play','{listdir}');" title="分类英文名" class="dir">{listdir}</a> <a href="javascript:" onClick="dir_html_add('url_play','{pinyin}');" title="影片拼音" class="dir">{pinyin}</a> <a href="javascript:" onClick="dir_html_add('url_play','{id}');" title="影片ID" class="dir">{id}</a> <a href="javascript:" onClick="dir_html_add('url_play','{md5}');" title="影片md5(id)" class="dir">{md5}</a> <a href="javascript:" onClick="dir_html_add('url_play','play<?php echo ($html_file_suffix); ?>');" title="影片md5(id)" class="dir">play<?php echo ($html_file_suffix); ?></a></li></ul> 
     <ul><li class="left">网站专题保存目录：</li>
    	<li class="right"><input type="text" name="config[url_special]" id="url_special" maxlength="15" value="<?php echo ($url_special); ?>" class="w120">&nbsp;</li>    
    </ul>       
    <ul><li class="left">网站地图保存目录：</li>
    	<li class="right"><input type="text" name="config[url_map]" id="url_map" maxlength="15" value="<?php echo ($url_map); ?>" class="w120">&nbsp;</li>    
    </ul>
    <ul><li class="left">自定义模板保存目录：</li>
    	<li class="right"><input type="text" name="config[url_mytpl]" id="url_mytpl" maxlength="15" value="<?php echo ($url_mytpl); ?>" class="w120">&nbsp;</li>    
    </ul>   
     <ul><li class="left">生成每页间隔(秒)：</li>
    	<li class="right"><input type="text" name="config[url_time]" id="url_time" maxlength="50" value="<?php echo ($url_time); ?>" class="w120">&nbsp;</li>        
    </ul>
    <ul><li class="left">每页生成数量：</li>
    	<li class="right"><input type="text" name="config[url_number]" id="url_number" maxlength="6" value="<?php echo ($url_number); ?>" class="w120">&nbsp;</li>
    </ul>
    </div>
</div>
<div class="add" id="tabs9" style="display:none;">
    <ul><li class="left">伪静态重写功能：</li>
    	<li class="right"><select name="config[url_rewrite]" class="w120" onChange="showtab('urlrewrite',this.value,1);showtab('urlrewrites',this.value,1);"><option value="0" >关闭</option><option value="1" <?php if(($url_rewrite)  ==  "1"): ?>selected<?php endif; ?>>开启</option></select> <span id="urlrewrite1" style="display:none">后缀名：<select name="config[url_html_suffix]"><option value=".html">.html</option><?php if(($url_html_suffix)  ==  ".htm"): ?><option value=".htm" selected>.htm</option><?php else: ?><option value=".htm">.htm</option><?php endif; ?><?php if(($url_html_suffix)  ==  ".shtml"): ?><option value=".shtml" selected>.shtml</option><?php else: ?><option value=".shtml">.shtml</option><?php endif; ?><?php if(($url_html_suffix)  ==  ".shtm"): ?><option value=".shtm" selected>.shtm</option><?php else: ?><option value=".shtm">.shtm</option><?php endif; ?></select></span>&nbsp;</li> 
    </ul>
    <div id="urlrewrites1" style="display:none">    
    <ul><li class="left">视频栏目页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_vodlist]" id="rewrite_vodlist" maxlength="150" value="<?php echo (($rewrite_vodlist)?($rewrite_vodlist):'/vod-show-id-$id-p-$page'); ?>" class="w250 diri"> 变量: $id，$page</li> 
    </ul>
    <ul><li class="left">视频内容页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_voddetail]" id="rewrite_voddetail" maxlength="150" value="<?php echo (($rewrite_voddetail)?($rewrite_voddetail):'/vod-read-id-$id'); ?>" class="w250 diri"> 变量: $id</li>            
    </ul>
    <ul><li class="left">视频播放页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_vodplay]" id="rewrite_vodplay" maxlength="150" value="<?php echo (($rewrite_vodplay)?($rewrite_vodplay):'/vod-play-id-$id-sid-$sid-pid-$pid'); ?>" class="w250 diri"> 变量: $id，$sid，$pid <label>说明: $sid=播放器组ID $pid=播放集数 必须为id>sid>pid的顺序 否则不能正常播放</label></li>            
    </ul>
    <ul><li class="left">视频搜索页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_vodsearch]" id="rewrite_vodsearch" maxlength="150" value="<?php echo (($rewrite_vodsearch)?($rewrite_vodsearch):'/vod-search-wd-$wd-p-$page'); ?>" class="w250 diri"> 变量: $wd，$actor，$director，$order，$page</li>            
    </ul>
    <ul><li class="left">视频筛选页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_vodtype]" id="rewrite_vodtype" maxlength="150" value="<?php echo (($rewrite_vodtype)?($rewrite_vodtype):'/vod-type-id-$id-wd-$wd-letter-$letter-year-$year-area-$area-order-$order-p-$page'); ?>" class="w250 diri"> 变量: $id，$wd，$letter，$year，$area，$language，$actor，$director，$order，$page</li> 
    </ul>    
    <ul><li class="left">视频TAG页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_vodtag]" id="rewrite_vodtag" maxlength="150" value="<?php echo (($rewrite_vodtag)?($rewrite_vodtag):'/tag-vod-wd-$wd-p-$page'); ?>" class="w250 diri"> 变量: $wd，$page</li>          
    </ul>
    <ul><li class="left">新闻栏目页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_newslist]" id="rewrite_newslist" maxlength="150" value="<?php echo (($rewrite_newslist)?($rewrite_newslist):'/news-show-id-$id-p-$page'); ?>" class="w250 diri"> 变量: $id，$page</li> 
    </ul>
    <ul><li class="left">新闻内容页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_newsdetail]" id="rewrite_newsdetail" maxlength="150" value="<?php echo (($rewrite_newsdetail)?($rewrite_newsdetail):'/news-read-id-$id'); ?>" class="w250 diri"> 变量: $id</li>            
    </ul>
     <ul><li class="left">新闻搜索页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_newssearch]" id="rewrite_newssearch" maxlength="150" value="<?php echo (($rewrite_newssearch)?($rewrite_newssearch):'/news-search-wd-$wd-p-$page'); ?>" class="w250 diri"> 变量: $wd，$page</li>            
    </ul>
    <ul><li class="left">新闻TAG页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_newstag]" id="rewrite_newstag" maxlength="150" value="<?php echo (($rewrite_newstag)?($rewrite_newstag):'/tag-shown-wd-$wd-p-$page'); ?>" class="w250 diri"> 变量: $wd，$page</li>          
    </ul>
    <ul><li class="left">专题栏目页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_speciallist]" id="rewrite_speciallist" maxlength="150" value="<?php echo (($rewrite_speciallist)?($rewrite_speciallist):'/special-show-p-$page'); ?>" class="w250 diri"> 变量: $page</li>
    </ul>
    <ul><li class="left">专题内容页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_specialdetail]" id="rewrite_specialdetail" maxlength="150" value="<?php echo (($rewrite_specialdetail)?($rewrite_specialdetail):'/special-read-id-$id'); ?>" class="w250 diri"> 变量: $id</li>            
    </ul>
    <ul><li class="left">留言本规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_guestbook]" id="rewrite_guestbook" maxlength="150" value="<?php echo (($rewrite_guestbook)?($rewrite_guestbook):'/gb-show-p-$page'); ?>" class="w250 diri"> 变量:$id，$page</li>            
    </ul>
     <ul><li class="left">地图页规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_map]" id="rewrite_map" maxlength="150" value="<?php echo (($rewrite_map)?($rewrite_map):'/map-show-id-$id-limit-$limit'); ?>" class="w250 diri"> 变量:$id，$limit</li>            
    </ul>   
    <ul><li class="left">自定义模板规则：</li>
    	<li class="right"><input type="text" name="config[rewrite_mytpl]" id="rewrite_mytpl" maxlength="150" value="<?php echo (($rewrite_mytpl)?($rewrite_mytpl):'/my-show-id-$id'); ?>" class="w250 diri"> 变量:$id</li>            
    </ul>
    <ul><li class="right">&nbsp;注意，修改静态格式后您需要修改服务器的 Rewrite 规则设置。如果有使用附加变量，模板调用一定要支持该参数才有效果 <a href="http://union.feifeicms.com/feifei/jump.php?id=5" target="_blank" style="color:red">获取更多帮助</a></li></ul>
    </div>
</div>
<!-- -->
<div class="add" id="tabs4" style="display:none;">    
     <ul><li class="left">数据缓存方式：</li>
    	<li class="right"><select name="config[data_cache_type]" class="w120"><option value="file">File 文件</option><option value="memcache" <?php if(($data_cache_type)  ==  "memcache"): ?>selected<?php endif; ?>>Memcache内存</option></select> <label>将从数据库查询出的数据缓存起来,可以降低MYSQL所占系统资源。如选择memcache，需要服务器系统以及PHP扩展模块支持</label></li>
    </ul>
    <div id="datacache1">
     <ul><li class="left">视频内容模块：</li>
    	<li class="right"><input type="text" name="config[data_cache_vod]" id="data_cache_vod" maxlength="8" value="<?php echo ($data_cache_vod); ?>" class="w70"><label>推荐开启，时间设置为86400秒，一天，表数据更新时缓存数据会同步，设为0该模块将不启用，单位秒</label></li>
    </ul> 
     <ul><li class="left">新闻内容模块：</li>
    	<li class="right"><input type="text" name="config[data_cache_news]" id="data_cache_news" maxlength="8" value="<?php echo ($data_cache_news); ?>" class="w70"><label>推荐开启，时间设置为86400秒，一天，表数据更新时缓存数据会同步，设为0该模块将不启用，单位秒</label></li>
    </ul>
     <ul><li class="left">专题内容模块：</li>
    	<li class="right"><input type="text" name="config[data_cache_special]" id="data_cache_special" maxlength="8" value="<?php echo ($data_cache_special); ?>" class="w70"><label>推荐开启，时间设置为86400秒，一天，表数据更新时缓存数据会同步，设为0该模块将不启用，单位秒</label></li>
    </ul>
     <ul><li class="left">视频循环调用标签：</li>
    	<li class="right"><input type="text" name="config[data_cache_vodforeach]" id="data_cache_vodforeach" maxlength="8" value="<?php echo ($data_cache_vodforeach); ?>" class="w70"><label>推荐开启，时间设置为600秒，开启后需要手动执行更新操作才会清除，设为0该模块将不启用</label></li>
    </ul>
     <ul><li class="left">新闻循环调用标签：</li>
    	<li class="right"><input type="text" name="config[data_cache_newsforeach]" id="data_cache_newsforeach" maxlength="8" value="<?php echo ($data_cache_newsforeach); ?>" class="w70"><label>推荐开启，时间设置为600秒，开启后需要手动执行更新操作才会清除，设为0该模块将不启用</label></li>
    </ul>
     <ul><li class="left">专题循环调用标签：</li>
    	<li class="right"><input type="text" name="config[data_cache_specialforeach]" id="data_cache_specialforeach" maxlength="8" value="<?php echo ($data_cache_specialforeach); ?>" class="w70"><label>推荐开启，时间设置为600秒，开启后需要手动执行更新操作才会清除，设为0该模块将不启用</label></li>
    </ul>             
    </div>
    <ul><li class="left">模板编译缓存功能：</li>
    	<li class="right"><select name="config[tmpl_cache_on]" class="w70"><option value="1">开启</option><option value="0" <?php if(($tmpl_cache_on)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul>
    <ul><li class="left">网站页面缓存功能：</li>
    	<li class="right"><select name="config[html_cache_on]" class="w70" onChange="showtab('htmlcache',this.value,1);"><option value="1">开启</option><option value="0" <?php if(($html_cache_on)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select> <label>网站动态模式运行下根据URL自动生成对应的PHP缓存文件</label></li>
    </ul>
    <div id="htmlcache1" style="display:none">
     <ul><li class="left">首页缓存有效期：</li>
    	<li class="right"><input type="text" name="config[html_cache_index]" id="html_cache_index" maxlength="2" value="<?php echo ($html_cache_index); ?>" class="w70"><label>设为0该模块将不启用缓存,可以为小数,单位小时</label></li>
    </ul> 
    <ul><li class="left">栏目页缓存有效期：</li>
    	<li class="right"><input type="text" name="config[html_cache_list]" id="html_cache_list" maxlength="2" value="<?php echo ($html_cache_list); ?>" class="w70"><label>设为0该模块将不启用缓存,可以为小数,单位小时</label></li>
    </ul>
    <ul><li class="left">内容页缓存有效期：</li>
    	<li class="right"><input type="text" name="config[html_cache_content]" id="html_cache_content" maxlength="2" value="<?php echo ($html_cache_content); ?>" class="w70"><label>设为0该模块将不启用缓存,可以为小数,单位小时</label></li>
    </ul>
    <ul><li class="left">播放页缓存有效期：</li>
    	<li class="right"><input type="text" name="config[html_cache_play]" id="html_cache_play" maxlength="2" value="<?php echo ($html_cache_play); ?>" class="w70"><label>设为0该模块将不启用缓存,可以为小数,单位小时</label></li>
    </ul>
    <ul><li class="left">其它缓存有效期：</li>
    	<li class="right"><input type="text" name="config[html_cache_ajax]" id="html_cache_ajax" maxlength="2" value="<?php echo ($html_cache_ajax); ?>" class="w70"><label>设为0该模块将不启用缓存,可以为小数,单位小时</label></li>
    </ul> 
    </div>
</div>
<!-- -->
<div class="add" id="tabs5" style="display:none;"> 
    <ul><li class="left">图片保存路径：</li>
    	<li class="right"><input type="text" name="config[upload_path]" id="upload_path" value="<?php echo ($upload_path); ?>" maxlength="20" class="w120">&nbsp;</li>
    </ul>
     <ul><li class="left">图片路径保存风格：</li>
    	<li class="right"><input type="text" name="config[upload_style]" id="upload_class" value="<?php echo ($upload_style); ?>" maxlength="20" class="w120">&nbsp;</li>
    </ul>
     <ul><li class="left">附件上传类型：</li>
    	<li class="right"><input type="text" name="config[upload_class]" id="upload_style" value="<?php echo ($upload_class); ?>" maxlength="20" class="w120"><label>BMP格式的图片不支持水印与缩略图</label></li>
    </ul>        
    <ul><li class="left">批量保存远程图片数量：</li>
    	<li class="right"><input type="text" name="config[upload_http_down]" maxlength="4" value="<?php echo ($upload_http_down); ?>" class="w70">&nbsp;</li>
    </ul>     
     <ul><li class="left">下载远程图片功能：</li>
    	<li class="right"><select name="config[upload_http]" class="w70"><option value="1">开启</option><option value="0" <?php if(($upload_http)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select> <label>手动添加数据与一键采集时自动保存图片</label></li>
    </ul>                       
    <ul><li class="left">生成缩略图功能：</li>
    	<li class="right"><select name="config[upload_thumb]" class="w70" onChange="showtab('thumb',this.value,1);"><option value="1">开启</option><option value="0" <?php if(($upload_thumb)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul>
    <ul id="thumb1" style="display:none"><li class="left">缩图图宽度与高度：</li>
    	<li class="right"><input type="text" name="config[upload_thumb_w]" value="<?php echo ($upload_thumb_w); ?>" class="w70"> X <input  type="text" name="config[upload_thumb_h]" value="<?php echo ($upload_thumb_h); ?>" class="w70"><label>(按原图比率缩小/小于该指定大小的图片将不生成缩略图)</label></li>
    </ul>    
    <ul><li class="left">图片加水印功能：</li>
    	<li class="right"><select name="config[upload_water]" class="w70" onChange="showtab('water',this.value,1);"><option value="1">开启</option><option value=0 <?php if(($upload_water)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul>
    <div id="water1" style="display:none">
    <ul><li class="left">水印透明度：</li>
    	<li class="right"><input type="text" name="config[upload_water_pct]" maxlength="3" value="<?php echo ($upload_water_pct); ?>" class="w70">&nbsp;</li>
    </ul>
    <ul><li class="left">水印位置：</li>
    	<li class="right"><input type="text" name="config[upload_water_pos]" maxlength="1" value="<?php echo ($upload_water_pos); ?>" class="w70"><label>(0=随机,从左&gt;右,上&gt;下 可以设置1-9 9个位置)</label></li>
    </ul>
    <ul><li class="left">水印图片路径：</li>
    	<li class="right"><input type="text" name="config[upload_water_img]" maxlength="30" value="<?php echo ($upload_water_img); ?>">&nbsp;</li>
    </ul>            	
    </div>         
    <ul><li class="left">FTP远程附件功能：</li>
    	<li class="right"><select name="config[upload_ftp]" class="w70" onChange="showtab('ftptab',this.value,1);"><option value="1">开启</option><option value="0" <?php if(($upload_ftp)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul> 
    <div id="ftptab1" style="display:none;"> 
    <ul><li class="left">是否删除本地图片：</li>
    	<li class="right"><select name="config[upload_ftp_del]" class="w70"><option value=0>否</option><option value=1 <?php if(($upload_ftp_del)  ==  "1"): ?>selected<?php endif; ?>>是</option></select><label>上传到远程服务器后是否删除本地的</label></li>
    </ul>         
     <ul><li class="left">FTP服务器：</li>
    	<li class="right"><input type="text" name="config[upload_ftp_host]" id="upload_ftp_host" maxlength="30" value="<?php echo ($upload_ftp_host); ?>" class="w120"><label>填写FTP服务器的IP或域名</label></li>
    </ul>    
    <ul><li class="left">FTP 用户：</li>
    	<li class="right"><input type="text" name="config[upload_ftp_user]" id="upload_ftp_user" value="<?php echo ($upload_ftp_user); ?>" maxlength="30" class="w120">&nbsp;</li>
    </ul>
    <ul><li class="left">FTP 密码：</li>
    	<li class="right"><input type="text" name="config[upload_ftp_pass]" id="upload_ftp_pass" value="<?php echo ($upload_ftp_pass); ?>" maxlength="30" class="w120">&nbsp;</li>
    </ul> 
    <ul><li class="left">FTP 端口：</li>
    	<li class="right"><input type="text" name="config[upload_ftp_port]" id="upload_ftp_port" value="<?php echo ($upload_ftp_port); ?>" maxlength="8" class="w120">&nbsp;</li>
    </ul>    
     <ul><li class="left">远程附件保存文件夹：</li>
    	<li class="right"><input type="text" name="config[upload_ftp_dir]" id="upload_ftp_dir" maxlength="50" value="<?php echo ($upload_ftp_dir); ?>" class="w120"><label>(相对于FTP服务器根目录, 如/wwwroot/upload/)</label></li>
    </ul>                      
    </div>
    <ul><li class="left">远程附件访问地址：</li>
    	<li class="right"><input type="text" name="config[upload_http_prefix]" id="upload_http_prefix" maxlength="100" value="<?php echo ($upload_http_prefix); ?>" class="w300"><label>(必须/结尾,留空则不使用,如http://www.feifeicms.com/upload/)</label></li>
    </ul>    
</div>    
<!-- -->
<div class="add" id="tabs6" style="display:none;"> 
    <ul><li class="left">播放器宽度与高度：</li>
    	<li class="right"><input  type="text" name="config[play_width]" value="<?php echo ($play_width); ?>" class="w70"> X <input  type="text" name="config[play_height]" value="<?php echo ($play_height); ?>" class="w70">&nbsp;</li>
    </ul>
     <ul><li class="left">默认是否开启列表：</li>
    	<li class="right"><select name="config[play_show]" class="w70"><option value=1>开启</option><option value=0 <?php if(($play_show)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select>&nbsp;</li>
    </ul>                      
    <ul><li class="left">FLV视频片头广告时长：</li>
    	<li class="right"><input  type="text" name="config[play_second]" value="<?php echo ($play_second); ?>" class="w70 ct" maxlength="2" title="设为0则不启用">&nbsp; </li>
    </ul>
    <ul><li class="left">默认视频地区列表：</li>
    	<li class="right"><input type="text" name="config[play_area]" id="play_area" value="<?php echo ($play_area); ?>" maxlength="250" class="w400">&nbsp;</li>    
    </ul> 
    <ul><li class="left">默认视频年代列表：</li>
    	<li class="right"><input type="text" name="config[play_year]" id="play_year" value="<?php echo ($play_year); ?>" maxlength="250" class="w400">&nbsp;</li>    
    </ul>
    <ul><li class="left">默认视频对白列表：</li>
    	<li class="right"><input type="text" name="config[play_language]" id="play_language" value="<?php echo ($play_language); ?>" maxlength="250" class="w400">&nbsp;</li>    
    </ul>         
     <ul><li class="left">节目缓冲广告地址：</li>
    	<li class="right"><input type="text" name="config[play_playad]" id="play_playad" value="<?php echo ($play_playad); ?>" maxlength="150" class="w400">&nbsp;</li>
    </ul> 
     <ul><li class="left">快播Qvod下载地址：</li>
    	<li class="right"><input type="text" name="config[play_qvod]" id="play_qvod" value="<?php echo ($play_qvod); ?>" maxlength="150" class="w400">&nbsp;</li>
    </ul>    
     <ul><li class="left">百度影音下载地址：</li>
    	<li class="right"><input type="text" name="config[play_bdhd]" id="play_bdhd" value="<?php echo ($play_bdhd); ?>" maxlength="150" class="w400">&nbsp;</li>
    </ul>
    <ul><li class="left">下载服务器组前缀：<br /><font color="red">前缀名称$$$对应地址</font><br />(一行一个)</li>
    	<li class="right" style="height:150px"><textarea name="config[play_server]" id="play_server" style="height:133px"><?php if(is_array($play_server)): $i = 0; $__LIST__ = $play_server;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><?php echo ($key); ?>$$$<?php echo ($ppvod); ?><?php echo(chr(13)); ?><?php endforeach; endif; else: echo "" ;endif; ?></textarea></li>
    </ul>
</div>
<!-- -->
<div class="add" id="tabs7" style="display:none;">
    <ul><li class="left">采集伪原创：</li>
    	<li class="right"><select name="config[play_collect]" class="w70"><option value="1">开启</option><option value="0" <?php if(($play_collect)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select><label>在采集数据时自动做同义词seo伪造</label></li>
    </ul>
    <ul><li class="left">自动生成TAG：</li>
    	<li class="right"><select name="config[rand_tag]" class="w70"><option value="1">开启</option><option value="0" <?php if(($rand_tag)  ==  "0"): ?>selected<?php endif; ?>>关闭</option></select><label>添加数据时是否自动生成TAG</label></li>
    </ul>    
    <ul><li class="left">采集相似度设置：</li>
    	<li class="right"><input type="text" name="config[play_collect_name]" id="play_collect_name" value="<?php echo ($play_collect_name); ?>" maxlength="5" class="w70" title="按名称结尾，减去多少个字符">&nbsp;<label>使用此功能可以减少重名，但会加重采集时的服务器负担(0不检查)</label></li>
    </ul>    
    <ul><li class="left">采集跳转时间间隔：</li>
    	<li class="right"><input type="text" name="config[play_collect_time]" id="play_collect_time" value="<?php echo ($play_collect_time); ?>" maxlength="5" class="w70">&nbsp;<label>每一页采集完毕后暂停几秒</label></li>
    </ul>
    <ul><li class="left">总人气随机最大值：</li>
    	<li class="right"><input type="text" name="config[rand_hits]" id="rand_hits" value="<?php echo ($rand_hits); ?>" maxlength="5" class="w70">&nbsp;</li>
    </ul> 
    <ul><li class="left">顶踩随机最大值：</li>
    	<li class="right"><input type="text" name="config[rand_updown]" id="rand_updown" value="<?php echo ($rand_updown); ?>" maxlength="5" class="w70">&nbsp;</li>
    </ul>
    <ul><li class="left">评分随机最大值：</li>
    	<li class="right"><input type="text" name="config[rand_gold]" id="rand_gold" value="<?php echo ($rand_gold); ?>" maxlength="1" class="w70">&nbsp;</li>
    </ul>
    <ul><li class="left">评分人数随机最大值：</li>
    	<li class="right"><input type="text" name="config[rand_golder]" id="rand_golder" value="<?php echo ($rand_golder); ?>" maxlength="5" class="w70">&nbsp;</li>
    </ul>
</div>
<!-- -->
<div class="add" id="tabs8" style="display:none;">
<!--    <ul><li class="left">是否开放会员注册：</li>
    	<li class="right"><input type="radio" class="radio" name="config[user_register]" value="1" <?php if(($user_register)  ==  "1"): ?>checked="checked"<?php endif; ?>/>开启 <input type="radio" class="radio" name="config[user_register]" value="0" <?php if(($user_register)  ==  "0"): ?>checked="checked"<?php endif; ?>/>关闭</li>
    </ul>
    <ul><li class="left">评论与留言需要登录：</li>
    	<li class="right"><input type="radio" class="radio" name="config[user_post]" value="1" <?php if(($user_post)  ==  "1"): ?>checked="checked"<?php endif; ?>/>开启 <input type="radio" class="radio" name="config[user_post]" value="0" <?php if(($user_post)  ==  "0"): ?>checked="checked"<?php endif; ?>/>关闭</li>
    </ul>  -->
    <ul><li class="left">是否开启留言验证码：</li>
    	<li class="right"><input type="radio" class="radio" name="config[user_vcode]" value="1" <?php if(($user_vcode)  ==  "1"): ?>checked="checked"<?php endif; ?>/>开启 <input type="radio" class="radio" name="config[user_vcode]" value="0" <?php if(($user_vcode)  ==  "0"): ?>checked="checked"<?php endif; ?>/>关闭</li>
    </ul>     
    <ul><li class="left">评论与留言需要审核：</li>
    	<li class="right"><input type="radio" class="radio" name="config[user_check]" value="1" <?php if(($user_check)  ==  "1"): ?>checked="checked"<?php endif; ?>/>开启 <input type="radio" class="radio" name="config[user_check]" value="0" <?php if(($user_check)  ==  "0"): ?>checked="checked"<?php endif; ?>/>关闭</li>
    </ul>
     <ul><li class="left">多次发表信息间隔：</li>
    	<li class="right"><input type="text" name="config[user_second]" maxlength="4" value="<?php echo ($user_second); ?>" class="w70"><label>(两次评论/留言/顶踩/评分间隔时间多少秒)</label>&nbsp;</li>
    </ul>
    <ul><li class="left">留言本每页显示数量：</li>
    	<li class="right"><input type="text" name="config[user_gbnum]" value="<?php echo ($user_gbnum); ?>" maxlength="4" class="w70">&nbsp;</li>
    </ul> 
    <ul><li class="left">评论信息每页显示数量：</li>
    	<li class="right"><input type="text" name="config[user_cmnum]" value="<?php echo ($user_cmnum); ?>" maxlength="4" class="w70">&nbsp;</li>
    </ul>        
    <ul><li class="left">脏话文字过滤：<br /><font color="red">用|分开，但不要在结尾加|</font></li>
    	<li class="right" style="height:75px"><textarea name="config[user_replace]" id="user_replace" style="height:60px"><?php echo ($user_replace); ?></textarea></li>
    </ul>
</div>
<div class="add">
	<ul class="footer"><input type="submit" name="submit" value="提交"> <input type="reset" name="reset" value="重置"></ul>
</div>
</form>
<br /><center>Version：<font color="#FF0000"><?php echo L("ppvod_version");?></font></center>
</body>
</html>