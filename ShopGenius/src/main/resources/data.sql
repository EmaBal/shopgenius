BEGIN	IF NOT EXISTS (SELECT * FROM users WHERE email = 'admin@admin.com') BEGIN INSERT INTO users (email, ENABLED, fist_name, last_name, password) VALUES ('admin@admin.com', true, 'admin', 'admin', 'admin') END END