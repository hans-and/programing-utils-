
-- added id to slapidata
ALTER TABLE slapidata ADD COLUMN id SERIAL PRIMARY KEY;

--Creates table for keeping data for implementation sl api call to bus 55  
 CREATE TABLE public.slapidata
(
    last_sl_api_call timestamp with time zone,
    number_of_calls_this_month bigint,
    total_no_of_calls bigint
);

ALTER TABLE public.slapidata
    OWNER to hansand;
    
---------------------------------------------------    

DROP TABLE IF EXISTS auth_user_role;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS auth_user;
CREATE SEQUENCE auth_role_seq;

CREATE TABLE auth_role (
  auth_role_id int NOT NULL DEFAULT NEXTVAL ('auth_role_seq'),
  role_name varchar(255) DEFAULT NULL,
  role_desc varchar(255) DEFAULT NULL,
  PRIMARY KEY (auth_role_id)
);
INSERT INTO auth_role VALUES (1,'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO auth_role VALUES (2,'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO auth_role VALUES (3,'SITE_USER','This user has access to site, after login - normal user');

CREATE SEQUENCE auth_user_seq;

CREATE TABLE auth_user (
  auth_user_id int NOT NULL DEFAULT NEXTVAL ('auth_user_seq'),
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  status varchar(255),
  PRIMARY KEY (auth_user_id)
);

CREATE TABLE auth_user_role (
  auth_user_id int NOT NULL,
  auth_role_id int NOT NULL,
  PRIMARY KEY (auth_user_id,auth_role_id)
 ,
  CONSTRAINT FK_auth_user FOREIGN KEY (auth_user_id) REFERENCES auth_user (auth_user_id),
  CONSTRAINT FK_auth_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id)
) ;
 
 CREATE INDEX FK_user_role ON auth_user_role (auth_role_id);

insert into auth_user (auth_user_id,first_name,last_name,email,password,status) values (1,'Hans','Andersson','hans.andersson@gmail.com','KattenOchPingvinen','VERIFIED');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','1');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','2');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','3');
    