-- :name create-user :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass, about_me, profile_picture)
VALUES (:id, :first_name, :last_name, :email, :pass, :about_me, :profile_picture)

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

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id
