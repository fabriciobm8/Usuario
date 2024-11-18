package com.multiversa.usuario.controller;

import com.multiversa.usuario.exception.UsuarioNaoEncontradoException;
import com.multiversa.usuario.model.UsuarioModel;
import com.multiversa.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioRepository usuarioRepository;
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioModel adicionarUsuario(@Valid @RequestBody UsuarioModel
      usuario) {
    return usuarioRepository.save(usuario);
  }
  // Atualizar um usuário existente
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioModel> atualizarUsuario(@PathVariable Long id,
      @Valid @RequestBody UsuarioModel usuarioAtualizado) {
    return usuarioRepository.findById(id)
        .map(usuario -> {
          usuario.setNome(usuarioAtualizado.getNome());
          usuario.setEmail(usuarioAtualizado.getEmail());
          UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);
          return ResponseEntity.ok(usuarioSalvo);
        })
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID" + id + " não foi encontrado."));
  }
  // Buscar usuário por ID
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioModel> buscarUsuario(@PathVariable Long id) {
    UsuarioModel usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID" + id + " não foi encontrado."));
    return ResponseEntity.ok(usuario);
  }
  //Buscar usuarios
  @GetMapping
  public List<UsuarioModel> getAllUsuarios() {
    return usuarioRepository.findAll();
  }
  // Remover um usuário
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new UsuarioNaoEncontradoException("Usuário com ID " + id + " não foi encontrado.");
    }
    usuarioRepository.deleteById(id);return ResponseEntity.noContent().build();
  }
}