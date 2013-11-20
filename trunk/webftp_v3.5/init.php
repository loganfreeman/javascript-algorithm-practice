<?php
// +----------------------------------------------------------------------
// | Copyright (C) 2008-2012 OSDU.Net    www.osdu.net    admin@osdu.net
// +----------------------------------------------------------------------
// | Licensed: ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author:   左手边的回忆 QQ:858908467 E-mail:858908467@qq.com
// +----------------------------------------------------------------------

//error_reporting(0);
//set_time_limit(0);
header('Content-type: text/html; charset=utf-8');

//应用主目录
define('ES_ROOT', str_replace(array('\\','//'), '/', dirname(__FILE__)).'/');
define('ES_PATH', str_replace(array('\\','//'), '/', dirname($_SERVER['SCRIPT_NAME']).'/'));

// 系统核心目录、数据目录
define('CORE_ROOT', ES_ROOT.'core/');
define('DATA_PATH', ES_ROOT.'data/');
define('DS', '/');

define('SYS_WIN', (strtoupper(substr(PHP_OS, 0,3)) === 'WIN'));
if(version_compare(PHP_VERSION, '5.4.0', '<')){
    ini_set('magic_quotes_runtime', 0);
    define('MAGIC_QUOTES_GPC', get_magic_quotes_gpc() ? true : false);
}else{
    define('MAGIC_QUOTES_GPC', false);
}

// 加载系统函数库
require(CORE_ROOT.'functions.php');
require(CORE_ROOT.'Auth.class.php');
require(CORE_ROOT.'FileFS.class.php');

set_error_handler('error_handler_fun');
if(function_exists('date_default_timezone_set')){
    date_default_timezone_set('PRC');
}

//  开启SESSION
session_set_cookie_params(3600 * 24 * 7);
session_name('webftp_sessid');
session_start();


$_CONFIG = array(
    
    /* 本地初始根目录 */
    'ROOT_PATH' => './storage/', // 系统存储根路径，请勿随意修改
    'USER_PATH' => '/??/',       // 用户存储虚拟路径，请勿随意修改
    
    /* 文件上传配置 */
    'UPLOAD' => array(
        'max_file_size' => 1024, // 上传单个文件限制大小，单位MB
        'chunk_size'    => 2,    // 文件分块大小，单位MB
        'filters' => array(
            array('All Files (*.rar;*.htm;*.jpg;*.pdf;*.doc;*.*)', '*,rar,zip,tar,gz,7z,php,js,css,htm,html,xml,jpg,png,gif,bmp,ico,pdf,doc,ppt,xls,docx,pptx,xlsx,wps,et,dps'),
            array('Archive Files (*.rar;*.zip;*.tar;*.gz;*.7z)', 'rar,zip,tar,gz,7z'), 
            array('Script Files (*.php;*.js;*.css;*.htm;*.xml)', 'php,js,css,htm,html,xml'), 
            array('Images Files (*.jpg;*.png;*.gif;*.bmp;*.ico)', 'jpg,png,gif,bmp,ico'), 
            array('Document Files (*.doc;*.ppt;*.xls;*.pdf;wps;*.et;*.dps)', 'pdf,doc,ppt,xls,docx,pptx,xlsx,wps,et,dps'),
        )
    ),
    
    /**  以下参数请谨慎设置 一般保留默认即可 **/
    'SYSTEM_NAME'           => 'WebFTP',
    'SYSTEM_VERSION'        => 'v3.5 旗舰版',
    
    'LOG_EXCEPTION_RECORD'  => true, //记录日志
    'LOG_EXCEPTION_TYPE'    => 'EMERG,ALERT,CRIT,ERR,WARNING,NOTICE,INFO,DEBUG',
    'LOG_FILE_SIZE'         => 2097152, // 默认2MB
    'LOG_SAVE_TYPE'         => 2,       // 1：只保留最新日志,2: 保留所有日志，
    
    /* Cookie设置 和 Session共用 */
    'COOKIE_EXPIRE'         => 3600*24,
    'COOKIE_DOMAIN'         => '',
    'COOKIE_PATH'           => '',
    'COOKIE_PREFIX'         => 'webftp_'
);


//初始化配置参数
C($_CONFIG);
if(isset($_SESSION['upath'])){
    C('ROOT_PATH', './storage/');
    C('USER_PATH', $_SESSION['upath']);
}