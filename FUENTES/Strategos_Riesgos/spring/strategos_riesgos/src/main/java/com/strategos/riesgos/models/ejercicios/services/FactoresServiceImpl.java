package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;
import com.strategos.riesgos.models.ejercicios.dao.IFactoresRiesgoDao;

@Service
public class FactoresServiceImpl implements IFactoresService{
	@Autowired
	private IFactoresRiesgoDao factoresRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<FactoresRiesgo> findAll() {
		return (List<FactoresRiesgo>) factoresRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FactoresRiesgo> findAll(Pageable pageable) {
		
		return factoresRiesgoDao.findAll(pageable);
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public FactoresRiesgo findById(Long id) {
		
		return factoresRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public FactoresRiesgo save(FactoresRiesgo causaRiesgo) {
		
		return factoresRiesgoDao.save(causaRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		factoresRiesgoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FactoresRiesgo> findAllByProcesoId(Long procesoId) {
		
		return (List<FactoresRiesgo>)factoresRiesgoDao.findAllByProcesoId(procesoId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FactoresRiesgo> findAllByProbabilidad(int probabilidad) {
		
		return(List<FactoresRiesgo>)factoresRiesgoDao.findAllByProbabilidad(probabilidad);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<FactoresRiesgo> findAllByImpacto(int impacto) {
		
		return (List<FactoresRiesgo>)factoresRiesgoDao.findAllByImpacto(impacto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<FactoresRiesgo> findAllBySeveridad(int severidad) {
		
		return (List<FactoresRiesgo>)factoresRiesgoDao.findAllBySeveridad(severidad);
		 
	}

}
