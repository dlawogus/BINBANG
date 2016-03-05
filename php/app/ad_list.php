<?php
	require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	$que = "SELECT * FROM advertise order by _id desc Limit 5";
	$qux = mysqli_query($connect,$que);
	
	$data['ad'] = array();	
	while( $result=mysqli_fetch_array($qux) ){
		$ad['_id'] = $result['_id'];
		$ad['url'] = urlencode( $result['url'] );
		$ad['call'] = urlencode( $result['call'] );
		$ad['date'] = urlencode( $result['date'] );
		array_push($data['ad'], $ad);
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>