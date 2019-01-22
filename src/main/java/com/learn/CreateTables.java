package com.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateTables {

    private static final String dropTableOrganizations = "DROP TABLE IF EXISTS organizations CASCADE;";
    private static final String dropTableEmployees = "DROP TABLE IF EXISTS employees CASCADE;";
    private static final String dropSequenceEmp = "DROP SEQUENCE IF EXISTS employees_id_seq CASCADE";
    private static final String dropSequenceOrg = "DROP SEQUENCE IF EXISTS organizations_id_seq CASCADE";
    private static final String createTableOrganizations = "CREATE TABLE organizations (" +
            "  org_id bigint NOT NULL," +
            "  org_name text NOT NULL ," +
            "  org_adress text NOT NULL ," +
            "  org_type text NOT NULL ," +
            "  activities text NOT NULL ," +
            "  director text NOT NULL ," +
            "  departments text NOT NULL ," +
            "  insalubrity boolean," +
            "  date_create timestamp without time zone NOT NULL," +
            "  status boolean," +
            "  PRIMARY KEY (org_id));";


    private static final String createTableEmployees = "CREATE TABLE employees (" +
            "emp_id bigint NOT NULL," +
            "emp_name text NOT NULL ," +
            "emp_last_name text NOT NULL ," +
            "emp_age bigint NOT NULL ," +
            "emp_pos text NOT NULL ," +
            "org_id bigint NOT NULL ," +
            "department text NOT NULL ," +
            "salary bigint NOT NULL ," +
            "education text NOT NULL," +
            "status boolean," +
            "PRIMARY KEY (emp_id));";

    private static final String createForeignKey = "ALTER TABLE employees ADD CONSTRAINT fk_organizations_id FOREIGN KEY (org_id) REFERENCES organizations (org_id);";

    private static final String createIndexes = "CREATE INDEX employees_index_emp_name_emp_lastname ON employees (emp_name, emp_last_name);" +

            "CREATE INDEX employees_index_emp_age ON employees(emp_age);" +

            "CREATE INDEX employees_index_salary ON employees(salary);" +

            "CREATE INDEX employees_index_organizatons ON employees(org_id);" +

            "CREATE INDEX organization_index_org_name ON organizations (org_name);";
    private static final String createSequences = "CREATE SEQUENCE organizations_id_seq;" +
            "ALTER TABLE organizations ALTER COLUMN org_id " +
            "SET DEFAULT NEXTVAL('organizations_id_seq');" +
            "CREATE SEQUENCE employees_id_seq;" +
            "ALTER TABLE employees " +
            "ALTER COLUMN emp_id " +
            "SET DEFAULT NEXTVAL('employees_id_seq'); ";

    private static final String insertIntoOrganizations = "INSERT INTO organizations" +
            "(org_name, org_adress, org_type, activities, director, departments, insalubrity, date_create, status )" +
            "values('{org_name}', '{org_address}', '{org_type}', '{activities}', '{director}', '{departments}', false, '2008-02-03', true)";

    private static String insertIntoEmployees = " INSERT INTO employees" +
            "(emp_name, emp_last_name, emp_age, emp_pos, org_id, department, salary, education, status)" +
            "values('{emp_name}', '{emp_last_name}', {emp_age}, '{emp_pos}', {org_id}, '{department}', {salary}, 'higher education', true)";


    private static int rand(int i) {
        int r = (int) (Math.random() * (i - 1));
        return r;
    }

    private static List<String> readFromInputStream(InputStream inputStream)
            throws IOException {

        List<String> list = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

    public static void createTab() throws Exception {

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/eddb";
        String login = "postgres";
        String password = "4061990";
        //подключение и создание таблиц
        try (Connection con = DriverManager.getConnection(url, login, password);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(dropTableOrganizations);
            stmt.executeUpdate(dropTableEmployees);
            stmt.executeUpdate(dropSequenceEmp);
            stmt.executeUpdate(dropSequenceOrg);
            stmt.executeUpdate(createTableOrganizations);
            stmt.executeUpdate(createTableEmployees);
            stmt.executeUpdate(createForeignKey);
            stmt.executeUpdate(createIndexes);
            stmt.executeUpdate(createSequences);


            //считываем имена из файла

            Class clazz = CreateTables.class;
            InputStream inputStreamDirectorNames = clazz.getResourceAsStream("/forDB/directors_names.txt");
            InputStream inputStreamWords = clazz.getResourceAsStream("/forDB/test.txt");
            InputStream inputStreamEmpNames = clazz.getResourceAsStream("/forDB/emp_names.txt");
            InputStream inputStreamEmpLastNames = clazz.getResourceAsStream("/forDB/emp_last_names.txt");
            InputStream inputStreamDepartments = clazz.getResourceAsStream("/forDB/departments.txt");
            List<String> listDirectorNames = readFromInputStream(inputStreamDirectorNames);
            List<String> listWords = readFromInputStream(inputStreamWords);
            List<String> listEmpNames = readFromInputStream(inputStreamEmpNames);
            List<String> listEmpLastNames = readFromInputStream(inputStreamEmpLastNames);
            List<String> listDepartments = readFromInputStream(inputStreamDepartments);
//заполняем даннми таблицу с организациями
            for (int i = 0; i < 50; i++) {

                String sqlOrg = insertIntoOrganizations.replace("{org_name}", listWords.get(rand(listWords.size())))
                        .replace("{org_address}", listWords.get(rand(listWords.size())))
                        .replace("{org_type}", listWords.get(rand(listWords.size())))
                        .replace("{activities}", listWords.get(rand(listWords.size())))
                        .replace("{director}", listDirectorNames.get(rand(listDirectorNames.size())))
                        .replace("{departments}", listWords.get(rand(listWords.size())));

                stmt.executeUpdate(sqlOrg);
          }
//заполняем данными таблицу с сотрудниками
          for(int i = 0; i < 250; i++){
                Integer emp_age = new Random().nextInt(50)+18;
                Integer org_id = new Random().nextInt(49)+1;
                Integer salary = new Random().nextInt(20000)+1000;
                String sqlEmp = insertIntoEmployees.replace("{emp_name}", listEmpNames.get(rand(listEmpNames.size())))
                        .replace("{emp_last_name}", listEmpLastNames.get(rand(listEmpLastNames.size())))
                        .replace("{emp_age}", emp_age.toString())
                        .replace("{emp_pos}", listWords.get(rand(listWords.size())))
                        .replace("{org_id}", org_id.toString())
                        .replace("{department}", listDepartments.get(rand(5)))
                        .replace("{salary}", salary.toString());

                stmt.executeUpdate(sqlEmp);
          }
        }
    }

      public static void main(String []args) throws Exception{
        createTab();
      }
}

