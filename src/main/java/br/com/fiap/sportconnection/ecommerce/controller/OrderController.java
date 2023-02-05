package br.com.fiap.sportconnection.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "order")
public class OrderController {

    @GetMapping
    public String getList() {
        return "";
    }
}
