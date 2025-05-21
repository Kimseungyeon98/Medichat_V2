--정기휴무일 
create table dayoff(
 doff_num number not null,
 doc_num number not null,
 doff_week number not null,
 constraint doff_pk primary key (doff_num),
 constraint doff_fk foreign key (doc_num) references member (mem_num)
);
create sequence dayoff_seq;
-- 휴무일
create table holiday(
 holi_num number not null,
 doc_num number not null,
 holi_date varchar2(30) not null,
 holi_time varchar2(30) not null,
 holi_status number,
 constraint holi_pk primary key (holi_num),
 constraint holi_fk foreign key (doc_num) references member (mem_num)
);
create sequence holi_seq;
-- 예약내역
create table reservation(
 res_num number not null,
 mem_num number not null,
 doc_num number not null,
 res_status number(2) default 0 not null,
 res_type number(2) not null,
 res_date varchar2(30) not null,
 res_time varchar2(30) not null,
 res_reg date default sysdate not null,
 res_content clob not null,
 constraint res_pk primary key (res_num),
 constraint res_fk1 foreign key (mem_num) references member (mem_num),
 constraint res_fk2 foreign key (doc_num) references member (mem_num)
);
create sequence reservation_seq;
-- 진료후기
create table review(
 rev_num number not null,
 res_num number not null,
 mem_num number not null,
 hos_num number not null,
 rev_grade float not null,
 rev_title varchar2(90) not null,
 rev_content clob not null,
 rev_reg date default sysdate not null,
 reg_modify date,
 reg_hit number(10) default 0 not null,
 rev_report number default 0 not null,
 constraint rev_pk primary key (rev_num),
 constraint rev_fk1 foreign key (res_num) references reservation (res_num),
 constraint rev_fk2 foreign key (mem_num) references member (mem_num),
 constraint rev_fk3 foreign key (hos_num) references hospital (hos_num)
);
create sequence review_seq;
--진료 후기 좋아요
create table review_fav(
 rev_num number not null,
 mem_num number not null,
 constraint rev_fav_fk1 foreign key (rev_num) references review (rev_num),
 constraint rev_fav_fk2 foreign key (mem_num) references member (mem_num)
);