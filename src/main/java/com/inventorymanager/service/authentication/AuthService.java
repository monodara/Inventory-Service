package com.inventorymanager.service.authentication;

import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.domain.supplier.SupplierCredential;
import com.inventorymanager.infrastructure.supplier.ISupplierJpaRepo;
import com.inventorymanager.service.supplier.Dtos.SupplierCreateDto;
import com.inventorymanager.service.supplier.Dtos.SupplierReadDto;
import com.inventorymanager.service.supplier.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{
    @Autowired
    private ISupplierJpaRepo supplierJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SupplierMapper supplierMapper;

    public SupplierReadDto register(SupplierCreateDto newUser){
        Supplier supplier = supplierMapper.toSupplier(newUser);
        supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
        supplier = supplierJpaRepo.save(supplier);
        return supplierMapper.toReadDto(supplier);
    }

    @Override
    public String login(SupplierCredential supplierCredential) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(supplierCredential.getName(), supplierCredential.getPassword()));
        Supplier supplier = supplierJpaRepo.findSupplierByName(supplierCredential.getName()).orElseThrow();
        return jwtService.generateToken(supplier);
    }
}
