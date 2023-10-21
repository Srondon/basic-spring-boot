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
import com.nimbeo.entity.GroupEntity;
import com.nimbeo.service.GroupService;

@RestController
@RequestMapping("group_controller")
public class GroupController {

	@Autowired
	private final GroupService groupService;

	//-> Constructor
	public GroupController (GroupService groupService) {
		this.groupService = groupService;
	}

    @PostMapping("/create_new_group")
    public ResponseEntity<Object> save(@RequestBody GroupEntity group) {
        try {
        	GroupEntity saved = groupService.save(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    @PutMapping("/update_group/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody GroupEntity updatedGroup) {
        try {
        	GroupEntity updated = groupService.update(id, updatedGroup);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
	
    @DeleteMapping("/delete_group_by_id/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
        	groupService.delete(id);
        	return ResponseEntity.ok().body("Deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping("/get_group_by_id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
    	GroupEntity user = groupService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id + " not found");
        }
    }

    @GetMapping("/get_all_groups")
    public ResponseEntity<Object> getAll() {
        List<GroupEntity> users = groupService.getAll();
        return ResponseEntity.ok(users);
    }
	
}