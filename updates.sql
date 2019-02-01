CREATE DATABASE eddb;
DROP TABLE IF EXISTS organization CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
CREATE TABLE organization (
  org_id bigint NOT NULL,
  org_name text NOT NULL ,
  org_adress text NOT NULL ,
  org_type text NOT NULL ,
  activities text NOT NULL ,
  director text NOT NULL ,
  departments text NOT NULL ,
  insalubrity boolean,
  date_create timestamp without time zone NOT NULL,
  status boolean,
  PRIMARY KEY (org_id)
);

CREATE TABLE employees (
  emp_id bigint NOT NULL,
  emp_name text NOT NULL ,
  emp_last_name text NOT NULL ,
  emp_age int NOT NULL ,
  emp_pos text NOT NULL ,
  org_id bigint NOT NULL ,
  department text NOT NULL ,
  salary bigint NOT NULL ,
  education text NOT NULL,
  status boolean,
  PRIMARY KEY (emp_id)
);

ALTER TABLE employees
  ADD CONSTRAINT fk_organization_id
FOREIGN KEY (org_id) REFERENCES organization (org_id);

CREATE SEQUENCE organization_id_seq;

ALTER TABLE organization 
    ALTER COLUMN org_id 
        SET DEFAULT NEXTVAL('organization_id_seq');

CREATE SEQUENCE employees_id_seq;

ALTER TABLE employees 
    ALTER COLUMN emp_id
        SET DEFAULT NEXTVAL('employees_id_seq');



CREATE INDEX employees_index_emp_name_emp_lastname ON employees (emp_name, emp_last_name);

CREATE INDEX employees_index_emp_age ON employees(emp_age);

CREATE INDEX employees_index_salary ON employees(salary);

CREATE INDEX employees_index_organizaton ON employees(org_id);

CREATE INDEX organization_index_org_name ON organization (org_name);











