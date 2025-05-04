CREATE DATABASE TPPE;

USE TPPE;

CREATE TABLE PERSON(
	id INT auto_increment,
    name varchar(200) NOT NULL,
    email varchar(200) NOT NULL,
    birthdate date,
    constraint PERSON_PK primary key (id)
);

CREATE TABLE CLIENT(
	registration INT auto_increment,
	personId int NOT NULL,
    registratioDate datetime NOT NULL,
    notifyPromotion BIT default 0,
    CONSTRAINT CLIENT_PK primary key (registration),
	CONSTRAINT CLIENT_FK foreign key (personId) references PERSON(id)
);

CREATE TABLE SELLER(
	registration INT auto_increment,
    personId INT NOT NULL,
    baseSalary DOUBLE NOT NULL,
    numberHours DOUBLE NOT NULL,
    CONSTRAINT SELLER_PK primary key (registration),
    CONSTRAINT SELLER_PERSON_FK foreign key (personId) references PERSON(id)
);

CREATE TABLE SALE(
	id INT auto_increment,
    registrationSeller INT NOT NULL,
    registrationClient INT NOT NULL,
    date datetime NOT NULL,
    CONSTRAINT SALE_PK primary key (id),
    CONSTRAINT SALE_SELLER_FK foreign key (registrationSeller) references SELLER(registration),
    CONSTRAINT SALE_CLIENT_FK foreign key (registrationClient) references CLIENT(registration)
);

CREATE TABLE DEPARTMENT(
	id INT auto_increment,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(300),
    CONSTRAINT DEPARTMENT_PK primary key (id)
);

CREATE TABLE PRODUCT(
	id INT auto_increment,
    name VARCHAR(100) NOT NULL,
    price DECIMAL NOT NULL,
    description VARCHAR(300),
    CONSTRAINT PRODUCT_PK primary key (id)
);