load "BinarySearchTree.rb"

bst = BinarySearchTree.new(10)
bst.insert(11)
bst.insert(9)
bst.insert(5)
bst.insert(7)
bst.insert(18)
bst.insert(17)
# Demonstrating Different Kinds of Traversals
puts "In-Order Traversal:"
bst.inOrderTraversal
puts "Pre-Order Traversal:"
bst.preOrderTraversal
puts "Post-Order Traversal:"
bst.postOrderTraversal