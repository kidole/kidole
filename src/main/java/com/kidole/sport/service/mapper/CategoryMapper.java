package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisciplineMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "discipline.id", target = "disciplineId")
    @Mapping(source = "discipline.disciplineName", target = "disciplineDisciplineName")
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
