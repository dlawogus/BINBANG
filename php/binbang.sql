
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `binbang` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,    #1. 시퀀스
  `building_id` int(11) unsigned NULL,         #건
  `building_hosu` varchar(100) NULL,        #3. 호수
  `sangse_juso` varchar(50) NULL,           #7. 상세주소
  `deposit` varchar(50) NULL,               #8. 보증금 
  `deposit_possible` int(1) NULL,           #9. 보증금 조정 가능
  `monthly_rental` varchar(50) NULL,        #10.월세 
  `base_price` varchar(50) NULL,            #11.기준가 
  `empty` varchar(50) NULL,                 #12.빈방여부 
  `price_type` varchar(50) NULL,            #13.금액 타입 
  `is_image`  int(1) NULL,                  #14.이미지여부
  `manage_price` varchar(50) NULL,          #15.관리비
  `manage_internet` int(1) NULL,            #16.관리비포함_인터넷     
  `manage_sudo` int(1) NULL,                #17.관리비포함_수도 
  `manage_yusun` int(1) NULL,               #18.관리비포함_유선
  `bang_type` varchar(50) NULL,             #19.방 구조 
  `possible_date` varchar(100) NULL,        #22.입주가능일 
  `bang_option` text NULL,                  #25.방옵션 
  `lat`    varchar(50) NULL,                #29.위도
  `lng`    varchar(50) NULL,                #30.경도
  `comment` text  NULL,                     #31.비고
  PRIMARY KEY (`_id`)
  KEY `building_id` (`building_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`building_id`) REFERENCES `building` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `building` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,    #시퀀스
  `si` varchar(50) NULL,                    #시
  `gu`  varchar(50) NULL,                   #구
  `dong` varchar(50) NULL,                  #동
  `sangse_juso` varchar(50) NULL,           #지번
  `juso`  varchar(50) NULL,                 #주소
  `building_name` varchar(50) NULL,         #건물이름
  `bang_password` varchar(50) NULL,         #현관비번
  `boiler` varchar(50) NULL,                #난방타입
  `is_park`   int(1) NULL,                  #주차여부
  `is_elevator`   int(1) NULL,              #엘레베이터
  `call_1_who` varchar(50) NULL,            #1누구 전화
  `call_1` varchar(50) NULL,                #전화번호1
  `call_2_who` varchar(50) NULL,            #2누구 전화
  `call_2` varchar(50) NULL,                #전화번호2
  `call_3_who` varchar(50) NULL,            #3누구 전화
  `call_3` varchar(50) NULL,                #전화번호3
  `building_comment` text NULL,             #건물 전달사항
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `binbang_image` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,    #시퀀스
  `bang_id` int(11) unsigned NOT NULL,
  `url` varchar(100) NOT NULL,                       #이미지 url
  PRIMARY KEY (`_id`),
  KEY `bang_id` (`bang_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`bang_id`) REFERENCES `binbang` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
