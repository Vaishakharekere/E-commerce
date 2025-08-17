package com.scem.ecommerce.service;

import java.util.List;

import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;

public interface AddressService {
	
	Address addAddress(User user, Address address);
	List<Address> getAddressesByUser(User user);
	Address updateAddress(Long addressId, User user, Address address);
	void deleteAddress(Long addressId, User user);
	
	
 
 
}
