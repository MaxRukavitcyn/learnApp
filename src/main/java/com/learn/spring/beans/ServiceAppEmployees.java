package com.learn.spring.beans;

//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pkg.db.classes.tables.Employees;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ServiceAppEmployees {
    // static Logger log = Logger.getLogger(com.zaxxer.hikari.HikariConfig.class);
    DataSource ds;

    @Autowired
    public ServiceAppEmployees(DataSource ds) {
        this.ds = ds;
    }

    //поиск сотрудников
    public List getListEmployees(String name, String lastName,
                                 Long age, Long idOrganization,
                                 String position, String department,
                                 Long salary, String education,
                                 Boolean status, String orderBy, Integer limit, Integer offset) throws Exception {
//        BasicConfigurator.configure();
//        log.info("This is Logger Info");


        try (Connection conn = ds.getConnection()) {


            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);

//проверяем поля на null, если не поле не null добовляем его в condition
            Condition condition = DSL.trueCondition();
            if (name != null && !name.isEmpty())
                condition = condition.and(Employees.EMPLOYEES.EMP_NAME.equal(name));

            if (lastName != null && !lastName.isEmpty())
                condition = condition.and(Employees.EMPLOYEES.EMP_LAST_NAME.equal(lastName));

            if (age != null)
                condition = condition.and(Employees.EMPLOYEES.EMP_AGE.equal(age));


            if (idOrganization != null)
                condition = condition.and(Employees.EMPLOYEES.ORG_ID.equal(idOrganization));

            if (position != null && !position.isEmpty())
                condition = condition.and(Employees.EMPLOYEES.EMP_POS.equal(position));

            if (department != null && !department.isEmpty())
                condition = condition.and(Employees.EMPLOYEES.DEPARTMENT.equal(department));

            if (salary != null)
                condition = condition.and(Employees.EMPLOYEES.SALARY.equal(salary));

            if (education != null && !education.isEmpty())
                condition = condition.and(Employees.EMPLOYEES.EMP_NAME.equal(education));

            if (status != null)
                condition = condition.and(Employees.EMPLOYEES.STATUS.equal(status));

            Collection<? extends OrderField<?>> fieldsToOrder = orderBy == null ? Collections.emptyList() :
                    Collections.singletonList(Employees.EMPLOYEES.field(orderBy));

            SelectSeekStepN<Record> orderedQuery = create.select().from(Employees.EMPLOYEES).
                    where(condition).orderBy(fieldsToOrder);

            SelectForUpdateStep lastQuery = orderedQuery;
            if (limit != null && offset != null) {

                lastQuery = orderedQuery.limit(limit).offset(offset);
            }

            return lastQuery.fetch().into(Employer.class);

        }
    }


    public Object getEmployer(long id) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            return create.select().from(Employees.EMPLOYEES).where(Employees.EMPLOYEES.EMP_ID.equal(id)).fetch().into(Employer.class);
        }
    }


    public void createEmploy(Employer emp) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.insertInto(Employees.EMPLOYEES)
                    .set(Employees.EMPLOYEES.EMP_NAME, emp.getEmpName())
                    .set(Employees.EMPLOYEES.EMP_LAST_NAME, emp.getEmpLastName())
                    .set(Employees.EMPLOYEES.EMP_AGE, emp.getEmpAge())
                    .set(Employees.EMPLOYEES.EMP_POS, emp.getEmpPos())
                    .set(Employees.EMPLOYEES.ORG_ID, emp.getOrgId())
                    .set(Employees.EMPLOYEES.DEPARTMENT, emp.getDepartment())
                    .set(Employees.EMPLOYEES.SALARY, emp.getSalary())
                    .set(Employees.EMPLOYEES.EDUCATION, emp.getEducation())
                    .set(Employees.EMPLOYEES.STATUS, emp.isStatus()).returning().fetch();

        }
    }


    public void changeEmployer(Employer emp) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.update(Employees.EMPLOYEES)
                    .set(Employees.EMPLOYEES.EMP_NAME, emp.getEmpName())
                    .set(Employees.EMPLOYEES.EMP_LAST_NAME, emp.getEmpLastName())
                    .set(Employees.EMPLOYEES.EMP_AGE, emp.getEmpAge())
                    .set(Employees.EMPLOYEES.EMP_POS, emp.getEmpPos())
                    .set(Employees.EMPLOYEES.ORG_ID, emp.getOrgId())
                    .set(Employees.EMPLOYEES.DEPARTMENT, emp.getDepartment())
                    .set(Employees.EMPLOYEES.SALARY, emp.getSalary())
                    .set(Employees.EMPLOYEES.EDUCATION, emp.getEducation())
                    .set(Employees.EMPLOYEES.STATUS, emp.isStatus()).where(Employees.EMPLOYEES.EMP_ID.equal(emp.getEmpId())).execute();

        }
    }


    public void deleteEmployer(Long id) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.deleteFrom(Employees.EMPLOYEES).where(Employees.EMPLOYEES.EMP_ID.equal(id)).execute();
        }
    }


    public List<Employer> getListEmployeesOfOrganization(Long id) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            return create.select().from(Employees.EMPLOYEES).where(Employees.EMPLOYEES.ORG_ID.equal(id)).fetch().into(Employer.class);
        }


    }


}