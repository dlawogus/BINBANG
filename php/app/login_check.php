<?php
	require 'dbconfig.php';
	//include 'session_ini.php';
	session_start();
	$user_id = $_POST['user_id'];
	$user_pw = $_POST['user_pw'];
	$token = $_POST['token'];

	if( !isset($user_id) || !isset($user_pw) || !isset($token) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	$que = "SELECT * FROM users WHERE user_id='".$user_id."' AND user_pw=password('".$user_pw."') AND token='".$token."';";
	$qux = mysqli_query($connect,$que);
	$result=mysqli_fetch_array($qux);
	if(mysqli_num_rows($qux)==1){
		$data['result'] = "1";

		$_SESSION['user_id'] = $user_id;
		$_SESSION['is_available'] = $result['is_available'];
	}else{
		$que2 = "SELECT * FROM users WHERE user_id='".$user_id."' AND user_pw=password('".$user_pw."');";
		$qux2 = mysqli_query($connect,$que2);
		if(mysqli_num_rows($qux2)==1){
			$data['result'] = "4";			//등록되지 않은 키  
		}else{
			$que1 = "SELECT * FROM users WHERE user_id='".$user_id."';";
			$qux1 = mysqli_query($connect,$que1);
			if(mysqli_num_rows($qux1)==1){	//비밀번호가 틀	림 
				$data['result'] = "2";
			}else{
				$data['result'] = "3";		//아이디가 존재 x 
			}
		}
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>