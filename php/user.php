<?php
  require 'dbconfig.php';
  require 'paging.php';
  $connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW, $DB_NAME);
  session_start();
  if( !isset($_SESSION['user_id']) ) {
    echo "<meta http-equiv='refresh' content='0;url=index.html'>";
    exit;
  }
  $que1 = "SELECT count(*) FROM company";
  $qux1 = mysqli_query($connect,$que1);
  $r=mysqli_fetch_array($qux1);
  $count = $r['count(*)'];
  $query="id={mid}&no={$no}";
  $total_data = $count; //전체 게시글 변수로 불러오도록 할것
  $num_per_page=20; //페이지당 표시할 갯수
  $page_per_list=10; //페이지 리스트 갯수
  if (!$page) $page = 1;

  $COMPANY = $_POST['company_name'];
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

    function serviceStop(frm){
      if ($("input[name='check_id[]']:checked").length == 0) { 
          window.alert("체크 박스를 선택해주세요"); 
          return;
      } 
      document.frmList.action = "service_stop.php";
      document.frmList.submit();
    }
    function serviceStart(frm){
      if ($("input[name='check_id[]']:checked").length == 0) { 
          window.alert("체크 박스를 선택해주세요"); 
          return;
      } 
      document.frmList.action = "service_start.php";
      document.frmList.submit();
    }
    function serviceQuit(frm){
      if ($("input[name='check_id[]']:checked").length == 0) { 
          window.alert("체크 박스를 선택해주세요"); 
          return;
      } 
      document.frmList.action = "service_quit.php";
      document.frmList.submit();
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
  <p align='center'>
    <button type="button" onclick="#" style="color:white ; background-color:black ; width:20%" >중개업체관리</button>
    <button type="button" onclick="location.href='user2.php'"  style="color:black ; background-color:white ; width:20%" >유저관리</button>
  </p>
  <p style="display:inline">선택한 업체 <button type="button" onclick="serviceStop(document.frmList);" >서비스중지</button>
    <button type="button" onclick="serviceStart(document.frmList);" >서비스활성</button>
    <button type="button" onclick="serviceQuit(document.frmList);" >강제탈퇴</button>
     서비스를 변경하면 업체에 소속된 직원들의 서비스가 변경됩니다
    <form style='display:inline' align='right' class="form-inline" role="form" action="user.php" method="POST">
      <div class="form-group">
        <label for="company_name">중개업소명: </label>
        <input type="text" class="form-control" name="company_name">
      </div>
      <button type="submit">검색</button>
    </form>
  </p>
    <table class="table">
    <thead>
      <tr>
        <th scope="col">선택</th>
        <th scope="col">중개업소</th>
        <th scope="col">대표자</th>
        <th scope="col">주소</th>
        <th scope="col">대표연락처</th>
        <th scope="col">사업자등록</th>
        <th scope="col">중개업등록</th>
        <th scope="col">승인</th>
        <th scope="col">수정</th>
      </tr>
    </thead>
    <tbody>
      <form role="form" name="frmList" method="POST" >
      <?php 
          if( isset($COMPANY) || $COMPANY != "" )
            $que = "SELECT * FROM company WHERE company_name like '%".$COMPANY."%' Order by _id desc LIMIT ".($num_per_page*($page-1)).",".$num_per_page."";
          else
            $que = "SELECT * FROM company Order by _id desc LIMIT ".($num_per_page*($page-1)).",".$num_per_page."";
          $qux = mysqli_query($connect,$que);
          while( $row=mysqli_fetch_array($qux) ){
      ?>
            <tr>
            <td>
                <input type="checkbox" name="check_id[]" value="<? echo $row['_id']; ?>" >
            </td>
            <td><? echo $row['company_name']; ?></td>
            <td><? echo $row['ceo_id']; ?></td>
            <td><? echo $row['juso']; ?></td>
            <td><? echo $row['phone_number']; ?></td>
            <td><? echo $row['company_reg_num']; ?></td>
            <td><? echo $row['relay_reg_num'];  ?></td>
            <td><? if( $row['is_avail'] == 1 ) echo "사용";
                    else echo "중지" ?></td>
            <td><a href="update_user_page.php?_id=<?echo $row['_id'];?>">수정</a></td>
            </tr>
      <?    
        }
      ?>
      </from>
    </tbody>
  </table>
  <p align='center'>
    <?
      $nav=page_nav($total_data,$num_per_page,$page_per_list,$page,$query);
      echo $nav;
    ?>
  </p>
</div>

</body>
</html>