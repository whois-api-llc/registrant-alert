########################
# Fill in your details #
########################

$username = "Your registrant alert api username"
$password = "Your registrant alert api password"

#######################
# Use a JSON resource #
#######################

$postParams = @{
        terms = @(
            @{
                section = "Admin"
                attribute = "name"
                value = "john"
                matchType = "anywhere"
                exclude = "false"
            }
        )
        recordsCounter = "false"
        username = $username
        password = $password
        output_format = "json"
        rows = 10
    } | ConvertTo-Json

$uri = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php'

$response = Invoke-WebRequest -Uri $uri -Method POST -Body $postParams `
            -ContentType "application/json"

echo $response.content | convertfrom-json | convertto-json -depth 10