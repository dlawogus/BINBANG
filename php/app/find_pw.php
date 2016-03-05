<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];
	$USER_PW = $_POST['user_pw'];
	$TOKEN = $_POST['token'];

	if( !isset($USER_ID) || !isset($USER_PW) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}
	
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	$que = "SELECT * FROM users WHERE user_id='".$USER_ID."';";
	$qux = mysqli_query($connect, $que);

	if(mysqli_num_rows($qux)==1){
		$que = "SELECT * FROM users WHERE user_id='".$USER_ID."' AND  token='".$TOKEN."';";
		$qux = mysqli_query($connect, $que);
		$result=mysqli_fetch_array($qux);
		if( mysqli_num_rows($qux)==1 ){
			$que = "UPDATE users set user_pw=password('".$USER_PW."') WHERE user_id='".$USER_ID."' AND  token='".$TOKEN."';";
			$qux = mysqli_query($connect, $que);

			$data['result'] = "1";
		}else{
			$data['result'] = "3";
		}
	}else{
		$data['result'] = "2";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>