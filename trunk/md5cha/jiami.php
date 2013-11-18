<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0020)http://md5.8309.com/ -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>md5在线解密,查询,md5破解 md5加密/解密</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META http-equiv=x-ua-compatible content=ie=7>
<META content="md5在线解密 md5加密 解密" name=keywords>
<META content="md5在线解密 md5加密/解密系统在设计结构上面参考多个md5在线查询网站，取其结构之优点，查询数据进行最大限度优化" 
name=description><LINK href="J/index.css" 
type=text/css rel=stylesheet>
<STYLE type=text/css>#tool_content {
	MARGIN-TOP: 20px; PADDING-LEFT: 15px; FLOAT: left; WIDTH: 600px
}
TEXTAREA {
	CLEAR: both; PADDING-RIGHT: 2px; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; WIDTH: 98%; PADDING-TOP: 2px
}
#output {
	BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-COLOR: #cccccc; BORDER-TOP-COLOR: #cccccc; BACKGROUND-COLOR: #f0f0f0; BORDER-RIGHT-COLOR: #cccccc
}
#md5_html {
	COLOR: #ff0000
}
</STYLE>

<SCRIPT src="J/md5.js" 
type=text/javascript></SCRIPT>

<SCRIPT src="J/j.js" 
type=text/javascript></SCRIPT>

<SCRIPT type=text/javascript>
function yige_md5() {   
  var txt = document.md5form.txt.value;
  MD5_Typ = 'Typ16';
  var md5_16_str = MD5(txt);
  document.md5form.md5_16a.value = md5_16_str;
  document.md5form.md5_16b.value = md5_16_str.toLowerCase();
  MD5_Typ = 'Typ32';
  var md5_32_str = MD5(txt);
  document.md5form.md5_32a.value = md5_32_str;
  document.md5form.md5_32b.value = md5_32_str.toLowerCase();                  
}

function get_md5() {
	$('#md5_html').html('<img src="/images/loading.gif" />');
	get_show("/source/ajax/md5.php?gid=0&str=" + $('#md5_txt').val(), 'md5_html');
}
function get_show(ajax_url, id) {
	$.ajax({ url: ajax_url, 
		success: function(data) {
			$('#'+id).html(data);
		} 
	});
}
</SCRIPT>

<META content="MSHTML 6.00.2900.5969" name=GENERATOR></HEAD>
<BODY>
<div align="center">　　　　　　　　　　<img src="md5.png" width="488" height="111"></div>
<DIV id=tool_content>
  <DIV class=html>
<FORM name=md5form onSubmit="return false;" action="" method=get>
<TABLE cellSpacing=5 cellPadding=1 width="113%" border=0>
  <TBODY>
  <TR>
    <TD noWrap align=right width=231>原字符串</TD>
    <TD width="428"><input name="txt" type="text" id="txt" style="WIDTH: 309px" onFocus="yige_md5()" onChange="yige_md5()" onKeyUp="yige_md5()" value=""></TD></TR>
  <TR>
    <TD noWrap align=right>16位（大写）</TD>
    <TD><INPUT id=md5_16a style="WIDTH: 309px" name=md5_16a></TD></TR>
  <TR>
    <TD noWrap align=right>16位（小写）</TD>
    <TD><INPUT id=md5_16b style="WIDTH: 309px" name=md5_16b></TD></TR>
  <TR>
    <TD noWrap align=right>32位（大写）</TD>
    <TD><INPUT id=md5_32a style="WIDTH: 309px" name=md5_32a></TD></TR>
  <TR>
    <TD noWrap align=right>32位（小写）</TD>
    <TD><INPUT id=md5_32b style="WIDTH: 309px" name=md5_32b></TD></TR>
  </TBODY></TABLE>
</FORM></DIV></DIV>
<TABLE height=6 width=760 align=center>
  <TBODY>
    <TR>
      <TD></TD>
    </TR>
  </TBODY>
</TABLE>
<TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=3 width=760 
align=center bgColor=#ffffff borderColorLight=#cccccc border=1>
  <TBODY>
    <TR>
      <TD height=30><DIV align=center>
        <DIV align="center">
          <p align="center">Powered by <A href="http://www.chen4.com">Md5.Chen4.com</A></p>
        </DIV>
      </DIV></TD>
    </TR>
  </TBODY>
</TABLE>
<p>&nbsp;</p>
</BODY></HTML>
