<?php
	require 'dbconfig.php';
	require 'date_diff.php';
	//require 'session_ini.php';
	session_start();
	$S_UID = $_SESSION['user_id'];
	$MONTH_PRICE = $_POST['monthly_rental_start'];	//월세 최저 
	$MONTH_PRICE_1 = $_POST['monthly_rental_end'];	//월세 최고
	$DEPOSIT = $_POST['deposit_start'];				//보증금 최저 
	$DEPOSIT_1 = $_POST['deposit_end'];				//보증금 최고
	$BASE_PRICE = $_POST['base_price_start'];		//기준가 최저 
	$BASE_PRICE_1 = $_POST['base_price_end'];		//기준가 최고
	$BANG_TYPE = $_POST['bang_type'];				//방 구조( 원룸, 투룸 등)	중복허용
	$BANG_TYPE_1 = $_POST['bang_type_1'];
	$PRICE_TYPE = $_POST['price_type'];				//금액 타입( 전세, 월세 등)
	$IS_ELEVATOR = $_POST['is_elevator'];			//엘레베이터 존재 여부 
	$GU = $_POST['gu'];								//구
	$DONG = $_POST['dong'];							//동   					중복허용
	$PAGE = ($_POST['page']) ? $_POST['page'] : 1;   
	$pageNum = 10;

	if( $_POST['orderby'] == "1" ){
		$ORDERBY = "building.building_name";
	}else if( $_POST['orderby'] == "2" ){
		$ORDERBY = "binbang.tradeprice";
	}else if( $_POST['orderby'] == "3" ){
		$ORDERBY = "building.juso";
	}else{
		$ORDERBY = "binbang._id";
	}

	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);


	//문자열 자르기 : 배열로 저장된다.
	if( isset($BANG_TYPE) ){
		$BANG_TYPE=explode('|' , $BANG_TYPE);
		//배열 크기 가져오기 
		$cnt_BANG_TYPE = count($BANG_TYPE);
	}
	if( isset($DONG) ){
		$DONG = explode('|', $DONG);
		$cnt_DONG = count($DONG);
	}

	//사용자 권한 확인
	$available_q = "SELECT * FROM users WHERE user_id='".$S_UID."';";
	$available_qx = mysqli_query($connect,$available_q);
	$r=mysqli_fetch_array($available_qx);
	$S_AVAILABLE = $r['is_available'];
	$PAY_END = $r['pay_end'];

	/*
	$Today = date('Y-m-d');
	$diff_day = date_diff($PAY_END,$Today);
	if($diff_day < 0){				//서비스 기간 만료
		$que1 = "UPDATE users SET is_available=0 WHERE user_id='".$user_id."';";
		$qux1 = mysqli_query($connect,$que1);
		$S_AVAILABLE = 0;
	}
	*/

	//$pay_r['pay_end'];

	//if( strtotime($pay_r['pay_end']) < time() ) {

	//}

	$add_query = "";
	$query_count = 0; 
	if( isset($GU) ){
		$add_query = $add_query . "AND building.gu='".$GU."' "; 
		$query_count ++;
	}
	if( $cnt_DONG > 0 ){
		if( $cnt_DONG == 1){
			$add_query = $add_query . "AND building.dong like '%".$DONG[0]."%' ";
		}else{
			$add_query = $add_query . "AND (";
			for( $i=0; $i<$cnt_DONG; $i++){
				$add_query = $add_query . " building.dong like '%".$DONG[$i]."%' ";
				if( $i < $cnt_DONG-1 )
					$add_query = $add_query . "OR";
			}
			$add_query = $add_query . ") ";
		}
		$query_count ++;
	}
	if( $cnt_BANG_TYPE > 0 ){
		if( $cnt_BANG_TYPE == 1){
			$add_query = $add_query . "AND binbang.bang_type='".$BANG_TYPE[0]."' ";
		}else{
			$add_query = $add_query . "AND (";
			for( $i=0; $i<$cnt_BANG_TYPE; $i++){
				$add_query = $add_query . " binbang.bang_type='".$BANG_TYPE[$i]."' ";
				if( $i < $cnt_BANG_TYPE-1 )
					$add_query = $add_query . "OR";
			}
				$add_query = $add_query . ") ";
		}
		$query_count ++;
	}
	if( isset($BANG_TYPE_1) ){
		$add_query = $add_query . "AND binbang.bang_type_1='".$BANG_TYPE_1."' ";
		$query_count++;
	}

	if( isset($IS_ELEVATOR) ){
		$add_query = $add_query . "AND building.is_elevator='".$IS_ELEVATOR."' ";
		$query_count ++;
	}
	if( isset($PRICE_TYPE) ){
		$add_query = $add_query . "AND binbang.price_type like '%".$PRICE_TYPE."%' ";
		$query_count ++;
	}
	if( isset($DEPOSIT) || isset($DEPOSIT_1) ){
		if( isset($DEPOSIT) && !isset($DEPOSIT_1) )
			$add_query = $add_query . " AND binbang.deposit >='".$DEPOSIT."' ";
		else if( !isset($DEPOSIT) && isset($DEPOSIT_1) )
			$add_query = $add_query . " AND binbang.deposit <='".$DEPOSIT_1."' ";
		else
			$add_query = $add_query . " AND binbang.deposit BETWEEN '".$DEPOSIT."' AND '".$DEPOSIT_1."' ";

		$query_count ++;
	}
	if( isset($MONTH_PRICE) || isset($MONTH_PRICE_1) ){
		if( isset($MONTH_PRICE) && !isset($MONTH_PRICE_1) )
			$add_query = $add_query . " AND binbang.monthly_rental >='".$MONTH_PRICE."' ";
		else if( !isset($MONTH_PRICE) && isset($MONTH_PRICE_1) )
			$add_query = $add_query . " AND binbang.monthly_rental <='".$MONTH_PRICE_1."' ";
		else
			$add_query = $add_query . " AND binbang.monthly_rental BETWEEN '".$MONTH_PRICE."' AND '".$MONTH_PRICE_1."' ";
		$query_count ++;
	}
	if( isset($BASE_PRICE) || isset($BASE_PRICE_1) ){
		if( isset($BASE_PRICE) && !isset($BASE_PRICE_1) )
			$add_query = $add_query . " AND binbang.base_price >='".$BASE_PRICE."' ";
		else if( !isset($BASE_PRICE) && isset($BASE_PRICE_1) )
			$add_query = $add_query . " AND binbang.base_price <='".$BASE_PRICE_1."' ";
		else
			$add_query = $add_query . " AND binbang.base_price BETWEEN '".$BASE_PRICE."' AND '".$BASE_PRICE_1."' ";
		
		$query_count ++;
	}

	if( $query_count == 0 ){
		$que = "SELECT * FROM binbang, building WHERE building.b_id=binbang.building_id Order by ".$ORDERBY." Limit ".($pageNum*($PAGE-1)).",".$pageNum." ";
	}
	else{
		$que = "SELECT * FROM binbang, building WHERE building.b_id=binbang.building_id ".$add_query." Order by ".$ORDERBY." Limit ".($pageNum*($PAGE-1)).",".$pageNum." ";
	}
	
	//echo $que;

	$qux = mysqli_query($connect,$que);
	$count = mysqli_num_rows($qux);
	if( $count>=1 ){
		$data['result'] = "1";
		$data['count'] = $count;
		$data['bang'] = array();
		while( $result=mysqli_fetch_array($qux) ){
			if( $S_AVAILABLE == 1 ){
				$bang = array();
				$bang['_id'] = $result['_id'];
				$bang['is_available'] = "1";
				$bang['building_name'] = urlencode( $result['building_name'] );
				$bang['building_hosu'] = urlencode( $result['building_hosu'] );
				$bang['dong'] = 		 urlencode( $result['dong'] );
				$bang['sangse_juso'] = 	 urlencode( $result['sangse_juso'] );
				$bang['deposit'] = 		 $result['deposit'];
				$bang['monthly_rental'] =$result['monthly_rental'];
				$bang['empty'] = 		 urlencode( $result['empty'] );
				$bang['price_type'] = 	 urlencode( $result['price_type'] );
				$bang['manage_price'] =  urlencode( $result['manage_price'] );
				if( $result['bang_type_1'] != "" ){
					$bang['bang_type'] = urlencode( $result['bang_type']." ".$result['bang_type_1']."" );
				}else{
					$bang['bang_type'] = urlencode( $result['bang_type'] );
				}
				$bang['call'] = urlencode( $result['call_1'] );
				$bang['lat'] = $result['lat'];
				$bang['lng'] = $result['lng'];
				//리스트 이미지 얻기 쿼리 
				$que2 = "SELECT * FROM binbang_image WHERE bang_id='".$bang['_id']."' AND is_list_image=1;";
				$qux2 = mysqli_query($connect,$que2);
				$result2=mysqli_fetch_array($qux2); 
				$bang['img_url'] =  	 urlencode( $result2['url'] );
				array_push($data['bang'], $bang);
			}else{
				$bang = array();
				$bang['_id'] = $result['_id'];
				$bang['is_available'] = "0";
				$bang['building_name'] = "";
				$bang['building_hosu'] = "";
				$bang['dong'] = 		 "";
				$bang['sangse_juso'] = 	 "";
				$bang['deposit'] = 		 "";
				$bang['monthly_rental'] ="";
				$bang['empty'] = 		 "";
				$bang['price_type'] = 	 "";
				$bang['manage_price'] =  "";
				$bang['bang_type'] = 	 "";
				$bang['call'] = "";
				$bang['lat'] = "";
				$bang['lng'] = "";
				//리스트 이미지 얻기 쿼리 
				$que2 = "SELECT * FROM binbang_image WHERE bang_id='".$bang['_id']."' AND is_list_image=1;";
				$qux2 = mysqli_query($connect,$que2);
				$result2=mysqli_fetch_array($qux2); 
				$bang['img_url'] =  	 urlencode( $result2['url'] );
				//$bang['is_favorite'] = "0";
				array_push($data['bang'], $bang);
			}
		}
	}else{
		$data['result'] = "0";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	//ereg_replace("사과","*",$output);
	echo $output;
?>