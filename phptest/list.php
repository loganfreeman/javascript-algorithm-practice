<?php
$output = "";
exec("ls -al",$output);
foreach($output as $line) {
	echo $line . "<br>\n";
}