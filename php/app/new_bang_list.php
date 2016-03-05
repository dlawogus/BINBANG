<?php
	require 'dbconfig.php';
	//require 'session_ini.php';
	session_start();
	$S_UID = $_SESSION['user_id'];

	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	//사용자 권한 확인
	$available_q = "SELECT * FROM users WHERE user_id='".$S_UID."';";
	$available_qx = mysqli_query($connect,$available_q);
	$r=mysqli_fetch_array($available_qx);
	$_SESSION['is_available'] = $r['is_available'];
	$S_AVAILABLE = $_SESSION['is_available'];

	$que = "SELECT * FROM binbang, building WHERE building.b_id=binbang.building_id order by binbang._id desc Limit 5 ;";

	$qux = mysqli_query($connect,$que);
	$count = mysqli_num_rows($qux);
	if( $count>=1 ){
		$data['result'] = "1";
		$data['count'] = $count;
		$data['bang'] = array();
		while( $result=mysqli_fetch_array($qux) ){
			if( $S_AVAILABLE == "1" ){
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
				//즐겨찾기 쿼리 
				/*
				$que3 = "SELECT count(*) FROM favorite WHERE user_id='".$S_UID."' AND bang_id='".$bang['_id']."';";
				$qux3 = mysqli_query($connect,$que3); 
				$result3 = mysqli_fetch_array($qux3);
				$bang['is_favorite'] = $result3['count(*)'];
				*/
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
