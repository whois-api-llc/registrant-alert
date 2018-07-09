var http = require('https');
var querystring = require('querystring');

const username = 'Your registrant alert api username';
const password = 'Your registrant alert api password';
const term1 = 'test';
const exclude_term1 = 'movie';
const exclude_term2 = 'online';
const format = 'JSON';

var url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php?';

var params = {
    term1: term1,
    username: username,
    password: password,
    output_format: format,
    exclude_term1: exclude_term1,
    exclude_term2: exclude_term2
};

url = url + querystring.stringify(params);

http.get(url, function(response) {
    var str = '';
    response.on('data', function(chunk) {
        str += chunk;
    });
    response.on('end', function() {
        console.log(str);
    });
}).end();