CREATE TABLE IF NOT EXISTS perm_group(
name_group CHAR(64) NOT NULL,
prefix CHAR(32),
PRIMARY KEY ( name_group )
);

CREATE TABLE IF NOT EXISTS perm(
id INT NOT NULL AUTO_INCREMENT,
name_perm CHAR(128) NOT NULL,
name_group CHAR(64) NOT NULL,
state BOOLEAN DEFAULT FALSE,
PRIMARY KEY ( id ),
KEY ( name_perm ),
FOREIGN KEY (name_group) REFERENCES perm_group(name_group)
);

CREATE TABLE IF NOT EXISTS child(
id INT NOT NULL AUTO_INCREMENT,
name_child CHAR(64) NOT NULL,
name_group CHAR(64) NOT NULL,
PRIMARY KEY ( id ),
FOREIGN KEY (name_child) REFERENCES perm_group(name_group),
FOREIGN KEY (name_group) REFERENCES perm_group(name_group)
);

CREATE TABLE IF NOT EXISTS perm_server(
id INT NOT NULL AUTO_INCREMENT,
name_server CHAR(64) NOT NULL,
name_group CHAR(64) NOT NULL,
state BOOLEAN DEFAULT FALSE,
PRIMARY KEY ( id ),
KEY ( name_server ),
KEY ( state ),
FOREIGN KEY (name_group) REFERENCES perm_group(name_group)
);

CREATE TABLE IF NOT EXISTS player(
id INT NOT NULL AUTO_INCREMENT,
uuid CHAR(36) NOT NULL,
name_player CHAR(16) NOT NULL,
name_group CHAR(64) NOT NULL,
PRIMARY KEY ( id ),
KEY ( name_player ),
KEY ( uuid ),
FOREIGN KEY (name_group) REFERENCES perm_group(name_group)
);