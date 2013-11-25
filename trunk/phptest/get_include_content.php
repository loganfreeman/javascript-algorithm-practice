<?php
$string = get_include_contents('somefile.php');

function get_include_contents($filename) {
    if (is_file($filename)) {
        ob_start();
        include $filename;
        return ob_get_clean();
    }
    return false;
}
function is_includeable($filename, $returnpaths = false) {
	$include_paths = explode(PATH_SEPARATOR, ini_get('include_path'));

	foreach ($include_paths as $path) {
		$include = $path.DIRECTORY_SEPARATOR.$filename;
		if (is_file($include) && is_readable($include)) {
			if ($returnpaths == true) {
				$includable_paths[] = $path;
			} else {
				return true;
			}
		}
	}

	return (isset($includeable_paths) && $returnpaths == true) ? $includeable_paths : false;
}

?>