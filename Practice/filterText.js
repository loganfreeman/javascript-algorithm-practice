var path = './data/qit.txt';

var fs = require('fs'), readline = require('readline');
var dataMapPat=/Field.DataMapId=(\d+)/;

var rd = readline.createInterface({
	input : fs.createReadStream(path),
	output : process.stdout,
	terminal : false
});
var data_map_ids = Object.create(null);
rd.on('line', function(line) {
	//console.log(line);
	var result = dataMapPat.exec(line);
	
	if(result){
		data_map_ids[result[1]] = true;
	}
});


var data = String(fs.readFileSync( path )).split('\n');

var groups = Object.create(null);

var current = null;

data.forEach(function(line){
	var result = dataMapPat.exec(line);
	
	if(result){
		data_map_ids[result[1]] = true;
	}
	var re = null;
	if((re = /DataStore.Name=(.*)/.exec(line))){

		groups[re[1]] = {};
		groups[re[1]]['name'] = re[1];
		groups[re[1]]['fields'] = [];
		current = re[1];
	}
	if(re = /DataStore.Parent=(.*)/.exec(line)){
		groups[current]['parent'] = re[1];
	}
});


Object.keys(data_map_ids).forEach(function(id){
	//console.log(id);
});

console.log(Object.keys(data_map_ids).join(','));
console.log(JSON.stringify(groups));