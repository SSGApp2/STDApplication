INSERT INTO app_role (id, created_by, created_date, is_active, status, updated_by, updated_date, version, description, role_code, role_name) VALUES(1, 'INIT', NULL, NULL, NULL, 'INIT', NULL, 0, NULL, 'ADMIN', 'ADMIN');

INSERT INTO app_user (id, created_by, created_date, is_active, status, updated_by, updated_date, version, description, password, user_email, username) VALUES(1, 'INIT', NULL, NULL, NULL, NULL, NULL, 0, NULL, 'test', NULL, 'test');

INSERT INTO app_user_role (id, created_by, created_date, is_active, status, updated_by, updated_date, version, app_role, app_user) VALUES(1, 'INIT', NULL, NULL, NULL, 'INIT', NULL, 0, 1, 1);
