<?php
	require 'dbconfig.php';
	$USER_ID = $_POST['user_id'];
	$USER_PW = $_POST['user_pw'];
	$USER_NAME = $_POST['user_name'];
	$COMPANY_TITLE = $_POST['company_title'];
	$CEO_ID = $_POST['ceo_id'];
	$PHONE = $_POST['phone'];
	$CALL = $_POST['call'];
	$EMAIL = $_POST['email'];
	$TOKEN = $PHONE;
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

	if( !isset($USER_ID)||!isset($USER_PW)||!isset($USER_NAME)||!isset($CEO_ID)||!isset($PHONE) ){
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('빈칸을 채워주세요');$url;</script>";
	}

	$que = "SELECT * FROM company WHERE ceo_id='".$CEO_ID."'";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)==0){
		$url = "history.go(-1)";
        echo "<script language='javascript'>alert('대표님 아이디가 존재하지 않습니다');$url;</script>";
        exit;
	}

	$que = "SELECT * FROM users WHERE user_id='".$user_id."';";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)>=1){	
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('이미 존재하는 아이디입니다.');$url;</script>";
	}else{
		$que1 = "INSERT INTO users VALUES ( '".$USER_ID."', password('".$USER_PW."'), '".$USER_NAME."', '".$COMPANY_TITLE."','".$CEO_ID."','".$PHONE."','".$CALL."','".$EMAIL."','0','".$TOKEN."', now(),now(),now());";
		$qux = mysqli_query($connect,$que1);
		if( $qux ){ 
		  echo("<script>alert('회원가입에 성공하였습니다.'); location.replace('../join_user2_page.php');</script>");
		}else{ 
		  echo("<script>alert('회원가입에 실패하였습니다. 다시시도해주세요'); location.replace('../join_user2_page.php');</script>");
		}
	}	
?>