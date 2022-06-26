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

import com.strategos.nueva.bancoproyecto.ideas.dao.IdeasProyectosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

@Service
public class IdeasProyectosServiceImpl implements IdeasProyectosService{

	@Autowired
	private IdeasProyectosDao ideasProyectosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IdeasProyectos> findAll() {
		return (List<IdeasProyectos>) ideasProyectosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IdeasProyectos findById(Long id) {
		
		return ideasProyectosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IdeasProyectos save(IdeasProyectos proyectos) {
		
		return ideasProyectosDao.save(proyectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		ideasProyectosDao.deleteById(id);
	}

	@Override
	public List<IdeasProyectos> queryFiltros(FIltroIdea filtro) {
		// TODO Auto-generated method stub
		
		Specification<IdeasProyectos> specification = new Specification<IdeasProyectos>() {
			public Predicate toPredicate(Root<IdeasProyectos> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> predicates = new ArrayList<Predicate>();
				 if(filtro.getOrganizacionId() != null) {
					 predicates.add(cb.equal(root.<String>get("dependenciaId"), filtro.getOrganizacionId()));
				 }
				 if(filtro.getPropuestaId() != null) {
					 predicates.add(cb.equal(root.<String>get("tipoPropuestaId"), filtro.getPropuestaId()));
				 }
				 if(filtro.getEstatusId() != null) {
					 predicates.add(cb.equal(root.<String>get("estatusIdeaId"), filtro.getEstatusId()));
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
		
		List<IdeasProyectos> ideasEntities = ideasProyectosDao.findAll(specification);
        List<IdeasProyectos> ideas = new ArrayList<IdeasProyectos>();
        for (IdeasProyectos idea : ideasEntities) {
            ideas.add(idea);
        }
        return ideas;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<IdeasProyectos> findAllByDependenciaId(Long dependenciaId) {
		
		return (List<IdeasProyectos>)ideasProyectosDao.findAllByDependenciaId(dependenciaId);
	}
		

	
}

