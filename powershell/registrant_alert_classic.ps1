$uri = "https://www.whoisxmlapi.com/registrant-alert-api/search.php?"`
     + "term1=test"`
     + "&username=Your registrant alert api username"`
     + "&password=Your registrant alert api password"`
     + "&rows=5"
$uri = [uri]::EscapeUriString($uri)

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