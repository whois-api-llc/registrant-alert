var http = require('https');
var querystring = require('querystring');

var username = 'Your registrant alert api username';
var password = 'Your registrant alert api password';
var term1 = 'test';
var exclude_term1 = 'movie';
var exclude_term2 = 'online';
var format = 'json';

var url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php';

var params = {
    term1: term1,
    username: username,
    password: password,
    output_format: format,
    exclude_term1: exclude_term1,
    exclude_term2: exclude_term2
};

url = url + '?' + querystring.stringify(params);

http.get(url, function(response) {
    var str = '';
    response.on('data', function(chunk) {
        str += chunk;
    });
    response.on('end', function() {
        console.log(JSON.parse(str));
    });
}).end();