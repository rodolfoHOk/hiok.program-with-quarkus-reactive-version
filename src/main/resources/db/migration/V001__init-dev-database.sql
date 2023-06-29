CREATE SEQUENCE fruit_sequence START WITH 1 INCREMENT by 1;

CREATE TABLE fruit (
	id bigint NOT NULL,
	name varchar(80) NOT NULL UNIQUE,
	quantity integer NOT NULL,
	CONSTRAINT fruit_pkey PRIMARY KEY (id)
);

INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Maçã', 5);
INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Pera', 3);
INSERT INTO Fruit(id, name, quantity) VALUES(nextval('fruit_sequence'), 'Laranja', 1);
