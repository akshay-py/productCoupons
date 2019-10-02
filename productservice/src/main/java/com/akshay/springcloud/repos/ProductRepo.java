package com.akshay.springcloud.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.springcloud.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
