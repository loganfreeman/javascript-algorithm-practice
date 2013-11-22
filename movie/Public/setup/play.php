<?php 
/** 
* @author by酷酷的鱼 
* @copyright 2013-7-13 
* @输出USER-AGENTS print_r($_SERVER['HTTP_USER_AGENT']); 
*/ 
$playname = $_GET['playname'];
$u = $_GET['u'];
$v = $_GET['v'];//USER_AGENT转换小写
$my_user_agent = strtolower($_SERVER['HTTP_USER_AGENT']); 
//定义播放器URL
$play_win_bdhd = "/Public/setup/bdhd.html?u="+$_GET['u'];
$play_android_bdhd = "/Public/setup/bdhd.html";
$play_ios_bdhd = "/Public/setup/bdhd.html"; 
$play_win_qvod = "/Public/setup/qvod.html?u="+$v;
$play_android_qvod = "/Public/setup/qvod.html";
$play_ios_qvod = "/Public/setup/qvod.html"; 

//跳转函数
function headerUrl($url)
{    
     header("HTTP/1.1 302 Moved Permanently");    
	 header("Location: $url");
} 
//Windows平台
if(strpos($my_user_agent,"windows") == true)
{    
       if($playname == "bdhd")    
	   {        
	       //Windows百度影音        
		   headerUrl($play_win_bdhd);    
		   }
	   elseif($playname == "qvod")
	   {        
	       //Windows快播        
		   headerUrl($play_win_qvod);    
		}
} 
 //android平台
if(strpos($my_user_agent,"android") == true)
{    
	     if($playname == "bdhd")    
		 {        
		    //android百度影音       
			headerUrl($play_android_bdhd);
	     }
		 elseif($playname == "qvod")
	    {        
	       //android快播        
		   headerUrl($play_android_bdhd);    
		}
} 
//iPhone平台 包含iPod
        if(strpos($my_user_agent,"iphone") == true || strpos($my_user_agent,"ipod") == true)
		{    
		    if($playname == "bdhd")    
			{       
			//iPhone百度影音        
			headerUrl($play_ios_bdhd);    
			}
			elseif($playname == "qvod") 
	    {        
	       //android快播        
		   headerUrl($play_ios_qvod);    
		}

} 
?> 
