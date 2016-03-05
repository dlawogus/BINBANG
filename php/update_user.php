<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$_ID = $_POST['_id'];
  	$COMPANY_NAME = $_POST['company_name'];
  	$COMPANY_REG_NUM = $_POST['company_reg_num'];
  	$RELAY_REG_NUM = $_POST['relay_reg_num'];
  	$PHONE_NUMBER = $_POST['phone_number'];
  	$FAX_NUMBER = $_POST['fax_number'];
  	$JUSO = $_POST['juso'];

	$que = "UPDATE company SET company_name='".$COMPANY_NAME."', company_reg_num='".$COMPANY_REG_NUM."', relay_reg_num='".$RELAY_REG_NUM."', phone_number='".add_hyphen($PHONE_NUMBER)."', fax_number='".$FAX_NUMBER."', juso='".$JUSO."' WHERE _id='".$_ID."';";
	$qux = mysqli_query($connect,$que);

	//echo $BOILER;
	//echo $qux;
	echo("<script>location.replace('../user.php');</script>");

	function add_hyphen($hp_no){
       return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
    }
?>
