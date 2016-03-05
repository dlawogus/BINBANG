<?php
require 'dbconfig.php';
$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

/*
$user_id = $_POST['user_id'];
$result = "";
$quex = "SELECT * FROM dbincoders.incode_users where user_id='".$user_id."';";
$qux = mysqli_query($connect, $quex);

$row = mysqli_fetch_array($qux);

if(mysqli_num_rows($qux)==0){
	echo "no";
}else if( $row['join_type'] != "email" ){
  echo "not_email";
}else{

	$stringtemp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";
	for($i=0;$i<6;$i++){ 
		$result.=$stringtemp[rand(0,43)]; 
	} 

	$que1 = "UPDATE dbincoders.incode_users SET user_pw = '".$result."' WHERE user_id = '".$user_id."';";
	sendMail("dlawogus1@naver.com","incoders",$user_id,"인코더스 고객님의 비밀번호가 변경되었습니다", 
		$result);
	mysqli_query($connect, $que1);
}
*/ 
  $PERIOD = "1";
  $pay = "1";


     $m = "신청 개월수: ".$PERIOD."</br>";
    $m .= "금액: ".$pay."</br>";
    $m .= "계좌번호: 95460640363</br>";
    $m .= "예금주: 김인용";


sendMail("dlawogus1@naver.com","임재현1","dlawogus1@naver.com","빈방어플 ".$m,"빈방어플 ".$m."");
send_mail("dlawogus1@naver.com","dlawogus1@naver.com","임재현2","빈방어플".$m,"빈방어플 ".$m, "UTF-8");


function sendMail($EMAIL, $NAME, $mailto, $SUBJECT, $CONTENT){
  //$EMAIL : 답장받을 메일주소
  //$NAME : 보낸이
  //$mailto : 보낼 메일주소
  //$SUBJECT : 메일 제목
  //$CONTENT : 메일 내용
  $admin_email = $EMAIL;
  $admin_name = $NAME;

  $header = "Return-Path: ".$admin_email."\n";
  $header .= "From: =?EUC-KR?B?".base64_encode($admin_name)."?= <".$admin_email.">\n";
  $header .= "MIME-Version: 1.0\n";
  $header .= "X-Priority: 3\n";
  $header .= "X-MSMail-Priority: Normal\n";
  $header .= "X-Mailer: FormMailer\n";
  $header .= "Content-Transfer-Encoding: base64\n";
  $header .= "Content-Type: text/html;\n \tcharset=euc-kr\n";

  $subject = "=?EUC-KR?B?".base64_encode($SUBJECT)."?=\n";
  $contents = $CONTENT;

  $message = base64_encode($contents);
  flush();
  return mail($mailto, $subject, $message, $header);
}

function send_mail($to_email,$from_email,$form_name,$subject,$mybody,$type) {
        //$charset='EUC-KR';
        $charset='UTF-8';
        if( $charset !='UTF-8' )
                $encoded_body = iconv('UTF-8', $charset, str_replace("UTF-8", $charset, $mybody));
        else
                $encoded_body = $mybody;
        $from = "\"=?".$charset."?B?".base64_encode($form_name)."?=\" <".$from_email.">" ; // 인코딩된 보내
        $encoded_subject = "=?".$charset."?B?".base64_encode($subject)."?=\n";
        $headers = "MIME-Version: 1.0\r\n";
        $header .= "Content-Type: text/html;charset=$charset;format=flowed\n";
        $header .= "Content-Transfer-Encoding: 8bit\n";
        $header .= "From: $from\n";
        $header .= "Return-Path: $from\n";
        $mybody = stripslashes($mybody);
  @mail($to_email, $encoded_subject,$encoded_body,$header);
}
?>