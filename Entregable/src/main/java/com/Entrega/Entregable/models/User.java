package com.Entrega.Entregable.models;
import java.util.List;
import lombok.Data;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Data
public class User{
	@JsonIgnore
  private String password;
  private UUID id;
  private String name;
  private String email;
  private String phone_number;
  private String taxId;
  private String createdAt;
  private List<Address> addresses;
  
  public User() {
	  
  }
  public User(UUID id, String email, String name, String phone_number,
          String password, String taxId, String createdAt, List<Address> addresses) {
	  
  this.id = id;
  this.email = email;
  this.name = name;
  this.phone_number = phone_number;
  this.password = password;
  this.taxId = taxId;
  this.createdAt = createdAt;
  this.addresses = addresses;
  }
  
  
}