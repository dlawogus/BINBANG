<?php
	session_start();
	if( !isset($_SESSION['user_id']) ){
		echo "<script>alert('로그인되어 있지 않습니다.');history.back();</script>";
	}else{
		session_destroy();
		echo("<script>location.replace('../index.html');</script>"); 
	}
?>