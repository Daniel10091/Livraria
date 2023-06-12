package com.udf.livraria.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.udf.livraria.domain.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
  
  Optional<Livro> findLivroById(Long id);

  @Query("select l.id from Livro l where l.id != 10")
  List<Livro> findByTitulo(String titulo);

  Optional<List<Livro>> findLivroByTitulo(String titulo);

  Optional<List<Livro>> findLivroByAutor(String autor);

}
