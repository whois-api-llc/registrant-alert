var https = require('https');

// Fill in your details
var username = 'Your registrant alert api username';
var password = 'Your registrant alert api password';

// Build the post string
var post_data ='{                      \
    "terms":[                          \
        {                              \
            "section": "Registrant",   \
            "attribute": "email",      \
            "value": "gmail.com",      \
            "exclude": "false",        \
            "matchType": "EndsWith"    \
        },                             \
        {                              \
            "section": "General",      \
            "attribute": "DomainName", \
            "value": ".com",           \
            "exclude": "false",        \
            "matchType": "EndsWith"    \
        }],                            \
    "recordsCounter": false,           \
    "outputFormat": "json",            \
    "username": "' + username + '",    \
    "password": "' + password +'",     \
    "rows": 20                         \
}';

// Set request options
var options = {
    hostname: 'www.whoisxmlapi.com',
    path: '/registrant-alert-api/search.php',
    port: 443,
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Content-Length': post_data.length
    },
    json: true
};

// Create request and get response
var req = https.request(options, function(res)  {
    var str = '';
    res.on('data', function(chunk) {
        str+=chunk;
    });
    res.on('end', function() {
        console.log(str);
    });

});

// Handle errors
req.on('error', function(e) {
    console.error(e);
});

// Send request
req.write(post_data);
req.end();