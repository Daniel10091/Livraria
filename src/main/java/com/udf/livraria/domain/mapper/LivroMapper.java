package com.udf.livraria.domain.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.udf.livraria.domain.dto.LivroDTO;
import com.udf.livraria.domain.model.Livro;

@Mapper
public interface LivroMapper {
  
  @Mapping(source = "livro.id", target = "codigo")
  LivroDTO toDto(Livro livro);

  @InheritInverseConfiguration
  Livro toEntity(LivroDTO livroDto);

}
