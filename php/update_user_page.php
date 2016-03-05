<?php
  require 'dbconfig.php';
  require 'paging.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }

  $COMPANY = $_GET['_id'];
  $que = "SELECT * FROM company WHERE _id='".$COMPANY."';";
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
    $("#company_name").val("<?echo $r['company_name'] ?>");
    $("#company_reg_num").val("<?echo $r['company_reg_num'] ?>");
    $("#relay_reg_num").val("<?echo $r['relay_reg_num'] ?>");
    $("#phone_number").val("<?echo $r['phone_number'] ?>");
    $("#fax_number").val("<?echo $r['fax_number'] ?>");
    $("#juso").val("<?echo $r['juso'] ?>");
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
    <div class="panel-heading">중개업소 정보 수정</div>
    <div class="panel-body">
      <form role="form" method="POST" action="update_user.php">
        <div class="form-group">
          <label for="company_name">중개업소명: </label>
          <input type="text" style="width:100%" class="form-control" name="company_name" id="company_name">
        </div>
        <div class="form-group">
          <label for="company_reg_num">사업자 등록번호: </label>
          <input type="text" style="width:100%" class="form-control" name="company_reg_num" id="company_reg_num">
        </div>
        <div class="form-group">
          <label for="relay_reg_num">중개업 등록번호: </label>
          <input type="text" style="width:100%" class="form-control" name="relay_reg_num" id="relay_reg_num">
        </div>
        <div class="form-group">
          <label for="phone_number">연락처: </label>
          <input type="text" style="width:100%" class="form-control" name="phone_number" id="phone_number">
        </div>
        <div class="form-group">
          <label for="fax_number">팩스번호: </label>
          <input type="text" style="width:100%" class="form-control" name="fax_number" id="fax_number">
        </div>
        <div class="form-group">
          <label for="juso">주소: </label>
          <input type="text" style="width:100%" class="form-control" name="juso" id="juso">
        </div>
        <input type="hidden" name="_id" value="<?echo $COMPANY;?>">
        <input type="submit" class="btn btn-info" value="수정하기">
      </form>
    </div>
  </div>
</div>
</body>
</html>