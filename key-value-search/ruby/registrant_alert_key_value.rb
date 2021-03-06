require 'json'
require 'net/https'
require 'openssl'
require 'uri'
require 'yaml' # only needed to print the returned result in a very pretty way

########################
# Fill in your details #
########################
username = 'Your registrant alert api username'
password = 'Your registrant alert api password'

#######################
# Use a JSON resource #
#######################
format = 'json'
url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php'

content = {
  terms: [
    {
      section: 'Registrant',
      attribute: 'Name',
      matchType: 'BeginsWith',
      value: 'Mark',
      exclude: false
    },
    {
      section: 'Technical',
      attribute: 'Country',
      matchType: 'Anywhere',
      value: 'US',
      exclude: true
    }
  ],
  recordsCounter: false,
  username: username,
  password: password,
  outputFormat: format,
  rows: 10
}

uri = URI.parse(url)
http = Net::HTTP.new(uri.host, uri.port)

# Connect using ssl
http.use_ssl = true
http.verify_mode = OpenSSL::SSL::VERIFY_NONE
request = Net::HTTP::Post.new(uri.request_uri)

# Set headers
request.add_field('Content-Type', 'application/json')
request.add_field('Accept', 'application/json')
request.body = content.to_json

# Get response
response = http.request(request)

# Print pretty parsed json
puts JSON.parse(response.body).to_yaml