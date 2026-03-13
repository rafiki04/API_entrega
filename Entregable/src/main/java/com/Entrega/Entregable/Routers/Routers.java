package com.Entrega.Entregable.Routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entrega.Entregable.Dto.LoginRequest;
import com.Entrega.Entregable.Services.UserService;
import com.Entrega.Entregable.models.User;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class Routers {
	@Autowired
    private final UserService userservice;

    public Routers(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String sortedBy, @RequestParam(required = false) String filter) {
        return userservice.getUsers(sortedBy,filter);
    }
    /*@GetMapping(params="filter")
    public List<User> getUsers(@RequestParam(required = false) String sortedBy,@RequestParam(required = false) String filter){
    	return userservice.getUsers(sortedBy,filter);
    }*/
    @PostMapping    
    public User createUser(@RequestBody User user){
        return userservice.createUser(user);
    }
    @PatchMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody User updatedUser){
        return userservice.updateUser(id, updatedUser);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userservice.deleteUser(id);
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request){
        return userservice.login(request);
    }
    
}