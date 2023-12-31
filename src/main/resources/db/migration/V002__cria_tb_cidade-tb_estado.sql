create table tb_cidade (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	estado_id bigint not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_estado (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table tb_cidade add constraint fk_cidade_estado foreign key (estado_id) references tb_estado (id);
