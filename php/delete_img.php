<?php
  require 'dbconfig.php';
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
	session_start();
	if( !isset($_SESSION['user_id']) ) {
  	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
  	exit;
	}

	$B_ID = $_GET['b_id'];
  $BANG_TYPE = $_GET['bang_type'];

  $que2 = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='".$BANG_TYPE."';";
  $qux2 = mysqli_query($connect,$que2);
  while( $row=mysqli_fetch_array($qux2) ){
    $url = $row['url'];
    $tok = explode("http://dlawogus1.cafe24.com/", $url); 
    $url = "./".$tok[1];  
    if(is_file($url)==true){
      //echo "파일삭제";
      unlink($url);
    }
    //echo $url;
  }

  //echo $BANG_TYPE;

	$que = "DELETE FROM building_image WHERE building_id='".$B_ID."' AND bang_type='".$BANG_TYPE."';";
	$qux = mysqli_query($connect,$que);

  $que2 = "SELECT _id FROM binbang WHERE building_id='".$B_ID."' AND bang_type='".$BANG_TYPE."'";
  $qux2 = mysqli_query($connect,$que2);
  while( $row=mysqli_fetch_array($qux2) ){
    $que1 = "DELETE FROM binbang_image WHERE bang_id='".$row['_id']."';";
    $qux1 = mysqli_query($connect,$que1);
  }



  echo("<script>location.replace('../insert_bang_page.php?b_id=".$B_ID."');</script>");
?>