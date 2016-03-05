
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` varchar(100) NOT NULL,
  `user_pw` varchar(100) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `company_title` varchar(50) NOT NULL,
  `ceo_id`  varchar(100) NOT NULL,
  `phone`   varchar(50) NOT NULL,
  `call_number` varchar(50) NOT NULL,
  `email`   varchar(100) NOT NULL,
  `is_available` int(1) NOT NULL,
  `token`   varchar(100) NOT NULL,
  `join_date` date,    
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `manager` (
  `user_id` varchar(100) NOT NULL,
  `user_pw` varchar(100) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `company` (
  `_id` int(11) unsigned NOT NULL,
  `ceo_id` varchar(100) NOT NULL,
  `company_title` varchar(100) NOT NULL,
  `company_reg_num`  varchar(100),
  `relay_reg_num`  varchar(100),
  `fax_number`  varchar(100),
  `juso` varchar(100),
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `service_req` (
  `_id` int(11) unsigned NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `period` int(2),
  `join_date` date,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `building_image` (
  `_id` int(11) unsigned NOT NULL,
  `building_id` int(11) NOT NULL,
  `url` varchar(50) NOT NULL,
  `is_list_image` int(1), 
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


_id
대표 아이디
업체명
사업자등록번호
중개업소등록번호
사업장 주소
팩스번호