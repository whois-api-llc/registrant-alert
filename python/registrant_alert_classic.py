try:
    from urllib.request import urlopen, pathname2url
except ImportError:
    from urllib import pathname2url
    from urllib2 import urlopen

terms = 'test'
username = 'Your registrant alert api username'
password = 'Your registrant alert api password'
rows = '5'

url = 'http://www.whoisxmlapi.com/registrant-alert-api/search.php?'\
    + 'term1=' + pathname2url(terms)\
    + '&username=' + pathname2url(username)\
    + '&password=' + pathname2url(password)\
    + '&rows=' + pathname2url(rows)

print(urlopen(url).read().decode('utf8'))
