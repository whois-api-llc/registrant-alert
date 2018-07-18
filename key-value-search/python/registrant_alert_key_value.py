try:
    import http.client as http
except ImportError:
    import httplib as http

import json


def print_response(txt):
    response_json = json.loads(txt)
    print(json.dumps(response_json, indent=4, sort_keys=True))


username = 'Your registrant alert api username'
password = 'Your registrant alert api password'

payload = {
    'terms': [
        {
            'section': 'Registrant',
            'attribute': 'Email',
            'value': 'gmail.com',
            'exclude': False,
            'matchType': 'EndsWith'
        },
    ],
    'recordsCounter': False,
    'rows': 10,
    'username': username,
    'password': password
}

headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
}

conn = http.HTTPSConnection('www.whoisxmlapi.com')

conn.request('POST', '/registrant-alert-api/search.php',
             json.dumps(payload), headers)

response = conn.getresponse()
text = response.read()

print_response(text)
