<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];

	if( !isset($USER_ID) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$USER_ID."'; ";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)>=1){	
		$data['result'] = "0";
	}else{
		$data['result'] = "1";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
	
?>