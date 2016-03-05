// ZIWOO FU (Flash Uploader)
// Licence : GPL (http://www.gnu.org/licenses/licenses.html#GPL)
// Update & Download  : http://www.ziwoo.net/zb/view.php?boardid=zb_ziwoo_actionscript&uid=6
// This page was last modified on 2011.12.09 (Since 2007.08.21)

function getCookie(name) { 
	var Found = false;
	var start, end;
	var i = 0;
	while(i <= document.cookie.length) {
		start = i;
		end = start + name.length;
		if(document.cookie.substring(start, end) == name) {
			Found = true;
			break;
		}
		i++;
	}
	if(Found == true) {
		start = end + 1;
		end = document.cookie.indexOf(";", start);
		if(end < start) end = document.cookie.length;
		return document.cookie.substring(start, end);
	}
	return "";
}

function makeSwfSimpleUpload(){
	var flashvars = "flash_width="+flash_width+"&amp;";
	flashvars += "limit_size="+limit_size+"&amp;";
	flashvars += "file_type_name="+file_type_name+"&amp;";
	flashvars += "allow_filetype="+allow_filetype+"&amp;";
	flashvars += "deny_filetype="+deny_filetype+"&amp;";
	flashvars += "upload_exe="+upload_exe+"&amp;";
	flashvars += "upload_id="+movie_id+"&amp;";
	flashvars += "border_color="+border_color+"&amp;";
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF���� upload.php���� ������ PHPSESSID�� �ο��ϹǷ� ������ ������ ��.

	var flashStr = "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000'";
	flashStr += "codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0'";
	flashStr += "width='"+flash_width+"' height='24' align='middle' id='"+movie_id+"' method='simple_upload'>";
	flashStr += "<param name='allowScriptAccess' value='sameDomain' />";
	flashStr += "<param name='movie' value='/fu/simple_upload.swf' />";
	flashStr += "<param name='quality' value='high' />";
	flashStr += "<param name='bgcolor' value='#ffffff' />";
	flashStr += "<param name='menu' value='false' />";
	flashStr += "<param name='wmode' value='transparent' />";	
	flashStr += "<param name='flashvars' value='"+flashvars+"' />";
	flashStr += "<embed src='/fu/simple_upload.swf' width='"+flash_width+"' height='24' quality='high' wmode='transparent'";
	flashStr += "bgcolor='#ffffff' name='"+movie_id+"' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash'";
	flashStr += "pluginspage='http://www.macromedia.com/go/getflashplayer' flashvars='"+flashvars+"' />";
	flashStr += "</object>";
	document.write(flashStr); 
}

function makeSwfSingleUpload(){
	var flashvars = "flash_width="+flash_width+"&amp;";
	flashvars += "limit_size="+limit_size+"&amp;";
	flashvars += "file_type_name="+file_type_name+"&amp;";
	flashvars += "allow_filetype="+allow_filetype+"&amp;";
	flashvars += "deny_filetype="+deny_filetype+"&amp;";
	flashvars += "upload_exe="+upload_exe+"&amp;";
	flashvars += "upload_id="+movie_id+"&amp;";
	flashvars += "border_color="+border_color+"&amp;";
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF���� upload.php���� ������ PHPSESSID�� �ο��ϹǷ� ������ ������ ��.

	var flashStr = "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000'";
	flashStr += "codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0'";
	flashStr += "width='"+flash_width+"' height='40' align='middle' id='"+movie_id+"' method='single_upload'>";
	flashStr += "<param name='allowScriptAccess' value='sameDomain' />";
	flashStr += "<param name='movie' value='/fu/single_upload.swf' />";
	flashStr += "<param name='quality' value='high' />";
	flashStr += "<param name='bgcolor' value='#ffffff' />";
	flashStr += "<param name='menu' value='false' />";
	flashStr += "<param name='wmode' value='transparent' />";	
	flashStr += "<param name='flashvars' value='"+flashvars+"' />";
	flashStr += "<embed src='/fu/single_upload.swf' width='"+flash_width+"' height='40' quality='high' wmode='transparent'";

	flashStr += "bgcolor='#ffffff' name='"+movie_id+"' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash'";
	flashStr += "pluginspage='http://www.macromedia.com/go/getflashplayer' flashvars='"+flashvars+"' />";
	flashStr += "</object>";
	document.write(flashStr); 
}

