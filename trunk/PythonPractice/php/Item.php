<?php
 
class Item {
    public $value, $key, $next = null;
 
    public function __construct($key, $value) {
        $this->key = $key;
        $this->value = $value;
    }
 
    public function next() {
        return $this->next;
    }
}