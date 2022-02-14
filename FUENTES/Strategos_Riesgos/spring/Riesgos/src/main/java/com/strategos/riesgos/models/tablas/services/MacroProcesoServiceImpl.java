package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.IMacroProcesoDao;
import com.strategos.riesgos.models.tablas.entity.MacroProceso;
import com.strategos.riesgos.models.tablas.entity.MacroProceso;

@Service
public class MacroProcesoServiceImpl implements IMacroProcesoService{
	
	@Autowired
	private IMacroProcesoDao macroProcesoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<MacroProceso> findAll() {
		return (List<MacroProceso>) macroProcesoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<MacroProceso> findAll(Pageable pageable) {
		
		return macroProcesoDao.findAll(pageable);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public MacroProceso findById(Long id) {
		
		return macroProcesoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public MacroProceso save(MacroProceso macroProceso) {
		
		return macroProcesoDao.save(macroProceso);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		macroProcesoDao.deleteById(id);
	}

	
}
