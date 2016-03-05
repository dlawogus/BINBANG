CREATE TABLE IF NOT EXISTS `favorite` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,   #시퀀스
  `user_id` varchar(100) NOT NULL,					#유저 아이디
  `bang_id` varchar(100) NOT NULL,					#방 아이디
   FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
   FOREIGN KEY (`bang_id`) REFERENCES `binbang` (`_id`),
   PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `notice` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,   #시퀀스
  `title` Text NOT NULL,
  `url` varchar(100) NULL,
   PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `recently_find` (
  `_id` int(11) unsigned NOT NULL AUTO_INCREMENT,   #시퀀스
  `bang_id` int(11) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `time` date NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;