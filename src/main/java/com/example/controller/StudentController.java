package com.example.controller;

import com.example.model.Student;
import com.example.service.IStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    IStudent<Student> service;
    @GetMapping("/")
    public ModelAndView getAll(){
        List<Student> students = service.findAll();
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("listStudent",students);
        return modelAndView;
    }
    @GetMapping("{id}")
    public ModelAndView showForm(@PathVariable int id){
        Student student = service.findById(id);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("student",student);
        return modelAndView;
    }
    @PostMapping(value="/edit")
    public String edit(@ModelAttribute Student student){
        service.edit(student.getId(),student);
        return "redirect:/student";
    }
    @GetMapping(value = "/create")
    public ModelAndView showFormAdd(){
        return new ModelAndView("create");
    }
    @PostMapping(value = "/create")
    public String add(@ModelAttribute Student student){
        service.add(student);
        return "redirect:/student";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.remove(id);
        return "redirect:/student";
    }
    @GetMapping("/search")
    public ModelAndView showSearch(@RequestParam String q){
        if (q == null){
            q = "";
        }
        List<Student> students = service.findByName(q);
        System.out.println(Arrays.toString(students.toArray()));
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("listStudent",students);
        return modelAndView;

    }
}
