<?php
  require 'dbconfig.php';
  header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
  header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
  header("Cache-Control: no-store, no-cache, must-revalidate"); 
  header("Cache-Control: post-check=0, pre-check=0", false); 
  header("Pragma: no-cache");
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }
  $B_ID = $_GET['b_id'];
  $_ID = $_GET['_id'];

?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv='Content-type' content='text/html; charset=utf-8'>
  <meta http-equiv="cache-control" content="no-cache, must-revalidate">
  <meta http-equiv="pragma" content="no-cache">
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
    <div class="panel-heading">방 정보</div>
    <div class="panel-body">
      <?
        $que = "SELECT * FROM binbang_image WHERE bang_id='".$_ID."'";
        $qux = mysqli_query($connect,$que);
        while( $r=mysqli_fetch_array($qux) ){
      
          echo "<img src=".$r['url']."?".time()." width='150' height='150'/>";
      
        }
        $que = "SELECT * FROM binbang WHERE _id='".$_ID."'";
        $qux = mysqli_query($connect,$que);
        $r=mysqli_fetch_array($qux);
      ?>
      <p></p>
      <form class="form-inline" role="form" action="update_bang.php" method="POST">
        <label for="bang_type_1">형태: </label>
        <select class="form-control" id="bang_type_1" name="bang_type_1">
          <option value="">전체</option>
          <option value="오픈형">오픈형</option>
          <option value="분리형">분리형</option>
          <option value="복층형">복층형</option>
        </select>
        <div class="form-group">
          <label for="deposit">보증금: </label>
          <input type="text" class="form-control" name="deposit" id="deposit">
        </div>
        <div class="form-group">
          <label for="rental">월세: </label>
          <input type="text" class="form-control" name="rental" id="rental">
        </div>
        <div class="form-group">
          <label for="manage">관리비: </label>
          <input type="text" class="form-control" name="manage" id="manage">
        </div>
        <div class="panel panel-default" style="display:inline-block">
          <div class="panel-heading">관리비 포함사항</div>
          <div class="panel-body"> 
            <input type="checkbox" name="internet" value="1" checked> 인터넷
            <input type="checkbox" name="sudo" value="1" checked> 수도
            <input type="checkbox" name="yusun" value="1" checked> 유선
          </div>
        </div>
        <div class="form-group">
          <label for="base_price">기준금: </label>
          <input type="text" class="form-control" name="base_price" id="base_price">
        </div>
        <p style="display:inline-block"><input type="checkbox" name="deposit_possible" value="1"> 보증금 조정가능</p>
        <div class="form-group" style="display:inline-block">
          <label for="empty">공실여부: </label>
          <select class="form-control" id="empty" name="empty">
            <option value="">전체</option>
            <option value="세유">세유</option>
            <option value="빈방">빈방</option>
          </select>
        </div>
        <div class="form-group">
          <label for="hosu">호수: </label>
          <input type="text" class="form-control" name="hosu" id="hosu">
        </div>
        <div class="form-group">
          <label for="option">옵션: </label>
          <input type="text" class="form-control" name="option" id="option">
        </div>
        <div class="form-group">
          <label for="comment">비고: </label>
          <input type="text" class="form-control" name="comment" id="comment">
        </div>
        <input type="hidden" name="b_id" value="<?echo $B_ID;?>">
        <input type="hidden" name="_id" value="<?echo $_ID;?>">
        <p></p>
        <h5>월세의 보증금을 변경할 경우 월세액과 보증금을 함께 입력해야됩니다. 보증금만 변경할 경우 전세로 전환됩니다</h5>
        <p></p>
      <p><b>호수</b>  <?echo $r['building_hosu']?></p>
      <p><b>가격</b>  <?echo $r['deposit']?>/<?echo $r['monthly_rental']?></p>
      <p><b>기준금</b>  <?echo $r['base_price']?></p>
      <p><b>전/월세</b>  <?echo $r['price_type']?></p>
      <p><b>관리비</b>  <?echo $r['manage_price']?></p>
      <p><b>구조</b>  <?echo $r['bang_type']?> <?echo $r['bang_type_1']?></p>
      <p><b>보증금 조정</b>  <? if($r['bang_type']=="1") echo "가능";
                            else echo "불가능"; ?></p>
      <p><b>공실여부</b>  <?echo $r['empty']?></p>
      <p><b>방옵션</b>   <?echo $r['bang_option']?></p>
      <p><b>비고</b>  <?echo $r['comment']?></p>
      <button type="submit" class="btn btn-default">수정</button>
      </form>
      <a href="delete_bang.php?_id=<? echo $r['_id'];?>&b_id=<? echo $B_ID;?>"><button class="btn btn-default">삭제</button></a>
      <a href="insert_bang_page.php?b_id=<? echo $B_ID;?>"><button class="btn btn-default">확인</button></a>
    </div>
  </div>  
</div>
</body>
</html>