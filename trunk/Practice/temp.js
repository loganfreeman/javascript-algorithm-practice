var s = "REGEXP_LIKE (c.code, ''^[0-9]*$'')";
var template = '%VIEW_DDL%';


console.log(fakeReplace(template, '%VIEW_DDL%', s));

s = s.replace(/\$/g,"$$$$");
//console.log(s);
//console.log(template);

console.log("--replaced--\n\n");

var ddl = template.replace('%VIEW_DDL%', s);
console.log(ddl);


function fakeReplace(str, substr, newstr) {
    return str.split(substr).join(newstr);
}

console.log("--fake replaced--\n\n");

var template1 =  "SET DEFINE OFF\n" +
"SET serveroutput ON\n" +
"DECLARE\n" +
"  view_name VARCHAR2(200) := '<OBJECT_NAME>';\n" +
"  cnt       NUMBER;\n" +
"  view_stm   VARCHAR2(200) := 'select count(*) from user_objects where object_name = :object_name';\n" +
"BEGIN\n" +
"  EXECUTE immediate view_stm INTO cnt USING view_name;\n" +
"  IF cnt > 0 THEN\n" +
"    DBMS_OUTPUT.PUT_LINE ('object name has been used');\n" +
"  ELSE\n" +
"    EXECUTE immediate '<VIEW_DDL>';\n" +
"  END IF;\n" +
"EXCEPTION\n" +
"WHEN OTHERS THEN\n" +
"  DBMS_OUTPUT.PUT_LINE('<OBJECT_NAME>' || ' creation encountered some issue!');\n" +
"  DBMS_OUTPUT.PUT_LINE (SQLCODE || ' ' || SQLERRM);\n" +
"  ROLLBACK;\n" +
"END;\n" +
"/";

console.log(template1);

var s = 'CREATE OR REPLACE FORCE VIEW "ADF"."ADF_CONCEPT"';

var view = "CREATE OR REPLACE FORCE VIEW \"EMY1\".\"LAB_PANELS_BON_SECOUR\" (\"PANEL_ID\", \"CPT_CODE\", \"SOURCE_ID\", \"LAB_CODE\", \"LAB_NAME\", \"PROCEDURE_ID\", \"CREATED_BY\", \"CREATION_DATE_TIME\", \"LAST_UPDATED_BY\", \"LAST_UPDATE_DATE_TIME\", \"LAST_UPDATING_PROCESS\") AS \n" +
"  (select \"PANEL_ID\",\"CPT_CODE\",\"SOURCE_ID\",\"LAB_CODE\",\"LAB_NAME\",\n" +
"\"PROCEDURE_ID\",\"CREATED_BY\",\"CREATION_DATE_TIME\",\"LAST_UPDATED_BY\",\"LAST_UPDATE_DATE_TIME\",\n" +
"\"LAST_UPDATING_PROCESS\"\n" +
"from lab_panels_map where source_id = 3)";

function findObjectName(s){
	var p = /CREATE OR REPLACE FORCE VIEW "(ADF|EMY1|UMLS|CONFIG_B|MASTER_B)"\."([A-Za-z0-9_]+)"/;
	var r = s.match(p);
	if(r){
		var t = s.replace(p, "CREATE OR REPLACE FORCE VIEW \"SCHENG\"\.\"$2\"")
		console.log(t)
		return [t, r[2]]
	}else{
		return null;
	}
}
findObjectName(s)
findObjectName('dddf')
findObjectName(view)

