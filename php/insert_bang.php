<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

  	$B_ID = $_POST['b_id'];
	$files=$_FILES["userfile"];
	$HOSU = $_POST['hosu'];
	$BANG_TYPE = $_POST['bang_type'];
	$BANG_TYPE_1 = $_POST['bang_type_1'];
	$DEPOSIT = $_POST['deposit'];
	$RENTAL = $_POST['rental'];
	$BASE_PRICE = $_POST['base_price'];
	$MANAGE = $_POST['manage'];
	$DEPOSIT = $_POST['deposit'];
	$DEPOSIT_POSSIBLE = $_POST['deposit_possible'];
	$INTERNET = $_POST['internet'];
	$SUDO = $_POST['sudo'];
	$YUSUN = $_POST['yusun'];
	$EMPTY = $_POST['empty'];
	$OPTION = $_POST['option'];
	$COMMENT = $_POST['comment'];

	if( $INTERNET == "") $INTERNET = 0;
	if( $SUDO == "") $SUDO = 0;
	if( $YUSUN == "") $YUSUN = 0;
	if( $RENTAL == "") $PRICE_TYPE = "전세";
	else $PRICE_TYPE = "월세";

	$TRADEPRICE = $DEPOSIT + $RENTAL*100;
	if( $TRADEPRICE < 5000 )
		$TRADEPRICE = $DEPOSIT + $RENTAL*70; 

	$que = "INSERT INTO binbang VALUES ('','".$B_ID."','".$HOSU."','".$DEPOSIT."',
		'".$DEPOSIT_POSSIBLE."','".$RENTAL."','".$BASE_PRICE."','".$TRADEPRICE."','".$EMPTY."','".$PRICE_TYPE."',
		'".$MANAGE."','".$INTERNET."','".$SUDO."','".$YUSUN."','".$BANG_TYPE."','".$BANG_TYPE_1."',
		'','".$OPTION."','".$COMMENT."')";

	$qux = mysqli_query($connect,$que);


	$que1 = "SELECT * FROM binbang WHERE building_id='".$B_ID."' Order by _id desc Limit 1;";
	$qux1 = mysqli_query($connect,$que1);
	$row =mysqli_fetch_array($qux1);
	$_ID = $row['_id'];

	images_check($files);
	store_image($files,$B_ID,$_ID,$BANG_TYPE);

	$count=count($files["name"]);
	if( $files["name"][0]=="" || $files["name"][0]==null ) $count = 0;

	for($i=0; $i < $count; $i++){
		if($files["type"][$i] == "image/gif"){
			$type = "gif";
		}else if($files["type"][$i] == "image/jpeg" ){
			$type = "jpeg";
		}else if($files["type"][$i] == "image/jpg" ){
			$type = "jpg";
		}else{
			$type = "png";
		}

  		if( $files["name"][$i]!="" || $files["name"][$i]!= null ){
			if( $i == 0 ){
				$que2 = "INSERT INTO binbang_image VALUES ('','".$B_ID."','".$_ID."','http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_".$_ID."_".$i.".".$type."','1')";
				$qux2 = mysqli_query($connect,$que2);
				//que2 = "INSERT INTO binbang_image VALUES ('','".$B_ID."','".$_ID."','http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_".$_ID."_".$i.".".$type."','0')";
				//$qux2 = mysqli_query($connect,$que2);
			}else{
				$que2 = "INSERT INTO binbang_image VALUES ('','".$B_ID."','".$_ID."','http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_".$_ID."_".$i.".".$type."','0')";
				$qux2 = mysqli_query($connect,$que2);
			}
		}
	}

	//"img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type;
	if( $BANG_TYPE == "원룸" )
	  $BT = "1";
	else if( $BANG_TYPE == "투룸" )
	  $BT = "2";
	else if( $BANG_TYPE == "쓰리룸" )
	  $BT = "3";
	else if( $BANG_TYPE == "오피스텔" )
	  $BT = "4";
	else
	  $BT = "5";

	if( $count == 0 ){
		$q = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='".$BANG_TYPE."';";
		$qx = mysqli_query($connect,$q);
		while( $r = mysqli_fetch_array($qx) ){
			if( $r['is_list_image'] == 1 ){
				$que3 = "INSERT INTO binbang_image VALUES ('','".$B_ID."','".$_ID."','".$r['url']."','1')";
			}else{
				$que3 = "INSERT INTO binbang_image VALUES ('','".$B_ID."','".$_ID."','".$r['url']."','0')";
			}
			mysqli_query($connect,$que3);
		}

	}
