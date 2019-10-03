package com.akshay.springcloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.springcloud.dto.Coupon;
import com.akshay.springcloud.model.Product;
import com.akshay.springcloud.repos.ProductRepo;
import com.akshay.springcloud.restclients.CouponClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	CouponClient couponClient;

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "sendErrorResponse")
	public Product create(@RequestBody Product product) {
		Coupon coupon = couponClient.getCoupon(product.getCouponCode());
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productRepo.save(product);
	}

	public Product sendErrorResponse(Product product) {
		return product;
	}
}
