--CREATE DATABASE

create database chama_computers;

use chama_computers;


CREATE TABLE employee(
                         emp_id VARCHAR(5) PRIMARY KEY,
                         name VARCHAR(20),
                         address VARCHAR(20),
                         nic VARCHAR(12),
                         position VARCHAR(20),
                         contact VARCHAR(10),
                         dob DATE,
                         enroll_date DATE,
                         email VARCHAR(40),
                         basic_salary DECIMAL(10,2),
                         path VARCHAR
);

CREATE TABLE users(
                      user_name VARCHAR(10) PRIMARY KEY,
                      password VARCHAR(15)
);

CREATE TABLE customer(
                         cust_id VARCHAR(5) PRIMARY KEY,
                         c_name VARCHAR(20),
                         c_address VARCHAR(20),
                         c_nic VARCHAR(12),
                         contact_no VARCHAR(10),
                         email VARCHAR(40)
);


CREATE TABLE transport(
                          tr_id VARCHAR(5) PRIMARY KEY,
                          vehicle_no VARCHAR(10),
                          driver_name VARCHAR(20),
                          transport_cost DECIMAL(10,2)
);


CREATE TABLE brand_new_item(
                               item_id VARCHAR(5) PRIMARY KEY,
                               name VARCHAR(20),
                               category VARCHAR(20),
                               brand VARCHAR(20),
                               model_no VARCHAR(15),
                               unit_price DECIMAL(8,2),
                               hand_on_qty VARCHAR(10),
                               description VARCHAR(20),
                               warranty VARCHAR(15)
);


CREATE TABLE used_item(
                          item_id VARCHAR(5) PRIMARY KEY,
                          name VARCHAR(20),
                          category VARCHAR(20),
                          brand VARCHAR(20),
                          model_no VARCHAR(15),
                          unit_price DECIMAL(8,2),
                          hand_on_qty VARCHAR(10),
                          description VARCHAR(20)
);

CREATE TABLE orders(
                       order_id VARCHAR(5) PRIMARY KEY,
                       cust_id VARCHAR(5),
                       CONSTRAINT FOREIGN KEY(cust_id) REFERENCES customer(cust_id) on Delete Cascade on Update Cascade,
                       tr_id VARCHAR(5),
                       CONSTRAINT FOREIGN KEY(tr_id) REFERENCES transport(tr_id) on Delete Cascade on Update Cascade,
                       order_date DATE,
                       payment VARCHAR(10)

);


CREATE TABLE supplier(
                         sup_id VARCHAR(5) PRIMARY KEY,
                         person_name VARCHAR(20),
                         company_name VARCHAR(20),
                         contact_no INT(10),
                         location VARCHAR(20),
                         email VARCHAR(40)
);


CREATE TABLE repair(
                       rep_id VARCHAR(5) PRIMARY KEY,
                       date_recive DATE,
                       date_return DATE,
                       repair_cost DECIMAL(8,2),
                       description VARCHAR(50),
                       item_id VARCHAR(5),
                       cust_id VARCHAR(5),
                       CONSTRAINT FOREIGN KEY(item_id) REFERENCES used_item(item_id) on Delete Cascade on Update Cascade,
                       CONSTRAINT FOREIGN KEY(cust_id) REFERENCES customer(cust_id) on Delete Cascade on Update Cascade
);


CREATE TABLE order_detail(
                                  order_id VARCHAR(5),
                                  CONSTRAINT FOREIGN KEY(order_id) REFERENCES orders(order_id) on Delete Cascade on Update Cascade,
                                  item_id VARCHAR(5),
                                  CONSTRAINT FOREIGN KEY(item_id) REFERENCES brand_new_item(item_id) on Delete Cascade on Update Cascade,
                                  qty VARCHAR(10),
                                  unit_price DECIMAL(10,2)
);


CREATE TABLE item_supplier_detail(
                                     item_id VARCHAR(5),CONSTRAINT FOREIGN KEY(item_id) REFERENCES brand_new_item(item_id) on Delete Cascade on Update Cascade,
                                     sup_id VARCHAR(5),CONSTRAINT FOREIGN KEY(sup_id) REFERENCES supplier(sup_id) on Delete Cascade on Update Cascade,
                                     qty VARCHAR(10),
                                     unit_price DECIMAL(10,2)
);