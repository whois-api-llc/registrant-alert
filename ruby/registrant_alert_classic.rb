require 'open-uri'
require 'json'
require 'yaml' # only needed to print the returned result in a very pretty way

########################
# Fill in your details #
########################
username = "Your registrant alert api username"
password = "Your registrant alert api password"

term1 = "test"
exclude_term1 = 'movie'
exclude_term2 = 'online'

#######################
# Use a JSON resource #
#######################
format = "JSON"
url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php?' +
    'term1=' + term1 +
    '&username=' + username +
    '&password=' + password +
    '&output_format=' + format +
    '&exclude_term1=' + exclude_term1 +
    '&exclude_term2=' + exclude_term2

# Open the resource
buffer = open(URI.encode(url)).read

# Parse the JSON result
result = JSON.parse(buffer)

# Print out a nice informative string
puts "JSON:\n" + result.to_yaml + "\n"