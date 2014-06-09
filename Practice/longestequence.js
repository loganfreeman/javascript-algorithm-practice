function importgrid(grid_string){
	var grid = [];
	var lines = grid_string.split('\n');
	lines.forEach(function(line){
		var digits = line.split(' ');
		
		grid.push(digits);
	})
	return grid;
	
}

function edge(from, to){
	this.from = from;
	this.to = to;
}

function node(v, i, j){
	this.v = v;
	this.i = i;
	this.j = j;
	this.edges = [];
}

var nodes = [];

var node_map = {};

function initNode(node){
	var i = node.i, j = node.j, v= node.v;
	var other;
	other = node_map[j-1 + n * (i-1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j + n * (i-1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j+1 + n * (i-1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j-1 + n * (i)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j+1 + n * (i)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j-1 + n * (i+1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j + n * (i+1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
	other = node_map[j+1 + n * (i+1)];
	if(other) {
		if(other.v > node.v){
			node.edges.push(other)
		}
	}
	
}



var grid_string = "8 2 4\n\
0 7 1\n\
3 7 9";
var grid = importgrid(grid_string);
console.log(grid);
var n = grid.length;
for(var i=0; i< n; i++){
	for(var j=0; j<n; j++){
		var value = grid[i][j];
		var current;
		nodes.push(current = new node(value, i, j));
		node_map[j + n*i]= current;
	}
}

Object.keys(node_map).forEach(function(key){
	console.log(key + ":" + node_map[key].v);
})

nodes.forEach(function(n){
	initNode(n);
})

nodes.forEach(function(n){
	n.edges.forEach(function(e){
		console.log(n.v + '=>' + e.v)
	})
})

function findLongestPath(node){
	if(node.edges.length > 0){
		var max_path = 0;
		node.edges.forEach(function(n){
			var subpath = findLongestPath(n)
			if(max_path <subpath ){
				max_path = subpath;
			}
		})
		node.path = max_path + 1;
	}else{
		node.path = 0;
	}
	return node.path;
}

nodes.forEach(function(n){
	findLongestPath(n);
})

nodes.forEach(function(n){
	console.log(n.i + ':' + n.j + ' ' + n.v + ' ' + n.path);
})

