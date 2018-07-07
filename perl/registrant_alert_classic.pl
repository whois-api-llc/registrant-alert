#!/usr/bin/perl

use LWP::Protocol::https;         # From CPAN
use LWP::Simple;                  # From CPAN
use URI::Encode qw( uri_encode ); # From CPAN
use strict;                       # Good practice
use warnings;                     # Good practice

my $base_url = "https://www.whoisxmlapi.com/registrant-alert-api/search.php";
my $term1 = "whois";
my $exclude_term1 = "domain";
my $exclude_term2 = "news";
my $username = "Your registrant alert api username";
my $password = "Your registrant alert api password";

#######################
# Use a JSON resource #
#######################
print "JSON\n---\n".getData("json");

#######################
# Use an XML resource #
#######################
print "XML\n---\n".getData("xml");

#######################
# Getting the Data    #
#######################
sub getData {
    my $format = $_[0];
    my $url = "$base_url?username=$username&password=$password&term1=$term1"
            . "&exclude_term1=$exclude_term1&exclude_term2=$exclude_term2"
            . "&output_format=$format";
    $url = uri_encode($url);

    print "Get data by URL: $url\n";
    # 'get' is exported by LWP::Simple;
    my $object = get($url);

    die "Could not get $base_url!" unless defined $object;
    return $object
}