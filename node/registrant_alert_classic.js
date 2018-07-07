var http = require('https');

const username = 'Your registrant alert api username';
const password = 'Your registrant alert api password';
const term1 = 'test';
const exclude_term1 = 'movie';
const exclude_term2 = 'online';
const format = 'JSON';

var url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php?'
    + 'term1=' + term1
    + '&username=' + username
    + '&password=' + password
    + '&output_format=' + format
    + '&exclude_term1=' + exclude_term1
    + '&exclude_term2=' + exclude_term2;

http.get(encodeURI(url), function(response) {
    var str = '';
    response.on('data', function(chunk) {
        str += chunk;
    });
    response.on('end', function() {
        console.log(str);
    });
}).end();