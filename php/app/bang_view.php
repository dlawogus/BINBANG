<?php
	require 'dbconfig.php';
	require 'date_diff.php';
	//require 'session_ini.p
	session_start();
	$S_UID = $_SESSION['user_id'];
	$bang_id = $_GET['bang_id'];

	if( !isset($S_UID) || !isset($bang_id) ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);
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
	
	if( $S_AVAILABLE ==  0 ){
		$data['result'] = "0";
		$output = json_encode($data);
		$output = urldecode($output);
		echo $output;
		exit;
	}

	$que = "SELECT * FROM binbang, building WHERE building.b_id=binbang.building_id AND binbang._id='".$bang_id."';";

	$qux = mysqli_query($connect,$que);
	if( mysqli_num_rows($qux)>=1 ){
		$data['result'] = "1";
		//$data['bang'] = array();
		$data['bang'];
		while( $result=mysqli_fetch_array($qux) ){
			//$bang = array();
			$bang['_id'] = $result['_id'];
			$bang['building_name'] = urlencode( $result['building_name'] );
			$bang['building_hosu'] = urlencode( $result['building_hosu'] );
			$bang['si'] = 			 urlencode( $result['si'] );
			$bang['gu'] = 			 urlencode( $result['gu'] );
			$bang['dong'] = 		 urlencode( $result['dong'] );
			$bang['sangse_juso'] = 	 urlencode( $result['sangse_juso'] );
			$bang['deposit'] = 		 $result['deposit'];
			$bang['deposit_possible'] =urlencode( $result['deposit_possible'] );
			$bang['monthly_rental'] =$result['monthly_rental'];
			$bang['base_price'] =  	 $result['base_price'];
			$bang['empty'] = 		 urlencode( $result['empty'] );
			$bang['price_type'] = 	 urlencode( $result['price_type'] );
			$bang['manage_price'] =  urlencode( $result['manage_price'] );
			$bang['is_manage_internet'] = $result['is_manage_internet'];
			$bang['is_manage_sudo'] = 	  $result['is_manage_sudo'];
			$bang['is_manage_yusun'] =	  $result['is_manage_yusun'];

			if( $result['bang_type_1'] != "" ){
				$bang['bang_type'] = urlencode( $result['bang_type']."(".$result['bang_type_1'].")" );
			}else{
				$bang['bang_type'] = urlencode( $result['bang_type'] );
			}

			$bang['boiler'] = 		 urlencode( $result['boiler'] );
			$bang['call_who'] = urlencode( $result['call_1_who'] );
			$bang['call'] = urlencode( $result['call_1'] );
			$bang['lat'] = $result['lat'];
			$bang['lng'] = $result['lng'];
			$bang['is_park'] = 		 $result['is_park'];
			$bang['possible_date'] = urlencode( $result['possible_date'] );
			$bang['building_password'] = $result['building_password'];
			$bang['is_elevator'] = 	 $result['is_elevator'];
			$bang['bang_option'] = 	 urlencode( $result['bang_option'] );
			$bang['comment'] = 			 urlencode( $result['comment'] );
			//리스트 이미지 얻기 쿼리 
			$que2 = "SELECT * FROM binbang_image WHERE bang_id='".$bang['_id']."' AND is_list_image=1;";
			$qux2 = mysqli_query($connect,$que2);
			$result2=mysqli_fetch_array($qux2); 
			$bang['list_img_url'] =  	 urlencode( $result2['url'] );
			
			//뷰 이미지 얻기 쿼리 
			$que3 = "SELECT * FROM binbang_image WHERE bang_id='".$bang['_id']."' AND is_list_image=0;";
			$qux3 = mysqli_query($connect,$que3);
			$img = array();
			while( $result3=mysqli_fetch_array($qux3) ){
				$url['url'] = array();
				$url['url'] = urlencode( $result3['url'] );
				array_push( $img , $url );
			}
			$bang['view_img_url'] = $img;

			//array_push($data['bang'], $bang);
			$data['bang'] = $bang;
		}
	}else{
		$data['result'] = "2";
	}

	$output = json_encode($data);
	$output = urldecode($output);
	echo $output;
?>