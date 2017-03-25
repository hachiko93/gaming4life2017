-- :name create-user :! :n
-- :doc creates a new user record
INSERT INTO users
(first_name, last_name, email, pass, about_me, profile_picture)
VALUES (:first_name, :last_name, :email, :pass, :about_me, :profile_picture)

-- :name update-user :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email, about_me = :about_me, profile_picture = :profile_picture
WHERE id = :id

-- :name change-pass :! :n
-- :doc change password
UPDATE users
SET pass = :pass
WHERE id = :id

-- :name login :? :1
-- :doc retrieve a user given the id and password.
SELECT * FROM users
WHERE id = :id AND pass = :pass

-- :name delete-user :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name get-types :? :*
-- :doc retrieve all types of products.
SELECT * FROM product_types

-- :name save-product :! :n
-- :doc creates a new product.
INSERT INTO products
(name, description, type, price, picture)
VALUES (:name, :description, :type, :price, :picture)

-- :name get-products :? :*
-- :doc retrieve all products.
SELECT * FROM products

-- :name search-products :? :*
-- :doc searches for products with given name
SELECT * FROM products
WHERE name LIKE '%' || :search || '%'
ORDER BY name

-- :name delete-product :! :n
-- :doc delete a product given the id
DELETE FROM products
WHERE id = :id
