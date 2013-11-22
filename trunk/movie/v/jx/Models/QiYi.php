<?php
function getqiyi($url){
        if(strpos($url,'_dd1')){
  $vid=str_replace('_dd1','',$url);
}
else{
    $content=get_curl_contents($url);
    $vid=inter($content,'data-drama-vid="','"');
}
    $content1=get_curl_contents('http://cache.video.qiyi.com/v/'.$vid);
    $content2=explode('fileUrl',$content1);
    $content2=$content2[1];
    preg_match_all('~<file>http://data.video.qiyi.com/videos/(.*)</file>~iUs',$content2,$vurl);
$i=0;
    foreach ($vurl[1] as $val){
$urllist.='  <video>'.chr(13);
$urllist.='   <file>http://wgdcnccdn.inter.qiyi.com/videos2/'.$val.'?start={start_bytes}</file>'.chr(13);
$urllist.='  </video>'.chr(13);
$i+=1;
}
$urllist2 = '';
$urllist2='<?xml version="1.0" encoding="utf-8"?>'.chr(13);
$urllist2.=' <ckplayer>'.chr(13);
$urllist2.=' <flashvars>'.chr(13);
$urllist2.='  {h->4}'.chr(13);
$urllist2.=' </flashvars>'.chr(13);
$urllist2.=$urllist;
$urllist2.=' </ckplayer>';
echo $urllist2;
}
?>