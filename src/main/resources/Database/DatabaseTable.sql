drop database chama_computers;

create database chama_computers;

use chama_computers;


CREATE TABLE employee(
                         emp_id VARCHAR(5) PRIMARY KEY,
                         name TEXT,
                         address TEXT,
                         nic VARCHAR(12),
                         position VARCHAR(20),
                         contact VARCHAR(10),
                         dob DATE,
                         enroll_date DATE,
                         email VARCHAR(40),
                         basic_salary DECIMAL(10,2),
                         path TEXT
);

CREATE TABLE users(
                      user_name VARCHAR(20) PRIMARY KEY,
                      password TEXT
);

CREATE TABLE customer(
                         cust_id VARCHAR(5) PRIMARY KEY,
                         c_name VARCHAR(20),
                         c_address VARCHAR(20),
                         c_nic VARCHAR(12) unique,
                         contact_no VARCHAR(10) unique,
                         email VARCHAR(40)
);


CREATE TABLE transport(
                          tr_id VARCHAR(5) PRIMARY KEY,
                          vehicle_no VARCHAR(10),
                          driver_name VARCHAR(20),
                          location VARCHAR(50),
                          transport_cost DECIMAL(10,2)
);


CREATE TABLE item(
                     item_id VARCHAR(5) PRIMARY KEY,
                     name VARCHAR(20),
                     category VARCHAR(20),
                     brand VARCHAR(20),
                     date DATE,
                     description TEXT,
                     warranty VARCHAR(15),
                     type VARCHAR(10),
                     path TEXT
);

CREATE TABLE orders(
                       order_id VARCHAR(5) PRIMARY KEY,
                       cust_id VARCHAR(5),
                       CONSTRAINT FOREIGN KEY(cust_id) REFERENCES customer(cust_id) on Delete Cascade on Update Cascade,
                       tr_id VARCHAR(5),
                       CONSTRAINT FOREIGN KEY(tr_id) REFERENCES transport(tr_id) on Delete Cascade on Update Cascade,
                       order_date DATE,
                       payment TEXT
);


CREATE TABLE supplier(
                         sup_id VARCHAR(5) PRIMARY KEY,
                         company_name TEXT,
                         person_name TEXT,
                         contact_no INT,
                         location VARCHAR(20),
                         email VARCHAR(40)
);


CREATE TABLE repair(
                       rep_id VARCHAR(5) PRIMARY KEY,
                       date_recive DATE,
                       date_return DATE,
                       repair_cost DECIMAL(8,2),
                       description TEXT,
                       cust_id VARCHAR(5),
                       itemName VARCHAR(30),
                       CONSTRAINT FOREIGN KEY(cust_id) REFERENCES customer(cust_id) on Delete Cascade on Update Cascade
);


CREATE TABLE order_detail(
                             order_id VARCHAR(5),
                             CONSTRAINT FOREIGN KEY(order_id) REFERENCES orders(order_id) on Delete Cascade on Update Cascade,
                             item_id VARCHAR(5),
                             CONSTRAINT FOREIGN KEY(item_id) REFERENCES item(item_id) on Delete Cascade on Update Cascade,
                             qty VARCHAR(10),
                             unit_price DECIMAL(10,2)
);


CREATE TABLE item_supplier_detail(
                                     item_id VARCHAR(5),
                                     CONSTRAINT FOREIGN KEY(item_id) REFERENCES item(item_id) on Update Cascade on Delete Cascade,
                                     sup_id VARCHAR(5),
                                     CONSTRAINT FOREIGN KEY(sup_id) REFERENCES supplier(sup_id) on Update Cascade on Delete Cascade,
                                     qty INT(10),
                                     unit_price DECIMAL(10,2)
);

CREATE TABLE location (
                          place VARCHAR(255) PRIMARY KEY,
                          lat DOUBLE,
                          lng DOUBLE
);

INSERT INTO location (place, lat, lng)
VALUES ('Kalutara', 6.585264855395206, 79.96085527795101);

INSERT INTO location (place, lat, lng)
VALUES ('Colombo',6.927536964290191, 79.86393207534826);

INSERT INTO location (place, lat, lng)
VALUES ('Kandy',7.290951950661763, 80.63147711707803);

INSERT INTO location (place, lat, lng)
VALUES ('Minuwangoda',7.184797957538298, 79.94836173247637);

INSERT INTO location (place, lat, lng)
VALUES ('Galle',6.03152881180865, 80.21480697621271);

INSERT INTO location (place, lat, lng)
VALUES ('Panadura',6.711053811499971, 79.9097716129893);

INSERT INTO location (place, lat, lng)
VALUES ('Matara',5.949174667858217, 80.54697120846816);

INSERT INTO location (place, lat, lng)
VALUES ('Anuradhapura',8.311363899295017, 80.41051955295175);

INSERT INTO location (place, lat, lng)
VALUES ('Negambo',7.202384607889234, 79.86889232460419);

INSERT INTO location (place, lat, lng)
VALUES ('Ja-Ela',7.0669296616245445, 79.90350557864505);

INSERT INTO location (place, lat, lng)
VALUES ('Kurunagala',7.48215024043348, 80.36018269338656);

INSERT INTO location (place, lat, lng)
VALUES ('Kuliyapitiya',7.472327402079904, 80.04429421970612);

INSERT INTO location (place, lat, lng)
VALUES ('Jaffna',9.66141109986867, 80.02507906307824);

INSERT INTO location (place, lat, lng)
VALUES ('Wadduwa',6.636454192598532, 79.9532466327645);

INSERT INTO location (place, lat, lng)
VALUES ('Rathmalana',6.819376599109911, 79.8808315820237);

INSERT INTO location (place, lat, lng)
VALUES ('Mount-Laveniya',6.8304555220194025, 79.87817083060982);

INSERT INTO location (place, lat, lng)
VALUES ('Maggona',6.510786925434501, 79.99261390086427);

INSERT INTO location (place, lat, lng)
VALUES ('Beruwala',6.474366952230507, 79.99054684231942);

INSERT INTO location (place, lat, lng)
VALUES ('Aluthgama',6.434579664077613, 79.99992919876203);

INSERT INTO location (place, lat, lng)
VALUES ('Katukurundha',6.5619542460121165, 79.96918173463999);

INSERT INTO location (place, lat, lng)
VALUES ('Moratuwa',6.787302781021451, 79.89225750145597);