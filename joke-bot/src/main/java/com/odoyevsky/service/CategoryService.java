package com.odoyevsky.service;

import com.odoyevsky.model.entity.Category;
import com.odoyevsky.model.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    //TODO throw Exception if repo is empty
    public Set<Category> getCategories(){
        Iterable<Category> categoryIterable = categoryRepository.findAll();

        Set<Category> categorySet = new HashSet<>();
        categoryIterable.forEach(categorySet::add);

        return categorySet;
    }
}
