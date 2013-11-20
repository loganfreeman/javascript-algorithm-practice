<?php
// +----------------------------------------------------------------------
// | Copyright (C) 2008-2012 OSDU.Net    www.osdu.net    admin@osdu.net
// +----------------------------------------------------------------------
// | Licensed: ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author:   左手边的回忆 QQ:858908467 E-mail:858908467@qq.com
// +----------------------------------------------------------------------

//系统初始化
require('./init.php');

define('MODULE_NAME', isset($_REQUEST['mod']) ? $_REQUEST['mod'] : '_unknown');
define('ACTION_NAME', isset($_REQUEST['act']) ? $_REQUEST['act'] : '_unknown');

$WebFTP = new WebFTP();

class WebFTP {
    private $FileFS = null;
    private $code = 200;
    private $mess = '';
    private $data = array();

    private $path  = '';
    private $name  = '';
    private $type  = '';

    public function __construct()
    {
        if(!Auth::is_login()){
            $this->code = 403;
            $this->mess = '登陆已过期，请重新登陆';
            $this->show();
        }

        if (!Auth::is_allow()){
            $this->code = 403;
            $this->mess = 'Request api not auth';
            //没有操作权限，请联系系统管理员！
            $this->show();
        }
     
        $act = 'on_' . ACTION_NAME;
        if (!method_exists($this, $act)){
            $this->code = 300;
            $this->mess = 'Request api not found';
            $this->show();
        }
        
        $this->FileFS = new FileFS();
        $this->init();
        $this->$act();
    }

    public function init(){
        // 基本参数
        $this->path  = gpc('fs-path');
        $this->name  = gpc('fs-name');
        $this->type  = gpc('fs-type');

        // 处理用户根目录
        $REAL_ROOT_PATH = realpath( C('ROOT_PATH') );
        $REAL_USER_PATH = realpath( C('ROOT_PATH').C('USER_PATH') );

        if (!empty($REAL_ROOT_PATH) && is_readable($REAL_ROOT_PATH)){
            $REAL_ROOT_PATH = str_replace('\\', '/', $REAL_ROOT_PATH.DS);
        } else{
            show(300, '文件系统错误，无法访问NFS根目录！' .$REAL_ROOT_PATH );
        }

        if (!empty($REAL_USER_PATH) && is_readable($REAL_USER_PATH)){
            $REAL_USER_PATH = str_replace('\\', '/', $REAL_USER_PATH.DS);
        } else{
            show(300, '文件系统错误，无法访问USER根目录！' .$REAL_USER_PATH );
        }

        if (strlen($REAL_USER_PATH) < strlen($REAL_ROOT_PATH)){
            show(300, '文件系统错误，无法访问ROOT-USER-Error！' .$REAL_USER_PATH );
        }

        // 定义文件系统根路径常量 - 唯一的全局路径
        define('REAL_ROOT_PATH', $REAL_ROOT_PATH);
        define('REAL_USER_PATH', $REAL_USER_PATH);
    }

    /**
     * 返回Ajax数据
     *
     */
    public  function show($json=true){
        show($this->code, $this->mess, $this->data, $json);
    }

