require 'erb'
require 'json'
require 'net/https'
require 'uri'
require 'yaml' # only needed to print the returned result in a very pretty way

########################
# Fill in your details #
########################
username = 'Your registrant alert api username'
password = 'Your registrant alert api password'

term1 = 'test'
exclude_term1 = 'movie'
exclude_term2 = 'online'

#######################
# Use a JSON resource #
#######################
format = 'json'
url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php' \
      '?term1=' + ERB::Util.url_encode(term1) +
      '&username=' + ERB::Util.url_encode(username) +
      '&password=' + ERB::Util.url_encode(password) +
      '&output_format=' + ERB::Util.url_encode(format) +
      '&exclude_term1=' + ERB::Util.url_encode(exclude_term1) +
      '&exclude_term2=' + ERB::Util.url_encode(exclude_term2)

# Open the resource
buffer = Net::HTTP.get(URI.parse(url))

# Parse the JSON result
result = JSON.parse(buffer)

# Print out a nice informative string
puts "JSON:\n" + result.to_yaml + "\n"