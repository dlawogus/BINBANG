<?
header('Content-Type: text/html; charset=euc-kr');
if(!session_id()) session_start();

if($_GET["cmd"]=="exec"){

	// 업로드파일 이름별 분리
	$folderName = "./files/".session_id();
	if(is_dir($folderName)) {
		$dirObj=opendir($folderName);
		while(($fileStr = readdir($dirObj))!==false){
			if($fileStr!="." && $fileStr!=".."){
				$split_str = explode("__fu__",$fileStr);
				$_FILES[$split_str[0]]['tmp_name'][] = $folderName."/".$fileStr;
				$_FILES[$split_str[0]]['name'][] = urldecode($split_str[1]);
				$_FILES[$split_str[0]]['size'][] =  filesize($folderName."/".$fileStr);
				$dummyStat = stat($folderName."/".$fileStr);
				$_FILES[$split_str[0]]['ctime'][] =  $dummyStat["ctime"];
				$splitName[] = $split_str[0];
			}
		}
		closedir($dirObj);
		
		if($splitName){
			$splitName = array_unique($splitName);
			$splitName = array_merge($splitName);
			sort($splitName);			
			foreach($splitName as $key){
				if(count($_FILES[$key]["ctime"])==1){
					$_FILES[$key]['ctime'] = implode("",$_FILES[$key]['ctime']);
					$_FILES[$key]['tmp_name'] = implode("",$_FILES[$key]['tmp_name']);
					$_FILES[$key]['name'] = implode("",$_FILES[$key]['name']);
					$_FILES[$key]['size'] = implode("",$_FILES[$key]['size']);
				}else{
					array_multisort($_FILES[$key]["ctime"], $_FILES[$key]["tmp_name"], $_FILES[$key]["name"], $_FILES[$key]["size"]);
				}
			}
		}
	}

	// $_POST 출력
	while(list($key,$value)= each($_POST)){
		if(is_array($value)){
			while(list($key1,$value1)=each($value)){
			echo "\$_POST['".$key."'][".$key1."] = \"". $value1."\"<br>\n";
			}
		}else{
			echo "\$_POST['".$key."'] = \"". $value."\"<br>\n";
		}
	}

	echo "<br>";

	if($_FILES['fu01']['tmp_name']){
		echo "\$_FILES['fu01']['name']: ".$_FILES['fu01']['name']."<br>";
		echo "\$_FILES['fu01']['tmp_name']: ".$_FILES['fu01']['tmp_name']."<br>";
		echo "\$_FILES['fu01']['size']: ".$_FILES['fu01']['size']."<br><br>";
		//@unlink($folder_name."/".$_FILES['fu01']['tmp_name']);
	}

	if($_FILES['fu02']['tmp_name']){
		echo "\$_FILES['fu02']['name']: ".$_FILES['fu02']['name']."<br>";
		echo "\$_FILES['fu02']['tmp_name']: ".$_FILES['fu02']['tmp_name']."<br>";
		echo "\$_FILES['fu02']['size']: ".$_FILES['fu02']['size']."<br><br>";
		//@unlink($folder_name."/".$_FILES['fu02']['tmp_name']);
	}

	for($i=0;$i<count($_FILES['fu03']['tmp_name']);$i++){
		if(is_array($_FILES['fu03']['name'])){
			echo "\$_FILES['fu03']['name'][$i]: ".$_FILES['fu03']['name'][$i]."<br>";
			echo "\$_FILES['fu03']['tmp_name'][$i]: ".$_FILES['fu03']['tmp_name'][$i]."<br>";
			echo "\$_FILES['fu03']['size'][$i]: ".$_FILES['fu03']['size'][$i]."<br><br>";
			//@unlink($folder_name."/".$_FILES['fu03']['tmp_name'][$i]);
		}else{
			echo "\$_FILES['fu03']['name']: ".$_FILES['fu03']['name']."<br>";
			echo "\$_FILES['fu03']['tmp_name']: ".$_FILES['fu03']['tmp_name']."<br>";
			echo "\$_FILES['fu03']['size']: ".$_FILES['fu03']['size']."<br><br>";
			//@unlink($folder_name."/".$_FILES['fu03']['tmp_name']);
		}
	}

	if($_FILES['user_photo']['tmp_name']){
		echo "\$_FILES['user_photo']['name']: ".$_FILES['user_photo']['name']."<br>";
		echo "\$_FILES['user_photo']['tmp_name']: ".$_FILES['user_photo']['tmp_name']."<br>";
		echo "\$_FILES['user_photo']['size']: ".$_FILES['user_photo']['size']."<br><br>";
		//@unlink($folder_name."/".$_FILES['fu01']['tmp_name']);
	}

	//@rmdir($folder_name);
	echo "<p>[<a href='".$_SERVER["PHP_SELF"]."'>돌아가기</a>]";
	exit;
} // 결과페이지 끝


// 이전 쓰레기 파일 삭제(페이지가 열릴때)
$folderName = "./files/".session_id();
if(is_dir($folderName)) {
	$dirObj=opendir($folderName);
	while(($fileStr = readdir($dirObj))!==false){
		if($fileStr!="." && $fileStr!=".."){
			//@unlink($folderName."/".$fileStr);
		}
	}
	closedir($dirObj);
	//rmdir($folderName);
}

