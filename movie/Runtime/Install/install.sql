SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS ff_admin (
  admin_id smallint(6) unsigned NOT NULL auto_increment,
  admin_name varchar(50) NOT NULL,
  admin_pwd char(32) NOT NULL,
  admin_count smallint(6) NOT NULL,
  admin_ok varchar(50) NOT NULL,
  admin_del bigint(1) NOT NULL,
  admin_ip varchar(40) NOT NULL,
  admin_email varchar(40) NOT NULL,
  admin_logintime int(11) NOT NULL,
  PRIMARY KEY  (admin_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

INSERT INTO ff_admin (admin_id, admin_name, admin_pwd, admin_count, admin_ok, admin_del, admin_ip, admin_email, admin_logintime) VALUES
(1, 'admin', '7fef6171469e80d32c0559f88b377245', 0, '1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1', 0, '127.0.0.1', 'admin@qq.com', 1311954804);


CREATE TABLE IF NOT EXISTS ff_ads (
  ads_id smallint(4) unsigned NOT NULL auto_increment,
  ads_name varchar(50) NOT NULL,
  ads_content text NOT NULL,
  PRIMARY KEY  (ads_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

INSERT INTO `ff_ads` (`ads_id`, `ads_name`, `ads_content`) VALUES
(1, 'index_gonggao', 'index_gonggao'),
(2, 'index_01_960', 'index_01_960'),
(3, 'index_02_960', 'index_02_960'),
(4, 'index_03_960', 'index_03_960'),
(5, 'index_04_960', 'index_04_960'),
(6, 'mov_960_01', 'mov_960_01'),
(7, 'mov_250300_a1', 'mov_250300_a1'),
(8, 'mov_250300_a2', 'mov_250300_a2'),
(9, 'tv_960_01', 'tv_960_01'),
(10, 'list_960_01', 'list_960_01'),
(11, 'vod_960_01', 'vod_960_01'),
(12, 'vod_300_01', 'vod_300_01'),
(13, 'vod_960_02', 'vod_960_02'),
(14, 'vod_960_03', 'vod_960_03'),
(15, 'vod_960_04', 'vod_960_04'),
(16, 'mov_250300_a3', 'mov_250300_a3'),
(17, 'play1', 'play1'),
(18, 'play2', 'play2');


CREATE TABLE IF NOT EXISTS ff_cm (
  cm_id mediumint(8) unsigned NOT NULL auto_increment,
  cm_cid mediumint(9) NOT NULL,
  cm_sid tinyint(1) NOT NULL default '1',
  cm_uid mediumint(9) NOT NULL default '1',
  cm_content text NOT NULL,
  cm_nickname varchar(20) NOT NULL,
  cm_up mediumint(9) NOT NULL default '0',
  cm_down mediumint(9) NOT NULL default '0',
  cm_ip varchar(20) NOT NULL,
  cm_addtime int(11) NOT NULL,
  cm_status tinyint(1) NOT NULL default '0',
  PRIMARY KEY cm_id (cm_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE ff_mcat (
  m_cid int(10) unsigned NOT NULL auto_increment,
  m_list_id int(10) unsigned NOT NULL DEFAULT '0',
  m_name varchar(30) NOT NULL DEFAULT '',
  m_order int(11) NOT NULL,
  PRIMARY KEY (m_cid),
  KEY `m_list_id` (`m_list_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

INSERT INTO `ff_mcat` (`m_cid`, `m_list_id`, `m_name`, `m_order`) VALUES
(1, 2, '言情', 22),
(2, 2, '都市', 21),
(3, 2, '家庭', 19),
(4, 2, '生活', 18),
(5, 2, '偶像', 17),
(6, 2, '喜剧', 16),
(7, 2, '历史', 15),
(8, 2, '古装', 14),
(9, 2, '武侠', 13),
(10, 2, '刑侦', 12),
(11, 2, '战争', 11),
(12, 2, '神话', 10),
(13, 2, '军旅', 9),
(14, 2, '谍战', 8),
(15, 2, '商战', 7),
(16, 2, '校园', 6),
(17, 2, '穿越', 5),
(18, 2, '悬疑', 4),
(19, 2, '犯罪', 3),
(20, 2, '科幻', 2),
(21, 2, '时装', 20),
(24, 1, '惊悚', 16),
(23, 2, '预告', 1),
(25, 1, '悬疑', 15),
(26, 1, '魔幻', 14),
(27, 1, '罪案', 13),
(28, 1, '冒险', 9),
(29, 1, '灾难', 11),
(30, 1, '动画', 6),
(31, 1, '古装', 10),
(32, 1, '青春', 5),
(33, 1, '歌舞', 4),
(34, 1, '文艺', 3),
(35, 1, '生活', 8),
(36, 1, '历史', 2),
(37, 1, '励志', 7),
(38, 1, '警匪', 12),
(39, 1, '伦理', 1),
(40, 3, '经典', 1),
(41, 3, '童话', 2),
(42, 3, '益智', 3),
(43, 3, '竞技', 4),
(44, 3, '推理', 5),
(45, 3, '魔幻', 6),
(46, 3, '少女', 7),
(47, 3, '搞笑', 8),
(48, 3, '热血', 9),
(49, 3, '冒险', 10),
(50, 3, '机战', 23),
(51, 3, '亲子', 24),
(52, 3, '校园', 25),
(53, 3, '励志', 26),
(54, 4, '晚会', 1),
(55, 4, '财经', 2),
(56, 4, '体育', 3),
(57, 4, '纪实', 4),
(58, 4, '生活', 5),
(59, 4, '歌舞', 6),
(60, 4, '故事', 7),
(61, 4, '军事', 8),
(62, 4, '少儿', 9),
(63, 4, '新闻', 10),
(64, 4, '情感', 11),
(65, 4, '访谈', 12),
(66, 4, '时尚', 13),
(67, 4, '音乐', 14),
(68, 4, '游戏', 15),
(69, 4, '美食', 16),
(70, 4, '旅游', 17),
(71, 4, '职场', 18),
(72, 4, '看点', 19),
(73, 4, '娱乐', 20),
(74, 4, '选秀', 21),
(75, 4, '搞笑', 22),
(76, 4, '真人秀', 23),
(77, 4, '脱口秀', 24),
(78, 24, '生活', 1),
(79, 24, '剧情', 2),
(80, 24, '恐怖', 3),
(81, 24, '励志', 4),
(82, 24, '搞笑', 5),
(83, 24, '爱情', 6),
(84, 2, '励志', 18),
(85, 2, '年代', 27),
(86, 1, '喜剧', 28),
(87, 1, '恐怖', 29),
(88, 1, '爱情', 30),
(89, 1, '科幻', 31),
(90, 1, '剧情', 32),
(91, 1, '战争', 33),
(92, 1, '动作', 34),
(93, 1, '武侠', 35),
(94, 1, '谍战', 36),
(96, 24, '动画', 37),
(97, 24, '职场', 38),
(98, 24, '明星', 39),
(99, 24, '生活', 40);

CREATE TABLE IF NOT EXISTS ff_gb (
  gb_id mediumint(8) unsigned NOT NULL auto_increment,
  gb_cid mediumint(8) NOT NULL default '0',
  gb_uid mediumint(9) NOT NULL default '1',
  gb_content text NOT NULL,
  gb_intro text NOT NULL,
  gb_addtime int(11) NOT NULL,
  gb_ip varchar(20) NOT NULL,
  gb_oid tinyint(1) NOT NULL default '0',
  gb_status tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (gb_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS ff_link (
  link_id tinyint(4) unsigned NOT NULL auto_increment,
  link_name varchar(255) NOT NULL,
  link_logo varchar(255) NOT NULL,
  link_url varchar(255) NOT NULL,
  link_order tinyint(4) NOT NULL,
  link_type tinyint(1) NOT NULL,
  PRIMARY KEY  (link_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

INSERT INTO ff_link (link_id, link_name, link_logo, link_url, link_order, link_type) VALUES
(1, '爱去影视网', 'http://', 'http://www.27236.com', 1, 1);

CREATE TABLE IF NOT EXISTS ff_list (
  list_id smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  list_pid smallint(3) NOT NULL,
  list_oid smallint(3) NOT NULL,
  list_sid tinyint(1) NOT NULL,
  list_name char(20) NOT NULL,
  list_skin char(20) NOT NULL,
  list_skin_detail varchar(20) NOT NULL DEFAULT 'pp_vod',
  list_skin_play varchar(20) NOT NULL DEFAULT 'pp_play',
  list_skin_type varchar(20) NOT NULL DEFAULT 'pp_vodtype',
  list_dir varchar(90) NOT NULL,
  list_status tinyint(1) NOT NULL DEFAULT '1',
  list_keywords varchar(255) NOT NULL,
  list_title varchar(50) NOT NULL,
  list_description varchar(255) NOT NULL,
  list_jumpurl varchar(150) NOT NULL,
  PRIMARY KEY (list_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

INSERT INTO ff_list (list_id, list_pid, list_oid, list_sid, list_name, list_skin, list_skin_detail, list_skin_play, list_skin_type, list_dir, list_status, list_keywords, list_title, list_description, list_jumpurl) VALUES
(23, 0, 23, 2, '新闻资讯', 'pp_newslist', 'pp_vod', 'pp_play', 'pp_vodtype', 'xinwenzixun', 0, '', '', '', ''),
(22, 0, 20, 1, '纪录片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'jilupian', 0, '', '', '', ''),
(19, 2, 8, 1, '海外剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'haiwaiju', 1, '', '', '', 'http://'),
(18, 2, 4, 1, '日本剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'ribenju', 1, '', '', '', 'http://'),
(17, 2, 7, 1, '欧美剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'oumeiju', 1, '', '', '', 'http://'),
(16, 2, 2, 1, '香港剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'xianggangju', 1, '', '', '', 'http://'),
(15, 2, 1, 1, '国产剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'guochanju', 1, '', '', '', 'http://'),
(14, 1, 7, 1, '剧情片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'juqingpian', 1, '', '', '', ''),
(13, 1, 6, 1, '战争片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'zhanzhengpian', 1, '', '', '', ''),
(12, 1, 5, 1, '恐怖片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'kongbupian', 1, '', '', '', ''),
(11, 1, 4, 1, '科幻片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'kehuanpian', 1, '', '', '', ''),
(10, 1, 3, 1, '爱情片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'aiqingpian', 1, '', '', '', ''),
(9, 1, 2, 1, '喜剧片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'xijupian', 1, '', '', '', ''),
(8, 1, 1, 1, '动作片', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'dongzuopian', 1, '', '', '', 'http://'),
(7, 0, 7, 1, '音乐', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'yinle', 0, '', '', '', ''),
(6, 0, 6, 1, '游戏', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'youxi', 0, '', '', '', ''),
(5, 0, 5, 1, '体育', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'tiyu', 0, '', '', '', ''),
(4, 0, 4, 1, '综艺', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'zongyi', 1, '', '', '', 'http://'),
(3, 0, 3, 1, '动漫', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'dongman', 1, '', '', '', ''),
(2, 0, 1, 1, '电视剧', 'tv_index', 'pp_vod', 'pp_play', 'pp_vodtype', 'dianshiju', 1, '', '', '', 'http://'),
(24, 0, 24, 1, '微电影', 'weidy_index', 'pp_vod', 'pp_play', 'pp_vodtype', 'weidianying', 1, '', '', '', 'http://'),
(25, 0, 25, 1, '电视直播', 'my_live', 'pp_vod', 'pp_play', 'pp_vodtype', 'live', 1, '', '', '', 'http://'),
(1, 0, 2, 1, '电影', 'mov_index', 'pp_vod', 'pp_play', 'pp_vodtype', 'dianying', 1, '', '', '', ''),
(26, 2, 3, 1, '台湾剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'taiwanju', 1, '', '', '', 'http://'),
(27, 2, 5, 1, '韩国剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'hanguoju', 1, '', '', '', 'http://'),
(28, 2, 6, 1, '泰国剧', 'pp_letter', 'pp_vod', 'pp_play', 'pp_vodtype', 'taiguoju', 1, '', '', '', 'http://'),
(29, 0, 26, 9, '口水', 'pp_vodlist', 'pp_vod', 'pp_play', 'pp_type', 'ks', 1, '', '', '', '/like/');


CREATE TABLE IF NOT EXISTS ff_news (
  news_id mediumint(8) unsigned NOT NULL auto_increment,
  news_cid smallint(6) NOT NULL default '0',
  news_name varchar(255) NOT NULL,
  news_keywords varchar(255) NOT NULL,
  news_color char(8) NOT NULL,
  news_pic varchar(255) NOT NULL,
  news_inputer varchar(50) NOT NULL,
  news_reurl varchar(255) NOT NULL,
  news_remark text NOT NULL,
  news_content text NOT NULL,
  news_hits mediumint(8) NOT NULL,
  news_hits_day mediumint(8) NOT NULL,
  news_hits_week mediumint(8) NOT NULL,
  news_hits_month mediumint(8) NOT NULL,
  news_hits_lasttime int(11) NOT NULL,
  news_stars tinyint(1) NOT NULL,
  news_status tinyint(1) NOT NULL default '1',
  news_up mediumint(8) NOT NULL,
  news_down mediumint(8) NOT NULL,
  news_jumpurl varchar(255) NOT NULL,
  news_letter char(2) NOT NULL,
  news_addtime int(8) NOT NULL,
  news_skin varchar(30) NOT NULL,
  news_gold decimal(3,1) NOT NULL,
  news_golder smallint(6) NOT NULL,
  PRIMARY KEY  (news_id),
  KEY news_cid (news_cid),
  KEY news_up (news_up),
  KEY news_down (news_down),
  KEY news_gold (news_gold),
  KEY news_hits (news_hits,news_cid)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS ff_slide (
  slide_id tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  slide_oid tinyint(3) NOT NULL,
  slide_cid tinyint(3) NOT NULL DEFAULT '1',
  slide_name varchar(255) NOT NULL,
  slide_logo varchar(255) NOT NULL,
  slide_pic varchar(255) NOT NULL,
  slide_url varchar(255) NOT NULL,
  slide_content varchar(255) NOT NULL,
  slide_status tinyint(1) NOT NULL,
  PRIMARY KEY (slide_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS ff_special (
  special_id mediumint(8) unsigned NOT NULL auto_increment,
  special_banner varchar(150) NOT NULL,
  special_logo varchar(150) NOT NULL,
  special_name varchar(150) NOT NULL,
  special_keywords varchar(150) NOT NULL,
  special_description varchar(255) NOT NULL,
  special_color char(8) NOT NULL,
  special_skin varchar(50) NOT NULL,
  special_addtime int(11) NOT NULL,
  special_hits mediumint(8) NOT NULL,
  special_hits_day mediumint(8) NOT NULL,
  special_hits_week mediumint(8) NOT NULL,
  special_hits_month mediumint(8) NOT NULL,
  special_hits_lasttime int(11) NOT NULL,
  special_stars tinyint(1) NOT NULL default '1',
  special_status tinyint(1) NOT NULL,
  special_content text NOT NULL,
  special_up mediumint(8) NOT NULL,
  special_down mediumint(8) NOT NULL,
  special_gold decimal(3,1) NOT NULL,
  special_golder smallint(6) NOT NULL,
  PRIMARY KEY  (special_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS ff_topic (
  topic_did mediumint(9) NOT NULL,
  topic_tid smallint(6) NOT NULL,
  topic_sid tinyint(1) NOT NULL,
  topic_oid smallint(3) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS ff_user (
  user_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  user_name varchar(50) NOT NULL,
  user_pwd char(32) NOT NULL,
  user_money mediumint(9) NOT NULL,
  user_staus tinyint(1) NOT NULL DEFAULT '1',
  user_pay tinyint(1) NOT NULL,
  user_question varchar(50) NOT NULL,
  user_answer varchar(50) NOT NULL,
  user_type tinyint(1) NOT NULL,
  user_logip varchar(16) NOT NULL,
  user_lognum smallint(5) NOT NULL DEFAULT '1',
  user_logtime int(10) NOT NULL,
  user_joinip varchar(16) NOT NULL,
  user_jointime int(10) NOT NULL,
  user_duetime int(10) NOT NULL,
  user_qq varchar(20) NOT NULL,
  user_email varchar(50) NOT NULL,
  user_face varchar(50) NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

INSERT INTO ff_user (user_id, user_name, user_pwd, user_money, user_staus, user_pay, user_question, user_answer, user_type, user_logip, user_lognum, user_logtime, user_joinip, user_jointime, user_duetime, user_qq, user_email, user_face) VALUES
(1, '游客', 'bdadsfsaewtgsdgfdsghdsafsa', 1, 1, 1, '1', '1', 1, '127.0.0.1', 1, 1, '127.0.0.1', 12345678, 12345678, '10000', '10000@qq.com', '');

CREATE TABLE IF NOT EXISTS ff_view (
  view_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  view_did mediumint(8) NOT NULL,
  view_uid mediumint(8) NOT NULL,
  view_addtime int(10) NOT NULL,
  PRIMARY KEY (view_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS ff_tag (
  tag_id mediumint(8) NOT NULL,
  tag_sid tinyint(1) NOT NULL,
  tag_name varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS ff_vod (
  vod_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  vod_cid smallint(6) NOT NULL DEFAULT '0',
  vod_mcid TEXT NOT NULL,
  vod_name varchar(255) NOT NULL,
  vod_title varchar(255) NOT NULL,
  vod_keywords varchar(255) NOT NULL,
  vod_color char(8) NOT NULL,
  vod_actor varchar(255) NOT NULL,
  vod_director varchar(255) NOT NULL,
  vod_content text NOT NULL,
  vod_pic varchar(255) NOT NULL,
  vod_diantai TEXT NOT NULL,
  vod_tvcont TEXT NOT NULL,
  vod_prty TEXT NOT NULL,
  vod_area char(10) NOT NULL,
  vod_language char(10) NOT NULL,
  vod_year smallint(4) NOT NULL,
  vod_continu varchar(20) NOT NULL DEFAULT '0',
  vod_total varchar(20) NOT NULL,
  vod_isend tinyint(1) NOT NULL DEFAULT '1',
  vod_addtime int(11) NOT NULL,
  vod_hits mediumint(8) NOT NULL DEFAULT '0',
  vod_hits_day mediumint(8) NOT NULL,
  vod_hits_week mediumint(8) NOT NULL,
  vod_hits_month mediumint(8) NOT NULL,
  vod_hits_lasttime int(11) NOT NULL,
  vod_stars tinyint(1) NOT NULL DEFAULT '0',
  vod_status tinyint(1) NOT NULL DEFAULT '1',
  vod_up mediumint(8) NOT NULL DEFAULT '0',
  vod_down mediumint(8) NOT NULL DEFAULT '0',
  vod_play varchar(255) NOT NULL,
  vod_server varchar(255) NOT NULL,
  vod_url longtext NOT NULL,
  vod_inputer varchar(30) NOT NULL,
  vod_reurl varchar(255) NOT NULL,
  vod_jumpurl varchar(150) NOT NULL,
  vod_letter char(2) NOT NULL,
  vod_skin varchar(30) NOT NULL,
  vod_gold decimal(3,1) NOT NULL,
  vod_golder smallint(6) NOT NULL,
  vod_isfilm tinyint(1) NOT NULL DEFAULT '1',
  vod_filmtime int(11) NOT NULL,
  vod_length smallint(3) NOT NULL,
  vod_weekday mediumint(7) NOT NULL,
  vod_gold_1 float not null default '0',
  vod_gold_2 float not null default '0',
  vod_gold_3 float not null default '0',
  vod_gold_4 float not null default '0',
  vod_gold_5 float not null default '0',
  PRIMARY KEY (vod_id),
  KEY vod_cid (vod_cid),
  KEY vod_up (vod_up),
  KEY vod_down (vod_down),
  KEY vod_addtime (vod_addtime,vod_cid),
  KEY vod_hits (vod_hits,vod_cid),
  KEY vod_gold (vod_gold)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;