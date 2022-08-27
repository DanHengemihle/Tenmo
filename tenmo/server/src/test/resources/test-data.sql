BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
INCREMENT BY 1
START WITH 3001
NO MAXVALUE;

CREATE TABLE transfer(
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	status varchar NOT NULL DEFAULT ('Pending'),
	CHECK (status = 'Pending' OR
	 status = 'Approved' OR status = 'Denied'),
	amount decimal(13, 2) NOT NULL,
	to_account_id int NOT NULL,
	from_account_id int NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_account FOREIGN KEY (from_account_id) REFERENCES account (account_id)
);



INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy');

INSERT INTO account (account_id, user_id, balance)
VALUES('4001', '1001', '1000.00'),
       ('4002', '1002', '750.00');

INSERT INTO transfer (transfer_id, status, amount, to_account_id, from_account_id)
VALUES('5001', 'Pending', '100.00', '4002', '4001'),
        ('5002', 'Pending', '250.00', '4001', '4002'),
        ('5003', 'Approved', '200.00', '4001', '4002'),
        ('5004', 'Denied', '300.00', '4002', '4001'),
        ('5005', 'Pending', '50.00', '4002', '4002'),
        ('5006', 'Pending', '-10.00', '4002', '4001'),
        ('5007', 'Pending', '0.00', '4001', '4002'),
        ('5008', 'Pending', '25.00', '4001', '4002');
COMMIT;