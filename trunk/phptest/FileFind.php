<?php

include "File/Find.php";

$dir = "C:\wamp\bin\php\php5.3.8";
list($directories, $files) = File_Find::maptree($dir);

echo "Directories ";
print_r($directories);

echo "Files ";
print_r($files);
?>