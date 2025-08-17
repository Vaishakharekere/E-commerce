package com.scem.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.AddressRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.exception.AddressNotFoundException;
import com.scem.ecommerce.service.AddressService;
@SuppressWarnings("unused")
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Address addAddress(User user, Address address) {
		
		address.setUser(user);
     	return addressRepo.save(address);
	}

	@Override
	public List<Address> getAddressesByUser(User user) {
		return addressRepo.findByUser(user);
	}

	@Override
	public Address updateAddress(Long addressId, User user, Address addressDetails) {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new AddressNotFoundException("Address not found"));
		if (!address.getUser().equals(user)) throw new AddressNotFoundException("Not your address");

		address.setAddress(addressDetails.getAddress());
		return addressRepo.save(address);
	}

	@Override
	public void deleteAddress(Long addressId, User user) {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new AddressNotFoundException("Address not found"));
		if (!address.getUser().equals(user)) throw new AddressNotFoundException("Not your address");

		addressRepo.delete(address);
	}

 
}
