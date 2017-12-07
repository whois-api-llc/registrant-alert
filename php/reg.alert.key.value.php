<?php

$password = 'Your_registrant_alert_api_password';
$user = 'Your_registrant_alert_api_username';

$header = "Content-Type: application/json\r\nAccept: application/json\r\n";
$options = array('http' => array('method' => 'POST', 'header' => $header));
$url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php';

$options['http']['content'] = json_encode(
    array('terms'=>array(array('section'=>'Registrant','attribute'=>'Email',
                               'value' => 'a@b.com', 'exclude' => false)),
          'recordsCounter'=>false,'username'=>$user,'password'=>$password));

print(file_get_contents($url, false, stream_context_create($options)));