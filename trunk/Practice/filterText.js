var jsonxml = require('jsontoxml');

xml2js = require('xml2js');

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

var groups = [];

var current = null;

var field = null;



data.forEach(function(line){

	var re = null;
	if((re = /DataStore.Name=(.*)/.exec(line))){

		current = {};
		groups.push(current);
		current['name'] = re[1];
		current['fields'] = [];
		current['children'] = [];
		
	}
	if(re = /DataStore.Parent=(.*)/.exec(line)){
		current['parent'] = re[1];
		addGroup(current, re[1]);
	}
	
	if(re = /Field.Name=(.*)/.exec(line)){
		field = {};
		field['name'] = re[1];
		
		current['fields'].push(field);
	}
	
	if(re = /Field.DataMapId=(.*)/.exec(line)){
		field['dm'] = re[1];
	}
	
	
});


function addGroup(group, parent){
	groups.some(function(g){
		if(g['name'] === parent){
			g['children'].push(group);
			return true;
		}
		return false;
	});
	

}

Object.keys(data_map_ids).forEach(function(id){
	//console.log(id);
});


console.log(JSON.stringify(groups));

var builder = new xml2js.Builder();
var xml = builder.buildObject(groups[0]);console.log(xml);

console.log(xml);