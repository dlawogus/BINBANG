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
  <script src="./dong.js"></script> 
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
        <li><a href="service_req.php">서비스 승인</a></li>
        <li><a href="notice_page.php">공지사항</a></li>
        <li class="active"><a href="ad_page.php" onclick="history.go(0)">광고삽입</a></li>
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
    <div class="panel-heading">현재 광고</div>
    <div class="panel-body">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">번호</th>
            <th scope="col">이미지</th>
            <th scope="col">전화번호</th>
            <th scope="col">생성일</th>
            <th scope="col">삭제</th>
          </tr>
        </thead>
        <tbody>
      <?
          $que = "SELECT * FROM advertise Order by _id desc Limit 5";
          $qux = mysqli_query($connect,$que);
          while( $row=mysqli_fetch_array($qux) ){     
      ?>
          <tr>
            <td><b><?echo $row['_id'];?></b></td>
            <td><img src="<?echo $row['url']; ?>" width="360" height="90"/></td> 
            <td><b><?echo $row['call'];?></b></td>
            <td><b><?echo $row['date'];?></b></td> 
            <td><a href="delete_ad.php?_id=<? echo $row['_id'];?>"><button>삭제</button></a></td>
          </tr>
      <?
          }
      ?>
        </tbody>
      </table>
    </div>
  </div>     
  <form class="form-inline" role="form" action="insert_ad.php" method="POST" enctype="multipart/form-data">
    <div class="panel panel-default">
      <div class="panel-heading">광고 삽입</div>
      <div class="panel-body">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">파일</th>
              <th scope="col">전화번호</th>
            </tr>
          </thead>
          <tbody>
              <tr>
                <td>
                  <div class="form-group">
                    <input type="file" class="form-control" name="adfile" id="adfile">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <input type="text" class="form-control" name="call" id="call">
                  </div>
                </td>
              </tr>
          </tbody>
        </table>
        <input type="submit" class="btn btn-info" value="생성하기">
      </div>
    </div>
  <form>
</div>
</body>
</html>