<title>Md5在线查询破解 - Md5.Chen4.com</title>

<meta name=keywords content="md5解密,md5加密,md5破解,md5查询,md5在线,salt解密,vbb密码解密">

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {color: #000000}
.STYLE2 {color: #FF0000}
-->
</style>
<h1 align="center" class="STYLE1">&nbsp;</h1>
<h1 align="center" class="STYLE1"><img src="md5.png" width="488" height="111" /></h1>
<form action="" method="get" class="STYLE1">
  <div align="center"><strong>MD5:</strong>
    <input type="text" name="md5" size="40" />
    <input type="submit" value="解密"/>
  </div>
</form>

<div align="center">
  <?php
/*
* Created on 2010-6-23{date}
*/



if(isset($_GET['md5'])){
	$md5=$_GET['md5'];
	$url="http://www.tmto.org/api/latest/?hash=$md5";
	
	try {
	    	$contents=file_get_contents($url);
		//$curl_handle=curl_init();
		//curl_setopt($curl_handle, CURLOPT_URL,$url);
		//curl_setopt($curl_handle, CURLOPT_CONNECTTIMEOUT, 2);
		//curl_setopt($curl_handle, CURLOPT_RETURNTRANSFER, 1);
		//curl_setopt($curl_handle, CURLOPT_USERAGENT, 'Your application name');
		//$contents = curl_exec($curl_handle);
		//curl_close($curl_handle);

		preg_match_all('|text="(.*)"|Uis',$contents,$text_arr);
		$text=$text_arr[1][0];

		echo "查询结果:".base64_decode($text)."<hr>";
	} catch (Exception $e) {
	    echo 'Caught exception: ',  $e->getMessage(), "\n";
	}


}

?>
</div>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0029)http://www.xiya.org/link.html -->
<HTML><HEAD><TITLE>Md5在线查询破解 - Md5.Chen4.com</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<STYLE type=text/css>
TD {
	FONT-SIZE: 9pt
}
BODY {
	FONT-SIZE: 9pt
}
A:link {
	COLOR: #000000; TEXT-DECORATION: none
}
A:visited {
	COLOR: #000000; TEXT-DECORATION: none
}
A:active {
	COLOR: green; TEXT-DECORATION: none
}
A:hover {
	COLOR: red; TEXT-DECORATION: underline
}
BODY {
	MARGIN-TOP: 0px
}
.style24 {
	FONT-SIZE: 8pt; COLOR: #3db836
}
.style26 {
	FONT-SIZE: 8pt; COLOR: #ff8000
}
.style28 {
	FONT-SIZE: 8pt; COLOR: #6699ff
}
.style30 {
	FONT-SIZE: 8pt; COLOR: #996600
}
.style32 {
	FONT-SIZE: 8pt; COLOR: #ff0099
}
.td2 {
	FONT-SIZE: 9pt; LINE-HEIGHT: 15px
}
A.slv:link {
	CURSOR: hand; COLOR: #008000; FONT-FAMILY: "宋体"; TEXT-DECORATION: none
}
A.slv:visited {
	COLOR: #008000; TEXT-DECORATION: none
}
A.slv:active {
	COLOR: green; TEXT-DECORATION: none
}
A.slv:hover {
	COLOR: red; TEXT-DECORATION: underline
}
.style1 {
	FONT-SIZE: 12pt
}
BODY {
	FONT-SIZE: 9pt; FONT-FAMILY: Tahoma
}
TD {
	FONT-SIZE: 9pt; FONT-FAMILY: Tahoma
}
TH {
	FONT-SIZE: 9pt; FONT-FAMILY: Tahoma
}
.STYLE33 {color: #000000}
.STYLE34 {color: #0000FF}
</STYLE>


<META content="MSHTML 6.00.2900.5969" name=GENERATOR></HEAD>
<BODY 
onmouseout="window.status='Md5在线查询破解 - Md5.Chen4.com';return true">
<TABLE height=6 width=760 align=center>
  <TBODY>
  <TR>
    <TD></TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=3 width=760 
align=center bgColor=#ffffff borderColorLight=#cccccc border=1>
  <TBODY>
  <TR>
    <TD height=30>
      <DIV align=center>
      <DIV align="center">
        <p>Powered by <A href="http://www.chen4.com">Md5.Chen4.com</A></p>
        </DIV>
      </DIV></TD></TR></TBODY></TABLE></BODY></HTML>






<p align="center"> </p>
<div align="center"><script language="javascript" type="text/javascript" src="http://js.users.51.la/3933985.js"></script>
<noscript><a href="http://www.51.la/?3933985" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/3933985.asp" style="border:none" /></a></noscript></div>
<p>&nbsp;</p>




<script>
function look(){ 
if(event.shiftKey) 
alert("按Shift干什么？"); 
} 
document.onkeydown=look; 
</script>


