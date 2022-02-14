package com.strategos.riesgos.models.procesos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.procesos.entity.Documento;


public interface IDocumentoDao extends JpaRepository<Documento, Long>{

}
