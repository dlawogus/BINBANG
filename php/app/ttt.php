<?php
	require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);
	
	$que = "SELECT * FROM binbang";
	$qux = mysqli_query($connect,$que);
	while( $r=mysqli_fetch_array($qux) ){
		$T = $r['deposit'] + $r['monthly_rental']*100;
		if( $T < 5000 )
			$T = $r['deposit'] + $r['monthly_rental']*70;

		$que1="UPDATE binbang set tradeprice='".$T."' WHERE _id='".$r['_id']."'";
		mysqli_query($connect,$que1);
	}
?>