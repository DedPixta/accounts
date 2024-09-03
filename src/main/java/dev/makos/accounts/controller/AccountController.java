package dev.makos.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/accounts")
@RestController
public class AccountController {

    @GetMapping
    public String helloWorld() {
        return "Hi World!";
    }
}
