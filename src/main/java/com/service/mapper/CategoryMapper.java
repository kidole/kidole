package com.service.mapper;

import com.domain.*;
import com.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisciplineMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "discipline.id", target = "disciplineId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "disciplineId", target = "discipline")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
