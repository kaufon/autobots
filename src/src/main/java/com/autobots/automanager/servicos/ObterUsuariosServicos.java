package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObterUsuariosServicos {
  @Autowired
  private EmpresaRepository empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public List<Usuario> obterUsuarios(Long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new RuntimeException(" encontrada");
    }
    var usuarios = empresa.get().obterUsuariosPorPerfil(autenticacaoProvedor.getPerfil());
    if (usuarios.isEmpty()) {
      throw new RuntimeException("Nenhum usuario cadastrado");
    }
    return usuarios;
  }
}
