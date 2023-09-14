package com.lino.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lino.dscatalog.dto.CategoryDTO;
import com.lino.dscatalog.dto.ProductDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.entities.Product;
import com.lino.dscatalog.repositories.CategoryRepository;
import com.lino.dscatalog.repositories.ProductRepository;
import com.lino.dscatalog.services.exceptions.DatabaseException;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPage(PageRequest pageRequest) {

		Page<Product> list = productRepository.findAll(pageRequest);

		return list.map(x -> new ProductDTO(x));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundExceptions("Entity not found"));
		return new ProductDTO(entity, entity.getCaegories());
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);

			return new ProductDTO(entity);
		} catch (ResourceNotFoundExceptions e) {

			throw new EntityNotFoundException("Id not found" + id);

		}
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		// TODO Auto-generated method stub

		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub

		try {

			productRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception

			throw new ResourceNotFoundExceptions("Id não encontrado " + id);

		} catch (DataIntegrityViolationException e) {

			throw new DatabaseException("Violação de integridade " + id);

		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		// TODO Auto-generated method stub

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());

		entity.getCaegories().clear();

		// vamos fazer um foreach que vai percorrer todas as categorias que estão
		// associados ao meu dto
		for (CategoryDTO catDto : dto.getCategories()) {
			// vai instaciar uma entidade de categorydto sem tocar no BD
			Category category = categoryRepository.getReferenceById(catDto.getId());// vai estar pegando a categoria
			entity.getCaegories().add(category);
		}

	}
}
