DELETE FROM role;

INSERT INTO role(name)
VALUES ('Admin');

INSERT INTO role(name)
VALUES ('Teacher');

INSERT INTO role(name)
VALUES ('Student');

DELETE FROM users;

INSERT INTO users(roleid, login, password)
VALUES ('1', 'donjuanmatus', 'tonnal');

INSERT INTO users(roleid, login, password)
VALUES ('2', 'dongenaro', 'nagval');

INSERT INTO users(roleid, login, password)
VALUES ('3', 'carlos', 'apprentice');


