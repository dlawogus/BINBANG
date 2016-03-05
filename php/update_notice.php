<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$title1 = $_POST['title1'];
  	$title2 = $_POST['title2'];
  	$title3 = $_POST['title3'];
  	$title4 = $_POST['title4'];
  	$title5 = $_POST['title5'];

  	$url1 = $_POST['url1'];
  	$url2 = $_POST['url2'];
  	$url3 = $_POST['url3'];
  	$url4 = $_POST['url4'];
  	$url5 = $_POST['url5'];

    $title1 = htmlspecialchars($title1);
    $title2 = htmlspecialchars($title2);
    $title3 = htmlspecialchars($title3);
    $title4 = htmlspecialchars($title4);
    $title5 = htmlspecialchars($title5);

  	$que1 = "UPDATE notice SET title='".$title1."', url='".$url1."' WHERE _id='1'; ";
  	mysqli_query($connect,$que1);

  	$que2 = "UPDATE notice SET title='".$title2."', url='".$url2."' WHERE _id='2'; ";
  	mysqli_query($connect,$que2);

  	$que3 = "UPDATE notice SET title='".$title3."', url='".$url3."' WHERE _id='3'; ";
  	mysqli_query($connect,$que3);

  	$que4 = "UPDATE notice SET title='".$title4."', url='".$url4."' WHERE _id='4'; ";
  	mysqli_query($connect,$que4);

  	$que5 = "UPDATE notice SET title='".$title5."', url='".$url5."' WHERE _id='5'; ";
  	mysqli_query($connect,$que5);

  	echo("<script>alert('수정하였습니다'); location.replace('../notice_page.php');</script>");
?>