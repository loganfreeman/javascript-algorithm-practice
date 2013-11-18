<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>MD5</title>
<link href="md5.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"> 
    function c_Copy(c_id){ 
        var c=document.getElementById(c_id);
        c.select(); 
        document.execCommand("Copy");
		alert('复制成功！'); 
    } 
</script>
</head>

<body bgcolor="#efefef">
<?php
if(isset($_GET['n_s'])){
	$n_s = $_GET['n_s'];
	$m_32 = md5($n_s);
	$m_16 = substr($m_32,8,16);
?>
<div id="h_h"></div>
&nbsp;大写32位：<span class="ss" onclick='c_Copy("ss321")' onmouseover="this.style.background='#FFFF00'" onmouseout="this.style.background='#efefef'";><?php echo strtoupper($m_32);?><input class="i_p" id="ss321" name="ss321" value="<?php echo strtoupper($m_32);?>"></span><div id="h_h"></div>
&nbsp;小写32位：<span class="ss" onclick='c_Copy("ss320")' onmouseover="this.style.background='#FFFF00'" onmouseout="this.style.background='#efefef'";><?php echo $m_32;?><input class="i_p" id="ss320" name="ss320" value="<?php echo $m_32;?>"></span><div id="h_h"></div>
&nbsp;大写16位：<span class="ss" onclick='c_Copy("ss161")' onmouseover="this.style.background='#FFFF00'" onmouseout="this.style.background='#efefef'";><?php echo strtoupper($m_16);?><input class="i_p" id="ss161" name="ss161" value="<?php echo strtoupper($m_16);?>"></span><div id="h_h"></div>
&nbsp;小写16位：<span class="ss" onclick='c_Copy("ss160")' onmouseover="this.style.background='#FFFF00'" onmouseout="this.style.background='#efefef'";><?php echo $m_16;?><input class="i_p" id="ss160" name="ss160" value="<?php echo $m_16;?>"></span>
<?php
}
else{
}
?>

</body>
</html>
