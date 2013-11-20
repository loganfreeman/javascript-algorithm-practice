<?php
// +----------------------------------------------------------------------
// | Copyright (C) 2008-2012 OSDU.Net    www.osdu.net    admin@osdu.net
// +----------------------------------------------------------------------
// | Licensed: ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author:   左手边的回忆 QQ:858908467 E-mail:858908467@qq.com
// +----------------------------------------------------------------------

class Auth
{
    //验证是否登录
    static function is_login(){
        $uname = Session::get('uname');
        $tokey = Session::get('tokey');

        // Session
        if(!empty($uname) && !empty($tokey)){
            return true;
        }

        if('upload' === ACTION_NAME){
            $_COOKIE['webftp_uname'] = gpc('webftp_uname', 'r');
            $_COOKIE['webftp_tokey'] = gpc('webftp_tokey', 'r');
        }
        
        // Cookie
        $uname = Cookie::get('uname');
        $tokey = Cookie::get('tokey');
        if($uname && $tokey){
            $info   = self::get_user_data($uname);
            $tokey2 = self::get_tokey($info['uname'], $info['upawd']);
            if($tokey === $tokey2){
                $info['tokey'] = $tokey;
                $_SESSION = $info;
                redirect('./');
            }else{
                Cookie::del('uname');
                Cookie::del('tokey');
            }
        }
        return false;
    }


    // 验证是管理员
    static function is_admin(){
        $uname = Session::get('uname');
        return 'admin' === $uname ? true : false;
    }

    // 验证是否有权限
    static function is_allow(){
        $auth_arr = Session::get('uauth');
        return (in_array('*', $auth_arr) || in_array(ACTION_NAME, $auth_arr)) ? true : false;
    }


    // 登出
    static function login_out(){
        Session::set('tokey', null);
        Session::set('uname', null);
        Session::set('uauth', null);
        Cookie::del('uname');
        Cookie::del('tokey');
    }

    // 本地登陆认证
    static function login_check(){
        $uname  = trim($_POST['uname']);
        $upawd  = trim($_POST['upawd']);
        $uhash  = trim($_POST['uhash']);
        $uhash2 = Session::get('uhash');

        if(empty($uhash) || $uhash != $uhash2){
            Session::set('login_error', '验证码非法！');
            redirect('login.php?act=in');
        }

        $user_info = self::get_user_data($uname);
        if ($user_info['uname'] == $uname && $user_info['upawd'] == md5($upawd)){
            Session::set('uname', $uname);
            Session::set('upath', $user_info['upath']);
            Session::set('uauth', $user_info['uauth']);

            $tokey = self::get_tokey($uname, md5($upawd));
            Cookie::set('uname', $uname);
            Cookie::set('tokey', $tokey);
            Session::set('tokey', $tokey);

            Session::set('login_error', null);
            redirect('./');
        } else{
            Session::set('login_error', '账户不存在或密码有误！');
            redirect('login.php?act=in');
        }
    }

    static function get_tokey($uname, $upawd){
        return md5($uname .C('AUTH.KEY'). $upawd);
    }

    /********* AUTH.TYPE' 为1(local:本地程序认证)是有效 ***************/
    //获取User本地数据路径
    static function get_user_path($uname){
        return DATA_PATH.'user/'.md5($uname).'.php';
    }

    //添加、修改本地管理员
    static function add_user_data($uname, $upawd, $upath, $uauth=array('*')){
        if(empty($uname) || empty($upawd)){return false;}
        $data = self::encode_user_data(array('uname'=>$uname,'upawd'=>$upawd, 'upath'=>trim($upath), 'uauth'=>$uauth));
        return file_put_contents(self::get_user_path($uname), $data);
    }

    //删除本地管理员
    static function del_user_data($uname){
        $file = self::get_user_path($uname);
        return is_file($file) ? unlink($file) : true;
    }

    //获取管理员信息
    static function get_user_data($uname, $ufile=''){
        $data = '';
        $file = $ufile ? $ufile : self::get_user_path($uname);
        if(is_file($file)){
            $data = file_get_contents($file);
            $data = self::decode_user_data($data);
        }
        if(!is_array($data)){
            $data = array('uname'=>'xxx','upawd'=>'xxx','upath'=>'xxx', 'uauth'=>'xxx');
        }
        return $data;
    }

    //更新管理员密码
    static function update_user_password(){
        $uname = Session::get('uname');
        $uinfo = self::get_user_data($uname);

        if(!empty($uname) && $uname == $uinfo['uname']){
            $uinfo['upawd'] = md5(gpc('newpasswd','r'));
            if(self::add_user_data($uinfo['uname'],$uinfo['upawd'],$uinfo['upath'],$uinfo['uauth'])){
                show(200,'密码已更新,请谨记新密码：<font color="red">'. gpc('newpasswd','r') .'</font>');
            }
        }
        show(300,'更新失败：<font color="red">你可能无权更改此项设置！</font>');
    }

