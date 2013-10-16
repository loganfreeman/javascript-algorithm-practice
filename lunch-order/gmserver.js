

var web = require('dingfan_new').app;
var CONFIG = require('config');

web.listen(CONFIG.httpport);
