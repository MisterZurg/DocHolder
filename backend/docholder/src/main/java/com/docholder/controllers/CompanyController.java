package com.docholder.controllers;

import com.docholder.model.Company;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @GetMapping
    public Company getCompany(){
        //    @RequestParam(value = "id") Integer id) {
        return null;
    }
    @PostMapping
    public Company createCompany(){
        //    TODO : Add logic
        return null;
    }

    @PutMapping
    public Company updateCompany(){
        //    TODO : Add logic
        return null;
    }
    @DeleteMapping
    public Company deleteCompany(){
        //    TODO : Add logic
        return null;
    }
}
