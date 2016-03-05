<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$GU = $_POST['gu'];
  	$DONG = $_POST['dong'];
  	$JIBUN = $_POST['jibun'];
  	$BUILDING = $_POST['building'];
  	$PASSWORD = $_POST['building_pw'];
	$CALL1_WHO = $_POST['call1_who'];
	$CALL1 = $_POST['call1'];
	$CALL2_WHO = $_POST['call2_who'];
	$CALL2 = $_POST['call2'];
	$CALL3_WHO = $_POST['call3_who'];
	$CALL3 = $_POST['call3'];
	$BOILER = $_POST['boiler'];
	$ELEVATOR = $_POST['elevator'];
	$PARK = $_POST['park'];
	$COMMENTS = $_POST['comments'];

	//네이버 지도API 좌표값 가져오기
	$key = '2c45cce11d55f51980cc07ab6044dfd4';
	$addr = '부산광역시 '.$GU.' '.$DONG.' '.$JIBUN;
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

	$que = "INSERT INTO building VALUES ('','부산','".$GU."','".$DONG."','".$JIBUN."','부산 ".$GU." ".$DONG."','".$BUILDING."',
		'".$PASSWORD."','".$BOILER."','".$PARK."','".$ELEVATOR."','".$CALL1_WHO."','".add_hyphen($CALL1)."','".$CALL2_WHO."','".add_hyphen($CALL2)."',
		'".$CALL3_WHO."','".add_hyphen($CALL3)."','".$y."','".$x."','".$COMMENTS."');";
	$qux = mysqli_query($connect,$que);

	echo("<script>location.replace('../main.php');</script>");

	function add_hyphen($hp_no){
       return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
    }
?>
