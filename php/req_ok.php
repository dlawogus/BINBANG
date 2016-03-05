<?php
	require 'dbconfig.php';
	session_start();
	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}
	$user_id = $_GET['user_id'];
	$period = $_GET['period'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);


	$que = "SELECT * FROM users WHERE user_id='".$user_id."';";
	$qux = mysqli_query($connect,$que);
	$re_due = mysqli_fetch_array($qux);
	
	//남은 날짜 더하기 
	$pay_start = $re_due['pay_start']; 
	$pay_end = $re_due['pay_end']; 


	//$newDate = date("Y-m-d", mktime(0,0,0,date("m"), date("d"), date("Y")));
	//$time = strtotime($date); 
	//$final = date("Y-m-d", strtotime( "+".$period." month", $time));
	//$final_1 = date("Y-m-d", strtotime( "+".$ddy." day", $final));
	
	$Today = date('Y-m-d');
	$diff_day = date_diff($pay_end,$Today);
	if($diff_day >= 0) {		//아직 서비스 기간 남음
	   	//echo "날짜가 큰 날임.";
		$time = strtotime($pay_end);
		$final = date("Y-m-d", strtotime( "+".$period." month", $time));
		$que1 = "UPDATE users SET is_available=1, pay_end='".$final."' WHERE user_id='".$user_id."';";
		$qux1 = mysqli_query($connect,$que1);

	} else {				//서비스 기간 만료
	   	//echo "날짜가 작은 날임.";
	   	$time = strtotime($Today);
		$final = date("Y-m-d", strtotime( "+".$period." month", $time));
		$que1 = "UPDATE users SET is_available=1, pay_start=now() ,pay_end='".$final."' WHERE user_id='".$user_id."';";
		$qux1 = mysqli_query($connect,$que1);
	}

	$que2 = "DELETE FROM service_req WHERE user_id='".$user_id."';";
	$qux2 = mysqli_query($connect,$que2);


	//echo $final;
	echo("<script>location.replace('../service_req.php');</script>");

	function date_diff($date1,$date2) {
   		$tmp_date1 = explode("-",$date1);
   		$tmp_date2 = explode("-", $date2);
   		$tmp1 = mktime(0,0,0,$tmp_date1[1], $tmp_date1[2], $tmp_date1[0]);
   		$tmp2 = mktime(0,0,0,$tmp_date2[1], $tmp_date2[2], $tmp_date2[0]);
 
 		$return_date = ($tmp1 - $tmp2) / 86400;
 
   		return $return_date;
	}
?>



 
 
