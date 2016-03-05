<?php
//require 'session_ini.php';
session_start();
session_destroy();
$data['result'] = "1";
$output = json_encode($data);
$output = urldecode($output);
echo $output;
?>