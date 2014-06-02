var fs = require('fs'), readline = require('readline');
var path = 'data/aap-stage.sql';
var data = String(fs.readFileSync(path)).split('<VIEW>');

var output = [];
var toFile = 'data/createView.sql';

var toFakeFile = 'data/createFakeView.sql';

var originalDDL = 'data/originalView.sql';

try{
	fs.unlinkSync(toFile);
}catch(e){
	
}

try{
	fs.unlinkSync(originalDDL);
}catch(e){
	
}

function fakeReplace(str, substr, newstr) {
    return str.split(substr).join(newstr);
}

var create_view_template = "";


var template = "SET DEFINE OFF\n" +
		"SET serveroutput ON\n\
DECLARE\n\
BEGIN\n\
 Execute immediate '<VIEW_DDL>';\n\
" + 
"EXCEPTION\n\
WHEN OTHERS THEN\n\
  " +
  "DBMS_OUTPUT.PUT_LINE('<VIEW_DDL>');\n" +
  "DBMS_OUTPUT.PUT_LINE (SQLCODE || ' ' || SQLERRM);\n\
  ROLLBACK;\n\
END;\n\
/\n\n";

var createViewTpl =  "SET DEFINE OFF\n" +
"SET serveroutput ON\n" +
"DECLARE\n" +
"  view_name VARCHAR2(200) := '<OBJECT_NAME>';\n" +
"  cnt       NUMBER;\n" +
"  view_stm   VARCHAR2(200) := 'select count(*) from user_objects where object_name = :object_name';\n" +
"BEGIN\n" +
"    EXECUTE immediate '<VIEW_DDL>';\n" +
"EXCEPTION\n" +
"WHEN OTHERS THEN\n" +
"  DBMS_OUTPUT.PUT_LINE('<OBJECT_NAME>' || ' creation encountered some issue!');\n" +
"  DBMS_OUTPUT.PUT_LINE (SQLCODE || ' ' || SQLERRM);\n" +
"  ROLLBACK;\n" +
"END;\n" +
"/\n\n\n";

function findObjectName(s){
	var p = /CREATE OR REPLACE FORCE VIEW "(ADF|EMY1|UMLS|CONFIG_B|MASTER_B)"\."([A-Za-z0-9_]+)"/;
	var r = s.match(p);
	if(r){
		var t = s.replace(p, "CREATE OR REPLACE FORCE VIEW \"SCHENG\"\.\"$2\"")
		//console.log(t)
		return [t, r[2]]
	}else{
		return null;
	}
}

data.forEach(function(line) {
	fs.appendFile(originalDDL, line);
    line = line.replace(/\n\s*\n/g, '\n');
    line = line.replace(/\$/g,"$$$$");
	line = line.replace(new RegExp("'", "g"), "''");



	//var regex = new RegExp('MASTER_B', "g");

	//line = line.replace(regex, "SCHENG");
	line = line.trim();
	line = line.replace(/;$/, '');
	
	var pair = findObjectName(line);
	//console.log(line);
	if(pair != null){
		var ddl = createViewTpl.replace(new RegExp('<VIEW_DDL>', 'g'), pair[0]);
		ddl = ddl.replace(new RegExp('<OBJECT_NAME>', 'g'), pair[1])
		//console.log(ddl);
		
		//var fakeDDL = fakeReplace(template, '<VIEW_DDL>', line);
		fs.appendFile(toFile, ddl, function(err) {
		    if(err) {
		        console.log(err);
		    } else {
		        console.log(toFile + " was saved!");
		    }
		}); 
	}else{
		console.log('view ddl is not valid');
	}


	
	/*
	fs.appendFile(toFakeFile, fakeDDL, function(err) {
	    if(err) {
	        console.log(err);
	    } else {
	        //console.log(toFile + " was saved!");
	    }
	}); 
	*/
});
