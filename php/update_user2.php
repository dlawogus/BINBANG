<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$USER_ID = $_POST['user_id'];
    $USER_NAME = $_POST['user_name'];
  	$COMPANY_TITLE= $_POST['company_title'];
  	$PHONE = $_POST['phone'];
  	$CALL = $_POST['call_number'];
  	$EMAIL = $_POST['email'];
  	$PAY_START = $_POST['pay_start'];
  	$PAY_END = $_POST['pay_end'];
    $TOKEN = $_POST['token'];

	$que = "UPDATE users SET user_name='".$USER_NAME."', company_title='".$COMPANY_TITLE."', phone='".add_hyphen($PHONE)."', call_number='".$CALL."', email='".$EMAIL."', pay_start='".$PAY_START."',pay_end='".$PAY_END."',token='".$TOKEN."' WHERE user_id='".$USER_ID."';";
	$qux = mysqli_query($connect,$que);

	//echo $BOILER;
	//echo $qux;
	echo("<script>location.replace('../user2.php');</script>");

	function add_hyphen($hp_no){
       return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
    }
?>
