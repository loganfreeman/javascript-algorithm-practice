<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资源采集管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='__PUBLIC__/css/admin-style.css' />
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/jquery/jquery-1.7.2.min.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$('#xmltip').hide();
	$('#xmllist').show();
});
var jumpurl = '<?php echo ($jumpurl); ?>';
</script>
</head>
<?php $rand=mt_rand(1,3000); ?>
<body class="body">
<div id="xmltip">资源列表载入中……</div>
<div id="xmllist" style="display:none"><script language="JavaScript" charset="utf-8" type="text/javascript" src="http://data.tbmov.com/tbmov.js?<?php echo ($rand); ?>"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="http://union.feifeicms.com/xml/ff_2.5.js?<?php echo ($rand); ?>"></script></div>
<br /><center>Version：<font color="#FF0000"><?php echo L("ppvod_version");?></font></center>
</body>
</html>