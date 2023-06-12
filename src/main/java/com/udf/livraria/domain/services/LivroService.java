package com.udf.livraria.domain.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udf.livraria.domain.dto.LivroDTO;
import com.udf.livraria.domain.exception.LivroAlreadyExistException;
import com.udf.livraria.domain.exception.LivroNotFoundException;
import com.udf.livraria.domain.mapper.LivroMapper;
import com.udf.livraria.domain.model.Livro;
import com.udf.livraria.domain.repository.LivroRepository;

@Service
public class LivroService {

  private final LivroRepository repository;
  private final LivroMapper mapper;
  
  public LivroService(LivroRepository repository, LivroMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * Lista todos os livros
   * 
   * @return List < Livro >
   */
  public List<Livro> listaTodos() {
    // Busca todos os livros no banco de dados
    return repository.findAll();
  }

  /**
   * Consulta o livro atravéz do id
   * 
   * @param id
   * @return
   */
  public Livro buscaLivroPorId(Long id) {
    // Retorna a consulta do livro no banco de dados atravéz do id e, caso não encotre, dispara uma excessão
    return repository.findLivroById(id)
        .orElseThrow(() -> new LivroNotFoundException("O livro com o id '" + id + "' não foi encontrado"));
  }

  /**
   * Consulta o livro atravéz do título
   * 
   * @param titulo
   * @return
   */
  public List<Livro> filtraPorTitulo(String titulo) {
    // Retorna a consulta do livro no banco de dados atravéz do titulo e, caso não encotre, dispara uma excessão
    return repository.findLivroByTitulo(titulo)
        .orElseThrow(() -> new LivroNotFoundException("O livro com o titulo '" + titulo + "' não foi encontrado"));
  }

  /**
   * Consulta o livro atravéz do autor
   * 
   * @param autor
   * @return
   */
  public List<Livro> filtraPorAutor(String autor) {
    // Retorna a consulta do livro no banco de dados atravéz do autor e, caso não encotre, dispara uma excessão
    return repository.findLivroByAutor(autor)
        .orElseThrow(() -> new LivroNotFoundException("O livro com o autor '" + autor + "' não foi encontrado"));
  }

  /**
   * Salva e atualiza o livro
   * 
   * @param livro
   * @return Livro
   */
  public Livro salvaLivro(LivroDTO livroDTO) {
    Livro livroSalvo = null;
    Livro novoLivro = null;
    // Verifica se o livro a ser salvo contém id
    if (livroDTO.getCodigo() != null) {
      // Consulta o livro no baco de dados atravéz do id
      novoLivro = buscaLivroPorId(livroDTO.getCodigo());
      // Verifica se o livro foi encontrado
      if (novoLivro != null) {
        // Consulta o livro atravéz do título
        var buscarLivroPorTitulo = repository.findByTitulo(livroDTO.getTitulo());
        // Verifica se o livro foi encontrado
        if (buscarLivroPorTitulo == null) {
          // Atualiza o livro populando um novo objeto com os dados atualizados
          novoLivro.setIsbn(livroDTO.getIsbn().trim());
          novoLivro.setTitulo(livroDTO.getTitulo().trim());
          novoLivro.setAutor(livroDTO.getAutor().trim());
          novoLivro.setEditora(livroDTO.getEditora().trim());
          novoLivro.setDataAtualizacao(LocalDateTime.now());
        } else {
          // Dispara uma excessão caso o livro já exista cadastrado no banco de dados
          throw new LivroAlreadyExistException("Um livro com o titulo '" + livroDTO.getTitulo() + "' já existe");
        }
      } else {
        // Dispara uma excessão caso o livro não for encontrado
        throw new LivroNotFoundException("O livro com o id '" + livroDTO.getCodigo() + "' não foi encontrado");
      }
    } else {
      // Consulta o livro atravéz do título
      var buscarLivroPorTitulo = repository.findByTitulo(livroDTO.getTitulo());
      // Verifica se o livro foi encontrado
      if (buscarLivroPorTitulo == null) {
        // Converte o dto para entidade
        novoLivro = mapper.toEntity(livroDTO);
      } else {
        // Dispara uma excessão caso o livro já exista cadastrado no banco de dados
        throw new LivroAlreadyExistException("Um livro com o titulo '" + livroDTO.getTitulo() + "' já existe");
      }
    }
    // Salva o livro no banco de dados
    livroSalvo = repository.save(novoLivro);
    // Retorna o livro salvo
    return livroSalvo;
  }

  /**
   * Deleta o livro atravéz do id
   * 
   * @param id
   */
  public void deletaLivro(Long id) {
    // Consulta o livro no baco de dados atravéz do id
    Livro livro = buscaLivroPorId(id);
    // Verifica se o livro foi encontrado
    if (livro == null) {
      // Dispara uma excessão caso o livro não for encontrado
      throw new LivroNotFoundException("O livro com o id '" + id + "' não foi encontrado");
    } else {
      // Deleta o livro do banco de dados
      repository.delete(livro);
    }
  }

}
