package com.Entrega.Entregable.Repository;

import com.Entrega.Entregable.models.Address;
import com.Entrega.Entregable.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	private List<User> users = new ArrayList<>();
	private List<Address> addresses = new ArrayList<>();
	
	public UserRepository() {
		
		Address Address1 = new Address(1,"CDMX","Juarez","01000");
		Address Address2 = new Address(2,"CDMX","Benito Juarez","01000");
		Address Address3 = new Address(3,"CDMX","Alvaro Obregon","01000");
		addresses.add(Address1);
		addresses.add(Address2);
		addresses.add(Address3);
		
		User user1 = new User();
		user1.setEmail("aristian@mail.com");
		user1.setName("aristian");
		user1.setId(UUID.randomUUID());
		user1.setPhone_number("+52 5577420595");
		user1.setPassword("password");
		user1.setTaxId("AARR990101XXX");
		user1.setCreatedAt("11-11-2000");
		user1.setAddresses(addresses);
		save(user1);
		
		User user2 = new User();
		user2.setEmail("Rafael@gmail.com");
		user2.setName("Rafael");
		user2.setId(UUID.randomUUID());
		user2.setPhone_number("+52 5555555555");
		user2.setPassword("password");
		user2.setTaxId("AARR990101YYY");
		user2.setCreatedAt("11-11-2000");
		user2.setAddresses(addresses);
		save(user2);
		
		User user3 = new User();
		user3.setEmail("CristianRafael@gmail.com");
		user3.setName("Cristian Rafael");
		user3.setId(UUID.randomUUID());
		user3.setPhone_number("+52 4444444444");
		user3.setPassword("password");
		user3.setTaxId("AARR990101WWW");
		user3.setCreatedAt("11-11-2000");
		user3.setAddresses(addresses);
		save(user3);
		
		
	}
	
    public List<User> getUsers() {
        return users;
    }

    public void save(User user) {
        users.add(user);
    }

}