package com.strategos.nueva.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.UsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		UsuarioStrategos usuario = usuarioService.findByUser(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("name","user: ".concat(authentication.getName()));
		info.put("userid", usuario.getUsuarioId());
		
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	
}
