--회원 테이블
create table member(
	mem_num NUMBER NOT NULL,
	mem_id VARCHAR2(12) NOT NULL,	--아이디
	mem_name VARCHAR2(30) NOT NULL,	--이름
	mem_photo VARCHAR2(400),	-- 프로필 사진(의사회원은 not null)
	mem_auth NUMBER(1) DEFAULT 2 NOT NULL,	--권한등급
	CONSTRAINT member_pk PRIMARY KEY (mem_num)
);
create sequence member_seq;

--일반회원 상세 테이블
create table member_detail(
	mem_num NUMBER NOT NULL,
	au_id VARCHAR2(36) UNIQUE,	--자동로그인에 식별되는 식별값
	mem_passwd VARCHAR2(20) NOT NULL,
	mem_birth DATE NOT NULL,
	mem_email VARCHAR2(50) NOT NULL,
	mem_phone VARCHAR2(11) NOT NULL,
	mem_zipcode VARCHAR2(5) NOT NULL,
	mem_address1 VARCHAR2(90) NOT NULL,
	mem_address2 VARCHAR2(90) NOT NULL,
	mem_reg DATE DEFAULT sysdate NOT NULL,
	mem_modify DATE,
	CONSTRAINT member_detail_pk PRIMARY KEY (mem_num),
	CONSTRAINT member_detail_fk FOREIGN KEY (mem_num) REFERENCES member (mem_num)
);

--의사회원 상세 테이블
create table doctor_detail(
	doc_num NUMBER NOT NULL,
	hos_num NUMBER NOT NULL,
	doc_passwd VARCHAR2(20) NOT NULL,
	doc_email VARCHAR2(50) NOT NULL,
	doc_reg DATE DEFAULT sysdate NOT NULL,
	doc_license VARCHAR2(400) NOT NULL,	--의사 면허증
	doc_history CLOB,	--연혁
	doc_off NUMBER(1),	--휴무일
	doc_time VARCHAR2(11), --진료 시간
	doc_treat NUMBER(1) DEFAULT 0 NOT NULL,	--비대면 진료 여부(0:진료X / 1:진료O)
	CONSTRAINT doctor_detail_pk PRIMARY KEY (doc_num),
	CONSTRAINT doctor_detail_fk1 FOREIGN KEY (doc_num) REFERENCES member(mem_num),
	CONSTRAINT doctor_detail_fk2 FOREIGN KEY (hos_num) REFERENCES hospital(hos_num)
); 
create sequence doctor_detail_seq;