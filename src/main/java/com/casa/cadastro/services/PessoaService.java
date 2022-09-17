package com.casa.cadastro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.casa.cadastro.models.Endereco;
import com.casa.cadastro.models.Pessoa;
import com.casa.cadastro.repositorys.EnderecoRepository;
import com.casa.cadastro.repositorys.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Pessoa salvar(Pessoa pessoa) {
		Endereco endereco = enderecoRepository.findById(pessoa.getEndereco().getId())
				.orElseThrow(() -> new RuntimeException(" Não encontrado."));
		Pessoa p = new Pessoa(pessoa.getId(), pessoa.getNome(), pessoa.getSobrenome(), pessoa.getIdade(),
				pessoa.getSexo(), pessoa.getTelefone(), endereco);
		pessoa = pessoaRepository.save(p);
		return pessoa;
	}

	public Pessoa atualizar(Pessoa pessoa, Long id) {
		Pessoa p = buscarPorId(id);
		if (p != null) {
			pessoa = pessoaRepository.save(pessoa);
			return pessoa;
		}
		return null;
	}

	public Pessoa buscarPorId(Long id) {
		Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException(id + " Não encontrado."));
		return pessoa;
	}

	public List<Pessoa> buscarListaCompleta() {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return pessoas;
	}

	public Page<Pessoa> buscarListaPaginada(Pageable pageable) {
		Page<Pessoa> pessoas = pessoaRepository.findAll(pageable);
		return pessoas;
	}

	public void deletar(Long id) {
		pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException(id + " não foi encontrado"));
		pessoaRepository.deleteById(id);
	}

}
