package com.learn.spring.beans;


import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pkg.db.classes.tables.Organizations;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ServiceAppOrganization {

    DataSource ds;
    // private Connection conn;

    @Autowired
    public ServiceAppOrganization(DataSource ds) {
        this.ds = ds;
        //   this.conn = ds.getConnection();
    }

    //поиск организаций по полям
    public List getListOrganization(String name, String address, String type, String activities,
                                    String director, String department, Boolean insalubrity,
                                    Timestamp dateCreate, Boolean status, String orderBy,
                                    Integer limit, Integer offset) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);

            Condition condition = DSL.trueCondition();

            if (name != null && !name.isEmpty())
                condition = condition.and(Organizations.ORGANIZATIONS.ORG_NAME.equal(name));

            if (address != null && !address.isEmpty())
                condition = condition.and((Organizations.ORGANIZATIONS.ORG_ADRESS.equal(address)));

            if (type != null && !type.isEmpty())
                condition = condition.and(Organizations.ORGANIZATIONS.ORG_TYPE.equal(type));

            if (activities != null && !activities.isEmpty())
                condition = condition.and(Organizations.ORGANIZATIONS.ACTIVITIES.equal(activities));

            if (director != null && !director.isEmpty())
                condition = condition.and(Organizations.ORGANIZATIONS.DIRECTOR.equal(director));

            if (department != null && !department.isEmpty())
                condition = condition.and(Organizations.ORGANIZATIONS.DEPARTMENTS.equal(department));

            if (insalubrity != null)
                condition = condition.and(Organizations.ORGANIZATIONS.INSALUBRITY.equal(insalubrity));

            if (dateCreate != null)
                condition = condition.and(Organizations.ORGANIZATIONS.DATE_CREATE.equal(dateCreate));

            if (status != null)
                condition = condition.and(Organizations.ORGANIZATIONS.STATUS.equal(status));

            Collection<? extends OrderField<?>> fieldsToOrder = orderBy == null ? Collections.emptyList() :
                    Collections.singletonList(Organizations.ORGANIZATIONS.field(orderBy));

            SelectSeekStepN<Record> orderedQuery = create.select().from(Organizations.ORGANIZATIONS)
                    .where(condition).orderBy(fieldsToOrder);

            SelectForUpdateStep lastQuery = orderedQuery;


            if (limit != null && offset != null)
                lastQuery = orderedQuery.limit(limit).offset(offset);

                return lastQuery.fetch().into(Organization.class);
        }


    }


    public void createOrganization(Organization org) throws Exception  {
        try(Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.insertInto(Organizations.ORGANIZATIONS)
                    .set(Organizations.ORGANIZATIONS.ORG_NAME, org.getOrgName())
                    .set(Organizations.ORGANIZATIONS.ORG_ADRESS, org.getOrgAdress())
                    .set(Organizations.ORGANIZATIONS.ORG_TYPE, org.getOrgType())
                    .set(Organizations.ORGANIZATIONS.ACTIVITIES, org.getActivities())
                    .set(Organizations.ORGANIZATIONS.DATE_CREATE, org.getDateCreate())
                    .set(Organizations.ORGANIZATIONS.DEPARTMENTS, org.getDepartments())
                    .set(Organizations.ORGANIZATIONS.DIRECTOR, org.getDirector())
                    .set(Organizations.ORGANIZATIONS.INSALUBRITY, org.isInsalubrity())
                    .set(Organizations.ORGANIZATIONS.STATUS, org.isStatus()).returning().fetch();
        }
    }


    public Object getOrganization(long id) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            return create.select().from(Organizations.ORGANIZATIONS)
                    .where(Organizations.ORGANIZATIONS.ORG_ID.eq(id)).fetch().into(Organization.class);
        }
    }


    public void changeOrganization(Organization org) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.update(Organizations.ORGANIZATIONS)
                    .set(Organizations.ORGANIZATIONS.ORG_NAME, org.getOrgName())
                    .set(Organizations.ORGANIZATIONS.ORG_ADRESS, org.getOrgAdress())
                    .set(Organizations.ORGANIZATIONS.ACTIVITIES, org.getActivities())
                    .set(Organizations.ORGANIZATIONS.DATE_CREATE, org.getDateCreate())
                    .set(Organizations.ORGANIZATIONS.DEPARTMENTS, org.getDepartments())
                    .set(Organizations.ORGANIZATIONS.DIRECTOR, org.getDirector())
                    .set(Organizations.ORGANIZATIONS.INSALUBRITY, org.isInsalubrity())
                    .set(Organizations.ORGANIZATIONS.STATUS, org.isStatus())
                    .where(Organizations.ORGANIZATIONS.ORG_ID.eq(Organizations.ORGANIZATIONS.ORG_ID)).returning().fetch();
        }
    }

    public void deleteOrganization(Long id) throws Exception {
        try (Connection conn = ds.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES_10);
            create.deleteFrom(Organizations.ORGANIZATIONS)
                    .where(Organizations.ORGANIZATIONS.ORG_ID.eq(id)).returning().fetch();

        }
    }

}