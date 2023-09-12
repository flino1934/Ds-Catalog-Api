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
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.repositories.CategoryRepository;
import com.lino.dscatalog.services.exceptions.DatabaseException;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {

		Page<Category> list = categoryRepository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundExceptions("Entity not found!! "));
		return new CategoryDTO(entity);

	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		// TODO Auto-generated method stub

		Category category = new Category();
		category.setName(dto.getName());
		category = categoryRepository.save(category);

		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO update(long id, CategoryDTO dto) {
		// TODO Auto-generated method stub
		try {
			Category category = categoryRepository.getReferenceById(id);
			category.setName(dto.getName());
			category = categoryRepository.save(category);

			return new CategoryDTO(category);

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundExceptions("Id Not found " + id);

		}
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		try {
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

			throw new ResourceNotFoundExceptions("Id Not found " + id);

		} catch (DataIntegrityViolationException e) {

			throw new DatabaseException("integrity violation " + id);

		}
	}

}
