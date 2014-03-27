var fs = require('fs'), readline = require('readline');
var data = String(fs.readFileSync( 'data/message.txt' ));
var pattern = /Qit.Activity=([a-zA-Z0-9_. ,]*)/;
//console.log(data);
var m = data.match(new RegExp(pattern));
if(m.length > 0) console.log(m[1]);