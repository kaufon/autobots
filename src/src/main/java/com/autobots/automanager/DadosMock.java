package com.autobots.automanager;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DadosMock implements CommandLineRunner {

  @Autowired
  private EmpresaRepository empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Override
  public void run(String... args) throws Exception {
    var admin = inserirUsuario();
    inserirEmpresa(admin);
  }

  public Empresa empresa = new Empresa();

  private Usuario inserirUsuario() {
    var usuario = new Usuario();
    var credencial = new Credencial();
    var email = new Email();
    var telefone = new Telefone();
    var documento = new Documento();
    var endereco = new Endereco();

    credencial.setNomeUsuario("gerente-usuario");
    credencial.setSenha("gerente2025");

    documento.setTipo(TipoDocumento.CPF);
    documento.setNumero("9876543210");
    documento.setDataEmissao(new Date());

    email.setEndereco("gerente@realisticmail.com");

    endereco.setBairro("Central Park");
    endereco.setCidade("New York" );
    endereco.setEstado("NY");
    endereco.setRua("5th Avenue");
    endereco.setNumero("101");
    endereco.setCodigoPostal("10001");
    endereco.setInformacoesAdicionais("Suite 500");

    telefone.setDdd("212");
    telefone.setNumero("5551234567");

    usuario.setCredencial(credencial);
    usuario.setNome("John Doe");
    usuario.setNomeSocial("Johnny");
    usuario.setEmails(List.of(email));
    usuario.setTelefones(List.of(telefone));
    usuario.setDocumentos(List.of(documento));
    usuario.setEndereco(endereco);
    usuario.setPerfil(PerfilUsuario.ADMIN);
    usuario.setInativo(false);

    autenticacaoProvedor.registrar(usuario);

    return usuario;
  }

  private void inserirEmpresa(Usuario admin) {
    var endereco = new Endereco();
    var telefone = new Telefone();

    endereco.setEstado("California");
    endereco.setCidade("San Francisco");
    endereco.setBairro("Financial District");
    endereco.setRua("Market Street");
    endereco.setNumero("100");
    endereco.setCodigoPostal("94105");
    endereco.setInformacoesAdicionais("Office 200");

    telefone.setDdd("415");
    telefone.setNumero("5559876543");

    empresa.setRazaoSocial("San Francisco Tech Inc.");
    empresa.setNomeFantasia("SF Tech");
    empresa.setCadastro(LocalDate.of(2023, 6, 1));
    empresa.setEndereco(endereco);
    empresa.setTelefones(List.of(telefone));
    empresa.setUsuarios(List.of(admin));

    empresaRepositorio.save(empresa);
  }
}
