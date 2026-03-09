package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping
  public String listUsers() {
    return "users";
  }

  @GetMapping("/{id}")
  public String getUser(@PathVariable Long id) {
    return "user-" + id;
  }

  @PostMapping
  public String createUser(@RequestBody String body) {
    return body;
  }
}
