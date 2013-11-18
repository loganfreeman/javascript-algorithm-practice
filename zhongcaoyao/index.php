<?php
set_time_limit(0);
$prescription = isset($_GET['id'])?trim($_GET['q']):"";
$id = isset($_GET['id'])?intval($_GET['id']):0;

$r_num = 0; //结果个数
$lan = 3;
$pf = "";
$pf_l = "";

if($prescription!=""){
	$dreamdb=file("data/zcyt.dat");//读取中草药文件
	$count=count($dreamdb);//计算行数

	for($i=0; $i<$count; $i++) {
		$keyword=explode(" ",$prescription);//拆分关键字
		$dreamcount=count($keyword);//关键字个数
		$detail=explode("\t",$dreamdb[$i]);
		for ($ai=0; $ai<$dreamcount; $ai++) {
			@eval("\$found = eregi(\"$keyword[$ai]\",\"$detail[0]\");");
			if(($found)){
				if(fmod($r_num,$lan)==0) $pf_l .= "<tr>";
				$pf_l .= '<td width="'.(100/$lan).'%"><img src="i/dot.gif" /> <a href="?id='.($i+1).'">'.$detail[0].'</a></td>';
				if(fmod($r_num,$lan)+1==$lan) $pf_l .= "</tr>";
				$r_num++;
				break;
			}
		}
	}
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b><a href="./">中草药</a>：找到 <a href="./?q='.urlencode($prescription).'"><font color="#c60a00">'.$prescription.'</font></a> 的相关中草药'.$r_num.'个</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}elseif($id>0){
	$dreamdb=file("data/zcy.dat");//读取中草药文件
	$count=count($dreamdb);//计算行数

	$detail=explode("\t",$dreamdb[$id-1]);
	$pf = '<table width="700" cellpadding=2 cellspacing=0 style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle"><b><a href="./">中草药</a> / '.$detail[0].'</b></td><td style="background:#EDF7FF;padding:0 5px;color:#014198;" align="right">';
	if($id>1 && $id<=$count) $pf .= '<a href="?id='.($id-1).'">上一个</a> ';
	$pf .= '<a href="./">查看全部</a>';
	if($id>=1 && $id<$count) $pf .= ' <a href="?id='.($id+1).'">下一个</a>';
	$pf .= '</td></tr><tr><td align="center" colspan="2"><h3>'.$detail[0].'</h3></td></tr><tr><td style="padding:5px;line-height:21px;" colspan="2"><p>'.$detail[1].'</p></td></tr></table>';
}else{
	$dreamdb=file("data/zcyt.dat");//读取中草药文件
	$count=count($dreamdb);//计算行数

	$pfl = rand(0,intval($count/60));

	for($i=$pfl*60; $i<$pfl*60+60; $i++) {
		if($i>=$count-1) break;
		$detail=explode("\t",$dreamdb[$i]);
		if(fmod($r_num,$lan)==0) $pf_l .= "<tr>";
		$pf_l .= '<td width="'.(100/$lan).'%"><img src="i/dot.gif" /> <a href="?id='.($i+1).'">'.$detail[0].'</a></td>';
		if(fmod($r_num,$lan)+1==$lan) $pf_l .= "</tr>";
		$r_num++;
	}
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>推荐中草药'.$r_num.'个</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<?php
if($prescription){
	echo "<title>".$prescription." - 中草药 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$prescription.',中草药,中草药大全" />';
}elseif($id>0 && $id<=$count){
	echo "<title>".$detail[0]." - 中草药 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$detail[0].',中草药,中草药大全" />';
	echo '<meta name="description" content="'.trim(str_replace("<br>","",$detail[1]),"　 ").'中草药www.5glive.com。" />';
}else{
	echo "<title>中草药 5Glive.com</title>";
	echo '<meta name="keywords" content="中草药,中草药大全" />';
}
?>
<link href="i/common.css" rel="stylesheet" type="text/css" />
<!--
程序: taddyshen
邮箱: taddyshen#126.com
演示: http://chaxun.5glive.com/
主页: http://www.5glive.com/

程序免费提供，方便就给个友情链接。
-->
</head>

<body>
<div align="center">
<table cellspacing="0" cellpadding="0" width="778" border="0"><tr><td align="left" style="padding:10px 0"><a href="http://www.5glive.com/"><img src="i/logo.gif" alt="中草药" /></a></td></tr></table>
<table cellspacing="4" cellpadding="0" style="background-color:#f7f7f7;border-bottom:1px solid #dfdfdf;" width="778">
<tr><td align="left"><a href="http://www.5glive.com/">首页</a> &gt; <a href="../">实用工具集</a> &gt; <a href="./">中草药</a><?php if($prescription) echo ' &gt; 关于 <strong>'. $prescription.'</strong> 的中草药'; ?></td><td align="right"><a href="javascript:;" onClick="window.external.AddFavorite(document.location.href,document.title);">收藏本页</a></td></tr></table>
<br>
<style type="text/css">
h3{font-size:24px;padding:15px 10px 5px 10px;color:#014198;}
p{padding: 10px;}
a.lan,a.lan:visited{color:#999;}
</style>
<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;" id="top"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>中草药搜索</b></td></tr><tr><td align="center" valign="middle" height="60"><form action="./" method="get" name="f1">搜索中草药：<input name="q" id="q" type="text" size="18" delay="0" value="" style="width:200px;height:22px;font-size:16px;font-family: Geneva, Arial, Helvetica, sans-serif;" /> <input type="submit" value=" 搜索 " /></form></td></tr></table><br />
<?php
if($prescription!=""){
	//echo $pf_l.$pf;
	echo $pf_l;
}elseif($id>0 && $id<=$count){
	echo $pf;
}else{
	echo $pf_l;
}
?>
</div>
<div id="footer">&copy; 2007-2008 <a href="http://www.5glive.com/">5G生活</a> 京ICP备06067378号</div>
</body>
</html>