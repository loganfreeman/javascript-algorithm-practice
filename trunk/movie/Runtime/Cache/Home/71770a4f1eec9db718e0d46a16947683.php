<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>2013最新电影,百度影音电影,电视剧排行榜,电视剧大全,好看的电视剧,好看的电影,<?php echo ($sitename); ?></title>
<meta name="keywords" content="<?php echo ($keywords); ?>">
<meta name="description" content="<?php echo ($description); ?>">
<script language="javascript">
<!-- 
 window.onerror=function(){return true;} 
// --></script>
<script type="text/javascript">var Root='<?php echo ($root); ?>';var Sid='<?php echo ($sid); ?>';var Cid='<?php echo ($list_id); ?>';<?php if($sid == 1): ?>var Id='<?php echo ($vod_id); ?>';<?php else: ?>var Id='<?php echo ($news_id); ?>';<?php endif; ?></script>
<link type="text/css" rel="stylesheet" href="<?php echo ($root); ?>static/css/base.css" />
<link href="favicon.ico" type="image/x-icon" rel="icon">
<script type="text/javascript" src="<?php echo ($root); ?>static/js/script.js"></script>
<base target="_blank" />
</head>
<body>
<?php
$s_area=explode(',',C('play_area'));
$s_language=explode(',',C('play_language'));
$s_year=explode(',',C('play_year'));
if($_GET[year]) $u_year="-year-".$_GET[year];else $u_year=NULL;if($_GET[area]) $u_area="-area-".$_GET[area];else $u_area=NULL;if($_GET[language]) $u_language="-language-".$_GET[language];else $u_order=NULL;if($_GET[order]) $u_order="-order-".$_GET[order];else $u_order=NULL;
?><div id="header">
  <div id="navbar">
    <div class="layout fn-clear">
      <ul id="nav" class="ui-nav">
         <li class="nav-item <?php if(($list_id)  ==  ""): ?>current<?php endif; ?>" class="nav-item " id="nav-home"><a class="nav-link" target="_self" href="<?php echo ($siteurl); ?>"><i class="ui-icon home-nav"></i></a></li> 
        <?php if(is_array($list_menu)): $i = 0; $__LIST__ = $list_menu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><?php if(($ppvod["list_pid"])  ==  "0"): ?><li class="nav-item <?php if(($ppvod["list_id"])  ==  $list_id): ?>current<?php endif; ?><?php if(($ppvod["list_id"])  ==  $list_pid): ?>current<?php endif; ?>" id="nav-<?php echo ($ppvod["list_dir"]); ?>"><a class="nav-link" target="_self" href="<?php echo ($ppvod["list_url"]); ?>"><i class="ui-icon <?php echo ($ppvod["list_dir"]); ?>-nav"></i><?php echo ($ppvod["list_name"]); ?></a></li><?php endif; ?><?php endforeach; endif; else: echo "" ;endif; ?> 
        </ul>
      <!-- // nav end -->
      <ul id="sign" class="ui-nav">
        <li class="nav-item drop-down" id="nav-looked"><a rel="nofollow" class="nav-link drop-title" target="_self"><i class="ui-icon looked-nav"></i>播放记录 </a>
          <div class="drop-box">
            <div class="looked-list">
              <p><a rel="nofollow" class="close-his" target="_self" href="javascript:;">关闭</a><a rel="nofollow" href="javascript:;" id="emptybt" data="1" target="_self">清空全部播放记录</a></p>
<ul class="highlight" id="playhistory"></ul>
              <div class="his-todo" id="morelog" style="display:none;"></div>
              <div class="his-todo" id="his-todo"></div>
            </div>
           <script type="text/javascript">PlayHistoryObj.viewPlayHistory('playhistory');</script>
            <!-- // looked-list end -->
          </div>
        </li>
      </ul>
      <!-- // sign end -->
    </div>
  </div>
  <!-- // navbar end -->
  <div id="subnav">
    <div class="layout fn-clear">
      <div class="subnav-tv fn-left"><strong class="tv">电视剧：</strong><?php $array_listid = getlistarr(2); ?><?php if(is_array($array_listid)): $i = 0; $__LIST__ = array_slice($array_listid,0,7,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ff_listid): ++$i;$mod = ($i % 2 )?><?php if(($key)  >  "0"): ?><em>|</em><?php endif; ?><a href="<?php echo getlistname($ff_listid,'list_url');?>"><?php echo getlistname($ff_listid);?></a><?php endforeach; endif; else: echo "" ;endif; ?></div>
      <div class="subnav-movie fn-right"><strong class="movie">电影：</strong><?php $array_listidd = getlistarr(1); ?><?php if(is_array($array_listidd)): $i = 0; $__LIST__ = array_slice($array_listidd,0,7,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ff_listid): ++$i;$mod = ($i % 2 )?><?php if(($key)  >  "0"): ?><em>|</em><?php endif; ?><a href="<?php echo getlistname($ff_listid,'list_url');?>"><?php echo getlistname($ff_listid);?></a><?php endforeach; endif; else: echo "" ;endif; ?></div>
    </div>
  </div>
  <!-- // subnav end -->
  <div id="headbar">
    <div class="layout fn-clear">
      <div id="logo"><a href="<?php echo ($siteurl); ?>"><img src="<?php echo ($root); ?>static/images/logo.png" alt="<?php echo ($sitename); ?>-最新电影-好看的电视剧排行榜" /></a></div>
      <!-- // logo end -->
<div id="searchbar"><div class="ui-search">
<form id="search" name="search"  method="POST" action="<?php echo ($siteurl); ?>vod-search" onSubmit="return qrsearch();">
<input type="text" id="wd" name="wd" class="search-input" value="请在此处输入影片片名或演员名称。" onfocus="if(this.value=='请在此处输入影片片名或演员名称。'){this.value='';}" onblur="if(this.value==''){this.value='请在此处输入影片片名或演员名称。';};" />
<input type="submit" id="searchbutton"  class="search-button" value="" /></form>
</div><!-- // search end -->
        <div class="hotkeys"> <?php echo ($hotkey); ?></div>
        <!-- // hotkeys end -->
      </div>
      <!-- // searchbar end -->
      <ul id="tbmov-plus">
        <li><a id="new" href="<?php echo ff_mytpl_url('my_new.html');?>"><i class="ui-icon new-icon"></i>最近<br />更新</a></li>
        <li><a href="<?php echo ff_mytpl_url('my_top.html');?>"><i class="ui-icon top-icon"></i>影片<br />排行</a></li>
        <li><a href="/faq/"><i class="ui-icon help-icon"></i>会员<br />帮助</a></li>
        <li><a href="<?php echo ($url_guestbook); ?>"><i class="ui-icon gb-icon"></i>留言<br />反馈</a></li>
        <li><a rel="nofollow" target="_self" id="a-clo" href="javascript:void(0);" onclick='fav();'><i class="ui-icon fav-icon"></i>收藏<br />本站</a></li>
      </ul>
      <!-- tbmov-plus end -->
    </div>
    <!-- // layout end -->
  </div>
  <!-- // headbar end -->
</div>
<!-- // header end -->
<script type="text/javascript"> 
	function fav(){
	 URL='<?php echo ($siteurl); ?>';title='<?php echo ($sitename); ?>';
	try  { window.external.addFavorite(URL, title);  }catch (e){ try {window.sidebar.addPanel(title, URL, "");}
   catch (e) { alert("加入收藏失败，请使用Ctrl+D进行添加");}}}
</script>

<?php $rq=date("w"); ?>
<?php $yg=date("Y-m-d"); ?>
<div id="content" class="layout">
  <div class="ui-sponsor">
    <!-- 广告位：index01 -->
