--의약품 API
create table DRUG(
	drg_num number not null,
	drg_name varchar2(4000),
	drg_code varchar2(4000),
	drg_company varchar2(4000),
	drg_effect varchar2(4000),
	drg_dosage CLOB,
	drg_warning CLOB,
	drg_precaution CLOB,
	drg_interaction CLOB,
	drg_seffect CLOB,
	drg_storage CLOB,
	drg_img varchar2(3000),
	constraint drug_pk primary key (drg_num)
);
create sequence drug_seq;

--의약품 복용 기록
create table MEDICINE(
	med_num number not null,
	mem_num number not null,
	med_title varchar2(400) not null,
	med_name clob not null,
	med_sdate varchar2(10) not null,
	med_edate varchar2(10) not null,
	med_time varchar2(30) not null,
	med_dosage varchar2(50) not null,
	med_note varchar2(300),
	constraint medicine_pk primary key (med_num),
	constraint medicine_fk foreign key (mem_num) references member(mem_num)
);
create sequence medicine_seq;


--커뮤니티
create table CBOARD(
	cbo_num number not null,
	mem_num number not null,
	cbo_type number not null,
	cbo_title varchar2(150) not null,
	cbo_content clob not null,
	cbo_hit number(9) default 0 not null,
	cbo_rdate date default sysdate not null,
	cbo_mdate date,
	cbo_report number default 0 not null, --누적 신고수
	constraint cboard_pk primary key (cbo_num),
	constraint cboard_fk foreign key (mem_num) references member (mem_num)
);
create sequence cboard_seq;

--커뮤니티 게시글 좋아요
create table cboard_fav(
	cbo_num number not null,
	mem_num number not null,
	constraint cboard_fav_fk1 foreign key (cbo_num) references cboard (cbo_num),
	constraint cboard_fav_fk2 foreign key (mem_num) references member (mem_num)
);

--커뮤니티 댓글(신고글을 위해서 cboard와 fk 미진행)
create table cboard_re(
	cre_num number not null,
	cbo_num number not null, --댓글의 부모글 번호
	mem_num number not null,
	cre_content varchar2(900) not null,
	cre_rdate date default sysdate not null,
	cre_mdate date,
	cre_level number default 0 not null, --깊이
	cre_ref number default 0 not null, --답글의 댓글번호
	cre_report number default 0 not null, --신고수
	constraint cboard_re_pk primary key (cre_num),
	constraint cboard_re_fk foreign key (mem_num) references member (mem_num)
);
create sequence cboard_re_seq;

--커뮤니티 댓글 좋아요
create table cboard_re_fav(
	cre_num number not null,
	mem_num number not null,
	constraint cboard_re_fav_fk1 foreign key (cre_num) references cboard_re (cre_num),
	constraint cboard_re_fav_fk2 foreign key (mem_num) references member (mem_num)
);