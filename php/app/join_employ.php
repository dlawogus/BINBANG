<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];
	$USER_PW = $_POST['user_pw'];
	$USER_NAME = $_POST['user_name'];
	$COMPANY_TITLE = $_POST['company_title'];
	$CEO_ID = $_POST['ceo_id'];
	$PHONE = $_POST['phone_number'];
	$CALL = $_POST['call_number'];
	$EMAIL = $_POST['email'];
	$TOKEN = $_POST['token'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($USER_ID)||!isset($USER_PW)||!isset($USER_NAME)||!isset($CEO_ID)||!isset($PHONE)||!isset($TOKEN)){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$user_id."';";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)>=1){	
		$data['result'] = "0";
	}else{
		$que1 = "INSERT INTO users VALUES ( '".$USER_ID."', password('".$USER_PW."'), '".$USER_NAME."', '".$COMPANY_TITLE."','".$CEO_ID."','".$PHONE."','".$CALL."','".$EMAIL."','1','".$TOKEN."', now(),now(),now());";
		$qux = mysqli_query($connect,$que1);
		if( $qux ){ 
		  $data['result'] = "1";
		}else{ 
		  $data['result'] = "0";
		}
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
	
?>