package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

@Service
public class ProyectosServiceImpl implements ProyectosService{

	@Autowired
	private ProyectosDao proyectosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyectos> findAll() {
		return (List<Proyectos>) proyectosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Proyectos findById(Long id) {
		
		return proyectosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Proyectos save(Proyectos proyectos) {
		
		return proyectosDao.save(proyectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		proyectosDao.deleteById(id);
	}
	
	@Override
	public List<Proyectos> queryFiltros(FIltroIdea filtro) {
		// TODO Auto-generated method stub
		
		Specification<Proyectos> specification = new Specification<Proyectos>() {
			public Predicate toPredicate(Root<Proyectos> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> predicates = new ArrayList<Predicate>();
				 if(filtro.getOrganizacionId() != null) {
					 predicates.add(cb.equal(root.<String>get("dependenciaId"), filtro.getOrganizacionId()));
				 }
				 if(filtro.getTipoId() != null) {
					 predicates.add(cb.equal(root.<String>get("tipoProyectoId"), filtro.getTipoId()));
				 }
				 if(filtro.getEstatusId() != null) {
					 predicates.add(cb.equal(root.<String>get("estatusId"), filtro.getEstatusId()));
				 }
				 if(filtro.getAnio() != null) {
					 predicates.add(cb.equal(root.<String>get("anioFormulacion"), filtro.getAnio()));
				 }
				 if(filtro.getHistorico() != null) {
					 predicates.add(cb.equal(root.<String>get("historico"), filtro.getHistorico()));
				 }
				 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		List<Proyectos> proyectosEntities = proyectosDao.findAll(specification);
        List<Proyectos> proyectoss = new ArrayList<Proyectos>();
        for (Proyectos pro : proyectosEntities) {
            proyectoss.add(pro);
        }
        return proyectoss;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyectos> findAllByDependenciaId(Long dependenciaId) {
		
		return (List<Proyectos>)proyectosDao.findAllByDependenciaId(dependenciaId);
	}

	@Override
	public List<Proyectos> findAllByDependenciaIdAndEstatusId(Long dependenciaId, Long estatusId) {
		// TODO Auto-generated method stub
		return (List<Proyectos>)proyectosDao.findAllByDependenciaIdAndEstatusId(dependenciaId, estatusId);
	}

	@Override
	public List<Proyectos> findAllByEstatusId(Long estatusId) {
		// TODO Auto-generated method stub
		return (List<Proyectos>)proyectosDao.findAllByEstatusId(estatusId);
	}

	@Override
	public List<Proyectos> findAllByDependenciaIdAndIsPreproyecto(Long dependenciaId, Boolean isPreproyecto) {
		// TODO Auto-generated method stub
		return (List<Proyectos>)proyectosDao.findAllByDependenciaIdAndIsPreproyecto(dependenciaId, isPreproyecto);
	}

	@Override
	public List<Proyectos> findAllByIsPreproyecto(Boolean isPreproyecto) {
		// TODO Auto-generated method stub
		return (List<Proyectos>)proyectosDao.findAllByIsPreproyecto(isPreproyecto);
	}
			
}
