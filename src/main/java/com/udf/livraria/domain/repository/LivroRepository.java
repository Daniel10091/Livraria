package com.udf.livraria.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udf.livraria.domain.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
  
  Optional<Livro> findLivroById(Long id);

  @Query("select l from Livro l where l.titulo = :titulo")
  List<Livro> findByTitulo(@Param("titulo") String titulo);

  Optional<Livro> findLivroByTitulo(String titulo);

  Optional<List<Livro>> findLivroByAutor(String autor);

}
