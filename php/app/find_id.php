<?php
	require 'dbconfig.php';
	$PHONE = $_POST['phone'];
	$NAME = $_POST['name'];

	if( !isset($PHONE) || !isset($NAME) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}
	
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	$que = "SELECT * FROM users WHERE user_name='".$NAME."' AND phone='".$PHONE."';";
	$qux = mysqli_query($connect, $que);
	$r=mysqli_fetch_array($qux);
	$ID= $r['user_id'];

	if(mysqli_num_rows($qux)==1){
		$data['result'] = "1";
		$data['user_id'] = $ID;
	}else{
		$data['result'] = "2";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>