package com.learn.spring.beans;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
public class MainController {

   @Autowired
   private ServiceAppEmployees serviceEmployer;

   @Autowired
   private ServiceAppOrganization serviceOrganization;

/*
   @Autowired
    public MainController(ServiceAppEmployees serviceEmployer, ServiceAppOrganization serviceOrganization) {
        this.serviceEmployer = serviceEmployer;
        this.serviceOrganization = serviceOrganization;
    }
*/
    @RequestMapping("/employer")
    public List getListEmployees(String name, String lastName,
                                           Long age, Long idOrganization,
                                           String position, String department,
                                           Long salary, String education,
                                           Boolean status, String orderBy,Integer limit, Integer offset) throws Exception {
        return serviceEmployer.getListEmployees(name, lastName, age,
                idOrganization, position, department, salary, education, status, orderBy,  limit, offset);
    }

    @GetMapping("/employer/{id}")
    public Object getEmployer(@PathVariable Long id) throws Exception {
        return serviceEmployer.getEmployer(id);
    }

    @RequestMapping(value = "/employer", method = RequestMethod.POST)
    public void createEmployer(@RequestBody Employer emp) throws Exception {
        serviceEmployer.createEmploy(emp);
    }

    @RequestMapping(value = "/employer", method = RequestMethod.PATCH)
    public void changeEmployer(@RequestBody Employer emp) throws Exception {
        serviceEmployer.changeEmployer(emp);
    }

    @GetMapping("/employees/{id}")
    public List<Employer> getListEmployeesOfOrg(@PathVariable Long id) throws Exception {
        return serviceEmployer.getListEmployeesOfOrganization(id);
    }

    @RequestMapping(value = "/employer/{id}", method = RequestMethod.DELETE)
    public void deleteEmployer(@PathVariable Long id) throws Exception {
        serviceEmployer.deleteEmployer(id);
    }

    @RequestMapping("/organization")
    public List getListOrganisations(String name, String address,
                                     String type, String activities,
                                     String director, String departments,
                                     Boolean insalubrity, Timestamp date,
                                     Boolean status, String orderBy,
                                     Integer limit, Integer offset)throws Exception {
        return serviceOrganization.getListOrganization(name,address, type,
                activities,director,departments, insalubrity,date,status, orderBy, limit, offset);
    }

    @RequestMapping(value = "/organization", method = RequestMethod.POST)
    public void createOrganization(@RequestBody Organization org) throws Exception {
        serviceOrganization.createOrganization(org);
    }

    @GetMapping("/organization/{id}")
    public Object getOrganization(@PathVariable Long id) throws Exception {
        return serviceOrganization.getOrganization(id);
    }

    @RequestMapping(value = "/organization", method = RequestMethod.PATCH)
    public void changeOrganization(@RequestBody Organization org) throws Exception {
        serviceOrganization.changeOrganization(org);
    }


    @DeleteMapping("/organization/{id}")
    public void deleteOrganization(@PathVariable Long id) throws Exception {
        serviceOrganization.deleteOrganization(id);
    }

}