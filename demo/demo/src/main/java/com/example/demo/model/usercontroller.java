package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class usercontroller {

    private final Map<Long, user> userDatabase = new HashMap<>();
    private long currentId = 1;

    // Метод POST: добавление нового пользователя
    @PostMapping
    public ResponseEntity<user> createUser(@RequestBody user user) {
        user.setId(currentId++);
        userDatabase.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Метод GET: получение списка всех пользователей
    @GetMapping
    public ResponseEntity<List<user>> getAllUsers() {
        return new ResponseEntity<>(new ArrayList<>(userDatabase.values()), HttpStatus.OK);
    }

    // Метод GET: получение пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<user> getUserById(@PathVariable Long id) {
        user user = userDatabase.get(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Метод PUT: обновление пользователя
    @PutMapping("/{id}")
    public ResponseEntity<user> updateUser(@PathVariable Long id, @RequestBody user userDetails) {
        user user = userDatabase.get(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        userDatabase.put(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Метод DELETE: удаление пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userDatabase.remove(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}