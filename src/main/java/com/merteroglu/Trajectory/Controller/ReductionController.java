package com.merteroglu.Trajectory.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reduction")
public class ReductionController {


    @RequestMapping("/hello")
    String helloWorld(){
        return "Hello World !";
    }



}
