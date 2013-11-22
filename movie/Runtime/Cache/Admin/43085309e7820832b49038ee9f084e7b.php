<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资源库列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='__PUBLIC__/css/admin-style.css' />
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/jquery/jquery-1.7.2.min.js"></script>
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/js/admin.js"></script>
<script language="javascript">
function changeurl($cid,$hour){
	self.location.href = getjumpurl($cid,$hour);
}
function getjumpurl($action,$cid,$hour){
	return '?s=Admin-Xml-<?php echo constant("ACTION_NAME");?>-action-'+$action+'-fid-<?php echo ($fid); ?>-xmlurl-<?php echo ($xmlurl); ?>-reurl-<?php echo ($reurl); ?>-vodids-<?php echo ($vodids); ?>-play-<?php echo ($play); ?>-inputer-<?php echo ($inputer); ?>-cid-'+$cid+'-wd-<?php echo ($wd); ?>-h-'+$hour;
}
$(document).ready(function(){
	$feifeicms.show.table();	
});
</script>
</head>
<body class="body">
<!--背景灰色变暗-->
<div id="showbg" style="position:absolute;left:0px;top:0px;filter:Alpha(Opacity=0);opacity:0.0;background-color:#fff;z-index:8;"></div>
<!--绑定分类表单框-->
<div id="setbind" style="position:absolute;display:none;background: #85BFDC;padding:4px 5px 5px 5px;z-index:9;"></div>
<form action="?s=Admin-Xml-<?php echo constant("ACTION_NAME");?>" method="post" name="myform" id="myform">{__NOTOKEN__}
<table border="0" cellpadding="0" cellspacing="0" class="table" style="border-bottom:1px solid #cad9ea">
<thead><tr><th colspan="6" class="r"><span style="float:left">分类绑定设置 <?php if(!empty($cid)): ?><a href="javascript:changeurl('','');"><font color="red">查看全部资源</font></a><?php endif; ?></span><span style="float:right"><a href="?s=Admin-Xml-Show">返回资源库列表</a></span></th></tr></thead>
  <tr><?php if(is_array($list_class)): $i = 0; $__LIST__ = $list_class;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><?php if($i != 1 and $i%6 == 1): ?></tr><tr><?php endif; ?>
  <td class="ct"><a href="?s=Admin-Xml-<?php echo constant("ACTION_NAME");?>-fid-<?php echo ($fid); ?>-xmlurl-<?php echo ($xmlurl); ?>-reurl-<?php echo ($reurl); ?>-play-<?php echo ($play); ?>-inputer-<?php echo ($inputer); ?>-cid-<?php echo ($ppvod["list_id"]); ?>-wd-<?php echo ($wd); ?>"><?php echo ($ppvod["list_name"]); ?></a> <a href="javascript:void(0)" onClick="setbind(event,'<?php echo ($ppvod["list_id"]); ?>','<?php echo ($ppvod["bind_id"]); ?>');" id="bind_<?php echo ($ppvod["bind_id"]); ?>"><?php if(ff_bind_id($ppvod['bind_id']) > 0): ?><font color="green">已绑定</font><?php else: ?><font color="red">未绑定</font><?php endif; ?></a></td><?php endforeach; endif; else: echo "" ;endif; ?>
  </tr>
  <tr><td colspan="6" class="ct"><input type="button" value="全选" class="submit" onClick="checkall('all');"> <input name="" type="button" value="反选" class="submit" onClick="checkall();"> <input type="button" value="采集" class="submit" onClick="post(getjumpurl('ids','',''));"> <input type="button" value="采集当天" class="submit" onClick="post(getjumpurl('day','',24));"> <?php if(!empty($cid)): ?><input type="button" value="采集本类" class="submit" onClick="post(getjumpurl('all','<?php echo ($cid); ?>',''));"><?php endif; ?> <input type="button" value="采集所有" class="submit" onClick="post(getjumpurl('all','',''));"> <input type="text" name="wd" id="wd" maxlength="20" value="<?php echo (urldecode($wd)); ?>" onClick="this.select();" style="color:#666666"> <input type="button" value="搜索" class="submit" onClick="post(getjumpurl('','',''));"></td>
  </tr>  
</table>
<br />
<table border="0" cellpadding="0" cellspacing="0" class="table">
<thead>
<tr class="ct">
  <th class="l" ><span style="float:left;">视频资源列表</span>视频名称 </th>
  <th class="l" width="100">来源</th>
  <th class="r ct" width="130">更新时间</th>
</tr>
</thead>
<?php if(is_array($list_vod)): $i = 0; $__LIST__ = $list_vod;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><tbody>
  <tr>
    <td class="l pd"><input name='ids[]' type='checkbox' value='<?php echo ($ppvod["vod_id"]); ?>' class="noborder">『<?php echo ($ppvod["list_name"]); ?>』<?php echo ($ppvod["vod_name"]); ?><?php echo ($ppvod["vod_title"]); ?> <?php if(($ppvod['vod_continu'])  !=  "0"): ?><sup><?php echo ($ppvod["vod_continu"]); ?></sup><?php endif; ?> <a href="?s=Admin-Vod-Show-wd-<?php echo (urlencode(msubstr($ppvod["vod_name"],0,4))); ?>" target="_blank" style="color:#FF0000">查</a></td>
    <td class="l ct"><?php echo (str_replace('$$$',' ',$ppvod["vod_play"])); ?></td>
    <td class="r ct"><?php echo ($ppvod["vod_addtime"]); ?></td>
  </tr>
  </tbody><?php endforeach; endif; else: echo "" ;endif; ?>
   <tr>
      <td colspan="3" class="r pages"><?php echo ($pagelist); ?></td>
    </tr>   
  <tfoot>
    <tr>
      <td colspan="3" class="r"><input type="button" value="全选" class="submit" onClick="checkall('all');"> <input name="" type="button" value="反选" class="submit" onClick="checkall();"> <input type="button" value="采集" class="submit" onClick="post(getjumpurl('ids','',''));"> <input type="button" value="采集当天" class="submit" onClick="post(getjumpurl('day','',24));"> <?php if(!empty($cid)): ?><input type="button" value="采集本类" class="submit" onClick="post(getjumpurl('all','<?php echo ($cid); ?>',''));"><?php endif; ?> <input type="button" value="采集所有" class="submit" onClick="post(getjumpurl('all','',''));"></td>
    </tr>  
  </tfoot>        
</table>
</form>
<br /><center>Version：<font color="#FF0000"><?php echo L("ppvod_version");?></font></center>
</body>
</html>