<?
header('Content-Type: text/html; charset=euc-kr');
if(!session_id()) session_start();

if($_GET["cmd"]=="exec"){

	// ���ε����� �̸��� �и�
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

	// $_POST ���
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
	echo "<p>[<a href='".$_SERVER["PHP_SELF"]."'>���ư���</a>]";
	exit;
} // ��������� ��


// ���� ������ ���� ����(�������� ������)
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

// 24�ð� �̻� ���� ������ ���� ����(�������� ������)
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
<title>���� ���ε� ����</title>
<link rel="stylesheet" href="/include/default.css" type="text/css">
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<script language="JavaScript" type="text/JavaScript" src="fu.js"></script>
<script language="JavaScript">
<!--
	function checkForm(){
		if(!document.getElementsByName('subject')[0].value){
			alert("������ �Է��ϼ���.");
			document.getElementsByName('subject')[0].focus();
			return;
		}
		if(!document.getElementsByName('name')[0].value){
			alert("�̸��� �Է��ϼ���.");
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
<!-- �÷��þ��ε忡 enctype="multipart/form-data" �� ��� �˴ϴ�. -->

<table align="center" width="500" cellpadding="0" cellspacing="1" border="0" style="color:#FFFFFF;" style="margin-top:20px">
<tr><td align="center" style="font:bold 14pt ����;color:#8B9CB4;">����� Flash Uploader</td></tr>
</table>

<table align="center" width="500" cellpadding="0" cellspacing="1" border="0" style="color:#FFFFFF;" style="margin-top:20px">
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center"  bgcolor="#A9C0D0" width="100">����</td>
	<td style="padding:2px 0 2px 2px;"><input type="text" name="subject" value="�÷����� �ɷ��� ���� ���ε����ฦ �����ݴϴ�." style="width:310px; height:21px; border:1px solid #CCCCCC;">
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">�̸�</td>
	<td style="padding:2px 0 2px 2px;"><input type="text" name="name" value="" style="width:310px; height:21px; border:1px solid #CCCCCC;">
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">����(�������)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfSimpleUpload(
				movie_id='fu01', //������ ����ID
				flash_width='400', //������ �ʺ� (�⺻�� 400, �����ּ� 300)
				limit_size='1024', // ���ε� ���ѿ뷮 (�⺻�� 10)MB
				file_type_name='�������', // ���ϼ���â �������ĸ� (��: �׸�����, ��������, ������� ��)
				allow_filetype='*.*', // ���ϼ���â �������� (��: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // ���ε� �Ұ�����
				border_color='0xCCCCCC', //�׵θ����� (�⺻�� 0xCCCCCC)
				upload_exe='upload.php' // ���ε� ������α׷�
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">�̱�(�������)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfSingleUpload(
				movie_id='fu02', //������ ����ID
				flash_width='400', //������ �ʺ� (�⺻�� 400, �����ּ� 300)
				limit_size='1024', // ���ε� ���ѿ뷮 (�⺻�� 10)MB
				file_type_name='�������', // ���ϼ���â �������ĸ� (��: �׸�����, ��������, ������� ��)
				allow_filetype='*.*', // ���ϼ���â �������� (��: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // ���ε� �Ұ�����
				border_color='0xCCCCCC', //�׵θ����� (�⺻�� 0xCCCCCC)
				upload_exe='upload.php' // ���ε� ������α׷�
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">��Ƽ(�׸�����)</td>
	<td style="padding:2px 0 2px 2px;">
		<script language="javascript">
			makeSwfMultiUpload(
				movie_id='fu03', //������ ����ID
				flash_width='400', //������ �ʺ� (�⺻�� 400, �����ּ� 300)
				list_rows='5', // ���ϸ�� �� (�����ּ�:4)
				limit_size='1024', // ���ε� ���ѿ뷮 (�⺻�� 10)MB
				file_type_name='�׸�����', // ���ϼ���â �������ĸ� (��: �׸�����, ��������, ������� ��)				
				allow_filetype=' *.jpg *.jpeg *.gif *.png', // ���ϼ���â �������� (��: *.jpg *.jpeg *.gif *.png)
				deny_filetype='*.cgi *.pl', // ���ε� �Ұ�����
				border_color='0xCCCCCC', //�׵θ����� (�⺻�� 0xCCCCCC)
				upload_exe='upload.php' // ���ε� ������α׷�
			);
		</script>
	</td>
</tr>
<tr><td height="1" colspan="2" bgcolor="#A9C0D0"></td></tr>
<tr>
	<td align="center" bgcolor="#A9C0D0">�Ϲݾ��ε�</td>
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

