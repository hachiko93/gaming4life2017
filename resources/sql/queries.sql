-- :name create-user :! :n
-- :doc creates a new user record
INSERT INTO users
(first_name, last_name, email, admin, pass)
VALUES (:first_name, :last_name, :email, :admin, :pass)

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
WHERE email = :email AND pass = :pass

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

-- :name get-products-params :? :*
-- :doc retrieve all products from certain type
SELECT *
FROM products AS p
JOIN product_types AS pt
ON p.type=pt.id
WHERE pt.name=:type
ORDER BY p.name

-- :name delete-product :! :n
-- :doc delete a product given the id
DELETE FROM products
WHERE id = :id

-- :name add-to-cart :! :n
-- :doc adds a product to cart.
INSERT INTO cart
(product_id, user_id, quantity)
VALUES (:product_id, :user_id, :quantity)

-- :name get-cart-products :? :*
-- :doc retrieve all products of a specific user.
SELECT * FROM cart
