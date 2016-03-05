<?php
  require 'dbconfig.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }
?>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
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
        <li><a href="main.php">빈방 관리</a></li>
        <li><a href="user.php">고객 관리</a></li>
        <li><a href="join_user_page.php">회원 가입</a></li>
        <li class="active"><a href="#" onclick="history.go(0)">서비스 승인</a></li>
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
  <table class="table">
    <thead>
      <tr>
        <th scope="col">아이디</th>
        <th scope="col">이름</th>
        <th scope="col">기간</th>
        <th scope="col">연락처</th>
        <th scope="col">신청날짜</th>
        <th scope="col">승인여부</th>
      </tr>
    </thead>
    <tbody>
      <?php 
          $que = "SELECT * FROM service_req, users WHERE service_req.user_id=users.user_id Order by _id desc";
          $qux = mysqli_query($connect,$que);
          while( $row=mysqli_fetch_array($qux) ){
      ?>
            <tr>
            <td><? echo $row['user_id'] ?></td>
            <td><? echo $row['user_name'] ?></td>
            <td><? echo $row['period']."개월" ?></td>
            <td><? echo $row['phone'] ?></td>
            <td><? echo $row['req_date'] ?></td>
            <td><a href="req_ok.php?user_id=<?echo $row['user_id'];?>&period=<?echo $row['period']?>">승인</a> | <a href="req_delete.php?user_id=<? echo $row['user_id'];?>">삭제</a></td>
            </tr>
      <?    
        }
      ?>
    </tbody>
  </table>

</div>

</body>
</html>