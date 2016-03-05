<?php
  require 'dbconfig.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }
  $B_ID = $_GET['b_id'];

?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="./dong.js"></script>
  <script src="./img_resize.js"></script>
  <link rel="stylesheet" href="./img_table.css" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <title>BINBANG 관리자페이지</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="../index.html">BINBANG</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li class="active"><a href="main.php">빈방 관리</a></li>
        <li><a href="user.php">고객 관리</a></li>
        <li><a href="join_user_page.php">회원 가입</a></li>
        <li><a href="service_req.php">서비스 승인</a></li>
        <li><a href="notice_page.php">공지사항</a></li>
        <li><a href="ad_page.php">광고삽입</a></li>
        <?
          if( isset($_SESSION['user_id']) )
            echo "<li><a href='../logout.php'>로그 아웃</a></li>";
        ?>
      </ul>
    </div>
  </div>
</nav>
<div class="container">
  <div class="panel panel-default">
    <? 
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='원룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">원룸</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){      
    ?>
              <img src="<?echo $r['url']; ?>" width="150" height="150"/>
    <?
            }
    ?>
            </div>
    <?   
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='투룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">투룸</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="150" height="150"/>
    <?
            }
    ?>
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='쓰리룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">쓰리룸</div>
            <div class="panel-body">
    <?  
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="150" height="150"/>
    <?
            }
    ?>
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='오피스텔' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">오피스텔</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="150" height="150"/>
    <?
            }
    ?>  
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='기타' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">기타</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="150" height="150"/>
    <?
            }
        }
    ?>
            </div>
    <a href="insert_bang_page.php?b_id=<? echo $B_ID;?>"><button class="btn btn-default">확인</button></a>
  </div>  
</div>
</body>
</html>