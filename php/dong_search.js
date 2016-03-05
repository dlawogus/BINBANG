function dong_search_func(sVal){ 
    if( sVal == '' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
    }else if( sVal == '동구'){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='초량1동'>초량1동</option>");
        $("#dong_search").append("<option value='초량2동'>초량2동</option>");
        $("#dong_search").append("<option value='초량3동'>초량3동</option>");
        $("#dong_search").append("<option value='초량6동'>초량6동</option>");
        $("#dong_search").append("<option value='수정1동'>수정1동</option>");
        $("#dong_search").append("<option value='수정2동'>수정2동</option>");
        $("#dong_search").append("<option value='수정4동'>수정4동</option>");
        $("#dong_search").append("<option value='수정5동'>수정5동</option>");
        $("#dong_search").append("<option value='좌천동'>좌천동</option>");
        $("#dong_search").append("<option value='범일1동'>범일1동</option>");
        $("#dong_search").append("<option value='범일2동'>범일2동</option>");
        $("#dong_search").append("<option value='범일4동'>범일4동</option>");
        $("#dong_search").append("<option value='범일5동'>범일5동</option>");
    }else if( sVal == '영도구'){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='남한동'>남한동</option>");
        $("#dong_search").append("<option value='영선1동'>영선1동</option>");
        $("#dong_search").append("<option value='영선2동'>영선2동</option>");
        $("#dong_search").append("<option value='신선동'>신선동</option>");
        $("#dong_search").append("<option value='봉래1동'>봉래1동</option>");
        $("#dong_search").append("<option value='봉래2동'>봉래2동</option>");
        $("#dong_search").append("<option value='청학1동'>청학1동</option>");
        $("#dong_search").append("<option value='청학2동'>청학2동</option>");
        $("#dong_search").append("<option value='동삼1동'>동삼1동</option>");
        $("#dong_search").append("<option value='동삼2동'>동삼2동</option>");
        $("#dong_search").append("<option value='동삼3동'>동삼3동</option>");
    }else if( sVal == '부산진구'){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='부전1동'>부전1동</option>");
        $("#dong_search").append("<option value='부전2동'>부전2동</option>");
        $("#dong_search").append("<option value='연지동'>연지동</option>");
        $("#dong_search").append("<option value='초읍동'>초읍동</option>");
        $("#dong_search").append("<option value='양정1동'>양정1동</option>");
        $("#dong_search").append("<option value='양정2동'>양정2동</option>");
        $("#dong_search").append("<option value='전포1동'>전포1동</option>");
        $("#dong_search").append("<option value='전포2동'>전포2동</option>");
        $("#dong_search").append("<option value='부암1동'>부암1동</option>");
        $("#dong_search").append("<option value='부암3동'>부암3동</option>");
        $("#dong_search").append("<option value='당감1동'>당감1동</option>");
        $("#dong_search").append("<option value='당감2동'>당감2동</option>");
        $("#dong_search").append("<option value='당감4동'>당감4동</option>");
        $("#dong_search").append("<option value='가야1동'>가야1동</option>");
        $("#dong_search").append("<option value='가야2동'>가야2동</option>");
        $("#dong_search").append("<option value='개금1동'>개금1동</option>");
        $("#dong_search").append("<option value='개금2동'>개금2동</option>");
        $("#dong_search").append("<option value='범천1동'>범천1동</option>");
        $("#dong_search").append("<option value='범천2동'>범천2동</option>");
        $("#dong_search").append("<option value='범천4동'>범천4동</option>");
    }else if( sVal == '동래구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='수민동'>수민동</option>");
        $("#dong_search").append("<option value='복산동'>복산동</option>");
        $("#dong_search").append("<option value='명륜동'>명륜동</option>");
        $("#dong_search").append("<option value='온천1동'>온천1동</option>");
        $("#dong_search").append("<option value='온천2동'>온천2동</option>");
        $("#dong_search").append("<option value='온천3동'>온천3동</option>");
        $("#dong_search").append("<option value='사직1동'>사직1동</option>");
        $("#dong_search").append("<option value='사직2동'>사직2동</option>");
        $("#dong_search").append("<option value='사직3동'>사직3동</option>");    
        $("#dong_search").append("<option value='안락1동'>안락1동</option>");    
        $("#dong_search").append("<option value='안락2동'>안락2동</option>");   
        $("#dong_search").append("<option value='명량1동'>명량1동</option>");    
        $("#dong_search").append("<option value='명량2동'>명량2동</option>");     
    }else if( sVal == '서구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='동대신1동'>동대신1동</option>");
        $("#dong_search").append("<option value='동대신2동'>동대신2동</option>");
        $("#dong_search").append("<option value='동대신3동'>동대신3동</option>");
        $("#dong_search").append("<option value='서대신1동'>서대신1동</option>");
        $("#dong_search").append("<option value='서대신3동'>서대신3동</option>");
        $("#dong_search").append("<option value='서대신4동'>서대신4동</option>");
        $("#dong_search").append("<option value='부민동'>부민동</option>");
        $("#dong_search").append("<option value='초장동'>초장동</option>");
        $("#dong_search").append("<option value='충무동'>충무동</option>");
        $("#dong_search").append("<option value='남부민1동'>남부민1동</option>");
        $("#dong_search").append("<option value='남부민2동'>남부민2동</option>");
        $("#dong_search").append("<option value='영남동'>영남동</option>");
    }else if( sVal == '남구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='대연1동'>대연1동</option>");
        $("#dong_search").append("<option value='대연3동'>대연3동</option>");
        $("#dong_search").append("<option value='대연4동'>대연4동</option>");
        $("#dong_search").append("<option value='대연5동'>대연5동</option>");
        $("#dong_search").append("<option value='대연6동'>대연6동</option>");
        $("#dong_search").append("<option value='용호1동'>용호1동</option>");
        $("#dong_search").append("<option value='용호2동'>용호2동</option>");
        $("#dong_search").append("<option value='용호3동'>용호3동</option>");
        $("#dong_search").append("<option value='용호4동'>용호4동</option>");
        $("#dong_search").append("<option value='용당동'>용당동</option>");
        $("#dong_search").append("<option value='감만1동'>감만1동</option>");
        $("#dong_search").append("<option value='감만2동'>감만2동</option>");
        $("#dong_search").append("<option value='우암동'>우암동</option>");
        $("#dong_search").append("<option value='문현1동'>문현1동</option>");
        $("#dong_search").append("<option value='문현2동'>문현2동</option>");
        $("#dong_search").append("<option value='문현3동'>문현3동</option>");
        $("#dong_search").append("<option value='문현4동'>문현4동</option>");
    }else if( sVal == '북구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='구포1동'>구포1동</option>");
        $("#dong_search").append("<option value='구포2동'>구포2동</option>");
        $("#dong_search").append("<option value='구포3동'>구포3동</option>");
        $("#dong_search").append("<option value='금곡동'>금곡동</option>");
        $("#dong_search").append("<option value='화명1동'>화명1동</option>");
        $("#dong_search").append("<option value='화명2동'>화명2동</option>");
        $("#dong_search").append("<option value='화명3동'>화명3동</option>");
        $("#dong_search").append("<option value='덕천1동'>덕천1동</option>");
        $("#dong_search").append("<option value='덕천2동'>덕천2동</option>");
        $("#dong_search").append("<option value='덕천3동'>덕천3동</option>");
        $("#dong_search").append("<option value='만덕1동'>만덕1동</option>");
        $("#dong_search").append("<option value='만덕2동'>만덕2동</option>");
        $("#dong_search").append("<option value='만덕3동'>만덕3동</option>");
    }else if( sVal == '해운대구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='우동1동'>우동1동</option>");
        $("#dong_search").append("<option value='우동2동'>우동2동</option>");
        $("#dong_search").append("<option value='중동1동'>중동1동</option>");
        $("#dong_search").append("<option value='중동2동'>중동2동</option>");
        $("#dong_search").append("<option value='좌동1동'>좌동1동</option>");
        $("#dong_search").append("<option value='좌동2동'>좌동2동</option>");
        $("#dong_search").append("<option value='좌동3동'>좌동3동</option>");
        $("#dong_search").append("<option value='좌동4동'>좌동4동</option>");
        $("#dong_search").append("<option value='송정동'>송정동</option>");
        $("#dong_search").append("<option value='반여1동'>반여1동</option>");
        $("#dong_search").append("<option value='반여2동'>반여2동</option>");
        $("#dong_search").append("<option value='반여3동'>반여3동</option>");
        $("#dong_search").append("<option value='반여4동'>반여4동</option>");
        $("#dong_search").append("<option value='반송1동'>반송1동</option>");
        $("#dong_search").append("<option value='반송2동'>반송2동</option>");
        $("#dong_search").append("<option value='반송3동'>반송3동</option>");
        $("#dong_search").append("<option value='제송1동'>제송1동</option>");
        $("#dong_search").append("<option value='제송2동'>제송2동</option>");
    }else if( sVal == '금정구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='서1동'>서1동</option>");
        $("#dong_search").append("<option value='서2동'>서2동</option>");
        $("#dong_search").append("<option value='서3동'>서3동</option>");
        $("#dong_search").append("<option value='금사동'>금사동</option>");
        $("#dong_search").append("<option value='부곡1동'>부곡1동</option>");
        $("#dong_search").append("<option value='부곡2동'>부곡2동</option>");
        $("#dong_search").append("<option value='부곡3동'>부곡3동</option>");
        $("#dong_search").append("<option value='부곡4동'>부곡4동</option>");
        $("#dong_search").append("<option value='장전1동'>장전1동</option>");
        $("#dong_search").append("<option value='장전2동'>장전2동</option>");
        $("#dong_search").append("<option value='장전3동'>장전3동</option>");
        $("#dong_search").append("<option value='선두구동'>선두구동</option>");
        $("#dong_search").append("<option value='청룡노포동'>청룡노포동</option>");
        $("#dong_search").append("<option value='남산동'>남산동</option>");
        $("#dong_search").append("<option value='구서1동'>구서1동</option>");
        $("#dong_search").append("<option value='구서2동'>구서2동</option>");
        $("#dong_search").append("<option value='금성동'>금성동</option>");
    }else if( sVal == '강서구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='대저1동'>대저1동</option>");
        $("#dong_search").append("<option value='대저2동'>대저2동</option>");
        $("#dong_search").append("<option value='강동동'>강동동</option>");
        $("#dong_search").append("<option value='명지동'>명지동</option>");
        $("#dong_search").append("<option value='가락동'>가락동</option>");
        $("#dong_search").append("<option value='녹산동'>녹산동</option>");
        $("#dong_search").append("<option value='가덕도동'>가덕도동</option>");
    }else if( sVal == '연제구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='거제1동'>거제1동</option>");
        $("#dong_search").append("<option value='거제2동'>거제2동</option>");
        $("#dong_search").append("<option value='거제3동'>거제3동</option>");
        $("#dong_search").append("<option value='거제4동'>거제4동</option>");
        $("#dong_search").append("<option value='연산1동'>연산1동</option>");
        $("#dong_search").append("<option value='연산2동'>연산2동</option>");
        $("#dong_search").append("<option value='연산3동'>연산3동</option>");
        $("#dong_search").append("<option value='연산4동'>연산4동</option>");
        $("#dong_search").append("<option value='연산5동'>연산5동</option>");
        $("#dong_search").append("<option value='연산6동'>연산6동</option>");
        $("#dong_search").append("<option value='연산8동'>연산8동</option>");
        $("#dong_search").append("<option value='연산9동'>연산9동</option>");
    }else if( sVal == '수영구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='남천1동'>남천1동</option>");
        $("#dong_search").append("<option value='남천2동'>남천2동</option>");
        $("#dong_search").append("<option value='수영동'>수영동</option>");
        $("#dong_search").append("<option value='망미1동'>망미1동</option>");
        $("#dong_search").append("<option value='망미2동'>망미2동</option>");
        $("#dong_search").append("<option value='광안1동'>광안1동</option>");
        $("#dong_search").append("<option value='광안2동'>광안2동</option>");
        $("#dong_search").append("<option value='광안3동'>광안3동</option>");
        $("#dong_search").append("<option value='광안4동'>광안4동</option>");
        $("#dong_search").append("<option value='민락동'>민락동</option>");
    }else if( sVal == '사상구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='삼락동'>삼락동</option>");
        $("#dong_search").append("<option value='모라1동'>모라1동</option>");
        $("#dong_search").append("<option value='모라3동'>모라3동</option>");
        $("#dong_search").append("<option value='덕포1동'>덕포1동</option>");
        $("#dong_search").append("<option value='덕포2동'>덕포2동</option>");
        $("#dong_search").append("<option value='괘법동'>괘법동</option>");
        $("#dong_search").append("<option value='감전동'>감전동</option>");
        $("#dong_search").append("<option value='주례1동'>주례1동</option>");
        $("#dong_search").append("<option value='주례2동'>주례2동</option>");
        $("#dong_search").append("<option value='주례3동'>주례3동</option>");
        $("#dong_search").append("<option value='학장동'>학장동</option>");
        $("#dong_search").append("<option value='엄궁동'>엄궁동</option>");
    }else if( sVal == '기장군' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='기장읍'>기장읍</option>");
        $("#dong_search").append("<option value='장안읍'>장안읍</option>");
        $("#dong_search").append("<option value='일관면'>일관면</option>");
        $("#dong_search").append("<option value='정관면'>정관면</option>");
        $("#dong_search").append("<option value='철마면'>철마면</option>");
    }else if( sVal == '중구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='중앙동'>중앙동</option>");
        $("#dong_search").append("<option value='동광동'>동광동</option>");
        $("#dong_search").append("<option value='대청동'>대청동</option>");
        $("#dong_search").append("<option value='보수동'>보수동</option>");
        $("#dong_search").append("<option value='부평동'>부평동</option>");
        $("#dong_search").append("<option value='광복동'>광복동</option>");
        $("#dong_search").append("<option value='남포동'>남포동</option>");
        $("#dong_search").append("<option value='영주1동'>영주1동</option>");
        $("#dong_search").append("<option value='영주2동'>영주2동</option>");
    }else if( sVal == '사하구' ){
        $("#dong_search option").remove();
        $("#dong_search").append("<option value=''>선택</option>");
        $("#dong_search").append("<option value='괴정1동'>괴정1동</option>");
        $("#dong_search").append("<option value='괴정2동'>괴정2동</option>");
        $("#dong_search").append("<option value='괴정3동'>괴정3동</option>");
        $("#dong_search").append("<option value='괴정4동'>괴정4동</option>");
        $("#dong_search").append("<option value='당리동'>당리동</option>");
        $("#dong_search").append("<option value='하단1동'>하단1동</option>");
        $("#dong_search").append("<option value='하단2동'>하단2동</option>");
        $("#dong_search").append("<option value='신평1동'>신평1동</option>");
        $("#dong_search").append("<option value='신평2동'>신평2동</option>");
        $("#dong_search").append("<option value='장림1동'>장림1동</option>");
        $("#dong_search").append("<option value='장림2동'>장림2동</option>");
        $("#dong_search").append("<option value='다대1동'>다대1동</option>");
        $("#dong_search").append("<option value='다대2동'>다대2동</option>");
        $("#dong_search").append("<option value='구평동'>구평동</option>");
        $("#dong_search").append("<option value='감천1동'>감천1동</option>");
        $("#dong_search").append("<option value='감천2동'>감천2동</option>");
    }
}