/*
	$filepath = "../upload_files/heavening.jpg";

if(file_exists($filepath))
{
echo "있어요!!!";
}
else
{
echo "없어요..ㅠㅠ";
}
*/
	
	
	echo("<script>location.replace('../insert_bang_page.php?b_id=".$B_ID."');</script>");


function images_check($files) {
  //카운트 수
  $count=count($files["name"]);
  //이미지 파일
  for ($i = 0; $i < $count; $i++) {
    //빈 파일 체크 if문 start
    if($files["name"][$i]!="" || $files["name"][$i]!=null){
      //파일 업로드시 에러
      if ($files["error"][$i] > 0){
        $msg = "Error: " . $files["error"][$i] . "<br />";
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('$msg');$url;</script>";
        exit;
      }
      //파일 이미지 체크
      $image_check=false;
      if (($files["type"][$i] == "image/gif")|| ($files["type"][$i] == "image/jpeg")|| ($files["type"][$i] == "image/jpg") || ($files["type"][$i] == "image/png") ){
         $image_check=true;
      }//if문
 
      if ($image_check=false) {
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('지원하는 이미지 파일이 아닙니다');$url;</script>";
        exit;
      }
 
      //파일 사이즈 체크
      if ($files["size"][$i] > 20000000){
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('사이즈가 너무 큽니다');$url;</script>";
        exit;
      }//if문
    }//빈 파일 체크 if문 end 
  }//for문
}//이미지 확인 함수 끝


function store_image($files,$B_ID,$_ID,$BANG_TYPE) {
  $count=count($files["name"]);
  for ($i = 0; $i < $count; $i++) {
	if($files["type"][$i] == "image/gif"){
		$type = "gif";
	}else if($files["type"][$i] == "image/jpeg" ){
		$type = "jpeg";
	}else if($files["type"][$i] == "image/jpg" ){
		$type = "jpg";
	}else{
		$type = "png";
	}
    $uploaddir = "./uploads/";
    $uploadfile = $uploaddir ."img_".$B_ID."_".$_ID."_".$i.".".$type;


    if (file_exists($uploadfile) ){
      echo $files["name"][$i] . " already exists. ";
      $url = "history.go(-1)";
      echo "<script language='javascript'>alert('이미지 존재하는 이미지입니다');$url;</script>";
      exit;
    }else{

      if (move_uploaded_file($_FILES['userfile']["tmp_name"][$i], $uploadfile )) {
        //echo "ok !!!!!.\n";
  		$srcimg = "img_".$B_ID."_".$_ID."_".$i.".".$type;
		img_resize($srcimg, $srcimg, "./uploads", 420, 600);
        //echo("<script>location.replace('../insert_bang_page.php?b_id=".$B_ID."');</script>");
      }
    } 
  }
  echo("<script>location.replace('../insert_bang_page.php?b_id=".$B_ID."');</script>");
}//이미지 저장 함수 끝



function img_resize( $srcimg, $dstimg, $imgpath, $rewidth, $reheight )
{

$src_info = getimagesize("$imgpath/$srcimg");

if($rewidth < $src_info[0] || $reheight < $src_info[1]) {
if(($src_info[0] - $rewidth) > ($src_info[1] - $reheight)) {
$reheight = round(($src_info[1]*$rewidth)/$src_info[0]);
} else {
$rewidth = round(($src_info[0]*$reheight)/$src_info[1]);
}
} else {
exec("cp $imgpath/$srcimg $imgpath/$dstimg");
return;
}

$dst = imageCreatetrueColor($rewidth, $reheight);

if($src_info[2] == 1) {
$src = ImageCreateFromGIF("$imgpath/$srcimg");

imagecopyResampled($dst, $src,0,0,0,0,$rewidth,$reheight,ImageSX($src),ImageSY($src));
Imagejpeg($dst,"$imgpath/$dstimg",100);
} elseif($src_info[2] == 2) {
$src = ImageCreateFromJPEG("$imgpath/$srcimg");

imagecopyResampled($dst, $src,0,0,0,0,$rewidth,$reheight,ImageSX($src),ImageSY($src));
Imagejpeg($dst,"$imgpath/$dstimg",100);
} elseif($src_info[2] == 3) {
$src = ImageCreateFromPNG("$imgpath/$srcimg");

imagecopyResampled($dst, $src,0,0,0,0,$rewidth,$reheight,ImageSX($src),ImageSY($src));
Imagepng($dst,"$imgpath/$dstimg",100);
}
imageDestroy($src);
imageDestroy($dst);
}

?>