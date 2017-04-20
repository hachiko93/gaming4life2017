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
 profile_picture VARCHAR(MAX));

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
 description VARCHAR(500),
 type VARCHAR(20),
 price INT(10),
 picture VARCHAR(MAX),
 FOREIGN KEY (type) REFERENCES product_types(id));

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Civilization V', 'One of the best strategy games of all times.', '1', 50, '/img/store/civilization.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Diablo 3 Mouse', 'Necessary gaming equipment for any gamer.', '3', 20, '/img/store/diablo3.png');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Fallout IV', 'THE FPS game that most of us played but not many can say that they truly seen it.', '1', 50, '/img/store/fallout4.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Fallout Headphones', 'Made for the real FPS fanatics.', '3', 30, '/img/store/fallout4v3.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('The Elder Scrolls III: Oblivion', 'Third and the best part of The Elder Scrolls series.', '1', 40, '/img/store/oblivion.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Play Station 4', 'The newest Play Station console, a must-have for all you console pesants.', '2', 500, '/img/store/ps4.png');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('Play Station 4 Joypad', 'If you are gonna play games on consoles, you gonna need one of these.', '3', 25, '/img/store/ps4c.png');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('The Elder Scrolls III: Skyrim', 'The forth part of The Elder Scrolls series, full of beautiful scenary and amaizing story.', '1', 30, '/img/store/skyrim.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('XBOX One Blue Joypad', 'If you are gonna play games on consoles, you gonna need one of these.', '3', 30, '/img/store/xboxc.jpg');

INSERT INTO products
(name, description, type, price, picture)
VALUES ('XBOX One Console with Joypad', 'Feel like PlayStation is not really for you? Then here is something that you will not be able to resist', '2', 400, '/img/store/xbox.jpg');

CREATE TABLE cart
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 product_id VARCHAR(20),
 user_id VARCHAR(20),
 quantity INT(10),
 FOREIGN KEY (product_id) REFERENCES products(id),
 FOREIGN KEY (user_id) REFERENCES users(id));
