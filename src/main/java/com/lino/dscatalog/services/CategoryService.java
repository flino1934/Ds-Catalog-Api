package com.lino.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lino.dscatalog.dto.CategoryDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.repositories.CategoryRepository;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> list = categoryRepository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
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

}
