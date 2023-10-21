package com.nimbeo.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nimbeo.entity.UserEntity;
import com.nimbeo.service.UserService;

@RestController
@RequestMapping("user_controller")
public class UserController {

	@Autowired
	private final UserService userService;

	//-> Constructor
	public UserController (UserService userService) {
		this.userService = userService;
	}

    @PostMapping("/create_new_user")
    public ResponseEntity<Object> save(@RequestBody UserEntity user) {
        try {
            UserEntity saved = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    @PutMapping("/update_user/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        try {
            UserEntity updated = userService.update(id, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
	
    @DeleteMapping("/delete_user_by_id/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("Deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping("/get_user_by_id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id + " not found");
        }
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<Object> getAll() {
        List<UserEntity> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

}