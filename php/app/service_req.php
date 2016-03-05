<?php
	require 'dbconfig.php';
    //require 'session_ini.php';
	session_start();
	$S_UID = $_SESSION['user_id'];
    $PERIOD = $_GET['period'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);

    if( !isset($S_UID) ){
        $data['result'] = "0";
        $output = json_encode($data);
        $output = urldecode($output);
        echo $output;
        exit;
    }


    $que = "SELECT * FROM users WHERE user_id='".$S_UID."';";
    $qux = mysqli_query($connect, $que);
    $r=mysqli_fetch_array($qux);
    $ID= $r['user_id'];

    $que1 = "SELECT * FROM service_req WHERE user_id='".$S_UID."';";
    $qux1 = mysqli_query($connect, $que1);
    if( mysqli_num_rows($qux1) >= 1 ){
        $data['result'] = "2";
        $output = json_encode($data);
        $output = urldecode($output);
        echo $output;
        exit;
    }else{
        $que2 = "INSERT INTO service_req VALUES ('','".$ID."','".$PERIOD."', now() )";
        $qux2 = mysqli_query($connect,$que2);
    }

    if( $PERIOD == 1 )  $pay = "14,800원";
    else $pay = "40,000원";

    $msg = "<p><font size='5'><b>이름:</b> ".$r['user_name']."</font></p>";   
    $msg .= "<p><font size='5'><b>아이디:</b> ".$r['user_id']."</font></p>";
    $msg .= "<p><font size='5'><b>신청 개월수:</b> ".$PERIOD."개월</font></p>";
    $msg .= "<p><font size='5'><b>금액:</b> ".$pay."</font></p><br>";
    $msg .= "<p><font size='5'><a href='http://dlawogus1.cafe24.com/service_req.php'> 관리자페이지로 이동 </a></font></p>";
  
    send_mail("do101company@gmail.com","dlawogus1@naver.com","빈방", $ID."님 ".$PERIOD."개월 빈방앱 서비스 승인요청입니다.", $msg, 'utf-8' );

    /*
    $m = "<h2>아래의 계좌로 금액입금 확인후 사용가능합니다</h2>";
    $m .= "<p><font size='5'><b>신청 개월수:</b> ".$PERIOD."개월</font></p>";
    $m .= "<p><font size='5'><b>금액:</b> ".$pay."</font></p>";
    $m .= "<p><font size='5'><b>계좌번호:</b> 95460640363</font></p>";
    $m .= "<p><font size='5'><b>예금주:</b> 김인용</font></p>";

    send_mail("dlawogus1@naver.com",$r['email'],"빈방",$ID."님 "."서비스 신청 내역입니다", $m ,'utf-8');
    */
    $data['result'] = "1";
    $output = json_encode($data);
    $output = urldecode($output);
    echo $output;

function sendMail($EMAIL, $NAME, $mailto, $SUBJECT, $CONTENT)
{
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