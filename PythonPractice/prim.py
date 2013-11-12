# prim - minimum spanning tree
# Tim Wilson, 2-25-2002

#A = adjacency matrix, u = vertex u, v = vertex v
def weight(A, u, v):
    return A[u][v]

#A = adjacency matrix, u = vertex u
def adjacent(A, u):
    L = []
    for x in range(len(A)):
        if A[u][x] > 0 and x <> u:
            L.insert(0,x)
    return L

#Q = min queue
def extractMin(Q):
    q = Q[0]
    Q.remove(Q[0])
    return q

#Q = min queue, V = vertex list
def decreaseKey(Q, K):
    for i in range(len(Q)):
        for j in range(len(Q)):
            if K[Q[i]] < K[Q[j]]:
                s = Q[i]
                Q[i] = Q[j]
                Q[j] = s

#V = vertex list, A = adjacency list, r = root
def prim(V, A, r):
    u = 0
    v = 0

    # initialize and set each value of the array P (pi) to none
    # pi holds the parent of u, so P(v)=u means u is the parent of v
    P=[None]*len(V)

    # initialize and set each value of the array K (key) to some large number (simulate infinity)
    K = [999999]*len(V)

    # initialize the min queue and fill it with all vertices in V
    Q=[0]*len(V)
    for u in range(len(Q)):
        Q[u] = V[u]

    # set the key of the root to 0
    K[r] = 0
    decreaseKey(Q, K)    # maintain the min queue

    # loop while the min queue is not empty
    while len(Q) > 0:
        u = extractMin(Q)    # pop the first vertex off the min queue

        # loop through the vertices adjacent to u
        Adj = adjacent(A, u)
        for v in Adj:
            w = weight(A, u, v)    # get the weight of the edge uv

            # proceed if v is in Q and the weight of uv is less than v's key
            if Q.count(v)>0 and w < K[v]:
                # set v's parent to u
                P[v] = u
                # v's key to the weight of uv
                K[v] = w
                decreaseKey(Q, K)    # maintain the min queue
    return P

A = [ [0,  4,  0,  0,  0,  0,   0,  8,  0],
      [4,  0,  8,  0,  0,  0,   0, 11,  0],
      [0,  8,  0,  7,  0,  4,   0,  0,  2],
      [0,  0,  7,  0,  9, 14,   0,  0,  0],
      [0,  0,  0,  9,  0, 10,   0,  0,  0],
      [0,  0,  4, 14, 10,  0,   2,  0,  0],
      [0,  0,  0,  0,  0,  2,   0,  1,  6],
      [8, 11,  0,  0,  0,  0,   1,  0,  7],
      [0,  0,  2,  0,  0,  0,   6,  7,  0]]
V = [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]

P = prim(V, A, 0)
print P

[None, 0, 5, 2, 3, 6, 7, 0, 2]


from collections import defaultdict
from heapq import *
 
def prim2( nodes, edges ):
    conn = defaultdict( list )
    for n1,n2,c in edges:
        conn[ n1 ].append( (c, n1, n2) )
        conn[ n2 ].append( (c, n2, n1) )
 
    mst = []
    used = set( nodes[ 0 ] )
    usable_edges = conn[ nodes[0] ][:]
    heapify( usable_edges )
 
    while usable_edges:
        cost, n1, n2 = heappop( usable_edges )
        if n2 not in used:
            used.add( n2 )
            mst.append( ( n1, n2, cost ) )
 
            for e in conn[ n2 ]:
                if e[ 2 ] not in used:
                    heappush( usable_edges, e )
    return mst
 
#test
nodes = list("ABCDEFG")
edges = [ ("A", "B", 7), ("A", "D", 5),
          ("B", "C", 8), ("B", "D", 9), ("B", "E", 7),
      ("C", "E", 5),
      ("D", "E", 15), ("D", "F", 6),
      ("E", "F", 8), ("E", "G", 9),
      ("F", "G", 11)]
 
print "prim:", prim2( nodes, edges )
#output
#prim: [('A', 'D', 5), ('D', 'F', 6), ('A', 'B', 7), ('B', 'E', 7), ('E', 'C', 5), ('E', 'G', 9)]