package com.c12021sbl.Users;

class UserNotFoundException extends RuntimeException {

  UserNotFoundException(String id) {
    super("Could not find User " + id);
  }
}