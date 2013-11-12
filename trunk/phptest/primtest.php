<?php
require_once 'prim.php';

class PrimTest extends PHPUnit_Framework_TestCase
{
	public function testprim(){
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
		
		$prim = new Prim();
		$prim->run($G, 5);
	}
}