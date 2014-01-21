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
data.forEach(function(line){
		console.log(line);
	var result = dataMapPat.exec(line);
	
	if(result){
		data_map_ids[result[1]] = true;
	}
});


Object.keys(data_map_ids).forEach(function(id){
	//console.log(id);
});

console.log(Object.keys(data_map_ids).join(','));