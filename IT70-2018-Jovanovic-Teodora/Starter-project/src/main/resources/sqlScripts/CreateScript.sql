drop table if exists obrazovanje cascade;
drop table if exists preduzece cascade;
drop table if exists radnik cascade;
drop table if exists sektor cascade;

drop sequence if exists obrazovanje_seq;
drop sequence if exists preduzece_seq;
drop sequence if exists radnik_seq;
drop sequence if exists sektor_seq;



create table obrazovanje (
	id integer not null, 
	naziv varchar(100),
	stepen_strucne_spreme varchar(10),
	opis varchar(500)
);

create table radnik (
	id integer not null, 
	ime varchar(50),
	prezime varchar(50),
	broj_lk integer,
	obrazovanje integer not null,
	sektor integer not null
);

create table preduzece (
	id integer not null, 
	naziv varchar(100),
	pib integer,
	sediste varchar(100),
	opis varchar(500)
);

create table sektor (
	id integer not null, 
	naziv varchar(100),
	oznaka varchar(10),
	preduzece integer not null
);

alter table obrazovanje add constraint pk_obrazovanje primary key(id);
alter table radnik add constraint pk_radnik primary key(id);
alter table preduzece add constraint pk_preduzece primary key(id);
alter table sektor add constraint pk_sektor primary key(id);

alter table radnik add constraint fk_radnik_obrazovanje foreign key(obrazovanje) references obrazovanje(id);
alter table radnik add constraint fk_radnik_sektor foreign key(sektor) references sektor(id);
alter table sektor add constraint fk_sektor_preduzece foreign key(preduzece) references preduzece(id);

create index idxpk_obrazovanje on obrazovanje(id);
create index idxpk_radnik on radnik(id);
create index idxpk_preduzece on preduzece(id);
create index idxpk_sektor on sektor(id);

create index idxfk_radnik_obrazovanje on radnik(obrazovanje);
create index idxfk_radnik_sektor on radnik(sektor);
create index idxfk_sektor_preduzece on sektor(preduzece);

create sequence obrazovanje_seq
increment 1;
create sequence preduzece_seq
increment 1;
create sequence radnik_seq
increment 1;
create sequence sektor_seq
increment 1;


