DROP DATABASE IF EXISTS `mobile_bank_system`;
CREATE DATABASE `mobile_bank_system`;
USE `mobile_bank_system`;

CREATE TABLE users (
    id INT(11) NOT NULL AUTO_INCREMENT,
	login VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	phone NVARCHAR(10) NOT NULL,
	PRIMARY KEY (id),
	KEY (login),
	KEY (password),
	KEY (phone),
	KEY (address),
	UNIQUE KEY (login),
	UNIQUE KEY (phone)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE accounts (
    id VARCHAR(100) NOT NULL,
	client_id INT(11) NOT NULL,
	amount DECIMAL(11, 2) NOT NULL DEFAULT 0,
	acc_code VARCHAR(10) NOT NULL,
	PRIMARY KEY (id),
	KEY (client_id),
	KEY (amount),
	KEY (acc_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE operations (
    id INT(11) NOT NULL AUTO_INCREMENT,
	operation_date VARCHAR(30) NOT NULL,
	acc_code VARCHAR(10) NOT NULL,
	account_from VARCHAR(100) NOT NULL,
	account_to VARCHAR(100) NOT NULL,
	amount DECIMAL(11, 2) NOT NULL DEFAULT 0,
	amount_before DECIMAL(11, 2) NOT NULL,
	amount_after DECIMAL(11, 2) NOT NULL,
	PRIMARY KEY (id),
	KEY (operation_date),
	KEY (acc_code),
	KEY (account_from),
	KEY (account_to),
	KEY (amount),
	KEY (amount_before),
	KEY (amount_after)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE authorized_users (
    id INT(11) NOT NULL,
	token VARCHAR(100) NOT NULL,
	PRIMARY KEY (id),
	KEY (token)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


ALTER TABLE accounts ADD FOREIGN KEY (client_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE authorized_users ADD FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;