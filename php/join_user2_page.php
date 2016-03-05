<?
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
	<script>
	function join(frmList){
		if ( $("#user_id").val()  == "") { 
		  window.alert("아이디를 입력해주세요"); 
		  return;
		}
		if ( $("#user_pw").val()  == "") { 
		  window.alert("비밀번호를 확인해주세요"); 
		  return;
		} 
		if ( $("#user_pw").val()  !=  $("#user_repw").val()) { 
		  window.alert("비밀번호를 확인해주세요"); 
		  return;
		} 
		if ( $("#phone").val()  == "") { 
		  window.alert("전화번호를 입력해주세요"); 
		  return;
		} 
		if ( $("#user_name").val()  == "") { 
		  window.alert("사용자명을 입력해주세요"); 
		  return;
		} 
		document.frmList.method = "POST";
		document.frmList.action = "join_user2.php";
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
	        <li><a href="main.php" onclick="history.go(0)">빈방 관리</a></li>
	        <li><a href="user.php">고객 관리</a></li>
	        <li class="active"><a href="join_user_page.php">회원 가입</a></li>
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
		<button type="button" onclick="location.href='join_user_page.php'" style="color:black ; background-color:white ; width:20%" >대표님 회원가입</button>
		<button type="button" onclick="#" style="color:white ; background-color:black ; width:20%">직원회원가입</button>
	</p>
	<div class="panel panel-default">
		<div class="panel-heading">회원가입</div>
		<div class="panel-body">
			<form role="form" name="frmList">
	        <div class="form-group">
	          <label for="ceo_id">*소속 부동산 대표님 아이디: </label>
	          <input type="text" class="form-control" name="ceo_id" id="ceo_id">
	        </div>
	       	<div class="form-group">
	          <label for="user_name">*사용자명: </label>
	          <input type="text" class="form-control" name="user_name" id="user_name">
	        </div>
	       	<div class="form-group">
	          <label for="user_id">*아이디: </label>
	          <input type="text" class="form-control" name="user_id" id="user_id">
	        </div>
	       	<div class="form-group">
	          <label for="user_pw">*비밀번호: </label>
	          <input type="password" class="form-control" name="user_pw" id="user_pw">
	        </div>	        
	       	<div class="form-group">
	          <label for="user_repw">*비밀번호 확인: </label>
	          <input type="password" class="form-control" name="user_repw" id="user_repw">
	        </div>
	       	<div class="form-group">
	          <label for="company_title">직함: </label>
	          <input type="text" class="form-control" name="company_title" id="company_title">
	        </div> 
	    	<div class="form-group">
	          <label for="phone">*전화번호: -없이</label>
	          <input type="text" class="form-control" name="phone" id="phone">
	        </div>	
	       	<div class="form-group">
	          <label for="call">일반전화: </label>
	          <input type="text" class="form-control" name="call" id="call">
	        </div>
	       	<div class="form-group">
	          <label for="email">이메일: </label>
	          <input type="email" class="form-control" name="email" id="email">
	        </div>
	        <input class="btn btn-info" value="회원가입" onclick="join(document.frmList);">
	    	</form>
		</div>
	</div>
</div>
</body>
</html>