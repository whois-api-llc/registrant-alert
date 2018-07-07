<?php

$term = 'test';
$username = 'Your registrant alert api username';
$password = 'Your registrant alert api password';
$rows = '5';

$url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php?'
     . 'term1=' . urlencode($term)
     . '&username=' . urlencode($username)
     . '&password=' . urlencode($password)
     . '&rows=' . urlencode($rows);

print(file_get_contents($url));