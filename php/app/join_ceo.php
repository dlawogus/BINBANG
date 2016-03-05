<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];
	$USER_PW = $_POST['user_pw'];
	$USER_NAME = $_POST['user_name'];
	$COMPANY_NAME = $_POST['company_name'];
	$COMPANY_REG_NUM = $_POST['company_reg_num'];
	$RELAY_REG_NUM = $_POST['relay_reg_num'];
	$PHONE = $_POST['phone_number'];
	$FAX = $_POST['fax_number'];
	$JUSO = $_POST['juso'];
	$EMAIL = $_POST['email'];
	$PAY_NAME = $_POST['pay_name'];
	$PAY_TITLE = $_POST['pay_title'];
	$PAY_PHONE = $_POST['pay_phone'];
	$PAY_EMAIL = $_POST['pay_email'];
	$TOKEN = $_POST['token'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($USER_ID)||!isset($USER_PW)||!isset($USER_NAME)||!isset($PHONE)||!isset($TOKEN)){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$USER_ID."';";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)>=1){	
		$data['result'] = "0";
	}else{
		$que2 = "INSERT INTO company VALUES ('','".$USER_ID."','".$COMPANY_NAME."','".$COMPANY_REG_NUM."','".$RELAY_REG_NUM."','".$PHONE."','".$FAX."','".$JUSO."','','".$PAY_NAME."','".$PAY_TITLE."','".$PAY_PHONE."','".$PAY_EMAIL."','1')";
		$qux2 = mysqli_query($connect,$que2);
		$que1 = "INSERT INTO users VALUES ( '".$USER_ID."', password('".$USER_PW."'), '".$USER_NAME."', '대표','".$USER_ID."','".$PHONE."','','".$EMAIL."','1','".$TOKEN."', now(),now(),now() );";
		$qux1 = mysqli_query($connect,$que1);
		
		if( $qux1 && $qux2 ){ 
		  $data['result'] = "1";
		}else{ 
		  $data['result'] = "0";
		}
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>