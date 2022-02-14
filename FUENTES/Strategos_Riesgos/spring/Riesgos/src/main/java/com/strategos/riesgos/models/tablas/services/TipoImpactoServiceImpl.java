package com.strategos.riesgos.models.tablas.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.ITipoImpactoDao;
import com.strategos.riesgos.models.tablas.entity.Controles;
import com.strategos.riesgos.models.tablas.entity.TipoImpacto;




@Service
public class TipoImpactoServiceImpl implements ITipoImpactoService{
	@Autowired
	private ITipoImpactoDao tipoImpactoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoImpacto> findAll() {
		return (List<TipoImpacto>) tipoImpactoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoImpacto> findAll(Pageable pageable) {
		
		return tipoImpactoDao.findAll(pageable);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public TipoImpacto findById(Long id) {
		
		return tipoImpactoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoImpacto save(TipoImpacto tipoImpacto) {
		
		return tipoImpactoDao.save(tipoImpacto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tipoImpactoDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoImpacto> findAllByPuntaje() {
		return (List<TipoImpacto>) tipoImpactoDao.findAll(Sort.by(Sort.Direction.ASC, "tipoImpactoPuntaje"));
	}
	
	
}
