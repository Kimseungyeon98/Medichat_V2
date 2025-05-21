
CREATE TABLE Disease(
dis_num	NUMBER	NOT NULL primary key,
dis_name VARCHAR2(90) NOT NULL,
dis_symptoms	VARCHAR2(300)	NOT NULL,
dis_department	VARCHAR2(2)	NOT NULL,
dis_hit	NUMBER	DEFAULT 0	NOT NULL
);
CREATE sequence dis_seq;


CREATE TABLE Healthy_Blog (
	healthy_num	NUMBER		NOT NULL primary key,
	mem_num	NUMBER		NOT NULL,
	healthy_title	VARCHAR2(90)		NOT NULL,
	healthy_content	CLOB		NOT NULL,
	h_reg_date	DATE	DEFAULT SYSDATE	NOT NULL,
	h_modify_date	DATE		NULL,
	healthy_hit	NUMBER(8)	DEFAULT 0	NOT NULL,
    constraint healthy_fk1 foreign key (mem_num) references member(mem_num)
);

create sequence health_seq;

CREATE TABLE Healthy_Re (
	hre_num	NUMBER NOT NULL primary key,
	healthy_num	NUMBER		NOT NULL ,
	mem_num	NUMBER		NOT NULL,
	hre_renum	NUMBER		NULL,
	hre_content	VARCHAR2(900)		NOT NULL,
	hre_reg_date	DATE	DEFAULT sysdate	NOT NULL,
	hre_modify_date	DATE		NULL,
	hre_level	NUMBER(1)	DEFAULT 0	NOT NULL,
    constraint hre_fk1 foreign key (healthy_num) references healthy_blog(healthy_num),
    constraint hre_fk2 foreign key (mem_num) references member(mem_num)
);
=======
CREATE TABLE Disease(
dis_num	NUMBER	NOT NULL primary key,
dis_name VARCHAR2(90) NOT NULL,
dis_symptoms	VARCHAR2(300)	NOT NULL,
dis_department	VARCHAR2(2)	NOT NULL,
dis_hit	NUMBER	DEFAULT 0	NOT NULL
);
CREATE sequence dis_seq;


CREATE TABLE Healthy_Blog (
	healthy_num	NUMBER		NOT NULL primary key,
	mem_num	NUMBER		NOT NULL,
	healthy_title	VARCHAR2(90)		NOT NULL,
	healthy_content	CLOB		NOT NULL,
	h_reg_date	DATE	DEFAULT SYSDATE	NOT NULL,
	h_modify_date	DATE		NULL,
	healthy_hit	NUMBER(8)	DEFAULT 0	NOT NULL,
    constraint healthy_fk1 foreign key (mem_num) references member(mem_num)
);

create sequence health_seq;


CREATE TABLE Video_Healthy (
	video_num	NUMBER		NOT NULL primary key,
	mem_num	NUMBER		NOT NULL,
	video_title	VARCHAR2(90)		NOT NULL,
	video_content	CLOB		NOT NULL,
	v_reg_date	DATE	DEFAULT sysdate	NOT NULL,
	v_modify_date	DATE		NULL,
	video_hit	NUMBER(8)	DEFAULT 0	NOT NULL,
	v_category	CHAR(2)		NOT NULL,
    constraint v_healthy_fk1 foreign key (mem_num) references member(mem_num)
);
