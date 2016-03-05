<?php
	require 'dbconfig.php';
  	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  	session_start();
  	if( !isset($_SESSION['user_id']) ) {
    	echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    	exit;
  	}

	$files=$_FILES["adfile"];
	$call = $_POST['call'];

  if($files["type"] == "image/gif"){
    $type = "gif";
  }else if($files["type"] == "image/jpeg" ){
    $type = "jpeg";
  }else if($files["type"] == "image/jpg" ){
    $type = "jpg";
  }else{
    $type = "png";
  }

	$fn =  "http://dlawogus1.cafe24.com/ad_img/ad_".$call."_".$files["name"].".".$type;
	
	$que = "INSERT INTO advertise VALUES ('','".$fn."','".add_hyphen($call)."','0',now() );";
	$qux = mysqli_query($connect,$que);

	images_check($files);
	store_image($files,$call);


function add_hyphen($hp_no){
  return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
}

function images_check($files) {
  //이미지 파일
    if($files["name"]!="" || $files["name"]!=null){
      //파일 업로드시 에러
      if ($files["error"] > 0){
        $msg = "Error: " . $files["error"] . "<br />";
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('$msg');$url;</script>";
        exit;
      }
      //파일 이미지 체크
      $image_check=false;
      if (($files["type"] == "image/gif")|| ($files["type"] == "image/jpeg")|| ($files["type"] == "image/jpg") || ($files["type"] == "image/png") ){
         $image_check=true;
      }//if문
 
      if ($image_check=false) {
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('지원하는 이미지 파일이 아닙니다');$url;</script>";
        exit;
      }
 
      //파일 사이즈 체크
      if ($files["size"] > 20000000){
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('사이즈가 너무 큽니다');$url;</script>";
        exit;
      }//if문
    }//빈 파일 체크 if문 end 
  }//이미지 확인 함수 끝


function store_image($files,$call) {

	if($files["type"] == "image/gif"){
		$type = "gif";
	}else if($files["type"] == "image/jpeg" ){
		$type = "jpeg";
	}else if($files["type"] == "image/jpg" ){
		$type = "jpg";
	}else{
		$type = "png";
	}
    $uploaddir = "./ad_img/";
    $uploadfile = $uploaddir."ad_".$call."_".$files["name"].".".$type;

    if (file_exists($uploadfile) ){
      echo $files["name"]. " already exists. ";
      $url = "history.go(-1)";
      echo "<script language='javascript'>alert('이미지 존재하는 이미지입니다');$url;</script>";
      exit;
    }else{

      if (move_uploaded_file($_FILES['adfile']["tmp_name"], $uploadfile )) {
        //echo "ok !!!!!.\n";
  		$srcimg = "ad_".$call."_".$files["name"].".".$type;
		  img_resize($srcimg, $srcimg, "./ad_img", 800, 400);
        echo("<script>location.replace('../ad_page.php');</script>");
      }else{
        //echo "no!!!!!!\n";
        echo("<script>location.replace('../ad_page.php');</script>");
      }
    } 
  }
  //echo "<script language='javascript'>alert('ok!');$url;</script>";


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