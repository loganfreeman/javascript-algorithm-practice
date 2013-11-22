<?php
function getvideo($id,$pid=2){
	$hz='_tudou';
	$pidarrs[] = '2';
	$pidarrs[] = '3';
	$pidarrs[] = '4';
	$content=get_curl_contents('http://v2.tudou.com/v.action?it='.$id.'&hd=2');
	$pido = '';
	if(strpos($content,'brt="4"'))$pido = '3';
	if(!$pido){if(strpos($content,'brt="3"'))$pido = '2';}
	if(!$pido)$pido = '1';
	switch($pido){
		case '1':
			$qvars=__BQ__.'_'.$id.$hz;
			break;
		case '2':
			$qvars=__BQ__.'_'.$id.$hz.'|'.__GQ__.'_'.$id.$hz;
			break;
		case '3':
			$qvars=__BQ__.'_'.$id.$hz.'|'.__GQ__.'_'.$id.$hz.'|'.__CQ__.'_'.$id.$hz;
			break;
		default:
			$qvars=$id.$hz;
			break;
	}
	$pid=min($pid,$pido);
	preg_match('~brt="'.$pidarrs{$pid-1}.'">(http[s]{0,1}://.*)<~iUs',$content,$vurl);
	$urllist['urls'][0]['url']=strtr($vurl[1],array("&amp;" => "&"));
	$urllist['vars']='{h->3}{q->tflvbegin}{a->'.$qvars.'}{f->'.__HOSTURL__.'?url=[$pat'.($pid-1).']}';
	return $urllist;
}
?>