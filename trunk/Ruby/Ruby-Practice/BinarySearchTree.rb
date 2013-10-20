class TreeNode
    attr_accessor :value, :left, :right
    
    # The Tree node contains a value, and a pointer to two children - left and right 
    # Values lesser than this node will be inserted on its left
    # Values greater than it will be inserted on its right
    def initialize val,left,right
        @value = val
        @left = left
        @right = right
    end
end

class BinarySearchTree
    
    # Initialize the Root Node
    def initialize val
        puts "Initializing with: " + val.to_s
        @root = TreeNode.new(val,nil,nil)   
    end

    # Pre-Order Traversal
    def preOrderTraversal(node= @root)
        return if (node == nil)
        preOrderTraversal(node.left)
        preOrderTraversal(node.right)
        puts node.value.to_s
    end

    # Post-Order Traversal
    def postOrderTraversal(node = @root)
        return if (node == nil)
        puts node.value.to_s
        postOrderTraversal(node.left)
        postOrderTraversal(node.right)
    end

    # In-Order Traversal : Displays the final output in sorted order
    # Display smaller children first (by going left)
    # Then display the value in the current node 
    # Then display the larger children on the right
    def inOrderTraversal(node = @root)
        return if (node == nil)
        inOrderTraversal(node.left)
        puts node.value.to_s
        inOrderTraversal(node.right)
    end

     
    # Inserting a value
    # When value > current node, go towards the right
    # when value < current node, go towards the left
    # when you hit a nil node, it means, the new node should be created there
    # Duplicate values are not inserted in the tree
    def insert(value)
        puts "Inserting :" + value.to_s
        current_node = @root
        while nil != current_node
            if (value < current_node.value) && (current_node.left == nil)
                current_node.left = TreeNode.new(value,nil,nil)
            elsif  (value > current_node.value) && (current_node.right == nil)
                current_node.right = TreeNode.new(value,nil,nil)
            elsif (value < current_node.value)
                current_node = current_node.left
            elsif (value > current_node.value)
                current_node = current_node.right
            else
                return
            end
        end
    end
end

