package se.lernholt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.lernholt.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