    // 文件列表
    private function on_nlist(){
        $path  = $this->path;
        $otype = gpc('fs-otype');
        $osort = gpc('fs-osort');

        $this->code = $this->FileFS->nlist($path, $list) ? 200 : 300;
        $this->mess = $this->FileFS->error();

        if($otype && $osort && !empty($list)){
            //目录排序
            if(!empty($list['dirs'])){
                $arr = array();
                foreach($list['dirs'] as $k => &$v){
                    $arr['ext'][$k]   = $v['name'];
                    $arr['name'][$k]  = $v['name'];
                    $arr['size'][$k]  = $v['name'];
                    $arr['mtime'][$k] = $v['mtime'];
                }
                if('desc' == $osort){
                    array_multisort($arr[$otype], SORT_DESC, $list['dirs']);
                }else{
                    array_multisort($arr[$otype], SORT_ASC,  $list['dirs']);
                }
            }

            //文件排序
            if(!empty($list['files'])){
                $arr = array();
                foreach($list['files'] as $k => &$v){
                    $arr['ext'][$k]   = $v['ext'];
                    $arr['name'][$k]  = $v['name'];
                    $arr['size'][$k]  = $v['size'];
                    $arr['mtime'][$k] = $v['mtime'];
                }
                if('desc' == $osort){
                    array_multisort($arr[$otype], SORT_DESC, $list['files']);
                }else{
                    array_multisort($arr[$otype], SORT_ASC,  $list['files']);
                }
            }
        }
        unset($tmp, $arr);

        $data = array(
        'list' => $list,
        'path' => array( 'root' => '/', 'current' => $path, 'parent'  => str_replace('\\', '/', dirname($path)) )
        );
        $this->data = $data;
        $this->show();
    }

    // 重命名
    private function on_rename(){
        $path = $this->path;
        $old_name = gpc('fs-oname');
        $new_name = gpc('fs-nname');
        $old_path = get_dirname($path).'/'.$new_name;
        $new_path = get_dirname($path).'/'.$new_name;

        $this->code = $this->FileFS->rename($path, $new_path) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }

    // 粘贴
    private function on_paste(){
        $path  = gpc('path');
        $mode  = gpc('mode');
        $list  = gpc('list');

        if ('cut' == $mode) {
            $this->code = $this->FileFS->cut($path, $list, false, $this->data) ? 200 : 300;
            $this->mess = $this->FileFS->error();
        } else if('copy' == $mode){
            $this->code = $this->FileFS->copy($path, $list, false, $this->data) ? 200 : 300;
            $this->mess = $this->FileFS->error();
        } else{
            $this->code = 300;
            $this->mess = '请求参数错误';
        }
        $this->show();
    }

    private function on_pathinfo(){
        $path = $this->path;

        $this->code = $this->FileFS->pathinfo($path, $this->data) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }

    private function on_download(){
        $path = $this->path;
        $name = $this->name;
        $type = $this->type;

        $this->code = $this->FileFS->download($path, $name, $type) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->mess = "<script type='text/javascript'>top.$.dialog.alert('{$this->mess}', 'error')</script>";
        $this->show(false);
    }

    private function on_upload(){
        $path  = $this->path;
        $name  = gpc('name','r');
        $cover = gpc('fs-cover','r','intval');

        $this->code = $this->FileFS->upload($path, $name, $cover) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }

    // 文件夹压缩
    private function on_zip(){
        $path = $this->path;
        $name = $this->name;

        $this->code = 300;
        $this->mess = '免费体验版不支持 Zip压缩';
        $this->show();
    }

    // 文件解压
    private function on_unzip(){
        $path = $this->path;
        $name = $this->name;

        $this->code = 300;
        $this->mess = '免费体验版不支持 Zip解压';
        $this->show();
    }

    // 新建文件夹
    private function on_mkdir(){
        $path = $this->path;
        $type = $this->type;

        $act = "mk{$type}";
        $this->code = $this->FileFS->$act($path) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }

    // 删除文件夹、文件
    private function on_rmdir(){
        $path = $this->path;
        $type = $this->type;

        $act  = "rm{$type}";
        $this->code = $this->FileFS->$act($path, true, $this->data) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }
    private function on_chmod(){
        $path  = $this->path;
        $deep  = gpc('fs-deep');
        $chmod = gpc('fs-chmod', 'g', 'octdec');

        $this->code = $this->FileFS->chmod($path, $chmod, $deep, $this->data) ? 200 : 300;
        $this->mess = $this->FileFS->error();
        $this->show();
    }
    private function on_thumb(){
        $path = $this->path;
        $this->FileFS->thumb($path, 120, 100);
    }
}
exit();