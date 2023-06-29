CREATE SEQUENCE fruit_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE fruit (
	id int8 NOT NULL,
	name varchar(80) NULL,
	quantity int4 NULL,
	CONSTRAINT fruit_pkey PRIMARY KEY (id)
);

INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Maçã', 5);
INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Pera', 3);
INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Laranja', 1);
