<?php 

define('CR', "\r");          // Carriage Return: Mac
define('LF', "\n");          // Line Feed: Unix
define('CRLF', "\r\n");      // Carriage Return and Line Feed: Windows
function normalize($s) {
	// Normalize line endings using Global
	// Convert all line-endings to UNIX format
	$s = str_replace(CRLF, LF, $s);
	$s = str_replace(LF, CRLF, $s);
	// Don't allow out-of-control blank lines
	//$s = preg_replace("/\n{2,}/", LF . LF, $s);
	return $s;
}

function find_all_files($dir) 
{ 
    $root = scandir($dir); 
    foreach($root as $value) 
    { 
        if($value === '.' || $value === '..') {continue;} 
        if(is_file("$dir/$value")) {
        	$ext = pathinfo($value, PATHINFO_EXTENSION);
        	if("js" == $ext){
        		$content = file_get_contents("$dir/$value");
        		$content = normalize($content);
        		file_put_contents("$dir/$value", $content);
        		$result[]="$dir/$value";
        	}
        	continue;
        } 
        foreach(find_all_files("$dir/$value") as $value) 
        { 
            $result[]=$value; 
        } 
    } 
    return $result; 
} 

$files = find_all_files("E:/mosaic-client/Web/src/main/webapp/lib");
var_dump($files);
?>