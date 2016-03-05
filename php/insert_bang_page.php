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
  $que = "SELECT * FROM building WHERE b_id='".$B_ID."';";
  $qux = mysqli_query($connect,$que);
  $r=mysqli_fetch_array($qux);
?>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="./dong.js"></script>
  <script src="./img_resize.js"></script>
  <link rel="stylesheet" href="./img_table.css" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
  <link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css">
  <script>
      //첨부파일 추가
    var rowIndex = 1;
               
    //첨부파일 삭제
    function deleteFile(form){
        if(rowIndex<1){
            return false;
        }else{
            rowIndex--;
            var getTable = document.getElementById("insertTable");
            getTable.deleteRow(rowIndex);
       }
    }

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#blah').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    function imagesSelected(myFiles) {
      for (var i = 0 , f; f = myFiles[i]; i++) {
        var imageReader = new FileReader();
        imageReader.onload = (function(aFile) {
          return function(e) {
          var span = document.createElement('span');
          span.innerHTML = ['<img class="images" src="', e.target.result,'" title="', aFile.name,'" />'].join('');
          document.getElementById('thumbs').insertBefore(span, null);
          };
        })(f);
        imageReader.readAsDataURL(f);
      }
    }

    function addFile(form){
        if(rowIndex > 5) return false;

        rowIndex++;
        var getTable = document.getElementById("insertTable");
        var oCurrentRow = getTable.insertRow(getTable.rows.length);
        var oCurrentCell = oCurrentRow.insertCell(0);
        oCurrentCell.innerHTML = "<p align='center'><INPUT TYPE='FILE' NAME='userfile[]' multiple='true'  size=25 align='center' onchange='imagesSelected(this.files);'></p>";
    }

    /////////건물 이미지 입력 함수
          //첨부파일 추가
    var rowIndex_g = 1;

    //첨부파일 삭제
    function deleteFile_g(form){
        if(rowIndex<1){
            return false;
        }else{
            rowIndex_g--;
            var getTable = document.getElementById("insertTable_g");
            getTable.deleteRow(rowIndex_g);
       }
    }

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#blah').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    function imagesSelected_g(myFiles) {
      for (var i = 0 , f; f = myFiles[i]; i++) {
        var imageReader = new FileReader();
        imageReader.onload = (function(aFile) {
          return function(e) {
          var span = document.createElement('span');
          span.innerHTML = ['<img class="images" src="', e.target.result,'" title="', aFile.name,'" />'].join('');
          document.getElementById('thumbs_g').insertBefore(span, null);
          };
        })(f);
        imageReader.readAsDataURL(f);
      }
    }

    function addFile_g(form){
        if(rowIndex > 5) return false;

        rowIndex_g++;
        var getTable = document.getElementById("insertTable_g");
        var oCurrentRow = getTable.insertRow(getTable.rows.length);
        var oCurrentCell = oCurrentRow.insertCell(0);
        oCurrentCell.innerHTML = "<p align='center'><INPUT TYPE='FILE' NAME='userfile_g[]' multiple='true'  size=25 align='center' onchange='imagesSelected_g(this.files);'></p>";
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
    <div class="panel-heading">건물 정보</div>
    <div class="panel-body">
      <p><? echo $r['building_name']." ".$r['dong']." ".$r['sangse_juso']; ?></p>
      <p style="display:inline"><b>난방 </b></p><p style="display:inline"><?echo $r['boiler'];?></p>
      <p style="display:inline"><b>엘레베이터 </b></p><p style="display:inline">
        <?if($r['is_elevator']==1) echo "있음";
          else echo "없음";?>  </p>
      <p style="display:inline"><b>주차여부 </b></p>
      <p style="display:inline">
        <?if($r['is_park']==1) echo "가능";
          else echo "불가능";?>  </p>
      <p style="display:inline"><b>현관 </b></p><p style="display:inline"><? echo $r['building_password']; ?></p>
      <p></p>
      <p style="display:inline"><b>건물전달사항 </b></p><p style="display:inline"><? echo $r['building_comment']; ?></p>
    </div>
  </div>
  <div class="panel panel-default">
    <? 
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='원룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">원룸</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){      
    
              echo "<img src=".$r['url']."?".time()." width='100' height='100'/>";
          
            }
    ?>
            <a href="delete_img.php?b_id=<? echo $B_ID;?>&bang_type=원룸"><button class="btn btn-default">삭제</button></a>   
            </div>
    <?   
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='투룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">투룸</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="100" height="100"/>
    <?
            }
    ?>
            <a href="delete_img.php?b_id=<? echo $B_ID;?>&bang_type=투룸"><button class="btn btn-default">삭제</button></a> 
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='쓰리룸' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">쓰리룸</div>
            <div class="panel-body">
    <?  
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="100" height="100"/>
    <?
            }
    ?>
            <a href="delete_img.php?b_id=<? echo $B_ID;?>&bang_type=쓰리룸"><button class="btn btn-default">삭제</button></a> 
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='오피스텔' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">오피스텔</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="100" height="100"/>
    <?
            }
    ?>  
            <a href="delete_img.php?b_id=<? echo $B_ID;?>&bang_type=오피스텔"><button class="btn btn-default">삭제</button></a> 
            </div>
    <?
        }
        $que = "SELECT * FROM building_image WHERE building_id='".$B_ID."' AND bang_type='기타' ";
        $qux = mysqli_query($connect,$que);
        if( mysqli_num_rows($qux) > 0 ){
    ?>
            <div class="panel-heading">기타</div>
            <div class="panel-body">
    <?
            while( $r=mysqli_fetch_array($qux) ){
    ?>
              <img src="<?echo $r['url']; ?>" width="100" height="100"/>
    <?
            }
    ?>
            <a href="delete_img.php?b_id=<? echo $B_ID;?>&bang_type=기타"><button class="btn btn-default">삭제</button></a> 
            </div>
    <?
        }
    ?>
  </div>
  <form align='center' name="bang_form" class="form-inline" role="form" method="POST" action="insert_img.php" enctype="multipart/form-data">
    <table width="100%" border="1" cellspacing=0 cellpadding=5>
      <tr bgcolor="#F2F2F2">
        <td class="tableheader" align="left">
        <p>건물 이미지 업로드</p>
        </td>
      </tr>
      <tr>
        <td align="left" height="105" ondragenter="return false" ondragover="return false" >    
          <output id="thumbs_g"></output>
        </td>
      </tr>
      <tr>
        <td align="center">
          <label for="bang_type_g">구조: </label>
          <select class="form-control" id="bang_type_g" name="bang_type_g">
            <option value="">선택</option>
            <option value="원룸">원룸</option>
            <option value="투룸">투룸</option>
            <option value="쓰리룸">쓰리룸</option>
            <option value="오피스텔">오피스텔</option>
            <option value="기타">기타</option>
          </select>
          <p style="display:inline"><b>대표 이미지</b></p>
          <input type="file" id="input" name="userfile_g[]" size="10" onchange="imagesSelected_g(this.files)" style="display:inline" />
          <p style="display:inline"><b>상세 이미지</b></p>
          <input type="file" id="input" name="userfile_g[]" size="10" multiple="true" onchange="imagesSelected_g(this.files)"style="display:inline" />
          <input type="button" value="지우기" onClick="location.reload();" border=0 style='cursor:hand'>
          <p style="display:inline">이미지 추가</p>
          <input type="button" value="추가" onClick="addFile_g(this.form)" border=0 style='cursor:hand'>
          <input type="button" value="삭제" onClick='deleteFile_g(this.form)' border=0 style='cursor:hand'>
        </td>
        <table width="100%" border="1" cellspacing=0 cellpadding=5 id='insertTable_g'>
        </table>
      </tr>
    </table>
    <input type="hidden" name="b_id" value="<?echo $B_ID;?>">
    <input type="submit" class="btn btn-info" value="이미지 업로드">
  </form>
