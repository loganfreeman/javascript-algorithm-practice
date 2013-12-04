<?php
$handle = @fopen("Z:\\sites\\encperf\\db.txt", "r");
$db = array();
$count = 0;
if ($handle) {
	while (($buffer = fgets($handle, 4096)) !== false) {
		//echo $buffer;
		$buffer = trim($buffer);
		if($buffer != "" && !array_key_exists($buffer, $db)){
			$db[$buffer] = true;
			$count++;
		}

	}
	if (!feof($handle)) {
		echo "Error: unexpected fgets() fail\n";
	}
	fclose($handle);
	print($count."\n");
}

$handle = @fopen("Z:\\sites\\encperf\\db.txt", "w");
if($handle){
	$result = join("\n",array_keys($db));
	fwrite($handle, $result);
	fclose($handle);
}