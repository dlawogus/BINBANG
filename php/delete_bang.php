<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$_ID = $_GET['_id'];
  	$B_ID = $_GET['b_id'];

    $que2 = "SELECT * FROM binbang_image WHERE bang_id='".$_ID."';";
    $qux2 = mysqli_query($connect,$que2);
    /*
    while( $row=mysqli_fetch_array($qux2) ){
      $url = $row['url'];
      $tok = explode("http://dlawogus1.cafe24.com/", $url); 
      $url = "./".$tok[1];  
      if(is_file($url)==true){
        //echo "파일삭제";
        unlink($url);
      }
    }
    */

  	$que = "DELETE FROM binbang_image WHERE bang_id='".$_ID."';";
  	$qux = mysqli_query($connect,$que);

  	$que1 = "DELETE FROM binbang WHERE _id='".$_ID."';";
  	$qux1 = mysqli_query($connect,$que1);



  	echo("<script>location.replace('../insert_bang_page.php?b_id=".$B_ID."');</script>");
?>