<?php
set_time_limit(0);
$prescription =  isset($_GET['q']) ? trim($_GET['q']) : "";
$id = isset($_GET['q']) ? intval($_GET['id']) : 0;

$r_num = 0; //�������
$lan = 3;
$pf = "";
$pf_l = "";

if($prescription!=""){
	$dreamdb=file("data/yf.dat");//��ȡ�鷽�ļ�
	$count=count($dreamdb);//��������

	for($i=0; $i<$count; $i++) {
		$keyword=explode(" ",$prescription);//��ֹؼ���
		$dreamcount=count($keyword);//�ؼ��ָ���
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
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b><a href="./">����鷽</a>���ҵ� <a href="./?q='.urlencode($prescription).'"><font color="#c60a00">'.$prescription.'</font></a> ������鷽'.$r_num.'��</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}elseif($id>0){
	$dreamdb=file("data/yf.dat");//��ȡ�鷽�ļ�
	$count=count($dreamdb);//��������

	$detail=explode("\t",$dreamdb[$id-1]);
	$pf = '<table width="700" cellpadding=2 cellspacing=0 style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle"><b><a href="./">����鷽</a> / '.$detail[0].'</b></td><td style="background:#EDF7FF;padding:0 5px;color:#014198;" align="right">';
	if($id>1 && $id<=$count) $pf .= '<a href="?id='.($id-1).'">��һ��</a> ';
	$pf .= '<a href="./">�鿴ȫ��</a>';
	if($id>=1 && $id<$count) $pf .= ' <a href="?id='.($id+1).'">��һ��</a>';
	$pf .= '</td></tr><tr><td align="center" colspan="2"><h3>'.$detail[0].'</h3></td></tr><tr><td style="padding:5px;line-height:21px;" colspan="2"><p>'.$detail[1].'</p></td></tr></table>';
}else{
	$dreamdb=file("data/yf.dat");//��ȡ�鷽�ļ�
	$count=count($dreamdb);//��������

	$pfl = rand(0,intval($count/60));

	for($i=$pfl*60; $i<$pfl*60+60; $i++) {
		if($i>=$count-1) break;
		$detail=explode("\t",$dreamdb[$i]);
		if(fmod($r_num,$lan)==0) $pf_l .= "<tr>";
		$pf_l .= '<td width="'.(100/$lan).'%"><img src="i/dot.gif" /> <a href="?id='.($i+1).'">'.$detail[0].'</a></td>';
		if(fmod($r_num,$lan)+1==$lan) $pf_l .= "</tr>";
		$r_num++;
	}
	$pf_l = '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>�Ƽ�����鷽'.$r_num.'��</b></td></tr><tr><td><table cellpadding="5" cellspacing="10" width="100%">'.$pf_l.'</table></td></tr></table>';
}
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<?
if($prescription){
	echo "<title>".$prescription." - ����鷽��ȫ 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$prescription.',�в�ҩ,�鷽,��ҩ,����鷽��ȫ" />';
}elseif($id>0 && $id<=$count){
	echo "<title>".$detail[0]." - ����鷽��ȫ 5Glive.com</title>";
	echo '<meta name="keywords" content="'.$detail[0].',�в�ҩ,�鷽,��ҩ,����鷽��ȫ" />';
	echo '<meta name="description" content="'.trim(str_replace("<br>","",$detail[1]),"�� ").'����鷽��ȫwww.5glive.com��" />';
}else{
	echo "<title>����鷽��ȫ 5Glive.com</title>";
	echo '<meta name="keywords" content="����鷽,�鷽��ȫ" />';
	echo '<meta name="description" content="����鷽��ȫwww.5glive.com���в�ҩ������ҽ��ʹ�õĶ���ҩ�Ҳ����ҽ����������ҽѧ����Ҫ��־���й�������в�ҩ��̽�������˼�ǧ�����ʷ���ഫ����ũ���ٲݣ��״�ҽҩ����ũ����Ϊ��ҩ�ʡ�����ҩ��Ҫ��ֲ��ҩ����������Ҷ������������ҩ�����ࡢƤ���ǡ����ٵȣ��Ϳ���ҩ��ɡ���ֲ��ҩռ��ҩ�Ĵ������������ҩҲ���в�ҩ��" />';
}
?>
<link href="i/common.css" rel="stylesheet" type="text/css" />
<!--
����: taddyshen
����: taddyshen#126.com
��ʾ: http://chaxun.5glive.com/
��ҳ: http://www.5glive.com/

��������ṩ������͸����������ӡ�
-->
</head>

<body>
<div align="center">
<table cellspacing="0" cellpadding="0" width="778" border="0"><tr><td align="left" style="padding:10px 0"><a href="http://www.5glive.com/"><img src="i/logo.gif" alt="����鷽" /></a></td></tr></table>
<table cellspacing="4" cellpadding="0" style="background-color:#f7f7f7;border-bottom:1px solid #dfdfdf;" width="778">
<tr><td align="left"><a href="http://www.5glive.com/">��ҳ</a> &gt; <a href="http://chaxun.5glive.com/">ʵ�ù��߼�</a> &gt; <a href="./">����鷽</a><? if($prescription) echo ' &gt; ���� <strong>'. $prescription.'</strong> ���鷽'; ?></td><td align="right"><a href="javascript:;" onClick="window.external.AddFavorite(document.location.href,document.title);">�ղر�ҳ</a></td></tr></table>
<br>
<style type="text/css">
h3{font-size:24px;padding:15px 10px 5px 10px;color:#014198;}
p{padding: 10px;}
a.lan,a.lan:visited{color:#999;}
</style>
<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;" id="top"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>����鷽����</b></td></tr><tr><td align="center" valign="middle" height="60"><form action="./" method="get" name="f1">�����鷽��<input name="q" id="q" type="text" size="18" delay="0" value="" style="width:200px;height:22px;font-size:16px;font-family: Geneva, Arial, Helvetica, sans-serif;" /> <input type="submit" value=" ���� " /></form></td></tr></table><br />
<?
if($prescription!=""){
	//echo $pf_l.$pf;
	echo $pf_l;
}elseif($id>0 && $id<=$count){
	echo $pf;
}else{
	echo '<table width="700" cellpadding="2" cellspacing="0" style="border:1px solid #B2D0EA;"><tr><td style="background:#EDF7FF;padding:0 5px;color:#014198;" height="26" valign="middle" colspan="5"><b>����鷽˵��</b></td></tr><tr><td><p style="line-height:150%">������ν�в�ҩ������ҽ��ʹ�õĶ���ҩ�Ҳ����ҽ����������ҽѧ����Ҫ��־���й�������в�ҩ��̽�������˼�ǧ�����ʷ���ഫ����ũ���ٲݣ��״�ҽҩ����ũ����Ϊ��ҩ�ʡ���<br>������ҩ��Ҫ��ֲ��ҩ����������Ҷ������������ҩ�����ࡢƤ���ǡ����ٵȣ��Ϳ���ҩ��ɡ���ֲ��ҩռ��ҩ�Ĵ������������ҩҲ���в�ҩ��<br>����Ŀǰ������ʹ�õ���ҩ�Ѵ�5000�����ң��Ѹ���ҩ����������γɵķ�������������ʤ����������ǧ����о����γ���һ�Ŷ����Ŀ�ѧ��������ѧ��</p></td></tr></table><br>';
	echo $pf_l;
}
?>
</div>
<div id="footer">&copy; 2007-2008 <a href="http://www.5glive.com/">5G����</a> ��ICP��06067378��</div>
</body>
</html>