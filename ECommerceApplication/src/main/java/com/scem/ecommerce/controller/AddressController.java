package com.scem.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.AddressService;
import com.scem.ecommerce.util.AuthUtil;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AuthUtil authUtil;

    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
    	
        User currentUser = authUtil.getCurrentUser();
        
        Address savedAddress = addressService.addAddress(currentUser, address);
        return ResponseEntity.ok(savedAddress);
    }

 
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Address>> getAddresses() {
    	
        User currentUser = authUtil.getCurrentUser();
        List<Address> addresses = addressService.getAddressesByUser(currentUser);
        return ResponseEntity.ok(addresses);
    }

   
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        User currentUser = authUtil.getCurrentUser();
        Address updated = addressService.updateAddress(id, currentUser, address);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    	
        User currentUser = authUtil.getCurrentUser();
        addressService.deleteAddress(id, currentUser);
        return ResponseEntity.noContent().build();
        
    }
}
