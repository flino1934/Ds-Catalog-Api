package com.lino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.lino.dscatalog.entities.Product;
import com.lino.dscatalog.tests.ProductFactory;

@DataJpaTest
public class ProductRepositoryTest {

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void deleteShouldObjectWhenIdExist() {

		// Arranje

		// Action
		productRepository.deleteById(existingId);
		Optional<Product> result = productRepository.findById(existingId);

		// Assertion
		Assertions.assertFalse(result.isPresent());

	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		// Arranje
		Product product = ProductFactory.createProduct();
		product.setId(null);

		// Action
		product = productRepository.save(product);

		// Assertion

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());

	}

	@Test
	public void deleteShouldThrowEmptyEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		// Arranje

		// Assertion
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

			// Action
			productRepository.deleteById(nonExistingId);

		});

	}

}
