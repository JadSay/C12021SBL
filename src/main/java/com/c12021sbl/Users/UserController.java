package com.c12021sbl.Users;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

  private final UserRepository repository;

  UserController(UserRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/users")
  List<User> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/users")
  User newUser(@RequestBody User newUser) {
    return repository.save(newUser);
  }

  // Single item
  
  @GetMapping("/users/{id}")
  User one(@PathVariable String id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/users/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable String id) {
    
    return repository.findById(id)
      .map(User -> {
        User.setFirstName(newUser.getFirstName());
        User.setLastName(newUser.getLastName());
        User.setAge(newUser.getAge());
        return repository.save(User);
      })
      .orElseThrow(() -> new UserNotFoundException(id));
  }

  @DeleteMapping("/users/{id}")
  String deleteUser(@PathVariable String id) {
    repository.deleteById(id);
    return "Deleted user with id: " + id;
  }
}