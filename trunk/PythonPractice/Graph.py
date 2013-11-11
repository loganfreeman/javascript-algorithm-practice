from collections import defaultdict
class Vertex:
    def __init__(self,key):
        self.id = key
        self.connectedTo = {}

    def addNeighbor(self,nbr,weight=0):
        self.connectedTo[nbr] = weight

    def __str__(self):
        return str(self.id) + ' connectedTo: ' + str([x.id for x in self.connectedTo])

    def getConnections(self):
        return self.connectedTo.keys()

    def getId(self):
        return self.id

    def getWeight(self,nbr):
        return self.connectedTo[nbr]
    
class Edge:
    def __init__(self, src, dest, weight):
        self.src = src
        self.dest = dest
        self.weight = weight
    def __str__(self):
        return "%s => %s (%s)" % (self.src, self.dest, self.weight,)
    

class Graph:
    def __init__(self):
        self.vertList = {}
        self.numVertices = 0
        self.edgeList = []

    def addVertex(self,key):
        self.numVertices = self.numVertices + 1
        newVertex = Vertex(key)
        self.vertList[key] = newVertex
        return newVertex

    def getVertex(self,n):
        if n in self.vertList:
            return self.vertList[n]
        else:
            return None
    def nVertices(self):
        return len(self.vertList)

    def __contains__(self,n):
        return n in self.vertList

    def addEdge(self,f,t,cost=0):
        if f not in self.vertList:
            nv = self.addVertex(f)
        if t not in self.vertList:
            nv = self.addVertex(t)
        self.vertList[f].addNeighbor(self.vertList[t], cost)
        self.edgeList.append(Edge(f, t, cost))

    def getVertices(self):
        return self.vertList.keys()

    def __iter__(self):
        return iter(self.vertList.values())

def find(parent, x):
    if parent[x] == -1:
        return x
    return find(parent, parent[x])
def union(parent, x, y):
    xset = find(parent, x)
    yset = find(parent, y)
    parent[xset] = yset
       
def isCycle(graph):
    assert isinstance(graph, Graph)
    parent = defaultdict(lambda: -1)
    for i, edge in enumerate(graph.edgeList):
        print i, ":", edge
        x = find(parent, edge.src)
        y = find(parent, edge.dest)
        if x == y:
            return 1
        union(parent, x, y)
    return 0

graph = Graph()
graph.addEdge(3, 6, 15)
graph.addEdge(6, 9, 1)
graph.addEdge(3, 9, 1)

ret = isCycle(graph)
print ret

        
    
    
