$url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php'

$username = 'Your registrant alert api username'
$password = 'Your registrant alert api password'
$term1 = 'test'
$rows = 5

$uri = $url`
     + '?term1=' + [uri]::EscapeDataString($term1)`
     + '&username=' + [uri]::EscapeDataString($username)`
     + '&password=' + [uri]::EscapeDataString($password)`
     + '&rows=' + [uri]::EscapeDataString($rows)

#######################
# Use a JSON resource #
#######################

$j = Invoke-WebRequest -Uri $uri
echo "JSON:`n---" $j.content "`n"

#######################
# Use an XML resource #
#######################

$uri = $uri + "&output_format=xml"

$j = Invoke-WebRequest -Uri $uri
echo "XML:`n---" $j.content