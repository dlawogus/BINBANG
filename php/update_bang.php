<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$B_ID = $_POST['b_id'];
  	$_ID = $_POST['_id'];

	$HOSU = $_POST['hosu'];
	$BANG_TYPE = $_POST['bang_type'];
	$BANG_TYPE_1 = $_POST['bang_type_1'];
	$DEPOSIT = $_POST['deposit'];
	$RENTAL = $_POST['rental'];
	$BASE_PRICE = $_POST['base_price'];
	$MANAGE = $_POST['manage'];
	$DEPOSIT_POSSIBLE = $_POST['deposit_possible'];
	$INTERNET = $_POST['internet'];
	$SUDO = $_POST['sudo'];
	$YUSUN = $_POST['yusun'];
	$EMPTY = $_POST['empty'];
	$OPTION = $_POST['option'];
	$COMMENT = $_POST['comment'];
	
	$query = "";
	$cnt = 0;
	if( $HOSU != "" ){
		$query .= "building_hosu='".$HOSU."' ";
		$cnt++;
	}
	if( $BANG_TYPE != "" ){
		if( $cnt == 0){
			$query .= "bang_type='".$BANG_TYPE."' ";
		}else{
			$query .= ", bang_type='".$BANG_TYPE."' ";
		}
		$cnt++;
	}
	if( $BANG_TYPE_1 != "" ){
		if( $cnt == 0){
			$query .= "bang_type_1='".$BANG_TYPE_1."' ";
		}else{
			$query .= ", bang_type_1='".$BANG_TYPE_1."' ";
		}
		$cnt++;
	}
	if( $DEPOSIT != "" ){
		if( $cnt == 0){
			$query .= "deposit='".$DEPOSIT."' ";
		}else{
			$query .= ", deposit='".$DEPOSIT."' ";
		}
		$cnt++;
	}
	if( $RENTAL != "" ){
		if( $cnt == 0){
			$query .= "monthly_rental='".$RENTAL."'";
		}else{
			$query .= ", monthly_rental='".$RENTAL."', ";
		}
		$cnt++;
	}
	if( $BASE_PRICE != "" ){
		if( $cnt == 0){
			$query .= "base_price='".$BASE_PRICE."' ";
		}else{
			$query .= ", base_price='".$BASE_PRICE."' ";
		}
		$cnt++;
	}
	if( $MANAGE!= "" ){
		if( $cnt == 0){
			$query .= "manage_price='".$MANAGE."' ";
		}else{
			$query .= ", manage_price='".$MANAGE."' ";
		}
		$cnt++;
	}
	if( $DEPOSIT_POSSIBLE != "" ){
		if( $cnt == 0){
			$query .= "deposit_possible='".$DEPOSIT_POSSIBLE."' ";
		}else{
			$query .= ", deposit_possible='".$DEPOSIT_POSSIBLE."' ";
		}
		$cnt++;
	}
	if( $EMPTY != "" ){
		if( $cnt == 0){
			$query .= "empty='".$EMPTY."' ";
		}else{
			$query .= ", empty='".$EMPTY."' ";
		}
		$cnt++;
	}
	if( $OPTION != "" ){
		if( $cnt == 0){
			$query .= "bang_option='".$OPTION."' ";
		}else{
			$query .= ", bang_option='".$OPTION."' ";
		}
		$cnt++;
	}
	if( $COMMENT != "" ){
		if( $cnt == 0){
			$query .= "comment='".$COMMENT."' ";
		}else{
			$query .= ", comment='".$COMMENT."' ";
		}
		$cnt++;
	}

	if( $INTERNET == "") $INTERNET = 0;
	if( $SUDO == "") $SUDO = 0;
	if( $YUSUN == "") $YUSUN = 0;

	if( $RENTAL != "")
		$PRICE_TYPE = "월세";
	if( $RENTAL == "" && $DEPOSIT != "")
		$PRICE_TYPE = "전세";

	if( $cnt > 0 ){
		$que = "UPDATE binbang SET ".$query." , price_type='".$PRICE_TYPE."' ,is_manage_internet='".$INTERNET."', is_manage_sudo='".$SUDO."', is_manage_yusun='".$YUSUN."' WHERE _id='".$_ID."' ";
		mysqli_query($connect,$que);
	}

	echo("<script>alert('수정하였습니다'); location.replace('../view_bang.php?b_id=".$B_ID."&_id=".$_ID."');</script>");

?>