﻿DROP TABLE ITEM;
DROP TABLE ITEMGROUP;

CREATE TABLE ITEMGROUP (ID INTEGER PRIMARY KEY generated always as identity,
                       TITLE VARCHAR(100)  UNIQUE NOT NULL);

CREATE TABLE ITEM (ID INTEGER PRIMARY KEY generated always as identity,
                       NAME_FULL VARCHAR(100) UNIQUE NOT NULL, GROUPID INTEGER,
            FOREIGN KEY (GROUPID) REFERENCES ITEMGROUP(ID) );

INSERT INTO ITEMGROUP(TITLE) VALUES('Сборная России по биатлону');
INSERT INTO ITEMGROUP(TITLE) VALUES('Сборная России по баскетболу');
INSERT INTO ITEMGROUP(TITLE) VALUES('Сборная России по борьбе

INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Быстрова Т.А.',1);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Кротов П.Р.',3);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Самсонова Р.Р.',2);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Петров В.Р.',2);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Федорова Л.Д.',1);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Султанов Г.Ш.',1);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Антипова М.И.',3);
INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES('Никитин Д.С.',3);

