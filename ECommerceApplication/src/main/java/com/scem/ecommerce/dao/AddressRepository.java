package com.scem.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
