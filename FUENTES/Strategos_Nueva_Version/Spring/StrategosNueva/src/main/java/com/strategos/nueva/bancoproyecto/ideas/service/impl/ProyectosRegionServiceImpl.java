package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosRegionDao;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosRegion;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosRegionService;

@Service
public class ProyectosRegionServiceImpl implements ProyectosRegionService{
	
	@Autowired
	private ProyectosRegionDao proyectosRegionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProyectosRegion> findAll(){
		return (List<ProyectosRegion>) proyectosRegionDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProyectosRegion findById(Long id) {
		return proyectosRegionDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public ProyectosRegion save(ProyectosRegion proyectos) {
		return proyectosRegionDao.save(proyectos);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		proyectosRegionDao.deleteById(id);
	}
	
	@Override
	public List<ProyectosRegion> findAllByDepartamentoId(Long departamentoId){
		return (List<ProyectosRegion>)proyectosRegionDao.findAllByDepartamentoId(departamentoId);
	}
	
	@Override
	public List<ProyectosRegion> findAllByProyectoId(Long proyectoId){
		return (List<ProyectosRegion>)proyectosRegionDao.findAllByProyectoId(proyectoId);
	}
}
