<?php
  require 'dbconfig.php';
  require 'paging.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }

  $B_ID = $_GET['b_id'];

  function add_hyphen($hp_no){
     return preg_replace("/(0(?:2|[0-9]{2}))([0-9]+)([0-9]{4}$)/", "\\1-\\2-\\3", $hp_no); 
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
    $que = "SELECT * FROM building WHERE b_id='".$B_ID."';";
    $qux = mysqli_query($connect,$que);
    $r=mysqli_fetch_array($qux);
  ?>
  $(document).ready(function(){
    dong_func("<?echo $r['gu'] ?>");
    $("#gu").val("<?echo $r['gu'] ?>");
    $("#dong").val("<?echo $r['dong'] ?>");
    $('#jibun').val("<?echo $r['sangse_juso'] ?>");
    $('#building').val("<?echo $r['building_name'] ?>");
    $('#building_pw').val("<?echo $r['building_password'] ?>");
    $('#call1').val("<?echo $r['call_1'] ?>");
    $('#call2').val("<?echo $r['call_2'] ?>");
    $('#call3').val("<?echo $r['call_3'] ?>");
    $("#call1_who").val("<?echo $r['call_1_who'] ?>");
    $("#call2_who").val("<?echo $r['call_2_who'] ?>");
    $("#call3_who").val("<?echo $r['call_3_who'] ?>");
    $("#boiler").val("<?echo $r['boiler'] ?>");
    $("#park").val("<?echo $r['is_park'] ?>");
    $("#elevator").val("<?echo $r['is_elevator'] ?>");
    $('#comments').val("<?echo $r['building_comment'] ?>");
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
  <form align='center' class="form-inline" role="form" action="update_building.php" method="POST">
    <div class="panel panel-default">
      <div class="panel-heading">건물정보 수정</div>
      <div class="panel-body">
        <label for="gu">구: </label>
        <select class="form-control" id="gu" name="gu" onchange="dong_func(this.value);">
          <option value="">선택</option>
          <option value="동구">동구</option>
          <option value="영도구">영도구</option>
          <option value="동래구">동래구</option>
          <option value="부산진구">부산진구</option>
          <option value="서구">서구</option>
          <option value="남구">남구</option>
          <option value="북구">북구</option>
          <option value="해운대구">해운대구</option>
          <option value="금정구">금정구</option>
          <option value="강서구">강서구</option>
          <option value="연제구">연제구</option>
          <option value="수영구">수영구</option>
          <option value="사상구">사상구</option>
          <option value="기장군">기장군</option>
          <option value="중구">중구</option>
          <option value="사하구">사하구</option>
        </select>
        <label for="dong">동명: </label>
        <select class="form-control" name="dong" id="dong">
          <option value="">선택</option> 
        </select>
        <div class="form-group">
          <label for="jibun">지번: </label>
          <input type="text" class="form-control" name="jibun" id="jibun">
        </div>
        <div class="form-group">
          <label for="building">건물이름: </label>
          <input type="text" class="form-control" name="building" id="building">
        </div>
        <div class="form-group">
          <label for="password">현관: </label>
          <input type="text" class="form-control" name="building_pw" id="building_pw">
        </div>
        <br/><br/>
        <label for="call1_who">연락처1: </label>
        <select class="form-control" name="call1_who" id="call1_who">
          <option value="">선택</option>
          <option value="사장님">사장님</option>
          <option value="사모님">사모님</option> 
          <option value="관리인">관리인</option> 
          <option value="자택">자택</option>  
        </select>
        <input type="text" class="form-control" name="call1" id="call1">
        <label for="call2_who">연락처2: </label>
        <select class="form-control" name="call2_who" id="call2_who">
          <option value="">선택</option>
          <option value="사장님">사장님</option>
          <option value="사모님">사모님</option> 
          <option value="관리인">관리인</option> 
          <option value="자택">자택</option>  
        </select>
        <input type="text" class="form-control" name="call2" id="call2">

        <label for="call3_who">연락처3: </label>
        <select class="form-control" name="call3_who" id="call3_who">
          <option value="">선택</option>
          <option value="사장님">사장님</option>
          <option value="사모님">사모님</option> 
          <option value="관리인">관리인</option> 
          <option value="자택">자택</option> 
        </select>
        <input type="text" class="form-control" name="call3" id="call3">
        <br/><br/>
        <label for="boiler">난방: </label>
        <select class="form-control" name="boiler" id="boiler">
          <option value="전체">전체</option>
          <option value="도시가스">도시가스</option>
          <option value="심야전기">심야전기</option> 
          <option value="심야전기">전기판넬</option> 
          <option value="LPG">LPG</option> 
          <option value="기름보일러">기름보일러</option>
          <option value="기타">기타</option> 
        </select>
        <label for="elevator">엘레베이터: </label>
        <select class="form-control" name="elevator" id="elevator">
          <option value="전체">전체</option>
          <option value="1">있음</option>
          <option value="0">없음</option>
        </select>
        <label for="park">주차여부: </label>
        <select class="form-control" name="park" id="park">
          <option value="전체">전체</option>
          <option value="1">가능</option>
          <option value="0">불가능</option>
        </select>
        <div class="form-group">
          <label for="comments">건물전달사항: </label>
          <input type="text" class="form-control" name="comments" id="comments">
        </div>
        <br/><br/>
        <input type="submit" class="btn btn-info" value="수정하기">
        <input type="hidden" name="b_id" value="<?echo $B_ID; ?>">
      </div>
    </div>
  </form>
</div>

</body>
</html>