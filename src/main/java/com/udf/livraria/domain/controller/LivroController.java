package com.udf.livraria.domain.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udf.livraria.domain.dto.LivroDTO;
import com.udf.livraria.domain.exception.LivroAlreadyExistException;
import com.udf.livraria.domain.exception.LivroNotFoundException;
import com.udf.livraria.domain.mapper.LivroMapper;
import com.udf.livraria.domain.model.Livro;
import com.udf.livraria.domain.services.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {

  private final LivroService service;
  private final LivroMapper mapper;

  public LivroController(LivroService service, LivroMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }
  
  @GetMapping("/listar")
  public ResponseEntity<List<LivroDTO>> listaTotos() {
    // Busca os livros através do service
    List<Livro> livros = service.listaTodos();
    // Converte a entidade para dto
    var resultado = livros.stream().map(mapper::toDto).collect(Collectors.toList());
    // Caso exista retorna (ok = 200) | Caso não exista retorna (no content = 204)
    return resultado != null ? ResponseEntity.ok(resultado) : ResponseEntity.noContent().build();
  }

  @GetMapping("/filtrar")
  public ResponseEntity<List<LivroDTO>> filtraPorAutor(String titulo) throws LivroNotFoundException {
    // Consulta os livros através do titulo no service
    List<Livro> livros = service.filtraPorTitulo(titulo);
    // Converte a entidade para dto
    var resultado = livros.stream().map(mapper::toDto).collect(Collectors.toList());
    // Caso exista retorna (ok = 200) | Caso não exista retorna (no content = 204)
    return resultado != null ? ResponseEntity.ok(resultado) : ResponseEntity.noContent().build();
  }

  @PostMapping("/salvar")
  public ResponseEntity<LivroDTO> salvaLivro(@RequestBody LivroDTO livroDTO) throws LivroAlreadyExistException {
    // Salva o livro através do service
    var resposta = service.salvaLivro(livroDTO);
    // Retorna a conversão para dto do entidade salva (created = 201)
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(resposta).toUri()).build();
  }

  @PostMapping("/atualizar")
  public ResponseEntity<LivroDTO> atualizaLivro(@RequestBody LivroDTO livroDTO) throws LivroNotFoundException {
    // Salva o livro através do service
    var resposta = service.salvaLivro(livroDTO);
    // Retorna a conversão para dto do entidade salva (ok = 200)
    return ResponseEntity.ok(mapper.toDto(resposta));
  }

  @DeleteMapping("/deletar")
  public ResponseEntity<String> deletaLivro(@PathParam("id") Long id) throws LivroNotFoundException {
    // Deleta o livro atravéz do service
    service.deletaLivro(id);
    // Caso a operação seja realizada com sucesso, retorna (ok = 200)
    return ResponseEntity.ok().build();
  }

}
