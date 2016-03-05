<?php
	require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	$que = "SELECT * FROM notice order by _id Limit 5 ";
	$qux = mysqli_query($connect,$que);
	
	$data['notice'] = array();	
	while( $result=mysqli_fetch_array($qux) ){
		$item['_id'] = $result['_id'];
		$item['title'] = urlencode( $result['title'] );
		$item['url'] = urlencode( $result['url'] );
		$item['date'] = urlencode( $result['date'] );
		array_push($data['notice'], $item);
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>