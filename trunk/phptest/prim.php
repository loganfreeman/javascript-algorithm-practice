<?php
// Prim's algorithm
 
define('INFINITY', 100000000);


class Prim
{

	public function run(&$graph, $start)
	{
		$q = array(); // queue
		$p = array(); // parent
	
		foreach (array_keys($graph) as $k) {
			$q[$k] = INFINITY;
		}
	
		$q[$start] = 0;
		$p[$start] = NULL;
	
		asort($q);
	
		while ($q) {
			// get the minimum value
			$keys = array_keys($q);
			$u = $keys[0];
	
			foreach ($graph[$u] as $v => $weight) {
				if ($weight > 0 && in_array($v, $keys) && $weight < $q[$v]) {
					$p[$v] = $u;
					$q[$v] = $weight;
				}
			}
	
			unset($q[$u]);
			asort($q);
		}
	
		return $p;
	}
}
 


 


