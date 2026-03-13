package com.Entrega.Entregable.models;
import lombok.Data;


@Data
public class Address{
	private int id;
	private String name;
	private String street;
	private String country_code;
	
	public Address() {
		
	}
	
	public Address(int id, String name, String street, String country_code) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.country_code = country_code;
		
	
	}
	
}