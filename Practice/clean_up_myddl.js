var fs = require('fs'), readline = require('readline'), sanitize = require("sanitize-filename");

var path = 'data/myddl.sql';

fs.readdirSync('data/sql').forEach(function(fileName) {
	fs.unlinkSync('data/sql/'+fileName);
});

function unique(filename){
	var count = 1;
	while(fs.existsSync(filename)){
		filename = filename + count.toString();
		count++;
	}
	return filename;
}

var data = String(fs.readFileSync( path )).split('\n');
var qit = false;
var end = false;
var output = [];
var count = 0;
var files = {};
var filename;
data.forEach(function(line){
	//console.log(line);
	var result;
	if((result = /^\s*--\s*(.*)\s*$/.exec(line))){
		//console.log(result[1]);
		filename = result[1];
		if(!files[filename]){
			files[filename] = [];
		}
	}else if(!(line.trim().length == 0) && files[filename]){
		files[filename].push(line)
	}
});
for(var f in files){
	//console.log(f)
	//console.log(files[f].join("\n"));
	//console.log("\n\n");
	
	var tofile = unique('data/sql/' + sanitize(f));
	fs.writeFile(tofile, files[f].join("\n"), function(err) {
	    if(err) {
	        console.log(err);
	    } else {
	        //console.log(tofile + " was saved!");
	    }
	}); 
}