    static function encode_user_data($data){
        $encode = '<?php if(!defined("CORE_ROOT")){die("Forbidden Access");}?>';
        $data   = $encode.serialize($data);
        return $data;
    }
    static function decode_user_data($data){
        $decode = '<?php if(!defined("CORE_ROOT")){die("Forbidden Access");}?>';
        return unserialize(str_replace($decode, '',$data));
    }
}



class Cookie
{
    // 判断Cookie是否存在
    static function is_set($name){
        return isset($_COOKIE[C('COOKIE_PREFIX').$name]);
    }

    // 获取某个Cookie值
    static function get($name, $encode=false){
        $value = Cookie::is_set($name) ? $_COOKIE[C('COOKIE_PREFIX').$name] : null;
        $value = $encode ? unserialize(base64_decode($value)) : $value;
        return $value;
    }

    // 设置某个Cookie值
    static function set($name, $value, $encode=false, $expire='',$path='',$domain=''){
        $path   = empty($path)   ? C('COOKIE_PATH') : $path;
        $expire = empty($expire) ? C('COOKIE_EXPIRE') : $expire;
        $domain = empty($domain) ? C('COOKIE_DOMAIN') : $domain;

        $expire = empty($expire) ? 0 : time()+$expire;
        $value  =  $encode ? base64_encode(serialize($value)) : $value;
        setcookie(C('COOKIE_PREFIX').$name, $value, $expire, $path, $domain);
        $_COOKIE[C('COOKIE_PREFIX').$name] = $value;
    }

    // 删除某个Cookie值
    static function del($name){
        Cookie::set($name, '', false, -3600*8);
        unset($_COOKIE[C('COOKIE_PREFIX').$name]);
    }

    // 清空Cookie值
    static function clear(){
        $_COOKIE = array();
    }
}

class Session
{

    //启动Session
    static function start(){
        if(isset($_POST[self::name()]) && !empty($_POST[self::name()])){
            self::id(trim($_POST[self::name()]));
        }
        if(isset($_POST[C('COOKIE_PREFIX').'username']) && !empty($_POST[C('COOKIE_PREFIX').'username'])){
            $_COOKIE[C('COOKIE_PREFIX').'username'] = trim($_POST[C('COOKIE_PREFIX').'username']);
        }
        if(isset($_POST[C('COOKIE_PREFIX').'tokey']) && !empty($_POST[C('COOKIE_PREFIX').'tokey'])){
            $_COOKIE[C('COOKIE_PREFIX').'tokey'] = trim($_POST[C('COOKIE_PREFIX').'tokey']);
        }
        session_start();
        Session::setExpire(C('COOKIE_EXPIRE'));
    }

    //设置Session 过期时间
    static function setExpire($time){
        setcookie(Session::name(), Session::id(), time() + $time, '/');
    }

    //设置或者获取当前Session name
    static function name($name = null){
        return is_null($name) ? session_name() : session_name($name) ;
    }

    //设置或者获取当前SessionID
    static function id($id = null){
        return is_null($id) ? session_id() : session_id($id);
    }

    //检查Session 值是否已经设置
    static function is_set($name){
        $name = explode('.', $name);
        if(isset($name[2])) {
            return isset($_SESSION[$name[0]][$name[1]][$name[2]]);
        }elseif(isset($name[1])){
            return isset($_SESSION[$name[0]][$name[1]]);
        }elseif(isset($name[0])){
            return isset($_SESSION[$name[0]]);
        }else{
            return false;
        }
    }

    //取得Session 值
    static function get($name){
        $name = explode('.', $name);
        if(isset($name[2]) && isset($_SESSION[$name[0]][$name[1]][$name[2]])) {
            return $_SESSION[$name[0]][$name[1]][$name[2]];
        }elseif(isset($name[1]) && isset($_SESSION[$name[0]][$name[1]])){
            return $_SESSION[$name[0]][$name[1]];
        }elseif(isset($name[0]) && isset($_SESSION[$name[0]])){
            return $_SESSION[$name[0]];
        }else{
            return NULL;
        }
    }

    //设置Session 值
    static function set($name, $value=null){
        $name = explode('.', $name);
        if(isset($name[2])) {
            $_SESSION[$name[0]][$name[1]][$name[2]] = $value;
            if(is_null($value)){unset($_SESSION[$name[0]][$name[1]][$name[2]]);}
        }elseif(isset($name[1])){
            $_SESSION[$name[0]][$name[1]] = $value;
            if(is_null($value)){unset($_SESSION[$name[0]][$name[1]]);}
        }elseif(isset($name[0])){
            $_SESSION[$name[0]] = $value;
            if(is_null($value)){unset($_SESSION[$name[0]]);}
        }
        return $value;
    }

    //清空Session
    static function clear(){
        $_SESSION = array();
    }

    //销毁Session
    static function destroy(){
        unset($_SESSION);
        session_destroy();
    }
}