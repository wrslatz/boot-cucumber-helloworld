/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slatz.bootcucumberhelloworld;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author slattu_w
 */
@RestController
public class ExampleController {
    
    @CrossOrigin
    @ApiOperation(httpMethod = "GET", value = "Get version")
    @RequestMapping(value = "/test/version", method = RequestMethod.GET)
    public String getVersion(){
        return "Version 1.0";
    }
    
    @CrossOrigin
    @ApiOperation(httpMethod = "POST", value = "Create new person")
    @RequestMapping(value = "/test/person", produces = MediaType.APPLICATION_XML_VALUE, method = RequestMethod.POST)
    public Person createPerson(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("email") String email){

        Person p1 = new Person(name, age, email);
        return p1;
    }
}
