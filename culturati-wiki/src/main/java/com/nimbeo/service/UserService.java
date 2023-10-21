package com.nimbeo.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nimbeo.entity.UserEntity;
import com.nimbeo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
		
	public UserEntity save(UserEntity user) {
		String userName = user.getUser_name();
        String password = user.getPassword();
        String email = user.getEmail();
		try {
	        if (userName == null || userName.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
	            throw new IllegalArgumentException("Username, password or email cannot be empty.");
	        }

			return userRepository.save(user);
			
		}catch (Exception e) {
            System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
            throw new RuntimeException("Failed to save new entity", e);
		}	
	}
	
	public UserEntity update(Long id, UserEntity updatedUser) {
	    try {
	        if (!userRepository.existsById(id)) {
	            throw new EntityNotFoundException("User with ID " + id + " not found");
	        }
	        updatedUser.setId(id);
	        return userRepository.save(updatedUser);
	        
	    } catch (Exception e) {
	    	System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
	        throw new RuntimeException("Failed to update user with ID " + id + ": " + e);
	    }
	}

    public void delete(Long id) {    	    	
    	try {
    		if(!userRepository.existsById(id)){
    			throw new EntityNotFoundException("User with Id: " + id + " not found.");
    		}    	
    		userRepository.deleteById(id);
    	}catch (Exception e) {
            System.out.println("\n Something went wrong: " + e.getMessage() + " \n");
            throw new RuntimeException("Failed to delete user with ID " + id + ": " + e);
		}
    }

    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAll() {
        return (List<UserEntity>) userRepository.findAll();
    }
	
}