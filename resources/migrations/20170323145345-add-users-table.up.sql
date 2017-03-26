DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS purchase;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product_types;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 pass VARCHAR(300),
 about_me VARCHAR(300),
 profile_picture VARCHAR (300));

CREATE TABLE product_types
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(20));

INSERT INTO product_types
(name) VALUES ('GAMES');

INSERT INTO product_types
(name) VALUES ('CONSOLES');

INSERT INTO product_types
(name) VALUES ('OTHER');

CREATE TABLE products
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50),
 description VARCHAR(30),
 type VARCHAR(20),
 price INT(10),
 picture LONGTEXT,
 FOREIGN KEY (type) REFERENCES product_types(id));


CREATE TABLE cart
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 product_id VARCHAR(20),
 user_id VARCHAR(20),
 quantity INT(10),
 FOREIGN KEY (product_id) REFERENCES products(id),
 FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE purchase
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 product_id VARCHAR(20),
 user_id VARCHAR(20),
 quantity INT(10),
 FOREIGN KEY (product_id) REFERENCES products(id),
 FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE articles
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50),
 article_text VARCHAR(500),
 product_id VARCHAR(20),
 creation_date TIMESTAMP,
 FOREIGN KEY (product_id) REFERENCES products(id));
