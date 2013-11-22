<?php
class SearchAction extends HomeAction{
	public function vod(){
		$wd = trim($_GET['q']);
		$where = array();
		$where['vod_name'] = array('like',$wd.'%');
		$rs = D('Vod');
		//ËÑË÷Ìø×ª QQ£º182377860
        $list = $rs->field('vod_name,vod_id,vod_cid,vod_jumpurl')->where($where)->limit(10)->order('vod_hits_month desc')->select();
		if($list){
			foreach($list as $key=>$val){
				if(C('url_html')){
				$list[$key]['vod_url'] = ff_data_url('vod',$list[$key]['vod_id'],$list[$key]['vod_cid'],$list[$key]['vod_name'],1,$list[$key]['vod_jumpurl']);
	}
	           else{
	           $list[$key]['vod_url'] = UU('Home-vod/read', array('id'=>$val['vod_id']),false,true);}
			}
			//
			$this->ajaxReturn($list,"ok",1);
		}else{
			$this->ajaxReturn($list,"ok",0);
		}

		
	}
}
?>