create table chat(
	chat_num number not null,
	mem_num number not null,
	doc_num number not null,
	chat_reg_date date default SYSDATE not null,
	chat_status number default 0 not null,
	constraint chat_pk primary key (chat_num),
	constraint chat_fk1 foreign key (mem_num) references member(mem_num),
	constraint chat_fk2 foreign key (doc_num) references member(mem_num)
);

create sequence chat_seq;

create table chat_msg(
	msg_num number not null,
	chat_num number not null,
	msg_content varchar2(900) not null,
	msg_sender_type number not null,
	constraint chat_msg_pk primary key (msg_num),
	constraint chat_msg_fk foreign key (chat_num) references chat(chat_num)
);

create sequence msg_seq;

create table chat_files(
	file_num number not null,
	file_name varchar2(400) not null,
	mem_num number not null,
	doc_num number not null,
	file_type number not null,
	file_reg_date default sysdate not null,
	constraint chat_files_pk primary key (file_num),
	constraint chat_files_fk1 foreign key (mem_num) references member(mem_num),
	constraint chat_files_fk2 foreign key (doc_num) references member(mem_num)
);

create sequence files_seq;

create table chat_payment(
	pay_num number not null,
	chat_num number not null,
	mem_num number not null,
	pay_amount number not null
	constraint chat_payment_pk primary key (pay_num),
	constraint chat_payment_fk1 foreign key (chat_num) references chat(chat_num),
	constraint chat_payment_fk2 foreign key (mem_num) references member(mem_num)
);

create sequence payment_seq;