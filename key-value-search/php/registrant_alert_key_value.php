<?php

$user = 'Your registrant alert api username';
$password = 'Your registrant alert api password';

$header = "Content-Type: application/json\r\nAccept: application/json\r\n";
$url = 'https://www.whoisxmlapi.com/registrant-alert-api/search.php';

$options = array(
    'http' => array(
        'method' => 'POST',
        'header' => $header,
        'content' => json_encode(
            array(
                'terms' => array(
                    array(
                        'section' => 'Registrant',
                        'attribute' => 'Email',
                        'value' => 'a',
                        'exclude' => false,
                        'matchType' => 'BeginsWith'
                    )
                ),
                'recordsCounter' => false,
                'rows' => 10,
                'username' => $user,
                'password' => $password
            )
        )
    )
);

print(file_get_contents($url, false, stream_context_create($options)));