function makeSwfMultiUpload(){
	var flashvars = "flash_width="+flash_width+"&amp;";
	flashvars += "list_rows="+list_rows+"&amp;";
	flashvars += "limit_size="+limit_size+"&amp;";
	flashvars += "file_type_name="+file_type_name+"&amp;";
	flashvars += "allow_filetype="+allow_filetype+"&amp;";
	flashvars += "deny_filetype="+deny_filetype+"&amp;";
	flashvars += "upload_exe="+upload_exe+"&amp;";
	flashvars += "upload_id="+movie_id+"&amp;";
	flashvars += "border_color="+border_color+"&amp;";
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF���� upload.php���� ������ PHPSESSID�� �ο��ϹǷ� ������ ������ ��.

	var flashStr = "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000'";
	flashStr += "codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0'";
	flashStr += "width='"+flash_width+"' height='"+parseInt(list_rows*20+18,10)+"' align='middle' id='"+movie_id+"' method='multi_upload'>";
	flashStr += "<param name='allowScriptAccess' value='sameDomain' />";
	flashStr += "<param name='movie' value='/fu/multi_upload.swf' />";
	flashStr += "<param name='quality' value='high' />";
	flashStr += "<param name='bgcolor' value='#ffffff' />";
	flashStr += "<param name='menu' value='false' />";
	flashStr += "<param name='wmode' value='transparent' />";	
	flashStr += "<param name='flashvars' value='"+flashvars+"' />";
	flashStr += "<embed src='/fu/multi_upload.swf' width='"+flash_width+"' height='"+parseInt(list_rows*20+18,10)+"' quality='high' wmode='transparent'";
	flashStr += "bgcolor='#ffffff' name='"+movie_id+"' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash'";
	flashStr += "pluginspage='http://www.macromedia.com/go/getflashplayer' flashvars='"+flashvars+"' />";
	flashStr += "</object>";
	document.write(flashStr); 
}

function callSwfUpload(srcForm){ //�÷��� ���� ����Ʈ�� �߰��� ������ ����
	formName = srcForm;
	arrMovie = new Array()
	var arr_num = 0;
	var objectTags = document.getElementsByTagName('object');
	var movie;
	for (i = 0; i < objectTags.length; i++ ) {
		if(objectTags[i].getAttribute("method")=="simple_upload" || objectTags[i].getAttribute("method")=="single_upload" || objectTags[i].getAttribute("method")=="multi_upload"){
			if(document.getElementsByName(objectTags[i].getAttribute("id"))[0]) {
				movie = document.getElementsByName(objectTags[i].getAttribute("id"))[0];
			}else{
				movie = document.getElementById(objectTags[i].getAttribute("id"));
			}			
			if(movie.GetVariable("totalSize")>0){				
				arrMovie[arr_num] = movie;
				arr_num++;
			}
		}
	}

	if(arrMovie.length>0){
		if(arrMovie[0].getAttribute("method")=="simple_upload" || arrMovie[0].parentNode.getAttribute("method")=="simple_upload") arrMovie[0].height = 40;
		if(arrMovie[0].getAttribute("method")=="single_upload" || arrMovie[0].parentNode.getAttribute("method")=="single_upload") arrMovie[0].height = 56;
		if(arrMovie[0].getAttribute("method")=="multi_upload" || arrMovie[0].parentNode.getAttribute("method")=="multi_upload") arrMovie[0].height = parseInt(20*arrMovie[0].GetVariable("listRows")+17+34,10);
		arrMovie[0].SetVariable( "startUpload", "" );
		arr_mov = 0;
	}else{
		document.forms[formName].submit();
	}	
}

function swfUploadComplete(){	
	arr_mov++;
	if(arrMovie.length>arr_mov){
		if(arrMovie[arr_mov].getAttribute("method")=="simple_upload" || arrMovie[arr_mov].parentNode.getAttribute("method")=="single_upload") arrMovie[arr_mov].height = 40;
		if(arrMovie[arr_mov].getAttribute("method")=="single_upload" || arrMovie[arr_mov].parentNode.getAttribute("method")=="single_upload") arrMovie[arr_mov].height = 56;
		if(arrMovie[arr_mov].getAttribute("method")=="multi_upload" || arrMovie[arr_mov].parentNode.getAttribute("method")=="multi_upload") arrMovie[arr_mov].height = parseInt(20*arrMovie[arr_mov].GetVariable("listRows")+17+34,10);
		arrMovie[arr_mov].SetVariable( "startUpload", "" );
	}else{
		document.forms[formName].submit();
	}	
}

function fileTypeError( notAllowFileType ){ //������� ���� ������ ������ ��� ���� �޽��� ���
	alert("Ȯ���ڰ� " + notAllowFileType + "�� ���ϵ��� ���ε� �� �� �����ϴ�.");
}

function overSize( limitSize ){ //������� ���� ������ ������ ��� ���� �޽��� ���
	alert("������ ������ ũ�Ⱑ " + limitSize + "�� �ʰ��߽��ϴ�.");
}

function versionError(){ //�÷��� ������ 8 �̸��� ��� ���� �޽��� ���
	alert("�÷��� ������ 8.0 �̻����� Ȯ���ϼ���.\n�̹� ��ġ�Ͻ� ���� �������� ���� �ݰ� �ٽ� �����ϼ���.");
}

function httpError(){ //http ���� �߻��� ��� �޽���
	alert("��Ʈ��ũ ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.");
}

function ioError(){ //���� ����� ���� �߻��� ��� �޽���
	alert("����� ������ �߻��Ͽ����ϴ�.\n �ٸ� ���α׷����� �� ������ ��������� Ȯ���ϼ���.");
}

function onSecurityError(){ //���� ����� ���� �߻��� ��� �޽���
	alert("���� ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.");
}