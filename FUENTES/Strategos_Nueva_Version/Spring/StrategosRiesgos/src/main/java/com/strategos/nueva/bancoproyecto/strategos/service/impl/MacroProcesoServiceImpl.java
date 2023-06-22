package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.MacroProcesoDao;
import com.strategos.nueva.bancoproyecto.strategos.model.MacroProceso;
import com.strategos.nueva.bancoproyecto.strategos.service.MacroProcesoService;


@Service
public class MacroProcesoServiceImpl implements MacroProcesoService{
	
	@Autowired
	private MacroProcesoDao macroProcesoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<MacroProceso> findAll() {
		return (List<MacroProceso>) macroProcesoDao.findAll();
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
