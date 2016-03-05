<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}
  	$B_ID = $_POST['b_id'];
  	$GU = $_POST['gu'];
  	$DONG = $_POST['dong'];
  	$JIBUN = $_POST['jibun'];
  	$BUILDING = $_POST['building'];
  	$BUILDING_PW = $_POST['building_pw'];
	$CALL1_WHO = $_POST['call1_who'];
	$CALL1 = $_POST['call1'];
	$CALL2_WHO = $_POST['call2_who'];
	$CALL2 = $_POST['call2'];
	$CALL3_WHO = $_POST['call3_who'];
	$CALL3 = $_POST['call3'];
	$BOILER = $_POST['boiler'];
	$ELEVATOR = $_POST['elevator'];
	$PARK = $_POST['park'];
	$COMMENTS = $_POST['comments'];

	$que = "UPDATE building SET gu='".$GU."',dong='".$DONG."', sangse_juso='".$JIBUN."', building_name='".$BUILDING."',building_password='".$BUILDING_PW."',call_1_who='".$CALL1_WHO."',call_1='".add_hyphen($CALL1)."',call_2_who='".$CALL2_WHO."',call_2='".add_hyphen($CALL2)."',call_3_who='".$CALL3_WHO."',call_3='".add_hyphen($CALL3)."',boiler='".$BOILER."',is_elevator='".$ELEVATOR."',is_park='".$PARK."',building_comment='".$COMMENTS."' WHERE b_id='".$B_ID."';";
	$qux = mysqli_query($connect,$que);

	//echo $BOILER;
	//echo $qux;
	echo("<script>location.replace('../main.php');</script>");

	function add_hyphen($hp_no){
       return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
    }
?>
