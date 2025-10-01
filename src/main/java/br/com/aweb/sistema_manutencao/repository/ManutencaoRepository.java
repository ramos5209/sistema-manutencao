package br.com.aweb.sistema_manutencao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_manutencao.model.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
}
