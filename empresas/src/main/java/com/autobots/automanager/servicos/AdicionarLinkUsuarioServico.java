package com.autobots.automanager.servicos;

import java.util.Set;

import com.autobots.automanager.entidades.Usuario;

import org.springframework.stereotype.Service;


@Service
public class AdicionarLinkUsuarioServico implements AdicionarLinkServico<Usuario> {
	// @Autowired
	// private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;
	//
	// @Autowired
	// private AdicionarLinkTelefoneServico adicionaLinkTelefoneServico;
	//
	// @Autowired
	// private AdicionarLinkEnderecoServico adicionaLinkEnderecoServico;

	@Override
	public void adicionarLink(Set<Usuario> clientes) {
		for (Usuario cliente : clientes) {
			adicionarLink(cliente);
		}
	}

	@Override
	public void adicionarLink(Usuario cliente) {
		// adicionaLinkDocumentoServico.adicionarLink(cliente.getDocumentos());
		// adicionaLinkTelefoneServico.adicionarLink(cliente.getTelefones());
		// if (cliente.getEndereco() != null) {
		// 	adicionaLinkEnderecoServico.adicionarLink(cliente.getEndereco());
		// }
	}
}
