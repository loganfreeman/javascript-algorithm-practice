<?php
class DateUtil {
	public static function format($val) {
		if ($val === '.') {
			$t = time();
		} else {
			if (preg_match('/^\d{4}$/', $val)) {
				// http://us2.php.net/manual/en/function.strtotime.php#98834
				$val = '01/01/' . $val;
			}

			if (preg_match("/(\d{1,2}) (\d{1,2}) (\d{4}) (\d{1,2}:\d{2}:\d{2})/", $val, $matches)) {
				// damn jxl, kept using spaces instead of / in mm/dd/yyyy H:i:s
				// 1 & 2 are switched
				$val = "{$matches[2]}/{$matches[1]}/{$matches[3]} {$matches[4]}";
			}

			$t = strtotime($val);

			// if an ambiguous year is parsed as over ten years in the future, knock it back a century
			$parsed_year = date('Y', $t);
			$current_year = date('Y');
			if ($parsed_year > $current_year + 10 && strpos($val, $parsed_year) === false) {
				$date = new DateTime('@' . $t);
				$date -> modify('-100 years');
				$t = $date -> format('U');
			}
		}
		
		return $t;
	}

}
$t = DateUtil::format("2003");
print($t."\n");

$date = new DateTime('@' . $t);
$formatted = $date -> format("Y-m-d H:i:s");

print($formatted."\n");