// 24시간 이상 지난 쓰레기 폴더 삭제(페이지가 열릴때)
$folderName = "./files";
$dirObj=opendir($folderName);
while(($dirStr = readdir($dirObj))!==false){
	if($dirStr!="." && $dirStr!=".."){
		if(time()-filemtime($folderName."/".$dirStr)>86400){
			$subDirObj=opendir($folderName."/".$dirStr);
			while(($fileStr = readdir($subDirObj))!==false){
				if($fileStr!="." && $fileStr!=".."){
					//@unlink($folderName."/".$dirStr."/".$fileStr);
				}
			}
			closedir($subDirObj);
			//rmdir($folderName."/".$dirStr);
		}
	}
}
closedir($dirObj)
?>

<html>
<head>
<title>파일 업로드 예제</title>
<link rel="stylesheet" href="/include/default.css" type="text/css">
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<script language="JavaScript" type="text/JavaScript" src="fu.js"></script>
<script language="JavaScript">
<!--
	function checkForm(){
		if(!document.getElementsByName('subject')[0].value){
			alert("제목을 입력하세요.");
			document.getElementsByName('subject')[0].focus();
			return;
		}
		if(!document.getElementsByName('name')[0].value){
			alert("이름을 입력하세요.");
			document.getElementsByName('name')[0].focus();
			return;
		}
		callSwfUpload('F');
	}
//-->
</script>
</head>

<body>
<form name="F" method="post" enctype="multipart/form-data" action="<?=$_SERVER["PHP_SELF"]?>?cmd=exec">
<!-- 플래시업로드에 enctype="multipart/form-data" 는 없어도 됩니다. -->

<table align="center" width="500" cellpadding="0" cellspacing="1" border="0" style="color:#FFFFFF;" style="margin-top:20px">
<tr><td align="center" style="font:bold 14pt 바탕;color:#8B9CB4;">지우넷 Flash Uploader</td></tr>
</table>

<table align="center" width="500" cellpadding="0" cellspacing="1" border="0" style="color:#FFFFFF;" style="margin-top:20px">
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center"  bgcolor="#A9C0D0" width="100">제목</td>
	<td style="padding:2px 0 2px 2px;"><input type="text" name="subject" value="플래시의 능력을 빌려 업로드진행를 보여줍니다." style="width:310px; height:21px; border:1px solid #CCCCCC;">
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">이름</td>
	<td style="padding:2px 0 2px 2px;"><input type="text" name="name" value="" style="width:310px; height:21px; border:1px solid #CCCCCC;">
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">심플(모든파일)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfSimpleUpload(
				movie_id='fu01', //파일폼 고유ID
				flash_width='400', //파일폼 너비 (기본값 400, 권장최소 300)
				limit_size='1024', // 업로드 제한용량 (기본값 10)MB
				file_type_name='모든파일', // 파일선택창 파일형식명 (예: 그림파일, 엑셀파일, 모든파일 등)
				allow_filetype='*.*', // 파일선택창 파일형식 (예: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // 업로드 불가형식
				border_color='0xCCCCCC', //테두리색상 (기본값 0xCCCCCC)
				upload_exe='upload.php' // 업로드 담당프로그램
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">싱글(모든파일)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfSingleUpload(
				movie_id='fu02', //파일폼 고유ID
				flash_width='400', //파일폼 너비 (기본값 400, 권장최소 300)
				limit_size='1024', // 업로드 제한용량 (기본값 10)MB
				file_type_name='모든파일', // 파일선택창 파일형식명 (예: 그림파일, 엑셀파일, 모든파일 등)
				allow_filetype='*.*', // 파일선택창 파일형식 (예: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // 업로드 불가형식
				border_color='0xCCCCCC', //테두리색상 (기본값 0xCCCCCC)
				upload_exe='upload.php' // 업로드 담당프로그램
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">멀티(그림파일)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfMultiUpload(
				movie_id='fu03', //파일폼 고유ID
				flash_width='400', //파일폼 너비 (기본값 400, 권장최소 300)
				list_rows='5', // 파일목록 행 (권장최소:4)
				limit_size='1024', // 업로드 제한용량 (기본값 10)MB
				file_type_name='그림파일', // 파일선택창 파일형식명 (예: 그림파일, 엑셀파일, 모든파일 등)				
				allow_filetype=' *.jpg *.jpeg *.gif *.png', // 파일선택창 파일형식 (예: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // 업로드 불가형식
				border_color='0xCCCCCC', //테두리색상 (기본값 0xCCCCCC)
				upload_exe='upload.php' // 업로드 담당프로그램
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">일반업로드</td>
	<td style="padding:2px 0 2px 2px;"><input type="file" name="user_photo"style="width:310px; height:21px; border:1px solid #CCCCCC;">
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr height = "40">
	<td colspan="2" align = "center" valign="bottom">
		<img src="http://demo.ziwoo.net/zb/images/zbtn_submit.gif" onClick="checkForm();">
		<img src="http://demo.ziwoo.net/zb/images/zbtn_reset.gif" onClick="reset();">
	</td>
</tr>
</table>

</form>
</body>
</html>

