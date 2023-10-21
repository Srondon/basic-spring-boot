package com.nimbeo.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nimbeo.entity.GroupEntity;
import com.nimbeo.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;
		
	public GroupEntity save(GroupEntity group) {
		try {
	        /*if (userName == null || userName.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
	            throw new IllegalArgumentException("Username, password or email cannot be empty.");
	        }  
	        				-> COMMENTED BUT MUST IMPLEMENT VALIDATIONS   
	        */

			return groupRepository.save(group);
			
		}catch (Exception e) {
            System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
            throw new RuntimeException("Failed to save new entity", e);
		}	
	}
	
	public GroupEntity update(Long id, GroupEntity updatedGroup) {
	    try {
	        if (!groupRepository.existsById(id)) {
	            throw new EntityNotFoundException("Group with ID " + id + " not found");
	        }
	        updatedGroup.setId(id);
	        return groupRepository.save(updatedGroup);
	        
	    } catch (Exception e) {
	    	System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
	        throw new RuntimeException("Failed to update group with ID " + id + ": " + e);
	    }
	}

    public void delete(Long id) {    	    	
    	try {
    		if(!groupRepository.existsById(id)){
    			throw new EntityNotFoundException("Group with Id: " + id + " not found.");
    		}    	
    		groupRepository.deleteById(id);
    	}catch (Exception e) {
            System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
            throw new RuntimeException("Failed to delete group with ID " + id + ": " + e);
		}
    }

    public GroupEntity getById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<GroupEntity> getAll() {
        return (List<GroupEntity>) groupRepository.findAll();
    }
	
}