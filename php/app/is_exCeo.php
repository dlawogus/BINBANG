<?php
	require 'dbconfig.php';
	$ceo_id = $_GET['ceo_id'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($ceo_id) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM company WHERE ceo_id='".$ceo_id."'; ";
	$qux = mysqli_query($connect, $que);

	if(mysqli_num_rows($qux)==1){
		$data['result'] = "1";
	}else{
		$data['result'] = "0";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>