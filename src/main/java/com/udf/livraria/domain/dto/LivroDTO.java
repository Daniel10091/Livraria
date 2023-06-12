package com.udf.livraria.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LivroDTO {
  
  private Long codigo;
  private String isbn;
  private String titulo;
  private String autor;
  private String editora;
  private LocalDateTime dataCadastro;
  private LocalDateTime dataAtualizacao;

}
