<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='__PUBLIC__/css/admin-style.css' />
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/jquery/jquery-1.7.2.min.js"></script>
<script language="JavaScript" type="text/javascript" charset="utf-8" src="__PUBLIC__/js/admin.js"></script>
<script language="javascript">
$(document).ready(function(){
	$feifeicms.show.table();
});
</script>
</head>
<body class="body">
<table border="0" cellpadding="0" cellspacing="0" class="table">
  <thead>
    <tr class="ct">
      <th class="l" width="50">ID</th>
      <th class="l">后台用户名称</th>
      <th class="l" width="150">上次登录时间</th>
      <th class="l" width="120">上次登录IP</th>
      <th class="l" width="100">登录次数</th>
      <th class="r" width="90">相关操作</th>
    </tr>
  </thead>
  <form action="?s=Admin-Admin-Delall" method="post" name="myform" id="myform"> 
  <?php if(is_array($list)): $i = 0; $__LIST__ = $list;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><tbody>
  <tr>
    <td class="l ct"><input name='ids[]' type='checkbox' value='<?php echo ($ppvod["admin_id"]); ?>' class="noborder"><?php echo ($ppvod["admin_id"]); ?></td>
    <td class="l pd"><?php echo ($ppvod["admin_name"]); ?></td>
    <td class="l ct"><?php echo (date('Y-m-d H:i:s',$ppvod["admin_logintime"])); ?></td>
    <td class="l ct"><?php echo ($ppvod["admin_ip"]); ?></td>
    <td class="l ct"><?php echo ($ppvod["admin_count"]); ?></td>
    <td class="r ct"><a href="?s=Admin-Admin-Add-id-<?php echo ($ppvod["admin_id"]); ?>" title="编辑用户资料">编辑</a> <a href="?s=Admin-Admin-Del-id-<?php echo ($ppvod["admin_id"]); ?>" onClick="return confirm('确定删除?')"  title="删除用户资料">删除</a></td>
  </tr>
  </tbody><?php endforeach; endif; else: echo "" ;endif; ?>
  <tfoot>
    <tr>
      <td colspan="6" class="r"><input type="button" value="全选" class="submit" onClick="checkall('all');"> <input name="" type="button" value="反选" class="submit" onClick="checkall();"> <input type="button" value="批量删除" class="submit" onClick="post('?s=Admin-Admin-Delall');"> <input type="button" class="submit" value="添加" onClick="window.location='?s=Admin-Admin-Add';"></td>
    </tr>  
  </tfoot>
  </form>
</table>
<br /><center>Version：<font color="#FF0000"><?php echo L("ppvod_version");?></font></center>
</body>
</html>