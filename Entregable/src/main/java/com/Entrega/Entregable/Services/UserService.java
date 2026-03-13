package com.Entrega.Entregable.Services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Entrega.Entregable.Dto.LoginRequest;
import com.Entrega.Entregable.Repository.UserRepository;
import com.Entrega.Entregable.models.User;


@Service
public class UserService{
	private static final String RFC_REGEX ="^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$";
	@Autowired
	private UserRepository repo;
	
	public UserService(UserRepository repo) {
		this.repo = repo;
		
	}
	 public List<User> getUsers() {
	        return repo.getUsers();
	    }
	 
	 public List<User> getUsers(String sortedBy){
		 List<User> users = new ArrayList<>(repo.getUsers());
		 sortUsers(users,sortedBy);
		 return users;
		 
	 }
	 
	 
	 private void sortUsers(List<User> users, String sortedBy){

		    if(sortedBy == null){
		        return;
		    }

		    switch(sortedBy){
		        case "email":
		            users.sort(Comparator.comparing(User::getEmail));
		            break;

		        case "id":
		            users.sort(Comparator.comparing(User::getId));
		            break;

		        case "name":
		            users.sort(Comparator.comparing(User::getName));
		            break;

		        case "phone":
		            users.sort(Comparator.comparing(User::getPhone_number));
		            break;
		    }
		}
	  
	 public List<User> getUsers(String sortedBy, String filter) {

	    List<User> users = new ArrayList<>(repo.getUsers());

	    if(filter != null){
	        users = applyFilter(users, filter);
		    }

	    if(sortedBy != null){
	        sortUsers(users, sortedBy);
		    }

	    return users;
		}
	 
	 private List<User> applyFilter(List<User> users, String filter){

		    String[] parts = filter.split("\\+");

		    if(parts.length != 3){
		        return users;
		    }

		    String field = parts[0];
		    String operator = parts[1];
		    String value = parts[2];
		    
		    return users.stream().filter(user -> match(user, field, operator, value)).toList();
		}
	 
	 private boolean match(User user, String field, String operator, String value){

		    String fieldValue = "";

		    switch(field){

		        case "name":
		            fieldValue = user.getName();
		            break;

		        case "email":
		            fieldValue = user.getEmail();
		            break;

		        case "phone":
		            fieldValue = user.getPhone_number();
		            break;

		        case "tax_id":
		            fieldValue = user.getTaxId();
		            break;
		    }

		    if(fieldValue == null){
		        return false;
		    }

		    switch(operator){

		        case "co":
		            return fieldValue.contains(value);

		        case "sw":
		            return fieldValue.startsWith(value);

		        case "ew":
		            return fieldValue.endsWith(value);

		        case "eq":
		            return fieldValue.equals(value);
		    }

		    return false;
		}
	 
	 public User createUser(User user){
		 
		 
		 if(!isValidPhone(user.getPhone_number())){
			    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "formato de numero telefonico invalido");
			}
	
		 
		 if(!isRFCValido(user.getTaxId())){
			    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de RFC invalido");
			}
		 
		 
		 for(User u : repo.getUsers()){
			    if(u.getTaxId().equals(user.getTaxId())){
			        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tax id es unico");
			    }
			}

		    user.setId(UUID.randomUUID());
		    ZonedDateTime zona = ZonedDateTime.now(ZoneId.of("Indian/Antananarivo"));
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		    user.setCreatedAt(zona.format(formatter));
		    user.setPassword(encrypt(user.getPassword()));
		    repo.getUsers().add(user);
		    

		    return user;
		}
	 public User updateUser(UUID id, User updatedUser){

		    for(User user : repo.getUsers()){

		        if(user.getId().equals(id)){

		            if(updatedUser.getEmail() != null){
		                user.setEmail(updatedUser.getEmail());
		            }

		            if(updatedUser.getName() != null){
		                user.setName(updatedUser.getName());
		            }

		            if(updatedUser.getPhone_number() != null){
		                user.setPhone_number(updatedUser.getPhone_number());
		            }

		            if(updatedUser.getTaxId() != null){
		                user.setTaxId(updatedUser.getTaxId());
		            }

		            return user;
		        }
		    }

		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
	 public void deleteUser(UUID id){
		    boolean removed = repo.getUsers().removeIf(user -> user.getId().equals(id));
		    if(!removed){
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no econtrado");
		    }
		}
	 
	 public String encrypt(String value){

		    try{

		        String secret = "12345678901234567890123456789012";

		        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "AES");

		        Cipher cipher = Cipher.getInstance("AES");

		        cipher.init(Cipher.ENCRYPT_MODE, key);

		        byte[] encrypted = cipher.doFinal(value.getBytes());

		        return Base64.getEncoder().encodeToString(encrypted);

		    }catch(Exception e){
		        throw new RuntimeException(e);
		    }
		}
	 
	 
	 public User login(LoginRequest request){

		    for(User user : repo.getUsers()){

		        if(user.getTaxId().equals(request.getTaxId())){

		            if(user.getPassword().equals(encrypt(request.getPassword()))){
		                return user;
		            }

		            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "password invalido");
		        }
		    }

		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
	 
	 private boolean isRFCValido(String rfc){
		    return rfc != null && rfc.matches(RFC_REGEX);
		}
	 
	 private boolean isValidPhone(String phone){
		    return phone != null && phone.replaceAll("[^0-9]", "").length() >= 10;
		}
	 
}