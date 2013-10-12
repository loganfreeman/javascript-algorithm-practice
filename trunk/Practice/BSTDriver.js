var BinarySearchTree = require('./binary-search-tree');
var DoublyLinkedList = require("./doubly-linked-list");
var tree = new BinarySearchTree();
tree.add(3).add(5).add(7).add(6).add(4).add(13).add(23).add(34);
//console.log(tree.toString());
var lca = tree.LCA(tree._root, 7, 13);
console.log("lowest common ancestor:"+lca.value);
//tree.prettyPrint();
var list = tree.treeToDoublyList(tree.root());




console.log("doubly linked list: "+list.toString());