<?php echo getadsurl('index_01_960');?>
  </div>
  <!-- // ui-sponsor end -->
  <div class="layout fn-clear" id="latest-focus">
    <div class="latest-tab-nav">
      <ul class="fn-clear">
        <li id="latest1" onmouseover="setTab('latest',1,6);" class="current"><span><i class="ui-icon hot"></i>最热门影片推荐</span></li>
        <li id="latest2" onmouseover="setTab('latest',2,6);"><span><i class="ui-icon tv"></i><h2>最新电视剧</h2></span></li>
        <li id="latest3" onmouseover="setTab('latest',3,6);"><span><i class="ui-icon movie"></i><h2>最新电影</h2></span></li>
        <li id="latest4" onmouseover="setTab('latest',4,6);"><span><i class="ui-icon dm"></i><h2>最新动漫</h2></span></li>
        <li id="latest5" onmouseover="setTab('latest',5,6);"><span><i class="ui-icon fun"></i><h2>最新综艺</h2></span></li>
        <li id="latest6" onmouseover="setTab('latest',6,6);"><span><i class="ui-icon wei"></i><h2>最新微电影</h2></span></li>
      </ul>
    </div>
    <div class="latest-tab-box">
      <div id="con_latest_1" class="latest-item hot-latest">
        <div class="silder-cnt">
          <ul class="img-list">
         <?php $vod_hot_tv = ff_mysql_vod('limit:6;stars:5;order:vod_addtime desc'); ?>
          <?php if(is_array($vod_hot_tv)): $i = 0; $__LIST__ = $vod_hot_tv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
              <label class="mask"></label>
              <label class="text"><?php if(in_array(($ppvod["vod_cid"]), explode(',',"3,15,16,17,18,19,26,27,28"))): ?><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?><?php else: ?><?php switch($ppvod["vod_cid"]): ?><?php case "4":  ?><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全期<?php endif; ?><?php break;?><?php default: ?><?php echo ($ppvod["lastplay_name"]); ?><?php endswitch;?><?php endif; ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="hot"><?php echo ($ppvod["vod_actor"]); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list end -->
        </div>
        <ul class="txt-list">
         <?php $vod_hot_tv_wz = ff_mysql_vod('limit:6,12;stars:5;order:vod_hits desc'); ?>
         <?php if(is_array($vod_hot_tv_wz)): $i = 0; $__LIST__ = $vod_hot_tv_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($i); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a href="<?php echo ($ppvod["lastplay_url"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php if(in_array(($ppvod["vod_cid"]), explode(',',"3,15,16,17,18,19,26,27,28"))): ?><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?><?php else: ?><?php switch($ppvod["vod_cid"]): ?><?php case "4":  ?><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全期<?php endif; ?><?php break;?><?php default: ?><?php echo ($ppvod["lastplay_name"]); ?><?php endswitch;?><?php endif; ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // txt-list End -->
      </div>
      <div id="con_latest_2" class="latest-item tv-latest fn-hide">
        <div class="silder-cnt">
          <ul class="img-list">
          <?php $vod_hot_dsj = ff_mysql_vod('cid:2;limit:6;order:vod_addtime desc'); ?>
          <?php if(is_array($vod_hot_dsj)): $i = 0; $__LIST__ = $vod_hot_dsj;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><img width="110" height="150"  src="<?php echo ($ppvod["vod_picurl"]); ?>"  alt="<?php echo ($ppvod["vod_name"]); ?>" />
              <label class="mask"></label>
              <label class="text"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><?php echo (getcolor($ppvod["vod_name"],$ppvod['vod_color'])); ?></a></h3>
              <p class="time"><?php echo (date('Y-m-d',$ppvod["vod_addtime"])); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list End -->
        </div>
        <ul class="txt-list">
        <?php $vod_hot_dsj_wz = ff_mysql_vod('cid:2;limit:6,12;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_hot_dsj_wz)): $i = 0; $__LIST__ = $vod_hot_dsj_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo (date('m-d',$ppvod["vod_addtime"])); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a class="gray" href="<?php echo ($ppvod["lastplay_url"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
       
        </ul>
        <!-- // txt-list end -->
      </div>
      <div id="con_latest_3" class="latest-item movie-latest fn-hide">
        <div class="silder-cnt">
          <ul class="img-list">
          <?php $vod_new_mov = ff_mysql_vod('cid:1;limit:6;order:vod_addtime desc'); ?>
           <?php if(is_array($vod_new_mov)): $i = 0; $__LIST__ = $vod_new_mov;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><img width="110" height="150"  src="<?php echo ($ppvod["vod_picurl"]); ?>"  alt="<?php echo ($ppvod["vod_name"]); ?>"/>
              <label class="mask"></label>
              <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><?php echo (getcolor($ppvod["vod_name"],$ppvod['vod_color'])); ?></a></h3>
              <p class="time"><?php echo (date('Y-m-d',$ppvod["vod_addtime"])); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list End -->
        </div>
        <ul class="txt-list">
              <?php $vod_new_mov_wz = ff_mysql_vod('cid:1;limit:6,12;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_new_mov_wz)): $i = 0; $__LIST__ = $vod_new_mov_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo (date('m-d',$ppvod["vod_addtime"])); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a class="gray" href="<?php echo ($ppvod["lastplay_url"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"  target="_blank"><?php echo ($ppvod["lastplay_name"]); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // txt-list end -->
      </div>
      <div id="con_latest_4" class="latest-item dm-latest fn-hide">
        <div class="silder-cnt">
          <ul class="img-list">
          <?php $vod_new_dm = ff_mysql_vod('cid:3;limit:6;order:vod_addtime desc'); ?>
           <?php if(is_array($vod_new_dm)): $i = 0; $__LIST__ = $vod_new_dm;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" ><img width="110" height="150"  src="<?php echo ($ppvod["vod_picurl"]); ?>"  alt="<?php echo ($ppvod["vod_name"]); ?>" />
              <label class="mask"></label>
              <label class="text"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><?php echo (getcolor($ppvod["vod_name"],$ppvod['vod_color'])); ?></a></h3>
              <p class="time"><?php echo (date('Y-m-d',$ppvod["vod_addtime"])); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list End -->
        </div>
        <ul class="txt-list">
        <?php $vod_new_dm_wz = ff_mysql_vod('cid:3;limit:6,12;order:vod_addtime desc'); ?>
       <?php if(is_array($vod_new_dm_wz)): $i = 0; $__LIST__ = $vod_new_dm_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo (date('m-d',$ppvod["vod_addtime"])); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a class="gray" href="<?php echo ($ppvod["lastplay_url"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // txt-list end -->
      </div>
      <div id="con_latest_5" class="latest-item fun-latest fn-hide">
        <div class="silder-cnt">
          <ul class="img-list">
           <?php $vod_new_zy = ff_mysql_vod('cid:4;limit:6;order:vod_addtime desc'); ?>
           <?php if(is_array($vod_new_zy)): $i = 0; $__LIST__ = $vod_new_zy;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"><img width="110" height="150"  src="<?php echo ($ppvod["vod_picurl"]); ?>"  alt="<?php echo ($ppvod["vod_name"]); ?>" />
              <label class="mask"></label>
              <label class="text"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全期<?php endif; ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" ><?php echo (getcolor($ppvod["vod_name"],$ppvod['vod_color'])); ?></a></h3>
              <p class="time"><?php echo (date('Y-m-d',$ppvod["vod_addtime"])); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list End -->
        </div>
        <ul class="txt-list">
      <?php $vod_new_zy_wz = ff_mysql_vod('cid:4;limit:6,12;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_new_zy_wz)): $i = 0; $__LIST__ = $vod_new_zy_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo (date('m-d',$ppvod["vod_addtime"])); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a class="gray" href="<?php echo ($ppvod["lastplay_url"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"  target="_blank"><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全期<?php endif; ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // txt-list end -->
      </div>
      <div id="con_latest_6" class="latest-item wei-latest fn-hide">
        <div class="silder-cnt">
          <ul class="img-list">
         <?php $vod_new_wdy = ff_mysql_vod('cid:24;limit:6;order:vod_addtime desc'); ?>
           <?php if(is_array($vod_new_wdy)): $i = 0; $__LIST__ = $vod_new_wdy;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" ><img width="110" height="150"  src="<?php echo ($ppvod["vod_picurl"]); ?>"  alt="<?php echo ($ppvod["vod_name"]); ?>" />
              <label class="mask"></label>
              <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
              </a>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" ><?php echo (getcolor($ppvod["vod_name"],$ppvod['vod_color'])); ?></a></h3>
              <p class="time"><?php echo (date('Y-m-d',$ppvod["vod_addtime"])); ?></p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // img-list End -->
        </div>
        <ul class="txt-list">
             <?php $vod_new_wdy_wz = ff_mysql_vod('cid:24;limit:6,12;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_new_wdy_wz)): $i = 0; $__LIST__ = $vod_new_wdy_wz;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo (date('m-d',$ppvod["vod_addtime"])); ?>.</span><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>"  target="_blank"><?php echo (msubstr(getcolor($ppvod["vod_name"],$ppvod['vod_color']),0,10)); ?></a>/<a class="gray" href="<?php echo ($ppvod["lastplay_url"]); ?>"  title="<?php echo ($ppvod["list_name"]); ?><?php echo ($ppvod["vod_name"]); ?>" target="_blank"><?php echo ($ppvod["lastplay_name"]); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // txt-list end -->
      </div>
      <!-- // con_latest_x end -->
    </div>
  </div>
  <!-- // latest-focus end -->
  <div class="ui-sponsor">
    <!-- 广告位：index02-yigao -->
<?php echo getadsurl('index_02_960');?>
  </div>
  <!-- // ui-sponsor end -->
  <div class="ui-box ui-tbmov fn-clear" id="tbmov-tv">
    <div class="tbmov-bar">
      <div class="ui-title"><a class="ui-icon title-icon" href="<?php echo getlistname(2,'list_url');?>" title="电视剧排行榜">电视剧</a>
        <h2 class="hide-txt"><a href="<?php echo getlistname(2,'list_url');?>" title="好看的电视剧">电视剧<em>Teleplay</em></a></h2>
        <a class="view-all" href="<?php echo getlistname(2,'list_url');?>" title="电视剧大全">更多</a></div>
      <!-- // ui-title end -->
      <div class="ui-sort">
        <div class="sort-item sort-type">
        <ul class="sort-list fn-clear">
            <?php $array_listid = getlistarr(2); ?>
            <?php if(is_array($array_listid)): $i = 0; $__LIST__ = array_slice($array_listid,0,8,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ff_listid): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo getlistname($ff_listid,'list_url');?>"><?php echo (msubstr(getlistname($ff_listid),0,2)); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
            
          </ul>
          <!-- // sort-list#sort-star end -->
        </div>
        <!-- // sort-item end -->
        <div class="sort-item sort-star">
          <ul class="sort-list fn-clear">
          <?php $type_id = 2; ?>
          <?php $catlist = D('Mcat')->list_cat(2); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = $catlist;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>剧"><?php echo ($vo["m_name"]); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-type end -->
        </div>
        <!-- // sort-item end -->
      </div>
      <!-- // ui-sort end -->
      <div class="ui-ranking">
        <h2><a href="<?php echo ff_mytpl_url('my_top_tv.html');?>" title="电视剧排行榜">电视剧排行榜</a></h2>
        <ul class="ranking-list">
        <?php $vod_dsj_mx =ff_mysql_vod('cid:2;limit:10;day:30;order:vod_hits desc'); ?>
              <?php if(is_array($vod_dsj_mx)): $i = 0; $__LIST__ = $vod_dsj_mx;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($ppvod["vod_gold"]); ?></span><?php if(($i)  <  "4"): ?><em class="stress">0<?php echo ($i); ?>.</em>
           <?php else: ?><em><?php if(($i)  >  "9"): ?><em><?php echo ($i); ?>.</em><?php else: ?><em>0<?php echo ($i); ?>.</em><?php endif; ?></em><?php endif; ?>
           <a target="_blank" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音全集"><?php echo (msubstr($ppvod["vod_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // ranking-list end -->
      </div>
      <!-- // ui-ranking end -->
    </div>
    <!-- // tbmov-bar end -->
    <div class="tbmov-box">
      <div class="tbmov-commend">
        <ul class="img-list focus-list fn-clear">
         <?php $vod_dianshiju_hot_hb_h =ff_mysql_vod('cid:2;prty:1;limit:2;day:15;order:vod_addtime desc'); ?>
         <?php if(is_array($vod_dianshiju_hot_hb_h)): $i = 0; $__LIST__ = $vod_dianshiju_hot_hb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="130" height="175"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <div class="play-txt">
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音全集"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="nums"><?php echo ($ppvod["lastplay_name"]); ?></p>
              <p class="desc"><?php echo (msubstr($ppvod["vod_content"],0,43)); ?> …… </p>
              <dl class="item">
                <dt>演员：</dt>
                <dd><?php echo (ff_search_url($ppvod["vod_actor"])); ?></dd>
              </dl>
              <dl class="item">
                <dt>地区：</dt>
                <dd><?php echo ($ppvod["vod_area"]); ?></dd>
              </dl>
              <dl class="item">
                <dt>类型：</dt>
                <dd><a href="<?php echo ($ppvod["list_url"]); ?>" target="_blank"><?php echo ($ppvod["list_name"]); ?></a></dd>
              </dl>
              <dl class="item">
                <dt>年份：</dt>
                <dd><?php echo (($vod_year)?($vod_year):'未录入'); ?></dd>
              </dl>
            </div>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- focus-list end -->
      </div>
      <!-- // tbmov-commend end -->
      <div class="tbmov-random">
        <ul class="img-list fn-clear">
        <?php $vod_dianshiju_hot_hbb_h =ff_mysql_vod('cid:2;day:15;limit:3,5;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_dianshiju_hot_hbb_h)): $i = 0; $__LIST__ = $vod_dianshiju_hot_hbb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="mask"></label>
            <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音全集"><?php echo ($ppvod["vod_name"]); ?></a></h3>
            <p class="star"><?php echo (ff_search_url($ppvod["vod_actor"])); ?></p>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- img-list end -->
      </div>
      <!-- // tbmov-random end -->
      <div class="tbmov-assist fn-clear">
        <ul class="assist-tab-nav">
          <li id="ds1" onmouseover="setTab('ds',1,2);" class="current">卫视同步</li>
          <li id="ds2" onmouseover="setTab('ds',2,2);">新剧预告</li>
        </ul>
        <!-- // assist-tab-nav end -->
        <div id="con_ds_1" class="assist-tab-box">
          <ul class="synch-list">
            <li>
              <h3><a href="#"><img src="static/images/tv/hntv.gif" alt="湖南卫视" />湖南卫视</a></h3>
              <?php $vod_hnws_tv =ff_mysql_vod('cid:2;diantai:湖南卫视;prty:2;day:30;limit:3;order:vod_addtime desc'); ?> 
              <?php if(is_array($vod_hnws_tv)): $i = 0; $__LIST__ = $vod_hnws_tv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><p><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?>第<?php echo ($ppvod["vod_continu"]); ?>集<?php else: ?>全集<?php endif; ?>"><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></p><?php endforeach; endif; else: echo "" ;endif; ?>   
            </li>
            <li>
              <h3><a href="#"><img src="static/images/tv/jstv.gif" alt="江苏卫视" />江苏卫视</a></h3>
              <?php $vod_jsws_tv =ff_mysql_vod('cid:2;diantai:江苏卫视;prty:2;day:30;order:vod_addtime desc'); ?> 
              <?php if(is_array($vod_jsws_tv)): $i = 0; $__LIST__ = $vod_jsws_tv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><p><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?>第<?php echo ($ppvod["vod_continu"]); ?>集<?php else: ?>全集<?php endif; ?>"><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></p><?php endforeach; endif; else: echo "" ;endif; ?>   
            </li>
            <li>
              <h3><a href="#"><img src="static/images/tv/dftv.gif" alt="东方卫视" />东方卫视</a></h3>
              <?php $vod_dfws_tv =ff_mysql_vod('cid:2;diantai:东方卫视;prty:2;day:30;limit:3;order:vod_addtime desc'); ?> 
              <?php if(is_array($vod_dfws_tv)): $i = 0; $__LIST__ = $vod_dfws_tv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><p><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?>第<?php echo ($ppvod["vod_continu"]); ?>集<?php else: ?>全集<?php endif; ?>"><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></p><?php endforeach; endif; else: echo "" ;endif; ?> 
            </li>
            <li>
              <h3><a href="#"><img src="static/images/tv/zjtv.gif" alt="浙江卫视" />浙江卫视</a></h3>
              <?php $vod_zjws_tv =ff_mysql_vod('cid:2;diantai:浙江卫视;day:30;prty:2;limit:3;order:vod_addtime desc'); ?> 
              <?php if(is_array($vod_zjws_tv)): $i = 0; $__LIST__ = $vod_zjws_tv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><p><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?>第<?php echo ($ppvod["vod_continu"]); ?>集<?php else: ?>全集<?php endif; ?>"><?php echo (msubstr($ppvod["vod_name"],0,25)); ?></a><?php if(!empty($ppvod["vod_continu"])): ?><?php echo ($ppvod["lastplay_name"]); ?><?php else: ?>全集<?php endif; ?></p><?php endforeach; endif; else: echo "" ;endif; ?> 
            </li>
          </ul>
        </div>
        <!-- // assist-tab-box end -->
        <div id="con_ds_2" class="assist-tab-box fn-hide">
          <ul class="assist-list fn-clear">
   <?php $vod_dy_zztv = home_after_playtv() ?>
          <?php if(is_array($vod_dy_zztv)): $i = 0; $__LIST__ = $vod_dy_zztv;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo (msubstr($ppvod["vod_name"],0,10)); ?></a></h3>
              <p class="time"><?php echo (date('m月d日',$ppvod["vod_filmtime"])); ?> 首播</p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
           
          </ul>
          <!-- assist-list end -->
        </div>
        <!-- // assist-tab-box end -->
      </div>
      <!-- // tbmov-assist end -->
    </div>
    <!-- // tbmov-box end -->
  </div>
  <!-- // ui-tbmov end -->
  <div class="ui-box ui-tbmov fn-clear" id="tbmov-movie">
    <div class="tbmov-bar">
      <div class="ui-title"><a class="ui-icon title-icon" href="<?php echo getlistname(1,'list_url');?>" title="2013最新电影">电影</a>
        <h2 class="hide-txt"><a href="<?php echo getlistname(1,'list_url');?>" title="好看的电影">电影<em>Movie</em></a></h2>
        <a class="view-all" href="<?php echo getlistname(1,'list_url');?>" title="百度影音电影">更多</a></div>
      <!-- // ui-title end -->
      <div class="ui-sort">
        <div class="sort-item sort-type">
          <ul class="sort-list fn-clear">
          <?php $type_id = 1; ?>
          <?php $catlist = D('Mcat')->list_cat(1); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = $catlist;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>片"><?php echo ($vo["m_name"]); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-type end -->
        </div>
        <!-- // sort-item end -->
        <div class="sort-item sort-star">
          <ul class="sort-list fn-clear">
              <?php $vod_zxdy_mx =ff_mysql_vod('cid:1;limit:4;order:vod_hits desc'); ?>
              <?php if(is_array($vod_zxdy_mx)): $i = 0; $__LIST__ = $vod_zxdy_mx;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><?php echo (ff_search_url(msubstr($ppvod["vod_actor"],0,3))); ?></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-star end -->
        </div>
        <!-- // sort-item end -->
      </div>
      <!-- // ui-sort end -->
      <div class="ui-ranking">
        <h2><a href="<?php echo ff_mytpl_url('my_top_mov.html');?>" title="电影排行榜">电影排行榜</a></h2>
        <ul class="ranking-list">
        <?php $vod_dyx_mx =ff_mysql_vod('cid:1;limit:10;day:15;order:vod_hits desc'); ?>
              <?php if(is_array($vod_dyx_mx)): $i = 0; $__LIST__ = $vod_dyx_mx;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($ppvod["vod_gold"]); ?></span><?php if(($i)  <  "4"): ?><em class="stress">0<?php echo ($i); ?>.</em>
           <?php else: ?><em><?php if(($i)  >  "9"): ?><em><?php echo ($i); ?>.</em><?php else: ?><em>0<?php echo ($i); ?>.</em><?php endif; ?></em><?php endif; ?><a target="_blank" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音"><?php echo (msubstr($ppvod["vod_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // ranking-list end -->
      </div>
      <!-- // ui-ranking end -->
    </div>
    <!-- // tbmov-bar end -->
    <div class="tbmov-box">
      <div class="tbmov-commend">
        <ul class="img-list focus-list fn-clear">
        <?php $vod_dianying_hot_hb_h =ff_mysql_vod('cid:1;prty:1;day:30;limit:2;order:vod_hits desc'); ?>
         <?php if(is_array($vod_dianying_hot_hb_h)): $i = 0; $__LIST__ = $vod_dianying_hot_hb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="130" height="175"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <div class="play-txt">
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="nums"><?php echo ($ppvod["lastplay_name"]); ?></p>
              <p class="desc"><?php echo (msubstr($ppvod["vod_content"],0,43)); ?> …… </p>
              <dl class="item">
                <dt>演员：</dt>
                <dd><?php echo (ff_search_url($ppvod["vod_actor"])); ?></dd>
              </dl>
              <dl class="item">
                <dt>地区：</dt>
                <dd><?php echo ($ppvod["vod_area"]); ?></dd>
              </dl>
              <dl class="item">
                <dt>类型：</dt>
                <dd><a href="<?php echo ($ppvod["list_url"]); ?>" target="_blank"><?php echo ($ppvod["list_name"]); ?></a></dd>
              </dl>
              <dl class="item">
                <dt>年份：</dt>
                <dd><?php echo (($vod_year)?($vod_year):'未录入'); ?></dd>
              </dl>
            </div>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
      </ul>
        <!-- focus-list end -->
      </div>
      <!-- // tbmov-commend end -->
      <div class="tbmov-random">
        <ul class="img-list fn-clear">
          <?php $vod_ddianying_hot_hbb_h =ff_mysql_vod('cid:1;day:30;limit:3,5;order:vod_hits desc'); ?>
        <?php if(is_array($vod_ddianying_hot_hbb_h)): $i = 0; $__LIST__ = $vod_ddianying_hot_hbb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="mask"></label>
            <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音"><?php echo ($ppvod["vod_name"]); ?></a></h3>
            <p class="star"><?php echo (ff_search_url($ppvod["vod_actor"])); ?></p>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- img-list end -->
      </div>
      <!-- // tbmov-random end -->
      <div class="tbmov-assist fn-clear">
        <ul class="assist-tab-nav">
          <li id="dy1" onmouseover="setTab('dy',1,2);" class="current">正在热映</li>
          <li id="dy2" onmouseover="setTab('dy',2,2);">即将上映</li>
        </ul>
        <!-- // assist-tab-nav end -->
       
        <div id="con_dy_1" class="assist-tab-box">
          <ul class="assist-list fn-clear">
          <?php $vod_dy_zzry = home_hot_play() ?>
          <?php if(is_array($vod_dy_zzry)): $i = 0; $__LIST__ = $vod_dy_zzry;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音"><?php echo (msubstr($ppvod["vod_name"],0,10)); ?></a></h3>
              <p class="time"><?php echo (date('m月d日',$ppvod["vod_filmtime"])); ?> 正在热映</p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- assist-list end -->
        </div>
        <!-- // assist-tab-box end -->
        <div id="con_dy_2" class="assist-tab-box fn-hide">
          <ul class="assist-list fn-clear">
           <?php $vod_dy_jjsy =home_after_play(); ?>
          <?php if(is_array($vod_dy_jjsy)): $i = 0; $__LIST__ = $vod_dy_jjsy;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li>
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>百度影音"><?php echo (msubstr($ppvod["vod_name"],0,10)); ?></a></h3>
              <p class="time"><?php echo (date('m月d日',$ppvod["vod_filmtime"])); ?> 上映</p>
            </li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- assist-list end -->
        </div>
        <!-- // assist-tab-box end -->
      </div>
      <!-- // tbmov-assist end -->
    </div>
    <!-- // tbmov-box end -->
  </div>
  <!-- // ui-tbmov end -->
  <div class="ui-sponsor">
    <!-- 广告位：index03 -->
<?php echo getadsurl('index_03_960');?>
  </div>
  <!-- // ui-sponsor end -->
  <div class="ui-box ui-tbmov fn-clear" id="tbmov-cartoon">
    <div class="tbmov-bar">
      <div class="ui-title"><a class="ui-icon title-icon" href="<?php echo getlistname(3,'list_url');?>" title="动画">经典动漫</a>
        <h2 class="hide-txt"><a href="<?php echo getlistname(3,'list_url');?>" title="日本动漫">经典动漫<em>Cartoon</em></a></h2>
        <a class="view-all" href="<?php echo getlistname(3,'list_url');?>">更多</a></div>
      <!-- // ui-title end -->
      <div class="ui-sort">
        <div class="sort-item sort-type">
          <ul class="sort-list fn-clear">
          <?php $type_id = 3; ?>
          <?php $catlist = D('Mcat')->list_cat(3); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = $catlist;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-type end -->
        </div>
        <!-- // sort-item end -->
        <div class="sort-item sort-star">
          <ul class="sort-list fn-clear">
          <?php $type_id = 3; ?>
          <?php if(is_array($s_area)): $i = 0; $__LIST__ = array_slice($s_area,0,8,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$area): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-area-<?php echo (urlencode($area)); ?>.html"><?php echo ($area); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-star end -->
        </div>
        <!-- // sort-item end -->
      </div>
      <!-- // ui-sort end -->
      <div class="ui-ranking">
        <h2><a href="<?php echo ff_mytpl_url('my_top_comic.html');?>">动漫排行榜</a></h2>
        <ul class="ranking-list">
               <?php $vod_dmphb_mx =ff_mysql_vod('cid:3;limit:10;order:vod_hits desc'); ?>
              <?php if(is_array($vod_dmphb_mx)): $i = 0; $__LIST__ = $vod_dmphb_mx;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($ppvod["vod_gold"]); ?></span><?php if(($i)  <  "4"): ?><em class="stress">0<?php echo ($i); ?>.</em>
           <?php else: ?><em><?php if(($i)  >  "9"): ?><em><?php echo ($i); ?>.</em><?php else: ?><em>0<?php echo ($i); ?>.</em><?php endif; ?></em><?php endif; ?><a target="_blank" href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo (msubstr($ppvod["vod_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // ranking-list end -->
      </div>
      <!-- // ui-ranking end -->
    </div>
    <!-- // tbmov-bar end -->
    <div class="tbmov-box">
      <div class="tbmov-commend">
        <ul class="img-list focus-list fn-clear">
          <?php $vod_ddongman_hot_hb_h =ff_mysql_vod('cid:3;prty:1;day:30;limit:2;order:vod_addtime desc'); ?>
         <?php if(is_array($vod_ddongman_hot_hb_h)): $i = 0; $__LIST__ = $vod_ddongman_hot_hb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="130" height="175"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <div class="play-txt">
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="nums"><?php echo ($ppvod["lastplay_name"]); ?></p>
              <p class="desc"><?php echo (msubstr($ppvod["vod_content"],0,43)); ?> …… </p>
              <dl class="item">
                <dt>演员：</dt>
                <dd><?php echo (ff_search_url($ppvod["vod_actor"])); ?></dd>
              </dl>
              <dl class="item">
                <dt>地区：</dt>
                <dd><?php echo ($ppvod["vod_area"]); ?></dd>
              </dl>
              <dl class="item">
                <dt>类型：</dt>
                <dd><a href="<?php echo ($ppvod["list_url"]); ?>" target="_blank"><?php echo ($ppvod["list_name"]); ?></a></dd>
              </dl>
              <dl class="item">
                <dt>年份：</dt>
                <dd><?php echo (($vod_year)?($vod_year):'未录入'); ?></dd>
              </dl>
            </div>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- focus-list end -->
      </div>
      <!-- // tbmov-commend end -->
      <div class="tbmov-random">
        <ul class="img-list fn-clear">
       <?php $vod_dongman_hot_hbb_h =ff_mysql_vod('cid:3;day:30;limit:3,5;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_dongman_hot_hbb_h)): $i = 0; $__LIST__ = $vod_dongman_hot_hbb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="mask"></label>
            <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
            <p class="star"><?php echo (ff_search_url($ppvod["vod_actor"])); ?></p>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- img-list end -->
      </div>
      <!-- // tbmov-random end -->
      <div class="tbmov-forecast fn-clear">
        <ul class="forecast-tab-nav fn-clear">
          <li class="title">动漫更新时间表：</li>
         <li id="dm1" onmouseover="setTab('dm',1,7);" <?php if($rq == 1): ?>class="current">今天<?php else: ?>>星期一<?php endif; ?></li>
          <li id="dm2" onmouseover="setTab('dm',2,7);" <?php if($rq == 2): ?>class="current">今天<?php else: ?>>星期二<?php endif; ?></li>
          <li id="dm3" onmouseover="setTab('dm',3,7);" <?php if($rq == 3): ?>class="current">今天<?php else: ?>>星期三<?php endif; ?></li>
          <li id="dm4" onmouseover="setTab('dm',4,7);" <?php if($rq == 4): ?>class="current">今天<?php else: ?>>星期四<?php endif; ?></li>
          <li id="dm5" onmouseover="setTab('dm',5,7);" <?php if($rq == 5): ?>class="current">今天<?php else: ?>>星期五<?php endif; ?></li>
          <li id="dm6" onmouseover="setTab('dm',6,7);" <?php if($rq == 6): ?>class="current">今天<?php else: ?>>星期六<?php endif; ?></li>
          <li id="dm7" onmouseover="setTab('dm',7,7);" <?php if($rq == 0): ?>class="current">今天<?php else: ?>>星期日<?php endif; ?></li>
        </ul>
        <!-- // forecast-tab-nav end -->
        <div id="con_dm_1"  class="forecast-tab-box <?php if($rq == 1): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
            <?php $vod_zhongyi_riqi_yi =ff_mysql_vod('cid:3;weekday:1;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_yi)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_yi;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?>  
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_2"   class="forecast-tab-box <?php if($rq == 2): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
         <?php $vod_zhongyi_riqi_er =ff_mysql_vod('cid:3;weekday:2;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_er)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_er;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_3" class="forecast-tab-box <?php if($rq == 3): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
                      <?php $vod_zhongyi_riqi_san =ff_mysql_vod('cid:3;weekday:3;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_san)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_san;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_4"   class="forecast-tab-box <?php if($rq == 4): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
                       <?php $vod_zhongyi_riqi_si =ff_mysql_vod('cid:3;weekday:4;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_si)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_si;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_5"  class="forecast-tab-box <?php if($rq == 5): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
                      <?php $vod_zhongyi_riqi_wu =ff_mysql_vod('cid:3;weekday:5;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_wu)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_wu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_6"  class="forecast-tab-box <?php if($rq == 6): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
                       <?php $vod_zhongyi_riqi_liu =ff_mysql_vod('cid:3;weekday:6;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_liu)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_liu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_dm_7"  class="forecast-tab-box <?php if($rq == 0): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
                       <?php $vod_zhongyi_riqi_qi =ff_mysql_vod('cid:3;weekday:7;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_qi)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_qi;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
      </div>
      <!-- // tbmov-assist end -->
    </div>
    <!-- // tbmov-box end -->
  </div>
  <!-- // ui-tbmov end -->
  <div class="ui-box ui-tbmov fn-clear" id="tbmov-fun">
    <div class="tbmov-bar">
      <div class="ui-title"><a class="ui-icon title-icon" href="<?php echo getlistname(4,'list_url');?>" title="综艺节目">综艺娱乐</a>
        <h2 class="hide-txt"><a href="<?php echo getlistname(4,'list_url');?>">综艺娱乐<em>Variety</em></a></h2>
        <a class="view-all" href="<?php echo getlistname(4,'list_url');?>">更多</a></div>
      <!-- // ui-title end -->
<div class="ui-sort">      

  <div class="sort-item sort-type">
          <ul class="sort-list fn-clear">
          <?php $type_id = 4; ?>
          <?php $catlist = D('Mcat')->list_cat(4); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = $catlist;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?> 
        </ul><!-- // sort-list#sort-type end -->
        </div>
         <div class="sort-item sort-star">
          <ul class="sort-list fn-clear">
          <?php $type_id = 4; ?>
          <?php if(is_array($s_area)): $i = 0; $__LIST__ = array_slice($s_area,0,8,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$area): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-area-<?php echo (urlencode($area)); ?>.html"><?php echo ($area); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>
          </ul>
          <!-- // sort-list#sort-star end -->
        </div>
        <!-- // sort-item end -->
      </div>
      <!-- // ui-sort end -->
      <div class="ui-ranking">
        <h2><a href="<?php echo ff_mytpl_url('my_top_variety.html');?>" title="综艺排行榜">综艺排行榜</a></h2>
        <ul class="ranking-list">
           <?php $vod_zyjj_hot=ff_mysql_vod('cid:4;limit:10;order:vod_hits desc'); ?>
              <?php if(is_array($vod_zyjj_hot)): $i = 0; $__LIST__ = $vod_zyjj_hot;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($ppvod["vod_gold"]); ?></span><?php if(($i)  <  "4"): ?><em class="stress">0<?php echo ($i); ?>.</em>
           <?php else: ?><em><?php if(($i)  >  "9"): ?><em><?php echo ($i); ?>.</em><?php else: ?><em>0<?php echo ($i); ?>.</em><?php endif; ?></em><?php endif; ?><a target="_blank" href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo (msubstr($ppvod["vod_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // ranking-list end -->
      </div>
      <!-- // ui-ranking end -->
    </div>
    <!-- // tbmov-bar end -->
    <div class="tbmov-box">
      <div class="tbmov-commend">
        <ul class="img-list focus-list fn-clear">
          <?php $vod_zhongyi_hot_hb_h =ff_mysql_vod('cid:4;limit:2;prty:1;day:7;order:vod_addtime desc'); ?>
         <?php if(is_array($vod_zhongyi_hot_hb_h)): $i = 0; $__LIST__ = $vod_zhongyi_hot_hb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="130" height="175"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <div class="play-txt">
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="nums"><?php echo ($ppvod["lastplay_name"]); ?></p>
              <p class="desc"><?php echo (msubstr($ppvod["vod_content"],0,40)); ?> …… </p>
              <dl class="item">
                <dt>演员：</dt>
                <dd><?php echo (ff_search_url($ppvod["vod_actor"])); ?></dd>
              </dl>
              <dl class="item">
                <dt>地区：</dt>
                <dd><?php echo ($ppvod["vod_area"]); ?></dd>
              </dl>
              <dl class="item">
                <dt>类型：</dt>
                <dd><a href="<?php echo ($ppvod["list_url"]); ?>" target="_blank"><?php echo ($ppvod["list_name"]); ?></a></dd>
              </dl>
              <dl class="item">
                <dt>年份：</dt>
                <dd><?php echo (($vod_year)?($vod_year):'未录入'); ?></dd>
              </dl>
            </div>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- focus-list end -->
      </div>
      <!-- // tbmov-commend end -->
      <div class="tbmov-random">
        <ul class="img-list fn-clear">
         <?php $vod_zhongyi_hot_hbb_hh =ff_mysql_vod('cid:4;limit:3,5;rder:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_hot_hbb_hh)): $i = 0; $__LIST__ = $vod_zhongyi_hot_hbb_hh;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="mask"></label>
            <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
            <p class="star"><?php echo (ff_search_url($ppvod["vod_actor"])); ?></p>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- img-list end -->
      </div>
      <!-- // tbmov-random end -->
      <div class="tbmov-forecast fn-clear">
        <ul class="forecast-tab-nav fn-clear">
          <li class="title">综艺更新时间表：</li>
          <li id="zy1" onmouseover="setTab('zy',1,7);" <?php if($rq == 1): ?>class="current">今天<?php else: ?>>星期一<?php endif; ?></li>
          <li id="zy2" onmouseover="setTab('zy',2,7);" <?php if($rq == 2): ?>class="current">今天<?php else: ?>>星期二<?php endif; ?></li>
          <li id="zy3" onmouseover="setTab('zy',3,7);" <?php if($rq == 3): ?>class="current">今天<?php else: ?>>星期三<?php endif; ?></li>
          <li id="zy4" onmouseover="setTab('zy',4,7);" <?php if($rq == 4): ?>class="current">今天<?php else: ?>>星期四<?php endif; ?></li>
          <li id="zy5" onmouseover="setTab('zy',5,7);" <?php if($rq == 5): ?>class="current">今天<?php else: ?>>星期五<?php endif; ?></li>
          <li id="zy6" onmouseover="setTab('zy',6,7);" <?php if($rq == 6): ?>class="current">今天<?php else: ?>>星期六<?php endif; ?></li>
          <li id="zy7" onmouseover="setTab('zy',7,7);" <?php if($rq == 0): ?>class="current">今天<?php else: ?>>星期日<?php endif; ?></li>
        </ul>
        <!-- // forecast-tab-nav end -->
        <div id="con_zy_1"  class="forecast-tab-box <?php if($rq == 1): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
            <?php $vod_zhongyi_riqi_yi =ff_mysql_vod('cid:4;weekday:1;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_yi)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_yi;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?>  
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_2"   class="forecast-tab-box <?php if($rq == 2): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
          <?php $vod_zhongyi_riqi_er =ff_mysql_vod('cid:4;weekday:2;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_er)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_er;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_3" class="forecast-tab-box <?php if($rq == 3): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
          <?php $vod_zhongyi_riqi_san =ff_mysql_vod('cid:4;weekday:3;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_san)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_san;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_4"   class="forecast-tab-box <?php if($rq == 4): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
          <?php $vod_zhongyi_riqi_si =ff_mysql_vod('cid:4;weekday:4;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_si)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_si;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_5"  class="forecast-tab-box <?php if($rq == 5): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
         <?php $vod_zhongyi_riqi_wu =ff_mysql_vod('cid:4;weekday:5;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_wu)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_wu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_6"  class="forecast-tab-box <?php if($rq == 6): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
          <?php $vod_zhongyi_riqi_liu =ff_mysql_vod('cid:4;weekday:6;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_liu)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_liu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
        <div id="con_zy_7"  class="forecast-tab-box <?php if($rq == 0): ?><?php else: ?>fn-hide<?php endif; ?>">
          <ul class="forecast-list fn-clear">
          <?php $vod_zhongyi_riqi_qi =ff_mysql_vod('cid:4;weekday:7;limit:8;order:vod_addtime desc'); ?>
        <?php if(is_array($vod_zhongyi_riqi_qi)): $i = 0; $__LIST__ = $vod_zhongyi_riqi_qi;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>" target="_blank" title="<?php echo ($ppvod["vod_name"]); ?>>"><?php echo ($ppvod["vod_name"]); ?></a></h3></li><?php endforeach; endif; else: echo "" ;endif; ?> 
          </ul>
          <!-- forecast-list end -->
        </div>
        <!-- // forecast-tab-box end -->
      </div>
      <!-- // tbmov-assist end -->
    </div>
    <!-- // tbmov-box end -->
  </div>
  <!-- // ui-tbmov end -->
  <div class="ui-box ui-tbmov fn-clear" id="tbmov-wei">
    <div class="tbmov-bar">
      <div class="ui-title"><a class="ui-icon title-icon" href="<?php echo getlistname(24,'list_url');?>" title="微电影">微电影</a>
        <h2 class="hide-txt"><a href="<?php echo getlistname(24,'list_url');?>" title="微电影排行榜2013">微电影<em>Micro Film</em></a></h2>
        <a class="view-all" href="<?php echo getlistname(24,'list_url');?>" title="好看的微电影">更多</a></div><!-- // ui-title end --><div class="ui-sort"><div class="sort-item"><ul class="sort-list fn-clear">
          <?php $type_id = 24; ?>
          <?php $catlist = D('Mcat')->list_cat(24); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = $catlist;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><li><h2><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></h2></li><?php endforeach; endif; else: echo "" ;endif; ?>  
        </ul><!-- // sort-list#sort-type end -->
        </div>
        <!-- // sort-item end -->
      </div>
      <!-- // ui-sort end -->
      <div class="ui-ranking">
        <h2><a href="<?php echo ff_mytpl_url('my_top_weidy.html');?>" title="微电影排行榜2013">微电影排行榜</a></h2>
        <ul class="ranking-list">
              <?php $vod_weidy_hot=ff_mysql_vod('cid:24;limit:8;order:vod_hits desc'); ?>
              <?php if(is_array($vod_weidy_hot)): $i = 0; $__LIST__ = $vod_weidy_hot;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><span><?php echo ($ppvod["vod_gold"]); ?></span><?php if(($i)  >  "3"): ?><em>0<?php echo ($i); ?>.</em><?php else: ?><em class="stress">0<?php echo ($i); ?>.</em><?php endif; ?><a target="_blank" href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo (msubstr($ppvod["vod_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- // ranking-list end -->
      </div>
      <!-- // ui-ranking end -->
    </div>
    <!-- // tbmov-bar end -->
    <div class="tbmov-box">
      <div class="tbmov-commend">
        <ul class="img-list focus-list fn-clear">
         <?php $vod_weidy_hot_hb_h =ff_mysql_vod('cid:24;prty:1;limit:2;order:vod_addtime desc'); ?>
         <?php if(is_array($vod_weidy_hot_hb_h)): $i = 0; $__LIST__ = $vod_weidy_hot_hb_h;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="130" height="175"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <div class="play-txt">
              <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
              <p class="nums"><?php echo ($ppvod["lastplay_name"]); ?></p>
              <p class="desc"><?php echo (msubstr($ppvod["vod_content"],0,40)); ?> …… </p>
              <dl class="item">
                <dt>演员：</dt>
                <dd><?php echo (ff_search_url($ppvod["vod_actor"])); ?></dd>
              </dl>
              <dl class="item">
                <dt>地区：</dt>
                <dd><?php echo ($ppvod["vod_area"]); ?></dd>
              </dl>
              <dl class="item">
                <dt>类型：</dt>
                <dd><a href="<?php echo ($ppvod["list_url"]); ?>" target="_blank"><?php echo ($ppvod["list_name"]); ?></a></dd>
              </dl>
              <dl class="item">
                <dt>年份：</dt>
                <dd><?php echo (($vod_year)?($vod_year):'未录入'); ?></dd>
              </dl>
            </div>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- focus-list end -->
      </div>
      <!-- // tbmov-commend end -->
      <div class="tbmov-random">
        <ul class="img-list fn-clear">
            <?php $vod_weidy_hot_hbb_hh =ff_mysql_vod('cid:24;day:7;limit:3,5;order:vod_hits desc'); ?>
        <?php if(is_array($vod_weidy_hot_hbb_hh)): $i = 0; $__LIST__ = $vod_weidy_hot_hbb_hh;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a class="play-img" href="<?php echo ($ppvod["vod_readurl"]); ?>"><img class="loading" width="110" height="150"  src="<?php echo ($root); ?>static/images/blank.png" data-original="<?php echo ($ppvod["vod_picurl"]); ?>" alt="<?php echo ($ppvod["vod_name"]); ?>" />
            <label class="mask"></label>
            <label class="text"><?php echo ($ppvod["lastplay_name"]); ?></label>
            <label class="score"><?php echo ($ppvod["vod_gold"]); ?></label>
            </a>
            <h3><a href="<?php echo ($ppvod["vod_readurl"]); ?>"><?php echo ($ppvod["vod_name"]); ?></a></h3>
            <p class="star"><?php echo (ff_search_url($ppvod["vod_actor"])); ?></p>
          </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>
        <!-- img-list end -->
      </div>
      <!-- // tbmov-random end -->
    </div>
    <!-- // tbmov-box end -->
  </div>
  <!-- // ui-tbmov end -->
  <div class="ui-sponsor">
    <!-- 广告位：index04 -->
<?php echo getadsurl('index_04_960');?>
  </div>
  <!-- // ui-sponsor end -->
  <div class="ui-box ui-tbmov" id="directory-focus">
    <div class="directory-item">
      <ul class="directory-list">
        <li>
          <dl class="tv">
            <dt><a href="<?php echo getlistname(2,'list_url');?>">电视剧</a></dt>
           <?php $type_id = 2; ?>
          <?php $catlist = D('Mcat')->list_cat(2); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = array_slice($catlist,0,18,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><dd><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></dd><?php endforeach; endif; else: echo "" ;endif; ?>  
          </dl>
        </li>
        <li>
          <dl class="movie">
            <dt><a href="<?php echo getlistname(1,'list_url');?>">电影</a></dt>
           <?php $type_id = 1; ?>
          <?php $catlist = D('Mcat')->list_cat(1); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = array_slice($catlist,0,15,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><dd><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></dd><?php endforeach; endif; else: echo "" ;endif; ?> 
          </dl>
        </li>
        <li>
          <dl class="cartoon">
            <dt><a href="<?php echo getlistname(3,'list_url');?>">动漫</a></dt>
           <?php $type_id = 3; ?>
          <?php $catlist = D('Mcat')->list_cat(3); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = array_slice($catlist,0,12,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><dd><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></dd><?php endforeach; endif; else: echo "" ;endif; ?> 
          </dl>
        </li>
        <li>
          <dl class="fun">
            <dt><a href="<?php echo getlistname(4,'list_url');?>">综艺娱乐</a></dt>
           <?php $type_id = 4; ?>
          <?php $catlist = D('Mcat')->list_cat(4); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = array_slice($catlist,0,14,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><dd><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></dd><?php endforeach; endif; else: echo "" ;endif; ?> 
          </dl>
        </li>
        <li>
          <dl class="wei">
            <dt><a href="<?php echo getlistname(24,'list_url');?>">微电影</a></dt>
           <?php $type_id = 24; ?>
          <?php $catlist = D('Mcat')->list_cat(24); ?>
            <?php if(is_array($catlist)): $i = 0; $__LIST__ = array_slice($catlist,0,9,true);if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): ++$i;$mod = ($i % 2 )?><dd><a href="<?php echo ($root); ?>vod-type-id-<?php echo ($type_id); ?>-mcid-<?php echo ($vo["m_cid"]); ?>.html" title="<?php echo ($vo["m_name"]); ?>"><?php echo ($vo["m_name"]); ?></a></dd><?php endforeach; endif; else: echo "" ;endif; ?> 

          </dl>
        </li>
      </ul>
    </div>
    <!-- // directory-list end -->
    <div class="directory-search fn-clear">
      <div class="ui-search">
          <form id="ffsearch" name="ffsearch"  method="POST" action="<?php echo ($root); ?>/search" onSubmit="return qrsearch();">
            <input type="text" id="wd" name="wd" class="search-input" value="请在此处输入影片片名或演员名称。" onfocus="if(this.value=='请在此处输入影片片名或演员名称。'){this.value='';}" onblur="if(this.value==''){this.value='请在此处输入影片片名或演员名称。';};" />
            <input type="submit" id="searchbutton"  class="search-button" value="" />
          </form>
      </div>
      <!-- // search End -->
      <div class="hotkeys"><?php echo ($hotkey); ?></div>
      <!-- // hotkeys End -->
    </div>
    <!-- // directory-search end -->
  </div>
  <!-- // ui-box end -->
  <div class="ui-box ui-tbmov" id="star-collection">
    <h3><span><a href="<?php echo ($url_special); ?>">所有专题</a></span>明星合集</h3>
    <ul class="txt-list fn-clear">
    <?php $special_new = ff_mysql_special('limit:0,16;order:special_id desc'); ?>
<?php if(is_array($special_new)): $i = 0; $__LIST__ = $special_new;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a href="<?php echo ($ppvod["special_readurl"]); ?>" target="_blank"><?php echo (msubstr($ppvod["special_name"],0,20)); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?>		
    </ul>
  </div>
  <!-- // star-collection end -->
  <div id="links-focus">
    <div class="ui-title fn-clear"><span>欢迎同类优质站点相互连接联系QQ：182377860!</span>
      <h2>友情链接</h2>
    </div>
    <div class="ui-cnt">
      <ul class="ul-link fn-clear">
        <?php if(is_array($list_link)): $i = 0; $__LIST__ = $list_link;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$ppvod): ++$i;$mod = ($i % 2 )?><li><a href="<?php echo ($ppvod["link_url"]); ?>" target="_blank"><?php echo ($ppvod["link_name"]); ?></a></li><?php endforeach; endif; else: echo "" ;endif; ?> 
         </ul>
    </div>
  </div>
  <!-- // links-focus end -->
</div>
<!-- // content end -->
<!-- // footer start -->
<div class="footer">
  <div class="layout">
    <div class="foot-nav"><a class="color" href="/faq/baiduplayer/F.html" target="_blank" title="使用帮助">使用帮助</a>-<a class="color" href="/faq/" target="_blank" title="使用帮助">常见问题</a>-<a href="<?php echo ($url_guestbook); ?>" target="_blank" title="给我留言">给我留言</a>-<a href="<?php echo ff_mytpl_url('my_new.html');?>" target="_blank" title="最新更新">最新更新</a>-<a href="<?php echo ($url_map_baidu); ?>" target="_blank" title="百度地图">百度地图</a>-<a href="<?php echo ff_mytpl_url('sitemap.html');?>" target="_blank" title="网站地图">网站地图</a>-<a href="<?php echo ($url_map_rss); ?>" target="_blank" title="RSS订阅">RSS订阅</a>-<?php echo ($tongji); ?></div>
    <!-- // foot-nav End -->
    <div class="copyright">
    <p><strong><a href="<?php echo ($siteurl); ?>">最新电影</a></strong>，<strong><a href="<?php echo ($siteurl); ?>">最新电视剧</a></strong>，<strong><a href="<?php echo ($siteurl); ?>">电影排行榜</a>，<a href="<?php echo ($siteurl); ?>">电视剧排行榜</a></strong></p>
      <p>本网站提供新电视剧和电影资源均系收集于各大视频网站，本网站只提供web页面服务，并不提供影片资源存储，也不参与录制、上传</p>
      <p>若本站收录的节目无意侵犯了贵司版权，请给<a href="mailto:newcnnet@gmail.com">newcnnet#gmail.com</a>邮箱地址来信，我们将在第一时间处理与回复,谢谢</p>
<p>Copyright &#169; 2012-2013 <a href="<?php echo ($siteurl); ?>"><?php echo ($sitename); ?></a> <a href="<?php echo ($siteurl); ?>"><font face="Verdana, Arial, Helvetica, sans-serif"><b><font color="#F60">27236</font><font color="#CC0000">.Com</font></b></font></a>.All Rights Reserved .</p>
    </div>
    <!-- // copyright End -->
    <!-- // maxBox End -->
  </div>
</div>
<!-- // footer end -->
<?php if(($model)  ==  "index"): ?><script type="text/javascript" src="<?php echo ($root); ?>static/js/index_top.js"></script>
<div class="globalRightMenu" id="globalRightMenu" style="position: fixed;_position:absolute;overflow:visible;top: 233px;">
	<p class="pAnchor clearfix">
		<strong class="AnchorBegin" onclick="javascript:scroller('tbmov-tv', 300);">电视剧</strong>
		<strong onclick="javascript:scroller('tbmov-movie', 300);">电影</strong>
		<strong onclick="javascript:scroller('tbmov-cartoon', 300);">动漫</strong>
		<strong onclick="javascript:scroller('tbmov-fun', 300);">综艺</strong>
		<strong onclick="javascript:scroller('tbmov-wei', 300);">微电影</strong>
		<strong class="AnchorEnd" onclick="javascript:scroller('star-collection', 300);">专题</strong>
	</p>
	<a href="javascript:window.scrollTo(0, 0);" target="_self" class="aGoBackTop m8" id="BackTop" style="display: none;;"></a>
</div>
<?php else: ?>
<script type="text/javascript" src="<?php echo ($root); ?>static/js/IE6Top.js"></script>
<div class="back-to-top" id="back-to-top"><a href="javascript:window.scrollTo(0, 0);" target="_self">Back to Top</a></div><?php endif; ?>
<script language="javascript" src="<?php echo ($root); ?>static/js/tbmovobj.js" type="text/javascript"></script>
<?php echo getadsurl('bdsc');?>
</body>
</html>