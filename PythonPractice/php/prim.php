<?php
// Prim's algorithm
 
define('INFINITY', 100000000);
 
// the graph
$G = array(
    0 => array( 0,  4,  0,  0,  0,  0,  0,  0,  8),
    1 => array( 4,  0,  8,  0,  0,  0,  0,  0,  11),
    2 => array( 0,  8,  0,  7,  0,  4,  2,  0,  0),
    3 => array( 0,  0,  7,  0,  9,  14,  0,  0,  0),
    4 => array( 0,  0,  0,  9,  0,  10,  0,  0,  0),
    5 => array( 0,  0,  4,  14,  10,  0,  0,  2,  0),
    6 => array( 0,  0,  2,  0,  0,  0,  0,  6,  7),
    7 => array( 0,  0,  0,  0,  0,  2,  6,  0,  1),
    8 => array( 8,  11,  0,  0,  0,  0,  7,  1,  0),
);
 
function prim(&$graph, $start)
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
 
prim($G, 5);

print("Hello World");