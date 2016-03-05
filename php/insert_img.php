<?php
require 'dbconfig.php';
$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
$files=$_FILES["userfile_g"];
$B_ID = $_POST['b_id'];
$BANG_TYPE = $_POST['bang_type_g'];

if( $BANG_TYPE == "" ){
  $url = "history.go(-1)";
  echo "<script language='javascript'>alert('방구조를 선택해 주세요');$url;</script>";
  exit;
}

$BT = $BANG_TYPE;
if( $BANG_TYPE == "원룸" )
  $BANG_TYPE = "1";
else if( $BANG_TYPE == "투룸" )
  $BANG_TYPE = "2";
else if( $BANG_TYPE == "쓰리룸" )
  $BANG_TYPE = "3";
else if( $BANG_TYPE == "오피스텔" )
  $BANG_TYPE = "4";
else
  $BANG_TYPE = "5";

//빈미지 왔을 때 체크 방법 만들기
images_check($files);
store_image($files,$B_ID,$BANG_TYPE);


$q = "DELETE FROM building_image WHERE building_id='".$B_ID."' AND bang_type='".$BT."' ";
mysqli_query($connect,$q);

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
  if( ($files["name"][$i]!="") || ($files["name"][$i]!= null) ){
    $url = "http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type;

    if( $i == 0)
      $que = "INSERT INTO building_image VALUES ('','".$B_ID."','".$BT."','http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type."','1')";
    else
      $que = "INSERT INTO building_image VALUES ('','".$B_ID."','".$BT."','http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type."','0')";

    //http://dlawogus1.cafe24.com/uploads/img_".$B_ID."_".$_ID."_".$i.".".$type."'
    $qux = mysqli_query($connect,$que);
  }

}


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


function store_image($files,$B_ID,$BANG_TYPE) {
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
    $uploadfile = $uploaddir ."img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type;

    if (move_uploaded_file($_FILES['userfile_g']["tmp_name"][$i], $uploadfile )) {
      //echo "ok !!!!!.\n";
      $srcimg = "img_".$B_ID."_g_".$BANG_TYPE."_".$i.".".$type;
      img_resize($srcimg, $srcimg, "./uploads", 420, 600);
      //echo("<script>location.replace('../view_building.php?b_id=".$B_ID."');</script>");
    }
    /*
    else{
        $url = "history.go(-1)";
        echo "<script language='javascript'>alert('업로드에 실패하엿습니다');$url;</script>";
    }
    */

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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

