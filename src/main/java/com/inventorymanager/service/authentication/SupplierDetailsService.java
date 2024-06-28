package com.inventorymanager.service.authentication;

import com.inventorymanager.infrastructure.supplier.ISupplierJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SupplierDetailsService implements UserDetailsService {
    @Autowired
    private ISupplierJpaRepo supplierJpaRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return supplierJpaRepo.findSupplierByName(username).orElseThrow(() -> new UsernameNotFoundException("Supplier Not Found"));
    }
}
