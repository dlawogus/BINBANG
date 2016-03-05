<?php
	require 'dbconfig.php';
	$user_id = $_GET['user_id'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($user_id) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$user_id."'; ";
	$qux = mysqli_query($connect, $que);

	if(mysqli_num_rows($qux)==1){
		$data['result'] = "0";
	}else{
		$data['result'] = "1";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>