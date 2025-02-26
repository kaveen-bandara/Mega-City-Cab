package com.megaCityCab.backend.service;

import com.megaCityCab.backend.entity.Admin;
import com.megaCityCab.backend.entity.Customer;
import com.megaCityCab.backend.repository.AdminRepository;
import com.megaCityCab.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> admin = adminRepository.findByUsername(username);
        if(admin.isPresent()) {
            return admin.get();
        }

        Optional<Customer> customer = customerRepository.findByEmail(username);
        if(customer.isPresent()) {
            return customer.get();
        }

        throw new UsernameNotFoundException("User not found!");
    }
}
