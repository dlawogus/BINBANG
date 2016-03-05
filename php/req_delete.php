<?php
	require 'dbconfig.php';
	session_start();
	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}
	$user_id = $_GET['user_id'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);
	
	$que2 = "DELETE FROM service_req WHERE user_id='".$user_id."';";
	$qux2 = mysqli_query($connect,$que2);

	echo("<script>location.replace('../service_req.php');</script>");

?>