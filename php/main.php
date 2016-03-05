<?php
  require 'dbconfig.php';
  require 'paging.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }
  
  $GU = $_GET['gu_search'];
  $DONG = $_GET['dong_search'];
  $BUILDING = $_GET['building_search'];
  $JIBUN = $_GET['jibun_search'];
  $CALL = $_GET['call_search'];
  /*
  $GU = $_GET['gu'];
  $DONG = $_GET['dong'];
  $BUILDING = $_GET['building'];
  $JIBUN = $_GET['jibun'];
  $CALL = $_GET['call'];*/

  $query = "";
  //$get_query = "";
  $cnt = 0;
  if( $GU != "" ){
    $query = "gu='".$GU."'";
    $get_query = "gu_search=".$GU."";
    $cnt++;
  }
  if( $DONG != "" ){
    if( $cnt == 0){
      $query = "dong like '%".$DONG."%'";
      $get_query = "dong_search=".$DONG."";
    }else{
      $query = $query."AND dong like '%".$DONG."%'";
      $get_query = $get_query."&dong_search=".$DONG."";
    }
    $cnt++;
  }
  if( $BUILDING != "" ){
    if( $cnt == 0){
      $query = "building_name like '%".$BUILDING."%'";
      $get_query = "building_search=".$BUILDING."";
    }else{
      $query = $query." AND building_name like '%".$BUILDING."%'";
      $get_query = $get_query."&building_search=".$BUILDING."";
    }
    $cnt++;
  }
  if( $JIBUN != "" ){
    if( $cnt == 0){
      $query = "sangse_juso like '%".$JIBUN."%'";
      $get_query = "jibun_search=".$JIBUN."";
    }else{
      $query = $query." AND sangse_juso like '%".$JIBUN."%'";
      $get_query = $get_query."&jibun_search=".$JIBUN."";
    }
    $cnt++;
  }
  if( $CALL != "" ){
    $C = add_hyphen($CALL);
    if( $cnt == 0){
      $query = "(call_1 like '%".$C."%') OR (call_2 like '%".$C."%') OR (call_3 like '%".$C."%') ";
      $get_query = "call_search=".$C."";
    }else{
      $query = $query." AND ((call_1 like '%".$C."%') OR (call_2 like '%".$C."%') OR (call_3 like '%".$C."%'))";
      $get_query = $get_query."&call_search=".$C."";
    }
    $cnt++;
  }

  if( $cnt != 0 ){
    $que1 = "SELECT count(*) FROM building WHERE ".$query."";        
  }else{
    $que1 = "SELECT count(*) FROM building ";
  }

  //echo $GU." ".$DONG." ".$BUILDING." ".$JIBUN." ".$CALL;
  //echo $get_query;  
  $qux1 = mysqli_query($connect,$que1);
  $r=mysqli_fetch_array($qux1);
  $count = $r['count(*)'];

  $total_data = $count; //전체 게시글 변수로 불러오도록 할것
  $num_per_page=20; //페이지당 표시할 갯수
  $page_per_list=10; //페이지 리스트 갯수
  if (!$page) $page = 1;

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
  <script src="./dong_search.js"></script> 
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script> 
  function formCheck(FORM)
  {
    if (FORM.submitted) return false;
    // 입력 값 체크 루틴
    FORM.action = 'insert_building.php';
    FORM.method = 'POST';
    FORM.submitted = true;
    return true;
  }
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
        <li class="active"><a href="main.php" onclick="history.go(0)">빈방 관리</a></li>
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

<form align='center' class="form-inline" role="form" method="POST" action="insert_building.php">
    <label for="gu">구: </label>
    <select class="form-control" id="gu" name="gu" onchange="dong_func(this.value);">
      <option value="">선택</option>
      <option value="동구">동구</option>
      <option value="영도구">영도구</option>
      <option value="부산진구">부산진구</option>
      <option value="동래구">동래구</option>
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
      <label for="building_pw">현관: </label>
      <input type="text" class="form-control" name="building_pw" id="building_pw">
    </div>
    <br>
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
    <br>
    <label for="boiler">난방: </label>
    <select class="form-control" name="boiler" id="boiler">
      <option value="전체">전체</option>
      <option value="도시가스">도시가스</option>
      <option value="심야전기">심야전기</option>
      <option value="전기판넬">전기판넬</option>
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
    <div class="form-group" >
      <label for="comments">건물전달사항: </label>
      <input type="text" class="form-control" name="comments" id="comments">
    </div>
    <input type="submit" class="btn btn-info" value="생성하기">
