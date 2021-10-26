BEGIN	IF NOT EXISTS (SELECT * FROM users WHERE email = 'admin@admin.com') BEGIN INSERT INTO users (email, ENABLED, fist_name, last_name, password) VALUES ('admin@admin.com', true, 'admin', 'admin', 'admin') END END


INSERT INTO `users` (`id`, `email`, `ENABLED`, `first_name`, `last_name`, `password`) VALUES (NULL, 'admin@admin', b'1', 'admin', 'admin', 'admin');

id in users, id in roles
INSERT INTO `user_role` (`id`, `role_id`) VALUES ('3', '1');