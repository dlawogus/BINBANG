<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];
	$USER_PW = $_POST['user_pw'];
	$USER_NAME = $_POST['user_name'];
	$COMPANY_NAME = $_POST['company_name'];
	$COMPANY_REG_NUM = $_POST['company_reg_num'];
	$RELAY_REG_NUM = $_POST['relay_reg_num'];
	$PHONE = $_POST['phone'];
	$FAX = $_POST['fax'];
	$JUSO = $_POST['juso'];
	$EMAIL = $_POST['email'];
	$TOKEN = $PHONE;
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($USER_ID)||!isset($USER_PW)||!isset($USER_NAME)||!isset($PHONE)||!isset($TOKEN)){
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('빈칸을 채워주세요');$url;</script>";
        exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$USER_ID."';";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)>=1){	
      	$url = "history.go(-1)";
        echo "<script language='javascript'>alert('이미 존재하는 아이디입니다');$url;</script>";
	}else{
		$que2 = "INSERT INTO company VALUES ('','".$USER_ID."','".$COMPANY_NAME."','".$COMPANY_REG_NUM."','".$RELAY_REG_NUM."','".$PHONE."','".$FAX."','".$JUSO."','','".$PAY_NAME."','".$PAY_TITLE."','".$PAY_PHONE."','".$PAY_EMAIL."','1')";
		$qux2 = mysqli_query($connect,$que2);
		$que1 = "INSERT INTO users VALUES ( '".$USER_ID."', password('".$USER_PW."'), '".$USER_NAME."', '대표','".$USER_ID."','".$PHONE."','','".$EMAIL."','0','".$TOKEN."', now(),now(),now() );";
		$qux1 = mysqli_query($connect,$que1);
	
	}

	echo("<script>alert('회원가입에 성공하였습니다.'); location.replace('../join_user_page.php');</script>");
?>