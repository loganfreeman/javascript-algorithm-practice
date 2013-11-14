<?php


function add_inc_path($path)
{
	global $_incpath, $cfg;
	static $_incpath = array();
	$sep = PATH_SEPARATOR;

	if(!count($_incpath))
	{
		$_incpath = explode($sep, ini_get('include_path'));
		for($i = 0; $i < count($_incpath); $i++)
		{
			$_incpath[$i] = realpath($_incpath[$i]);
			if(empty($_incpath[$i])) unset($_incpath[$i]);
		}
	}

	$path = realpath($path);

	if(!in_array($path, $_incpath))
	{
		$_incpath[] = $path;
		ini_set('include_path', join($sep, $_incpath));
	}
	//echo '<pre>' . print_r($_incpath, true) . '</pre>';
}