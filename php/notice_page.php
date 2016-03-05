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
  <script> 
  <?
    $que = "SELECT * FROM notice Order by _id";
    $qux = mysqli_query($connect,$que);
    
  ?>
  $(document).ready(function(){
    <?
      $i=1;
      while( $r=mysqli_fetch_array($qux)){
    ?>
        $("#title<?echo $i;?>").val("<?echo $r['title'] ?>");
        $("#url<?echo $i;?>").val("<?echo $r['url'] ?>");
        $("#date<?echo $i;?>").val("<?echo $r['date'] ?>");
    <?
      $i++;
      }
    ?>
  });
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
        <li><a href="user.php">고객 관리</a></li>
        <li><a href="join_user_page.php">회원 가입</a></li>
        <li><a href="service_req.php">서비스 승인</a></li>
        <li class="active"><a href="notice_page.php" onclick="history.go(0)">공지사항</a></li>
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
  <form class="form-inline" role="form" action="update_notice.php" method="POST">
    <div class="panel panel-default">
      <div class="panel-heading">공지사항 정보</div>
      <div class="panel-body">
        <h5>제목에 ""쌍따움표대신에 ''홑따옴표를 사용해주세요 <>는 사용하면 안됩니다</h5>
        <table class="table">
          <thead>
            <tr>
              <th scope="col">제목</th>
              <th scope="col">URL</th>
            </tr>
          </thead>
          <tbody>
              <tr>
                <td>
                  <div class="form-group">
                    <label for="title1">제목: </label>
                    <input type="text" class="form-control" name="title1" id="title1">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <label for="url1">url: </label>
                    <input type="text" class="form-control" name="url1" id="url1">
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="form-group">
                    <label for="title2">제목: </label>
                    <input type="text" class="form-control" name="title2" id="title2">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <label for="url2">url: </label>
                    <input type="text" class="form-control" name="url2" id="url2">
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="form-group">
                    <label for="title3">제목: </label>
                    <input type="text" class="form-control" name="title3" id="title3">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <label for="url3">url: </label>
                    <input type="text" class="form-control" name="url3" id="url3">
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="form-group">
                    <label for="title4">제목: </label>
                    <input type="text" class="form-control" name="title4" id="title4">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <label for="url4">url: </label>
                    <input type="text" class="form-control" name="url4" id="url4">
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="form-group">
                    <label for="title5">제목: </label>
                    <input type="text" class="form-control" name="title5" id="title5">
                  </div>
                </td>
                <td>
                  <div class="form-group">
                    <label for="url5">url: </label>
                    <input type="text" class="form-control" name="url5" id="url5">
                  </div>
                </td>
              </tr>
          </tbody>
        </table>
        <input type="submit" class="btn btn-info" value="수정하기">
      </div>
    </div>
  <form>
</div>

</body>
</html>