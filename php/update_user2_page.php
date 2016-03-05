<?php
  require 'dbconfig.php';
  require 'paging.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }

  $USER_ID = $_GET['user_id'];
  $que = "SELECT * FROM users WHERE user_id='".$USER_ID."';";
  $qux = mysqli_query($connect,$que);
  $r=mysqli_fetch_array($qux);
?>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
    $("#user_name").val("<?echo $r['user_name'] ?>");
    $("#company_title").val("<?echo $r['company_title'] ?>");
    $("#phone").val("<?echo $r['phone'] ?>");
    $("#call_number").val("<?echo $r['call_number'] ?>");
    $("#email").val("<?echo $r['email'] ?>");
    $("#pay_start").val("<?echo $r['pay_start'] ?>");
    $("#pay_end").val("<?echo $r['pay_end'] ?>");
    $("#token").val("<?echo $r['token'] ?>");
  });
  </script>

  </script>
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
        <li class="active"><a href="#" onclick="history.go(0)">고객 관리</a></li>
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
    <div class="panel-heading">사용자 정보 수정</div>
    <div class="panel-body">
      <form role="form" method="POST" action="update_user2.php">
        <div class="form-group">
          <label for="user_name">사용자명: </label>
          <input type="text" style="width:100%" class="form-control" name="user_name" id="user_name">
        </div>
        <div class="form-group">
          <label for="company_title">직책: </label>
          <input type="text" style="width:100%" class="form-control" name="company_title" id="company_title">
        </div>
        <div class="form-group">
          <label for="phone">연락처: </label>
          <input type="text" style="width:100%" class="form-control" name="phone" id="phone">
        </div>
        <div class="form-group">
          <label for="call_number">일반전화: </label>
          <input type="text" style="width:100%" class="form-control" name="call_number" id="call_number">
        </div>
        <div class="form-group">
          <label for="email">이메일: </label>
          <input type="text" style="width:100%" class="form-control" name="email" id="email">
        </div>
        <div class="form-group">
          <label for="pay_start">사용 시작일: </label>
          <input type="text" style="width:100%" class="form-control" name="pay_start" id="pay_start">
        </div>
        <div class="form-group">
          <label for="pay_end">사용 종료일: </label>
          <input type="text" style="width:100%" class="form-control" name="pay_end" id="pay_end">
        </div>
        <div class="form-group">
          <label for="token">인증키: </label>
          <input type="text" style="width:100%" class="form-control" name="token" id="token">
        </div>
        <input type="hidden" name="user_id" value="<?echo $USER_ID;?>">
        <input type="submit" class="btn btn-info" value="수정하기">
      </form>
    </div>
  </div>
</div>
</body>
</html>