<!--
  <form action="upload_test.php"
    class="dropzone"
    enctype="multipart/form-data"
    id="my-dropzone"
    method="post">
</form>
-->
  <!--
  <div class="panel panel-default">
    <div class="panel-heading">사진 업로드</div>
    <div class="panel-body">
      <form action="file-upload.php" method="post" enctype="multipart/form-data"><br />
       <input name="userfile[]" type="file" onchange="readURL(this);" multiple/><img style="width:15%" id="blah" src="#" alt="your image" onload="resize(this)"/><br />
       <input name="userfile[]" type="file" /><br />
       <input name="userfile[]" type="file" /><br />
       <input name="userfile[]" type="file" /><br />
       <input type="submit" value="파일 전송" />
     </form>
    </div>
  </div>
-->
  <form align='center' name="bang_form" class="form-inline" role="form" method="POST" action="insert_bang.php" enctype="multipart/form-data">
  <table width="100%" border="1" cellspacing=0 cellpadding=5>
    <tr bgcolor="#F2F2F2">
      <td class="tableheader" align="left">
      <p>방 지정 이미지 업로드</p>
      </td>
    </tr>
    <tr>
      <td align="left" height="105" ondragenter="return false" ondragover="return false" >    
        <output id="thumbs"></output>
      </td>
    </tr>
    <tr>
      <td align="center">
        <p style="display:inline"><b>대표 이미지</b></p>
        <input type="file" id="input" name="userfile[]" size="10" onchange="imagesSelected(this.files)" style="display:inline" />
        <p style="display:inline"><b>상세 이미지</b></p>
        <input type="file" id="input" name="userfile[]" size="10" multiple="true" onchange="imagesSelected(this.files)"style="display:inline" />
        <input type="button" value="지우기" onClick="location.reload();" border=0 style='cursor:hand'>
        <p style="display:inline">이미지 추가</p>
        <input type="button" value="추가" onClick="addFile(this.form)" border=0 style='cursor:hand'>
        <input type="button" value="삭제" onClick='deleteFile(this.form)' border=0 style='cursor:hand'>
      </td>
      <table width="100%" border="1" cellspacing=0 cellpadding=5 id='insertTable'>
      </table>
    </tr>
  </table>
  <input type="hidden" name="MAX_FILE_SIZE" value="30000" />
  <div>
      <label for="bang_type">구조: </label>
      <select class="form-control" id="bang_type" name="bang_type">
        <option value="">선택</option>
        <option value="원룸">원룸</option>
        <option value="투룸">투룸</option>
        <option value="쓰리룸">쓰리룸</option>
        <option value="오피스텔">오피스텔</option>
        <option value="기타">기타</option>
      </select>
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
      <input type="submit" class="btn btn-info" value="생성하기">
    </form>
  </div>
    <br/>
    <table class="table">
    <thead>
      <tr>
        <th scope="col">번호</th>
        <th scope="col">호수</th>
        <th scope="col">가격</th>
        <th scope="col">기준금</th>
        <th scope="col">관리비</th>
        <th scope="col">인터넷</th>
        <th scope="col">수도</th>
        <th scope="col">유선</th>
        <th scope="col">빈방여부</th>
        <th scope="col">방구조</th>
        <th scope="col">방형태</th>
        <th scope="col">방옵션</th>
        <th scope="col">비고</th>
        <th scope="col">삭제</th>
      </tr>
    </thead>
    <tbody>
      <?php
          $que1 = "SELECT * FROM binbang WHERE building_id='".$B_ID."' Order by _id desc;";
          $qux1 = mysqli_query($connect,$que1);
        
          while( $row=mysqli_fetch_array($qux1) ){
      ?>
            <tr>
            <td><a href="view_bang.php?b_id=<? echo $B_ID;?>&_id=<? echo $row['_id'];?>" ><?echo $row['_id'];?></a></td>
            <td><? echo $row['building_hosu']; ?></td>
            <td><? if( $row['monthly_rental'] == "" ) echo $row['deposit']; 
                    else echo $row['deposit']."/".$row['monthly_rental'];?>
                  </td>
            <td><? echo $row['base_price']; ?></td>
            <td><? echo $row['manage_price']; ?></td>
            <td><? if( $row['is_manage_internet'] == 1){ echo "포함";}  
                   else echo "불포함"; ?></td>
            <td><? if( $row['is_manage_sudo'] == 1){ echo "포함";}   
                   else echo "불포함"; ?></td>
            <td><? if( $row['is_manage_yusun'] == 1){ echo "포함";}   
                   else echo "불포함"; ?></td>   
            <td><? echo $row['empty']; ?></td>
            <td><? echo $row['bang_type']; ?></td>
            <td><? echo $row['bang_type_1']; ?></td>
            <td><? echo $row['bang_option']; ?></td>
            <td><? echo $row['comment']; ?></td>
            <td><a href="delete_bang.php?_id=<? echo $row['_id'];?>&b_id=<? echo $B_ID;?>">삭제</a></td>
            </tr> 
      <?    
        }
      ?>
    </tbody>
  </table>
</div>
</body>
</html>