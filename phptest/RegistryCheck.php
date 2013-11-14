<?php
$keyConst = 'HKEY_CURRENT_USER';
 
// backslash is used as an escape so it must be escaped itself
$key = "Software\Intel";
 
// open the registry key HKCUSoftwareIntel
if (!($reg = @reg_open_key($keyConst, $key))) {
    throw new Exception("Cannot access registry.");
}

 
reg_close_key($reg);