</form>
<br/><br/>
<form align='center' class="form-inline" role="form" action="main.php" method="GET">
    <label for="gu">구: </label>
    <select class="form-control" id="gu_search" name="gu_search" onchange="dong_search_func(this.value);">
      <option value="">선택</option>
      <option value="동구">동구</option>
      <option value="영도구">영도구</option>
      <option value="부산진구">부산진구</option>
      <option value="동래구">동래구</option>
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
    <select class="form-control" name="dong_search" id="dong_search">
      <option value="">선택</option> 
    </select>
    <div class="form-group">
      <label for="building">건물이름: </label>
      <input type="text" class="form-control" name="building_search">
    </div>
    <div class="form-group">
      <label for="jibun">지번: </label>
      <input type="text" class="form-control" name="jibun_search">
    </div>
    <div class="form-group">
      <label for="call">전화번호: </label>
      <input type="text" class="form-control" name="call_search">
    </div>
    <button type="submit">검색</button>
  </form>

<div class="container">
    <table class="table">
    <thead>
      <tr>
        <th scope="col">번호</th>
        <th scope="col">연락처1</th>
        <th scope="col">연락처2</th>
        <th scope="col">연락처3</th>
        <th scope="col">주차</th>
        <th scope="col">엘레베이터</th>
        <th scope="col">난방</th>
        <th scope="col">현관</th>
        <th scope="col">구</th>
        <th scope="col">동</th>
        <th scope="col">건물전달사항</th>
        <th scope="col">건물명</th>
        <th scope="col">매물</th>
        <th scope="col">지번</th>
        <th scope="col">관리</th>
      </tr>
    </thead>
    <tbody>
      <?php

          if( $cnt != 0 ){
            $que = "SELECT * FROM building WHERE ".$query." Order by b_id desc LIMIT ".($num_per_page*($page-1)).",".$num_per_page."";
            $qux = mysqli_query($connect,$que);          
          }else{
            $que = "SELECT * FROM building Order by b_id desc LIMIT ".($num_per_page*($page-1)).",".$num_per_page."";
            $qux = mysqli_query($connect,$que);
          }
    
          while( $row=mysqli_fetch_array($qux) ){
      ?>
            <tr>
            <td><a href="map.php?lat=<?echo $row['lat']?>&lng=<?echo $row['lng']?>"><? echo $row['b_id']?></td>
            <td><? echo $row['call_1_who']."</br>".$row['call_1']; ?></td>
            <td><? echo $row['call_2_who']."</br>".$row['call_2']; ?></td>
            <td><? echo $row['call_3_who']."</br>".$row['call_3']; ?></td>
            <td><? if( $row['is_park'] == 1){ echo "가능";}  
                   else echo "불가능"; ?></td>
            <td><? if( $row['is_elevator'] == 1){ echo "있음";}   
                   else echo "없음"; ?></td>
            <td><? echo $row['boiler']; ?></td>
            <td><? echo $row['building_password']; ?></td>
            <td><? echo $row['gu']; ?></td>
            <td><? echo $row['dong']; ?></td>
            <td><? echo $row['building_comment']; ?></td>
            <td><a href="insert_bang_page.php?b_id=<?echo $row['b_id'];?>"><? echo $row['building_name']; ?></a></td>
            <td><? $q = "SELECT count(*) FROM binbang WHERE building_id='".$row['b_id']."'";
                    $qx = mysqli_query($connect,$q);
                    $r=mysqli_fetch_array($qx);
                   echo $r['count(*)'];
            ?></td>
            <td><? echo $row['sangse_juso']; ?></td>
            <td><a href="update_build_page.php?b_id=<?echo $row['b_id'];?>">수정</a> | <a href="delete_building_main.php?b_id=<? echo $row['b_id'];?>">삭제</a></td>
            </tr>
      <?    
        }
      ?>
    </tbody>
  </table>
  <p align='center'>
  <?
    $nav=page_nav($total_data,$num_per_page,$page_per_list,$page, $get_query );
    echo $nav;
  ?>
  </p>
</div>

</body>
</html>