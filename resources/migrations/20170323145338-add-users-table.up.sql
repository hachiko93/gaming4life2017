CREATE TABLE IF NOT EXISTS users
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 last_login TIME,
 is_active BOOLEAN,
 pass VARCHAR(300),
 about_me VARCHAR(300),
 profile_picture VARCHAR (300));

CREATE TABLE IF NOT EXISTS products
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50),
 description VARCHAR(30),
 type VARCHAR(10),
 price INT(10),
 about VARCHAR(300),
 picture VARCHAR (300));

CREATE TABLE IF NOT EXISTS cart
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 product_id VARCHAR(20),
 is_bought BOOLEAN,
 FOREIGN KEY (product_id) REFERENCES products(id));

CREATE TABLE IF NOT EXISTS usercart
(id VARCHAR(20) PRIMARY KEY AUTO_INCREMENT,
 user_id VARCHAR(20),
 cart_id VARCHAR(20),
 FOREIGN KEY (cart_id) REFERENCES cart(id),
 FOREIGN KEY (user_id) REFERENCES users(id));