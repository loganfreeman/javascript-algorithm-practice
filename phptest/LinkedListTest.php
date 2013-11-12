<?php
require_once 'LinkedList.php';
 
class LinkedListTest extends PHPUnit_Framework_TestCase {
    public function testLinkedList() {
    	print("Hello World Shanhong\n");
        $list = new LinkedList;
        $data = array('foo','bar');
        foreach ($data as $k => $v) $list->append($k, $v);
        $start = $list->first;
        while($start != null){
        	print($start->value."\n");
        	$start = $start->next;
        }
 
        $this->assertEquals($data[0], $list->first->value);
        $this->assertEquals($data[1], $list->last->value);
        $this->assertNull($list->last->next());
 
        // Prepend data
        $prepend = array('a','b');
        foreach (array_reverse($prepend) as $k => $v) $list->prepend($k, $v);
 
        $item = $list->first;
        foreach (array_merge($prepend, $data) as $value) {
            $this->assertEquals($value, $item->value);
            $item = $item->next();
        }
    }
}