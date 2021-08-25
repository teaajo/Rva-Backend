insert into "obrazovanje"("id", "naziv", "stepen_strucne_spreme", "opis")
values (nextval('obrazovanje_seq'),'Srednje', 'Cetvrti', 'Zavrsena srednja strucna skola u Novom Sadu');
insert into "obrazovanje"("id", "naziv", "stepen_strucne_spreme", "opis")
values (nextval('obrazovanje_seq'), 'OAS', 'Sesti', 'Zavrsene osnovne akademske studije u Beogradu');
insert into "obrazovanje"("id", "naziv", "stepen_strucne_spreme", "opis")
values (nextval('obrazovanje_seq'), 'MAS', 'Sedmi', null );
insert into "obrazovanje"("id", "naziv", "stepen_strucne_spreme", "opis")
values (nextval('obrazovanje_seq'), 'Doktorske', 'Osmi', null );

insert into "obrazovanje"("id", "naziv", "stepen_strucne_spreme", "opis")
values (-100, 'test', 'testtt', null );


insert into "preduzece"("id", "naziv", "pib", "sediste", "opis")
values(nextval('preduzece_seq'), 'ad NIS', '104052135','Novi Sad', null );
insert into "preduzece"("id", "naziv", "pib", "sediste", "opis")
values(nextval('preduzece_seq'), 'Levi9', '103941306', 'Novi Sad', null );
insert into "preduzece"("id", "naziv", "pib", "sediste", "opis")
values(nextval('preduzece_seq'), 'Vojvodinasume', '10345689', 'Novi Sad', null );
insert into "preduzece"("id", "naziv", "pib", "sediste", "opis")
values(nextval('preduzece_seq'), 'VegaIT', '10371306', 'Novi Sad', null );

insert into "preduzece"("id", "naziv", "pib", "sediste", "opis")
values(-100, 'test', '107771306', 'Novi Sad', null );

insert into "sektor" ("id","naziv","oznaka", "preduzece")
values (nextval('sektor_seq'), 'finansijski', 'fsekt', 1);
insert into "sektor" ("id","naziv","oznaka", "preduzece")
values (nextval('sektor_seq'), 'mktg', 'msekt', 2);
insert into "sektor" ("id","naziv","oznaka", "preduzece")
values (nextval('sektor_seq'), 'nabavka', 'nsekt', 3);
insert into "sektor" ("id","naziv","oznaka", "preduzece")
values (nextval('sektor_seq'), 'prodajni', 'psekt', 4);
insert into "sektor" ("id","naziv","oznaka", "preduzece")
values (-100, 'prodajni', 'psekt', 4);

insert into "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
values (nextval('radnik_seq'), 'Teodora', 'Jovanovic', '67486321', 2, 2);
insert into "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
values (nextval('radnik_seq'), 'Andrijana', 'Dragicevic', '142536257', 2, 2);
insert into "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
values (nextval('radnik_seq'), 'Kristina','Nikolic', '674762321', 4, 4);
insert into "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
values (nextval('radnik_seq'), 'Ivona', 'Jovanovic', '67872221', 3, 3);
insert into "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
values (-100, 'Ivona', 'Jovanovic', '67872221', 3, 3);

