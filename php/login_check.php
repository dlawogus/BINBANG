<?php
	require 'dbconfig.php';
	$user_id = $_POST['user_id'];
	$user_pw = $_POST['user_pw'];
	if( !isset($user_id) || !isset($user_pw) )
		echo "<script>alert('빈칸을 채워주세요');history.back();</script>";

	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	$que = "SELECT * FROM manager WHERE user_id='".$user_id."' && user_pw=password('".$user_pw."');";
	$qux = mysqli_query($connect,$que);
	if(mysqli_num_rows($qux)==1){
		session_start();
		$_SESSION['user_id'] = $user_id;
		echo("<script>location.replace('../main.php');</script>"); 
	}else{
		echo "<script>alert('아이디 또는 패스워드가 잘못되었습니다.');history.back();</script>";
	}
?>