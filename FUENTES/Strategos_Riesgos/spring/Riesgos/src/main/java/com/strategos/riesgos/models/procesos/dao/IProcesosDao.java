package com.strategos.riesgos.models.procesos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.procesos.entity.Procesos;

public interface IProcesosDao extends JpaRepository<Procesos, Long>{

}
