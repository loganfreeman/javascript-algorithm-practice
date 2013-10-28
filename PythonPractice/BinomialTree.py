#!/usr/bin/python
# -*- coding: UTF-8 -*-
"""
 BinomialQueue.py
 Meldable priority queues
 Written by Gregoire Dooms and Irit Katriel
"""

class LinkError(Exception): pass
class EmptyBinomialQueueError(Exception): pass
"""
A binomial tree of order 0 is a single node.
A binomial tree of order k has a root node whose children are roots of binomial trees of orders k−1, k−2, ..., 2, 1, 0
A binomial tree of order k has 2k nodes, height k.
Each binomial tree in a heap obeys the minimum-heap property.

Because of its unique structure, a binomial tree of order k can be constructed from two trees of order k−1 trivially by attaching one of them as the leftmost child of root of the other one. 
If only one of the heaps contains a tree of order j, this tree is moved to the merged heap. If both heaps contain a tree of order j, the two trees are merged to one tree of order j+1 so that the minimum-heap property is satisfied. 
Because each binomial tree in a binomial heap corresponds to a bit in the binary representation of its size, there is an analogy between the merging of two heaps and the binary addition of the sizes of the two heaps.
Whenever a carry occurs during addition, this corresponds to a merging of two binomial trees during the merge.
"""

class BinomialTree:
    "A single Binomial Tree"
    def __init__(self, value):
        "Create a one-node tree. value is the priority of this node"
        self.value = value  
        self.rank = 0
        self.children = []

    def link(self, other_tree):
        """Make other_tree the son of self. Both trees must have the
        same rank, and other_tree must have a larger minimum priority
        """
        if self.rank != other_tree.rank:
            raise LinkError()
        if self.value > other_tree.value:
            raise LinkError()
        self.children.append(other_tree)
        self.rank += 1 
        return True

    def str(self, indent = 0):
        return (" "*indent +
                "rank: %d value: %d"%(self.rank, self.value)+
                "\n"+"".join(child.str(indent+2)
                             for child in self.children)
               )
    
    def __str__(self):
        return self.str()
"""
A binomial heap is implemented as a collection of binomial trees


"""        
class BinomialQueue:
    """  A Meldable priority Queue """
    def __init__(self,infinity=1e300):
        """
        Create an empty Binomial Queue.
        Since a queue can hold any comparable data type, we need to know
        at initialization time what an "infinity" element looks like.
        """
        self.infinity = infinity
        self.parent = self
        self.trees = []
        self.elements = 0        
        self.min = self.infinity
        self.min_tree_rank = -1

    def __capacity(self):
        return 2**len(self.trees) - 1

    def __resize(self):
        while self.__capacity() < self.elements:
            self.trees.append(None)

    def __add_tree(self,new_tree):
        " Insert new_tree into self"
        self.elements = self.elements + 2**new_tree.rank
        self.__resize()
        while self.trees[new_tree.rank] is not None:
            if self.trees[new_tree.rank].value < new_tree.value:
                new_tree, self.trees[new_tree.rank] = \
                 self.trees[new_tree.rank], new_tree     # swap
            r = new_tree.rank
            new_tree.link(self.trees[r])
            self.trees[r] = None
        self.trees[new_tree.rank] = new_tree
        if new_tree.value <= self.min:
            self.min = new_tree.value
            self.min_tree_rank = new_tree.rank
        
    def meld(self, other_queue):
        "Insert all elements of other_queue into self "
        for tree in other_queue.trees:
            if tree is not None:
                self.__add_tree(tree)
    # Inserting a new element to a heap can be done by simply creating a new heap containing only this element and then merging it with the original heap. 
    def insert(self, value):
        "Insert value into self "
        tree = BinomialTree(value)        
        self.__add_tree(tree)

    def get_min(self):
        "Return the minimum element in self"
        return self.min
    # To delete the minimum element from the heap, first find this element, remove it from its binomial tree, and obtain a list of its subtrees. 
    # Then transform this list of subtrees into a separate binomial heap by reordering them from smallest to largest order. Then merge this heap with the original heap. 
    def delete_min(self):
        "Delete the minumum element from self "
        if not self:
            raise EmptyBinomialQueueError()
        to_remove = self.trees[self.min_tree_rank]
        self.trees[to_remove.rank] = None
        self.elements = self.elements - 2**to_remove.rank        
        for child in to_remove.children:
            self.__add_tree(child)
            
        self.min = self.infinity
        for tree in self.trees:
            if tree is not None:
                if tree.value <= self.min:
                    self.min = tree.value
                    self.min_tree_rank = tree.rank
                    
 
    def __nonzero__(self):
        return self.elements

    def __str__(self):
        s = """elements: %d min: %s
        min_tree_rank: %d
        tree vector: """ % (self.elements, str(self.min), self.min_tree_rank)
        s += " ".join("10"[tree is None] for tree in self.trees)
        s += "\n"
        s += "".join(str(tree) for tree in self.trees if tree is not None)
        return s
    
    def __len__(self):
        return self.elements
    
    def __iadd__(self,other):
        if type(other) == type(self):
            self.meld(other)
        else:
            self.insert(other)
        return self
            
    
def run_test():
    inf = 2e300
    N = 10

    Q1 = BinomialQueue(inf)
    Q2 = BinomialQueue(inf)
        
    print Q1    
    print "-------------------------------------------"        
    Q1 += 20  # Same as  Q1.insert(20)
    Q1.insert(1)    
    Q1.insert(5)
    Q1.insert(10)

    print Q1
    print "-------------------------------------------"        
    Q2.insert(2)
    Q2.insert(22)
    Q2.insert(12)

    print Q2
    print "-------------------------------------------"        

    Q1 += Q2   # Same as  Q1.meld(Q2)

    print Q1
    print "-------------------------------------------"            
    while Q1:
        print "Q1.min = ", Q1.min
        Q1.delete_min()

if __name__ == "__main__":
    run_test()


    
