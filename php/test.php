<?php
//네이버 지도API 좌표값 가져오기
$key = '2c45cce11d55f51980cc07ab6044dfd4';
$addr = '부산광역시 부산진구 양정1동';
$addr = preg_replace('/\s+/','',$addr); //공백제거
$url = 'http://openapi.map.naver.com/api/geocode.php?key='.$key.'&encoding=euc-kr&coord=latlng&query='.$addr;
$url = parse_url($url);

$fp = fsockopen($url['host'], 80, $errno, $errstr);
fputs($fp,"GET ".$url["path"].($url["query"] ? '?'.$url["query"] : '')." HTTP/1.0\r\n"); 
fputs($fp,"Host: " . $url["host"] . "\r\n"); 
fputs($fp,"User-Agent: PHP Script\r\n"); 
fputs($fp,"Connection: close\r\n\r\n");
$api_txt = fread($fp,1024);

preg_match('/<x>(.+)<\/x>/',$api_txt,$x);
preg_match('/<y>(.+)<\/y>/',$api_txt,$y);
$x = $x[1];
$y = $y[1];
echo $x;
echo $y;
?>