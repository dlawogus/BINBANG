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
		$q = "UPDATE users SET is_available=0 WHERE user_id='".$check_id[$i]."';";
		$qx = mysqli_query($connect,$q);
	}

	echo("<script>location.replace('../user2.php');</script>");
?>