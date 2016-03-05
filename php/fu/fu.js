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
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF에서 upload.php에게 별도의 PHPSESSID를 부여하므로 강제로 전달해 줌.

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
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF에서 upload.php에게 별도의 PHPSESSID를 부여하므로 강제로 전달해 줌.

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
	flashvars += "browser_id="+getCookie("PHPSESSID"); // FF에서 upload.php에게 별도의 PHPSESSID를 부여하므로 강제로 전달해 줌.

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

function callSwfUpload(srcForm){ //플래쉬 파일 리스트에 추가된 파일을 전송
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

function fileTypeError( notAllowFileType ){ //허용하지 않은 형식의 파일일 경우 에러 메시지 출력
	alert("확장자가 " + notAllowFileType + "인 파일들은 업로드 할 수 없습니다.");
}

function overSize( limitSize ){ //허용하지 않은 형식의 파일일 경우 에러 메시지 출력
	alert("선택한 파일의 크기가 " + limitSize + "를 초과했습니다.");
}

function versionError(){ //플래쉬 버전이 8 미만일 경우 에러 메시지 출력
	alert("플래쉬 버전이 8.0 이상인지 확인하세요.\n이미 설치하신 경우는 브라우저를 전부 닫고 다시 시작하세요.");
}

function httpError(){ //http 에러 발생시 출력 메시지
	alert("네트워크 에러가 발생하였습니다. 관리자에게 문의하세요.");
}

function ioError(){ //파일 입출력 에러 발생시 출력 메시지
	alert("입출력 에러가 발생하였습니다.\n 다른 프로그램에서 이 파일을 사용중인지 확인하세요.");
}

function onSecurityError(){ //파일 입출력 에러 발생시 출력 메시지
	alert("보안 에러가 발생하였습니다. 관리자에게 문의하세요.");
}