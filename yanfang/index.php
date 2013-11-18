<?php
set_time_limit(0);
$prescription =  isset($_GET['q']) ? trim($_GET['q']) : "";
$id = isset($_GET['q']) ? intval($_GET['id']) : 0;

$r_num = 0; //结果个数
$lan = 3;
$pf = "";
$pf_l = "";

if($prescription!=""){
	$dreamdb=file("data/yf.dat");//读取验方文件
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
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b><a href="./">民间验方</a>：找到 <a href="./?q='.urlencode($prescription).'"><font color="#c60a00">'.$prescription.'</font></a> 的相关验方'.$r_num.'个</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}elseif($id>0){
	$dreamdb=file("data/yf.dat");//读取验方文件
	$count=count($dreamdb);//计算行数

	$detail=explode("\t",$dreamdb[$id-1]);
	$pf = '<table width="700" cellpadding=2 cellspacing=0 style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle"><b><a href="./">民间验方</a> / '.$detail[0].'</b></td><td style="background:#EDF7FF;padding:0 5px;color:#014198;" align="right">';
	if($id>1 && $id<=$count) $pf .= '<a href="?id='.($id-1).'">上一个</a> ';
	$pf .= '<a href="./">查看全部</a>';
	if($id>=1 && $id<$count) $pf .= ' <a href="?id='.($id+1).'">下一个</a>';
	$pf .= '</td></tr><tr><td align="center" colspan="2"><h3>'.$detail[0].'</h3></td></tr><tr><td style="padding:5px;line-height:21px;" colspan="2"><p>'.$detail[1].'</p></td></tr></table>';
}else{
	$dreamdb=file("data/yf.dat");//读取验方文件
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
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>推荐民间验方'.$r_num.'个</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<?
if($prescription){
	echo "<title>".$prescription." - 民间验方大全 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$prescription.',中草药,验方,中药,民间验方大全" />';
}elseif($id>0 && $id<=$count){
	echo "<title>".$detail[0]." - 民间验方大全 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$detail[0].',中草药,验方,中药,民间验方大全" />';
	echo '<meta name="description" content="'.trim(str_replace("<br>","",$detail[1]),"　 ").'民间验方大全www.5glive.com。" />';
}else{
	echo "<title>民间验方大全 5Glive.com</title>";
	echo '<meta name="keywords" content="民间验方,验方大全" />';
	echo '<meta name="description" content="民间验方大全www.5glive.com，中草药，是中医所使用的独特药物，也是中医区别于其他医学的重要标志。中国人民对中草药的探索经历了几千年的历史。相传，神农尝百草，首创医药，神农被尊为“药皇”。中药主要由植物药（根、茎、叶、果）、动物药（内脏、皮、骨、器官等）和矿物药组成。因植物药占中药的大多数，所以中药也称中草药。" />';
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
<table cellspacing="0" cellpadding="0" width="778" border="0"><tr><td align="left" style="padding:10px 0"><a href="http://www.5glive.com/"><img src="i/logo.gif" alt="民间验方" /></a></td></tr></table>
<table cellspacing="4" cellpadding="0" style="background-color:#f7f7f7;border-bottom:1px solid #dfdfdf;" width="778">
<tr><td align="left"><a href="http://www.5glive.com/">首页</a> &gt; <a href="http://chaxun.5glive.com/">实用工具集</a> &gt; <a href="./">民间验方</a><? if($prescription) echo ' &gt; 关于 <strong>'. $prescription.'</strong> 的验方'; ?></td><td align="right"><a href="javascript:;" onClick="window.external.AddFavorite(document.location.href,document.title);">收藏本页</a></td></tr></table>
<br>
<style type="text/css">
h3{font-size:24px;padding:15px 10px 5px 10px;color:#014198;}
p{padding: 10px;}
a.lan,a.lan:visited{color:#999;}
</style>
<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;" id="top"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>民间验方搜索</b></td></tr><tr><td align="center" valign="middle" height="60"><form action="./" method="get" name="f1">搜索验方：<input name="q" id="q" type="text" size="18" delay="0" value="" style="width:200px;height:22px;font-size:16px;font-family: Geneva, Arial, Helvetica, sans-serif;" /> <input type="submit" value=" 搜索 " /></form></td></tr></table><br />
<?
if($prescription!=""){
	//echo $pf_l.$pf;
	echo $pf_l;
}elseif($id>0 && $id<=$count){
	echo $pf;
}else{
	echo '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>民间验方说明</b></td></tr><tr><td><p style="line-height:150%">　　所谓中草药，是中医所使用的独特药物，也是中医区别于其他医学的重要标志。中国人民对中草药的探索经历了几千年的历史。相传，神农尝百草，首创医药，神农被尊为“药皇”。<br>　　中药主要由植物药（根、茎、叶、果）、动物药（内脏、皮、骨、器官等）和矿物药组成。因植物药占中药的大多数，所以中药也称中草药。<br>　　目前，各地使用的中药已达5000种左右，把各种药材相配伍而形成的方剂，更是数不胜数。经过几千年的研究，形成了一门独立的科学——本草学。</p></td></tr></table><br>';
	echo $pf_l;
}
?>
</div>
<div id="footer">&copy; 2007-2008 <a href="http://www.5glive.com/">5G生活</a> 京ICP备06067378号</div>
</body>
</html>