var fs = require('fs'), readline = require('readline');
var path ='data/update.sql';
var data = String(fs.readFileSync( path )).split('\n');

var output = [];

data.forEach(function(line){
	var line = line.replace('{', '\\{', 'g');
	line = line.replace('*', '\\*', 'g')
	output.push(line);
});
console.log(output.join('\n'));