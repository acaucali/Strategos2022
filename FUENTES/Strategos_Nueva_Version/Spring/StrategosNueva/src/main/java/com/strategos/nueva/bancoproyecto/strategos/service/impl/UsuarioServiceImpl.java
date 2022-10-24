package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.UsuarioStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.UsuarioService;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService{

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioStrategosDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioStrategos> findAll() {
		return (List<UsuarioStrategos>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioStrategos findById(Long id) {
		
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public UsuarioStrategos save(UsuarioStrategos usuario) {
		
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		usuarioDao.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public UsuarioStrategos findByUser(String user) {
		
		return usuarioDao.findByUser(user);
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		UsuarioStrategos usuario = usuarioDao.findByUser(username);
		
		if(usuario == null) {
			logger.error("No existe el usuario "+ username+" en el sistema");
			throw new UsernameNotFoundException("No existe el usuario "+ username+" en el sistema");
		}
		
		Boolean activado= false;
		
		if(usuario.getDeshabilitado() == null || usuario.getDeshabilitado() == false) {
			activado = true;
		}else {
			activado = false;
		}
		
		//trae los permisos y seguridad del usuario
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		return new User(usuario.getUser(), usuario.getPwd(), activado, true, true, true, authorities);
	}
			
}
