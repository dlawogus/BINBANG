<?php
	require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
	$B_ID = $_GET['b_id'];

	session_start();
	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}
	
	//echo $check_id[$i]."</br>";
	
	$que1 = "SELECT _id FROM binbang WHERE building_id='".$B_ID."';";
	$qux1 = mysqli_query($connect,$que1);
	$r=mysqli_fetch_array($qux1);
  	$_ID = $r['_id'];

  	$que2 = "SELECT * FROM binbang_image WHERE bang_id='".$_ID."';";
	$qux2 = mysqli_query($connect,$que2);
	while( $row=mysqli_fetch_array($qux2) ){
      $url = $row['url'];
      $tok = explode("http://dlawogus1.cafe24.com/", $url); 
      $url = "./".$tok[1];  
      if(is_file($url)==true){
        //echo "파일삭제";
        unlink($url);
      }
	}
  	//방 이미지 삭제
	$que3 = "DELETE FROM binbang_image WHERE bang_id='".$_ID."';";
	$qux3 = mysqli_query($connect,$que3);

	//방 삭제
	$que4 = "DELETE FROM binbang WHERE building_id='".$B_ID."';";
	$qux4 = mysqli_query($connect,$que3);

	//건물 삭제 
	$que = "DELETE FROM building WHERE b_id='".$B_ID."';";
	$qux = mysqli_query($connect,$que);

	echo("<script>location.replace('../main.php');</script>");
?>