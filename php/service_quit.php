<?php
	require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
	session_start();
	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

	for($i=0;$i<count($check_id);$i++){ 
		//echo $check_id[$i]."</br>";
		$que = "SELECT * FROM company WHERE _id='".$check_id[$i]."';";
		$qux = mysqli_query($connect,$que);

		while( $row=mysqli_fetch_array($qux) ){
			$ceo_id = $row['ceo_id'];
			$q1 = "DELETE FROM users WHERE ceo_id='".$ceo_id."';";
			mysqli_query($connect,$q1);
		}

		$q = "DELETE FROM company WHERE _id='".$check_id[$i]."';";
		$qx = mysqli_query($connect,$q);

	}

	echo("<script>location.replace('../user.php');</script>");
?>