package com.bolsaideas.springboot.app.service;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.dao.IUsuarioDao;
import com.bolsaideas.springboot.app.models.entity.Role;
import com.bolsaideas.springboot.app.models.entity.Usuario;

/*LA INTERFAZ LA IMPLEMENTA SPRING SECURITY*/
@Service("jpaUserDetailService")
public class JpaUserDetailService  implements UserDetailsService{
	
	@Autowired
	private IUsuarioDao usuarioDao; 
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioDao.findByUsername(username);
		if(usuario == null) {
			//ERROR
			throw new UsernameNotFoundException("El usuario no existe"); 
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role r: usuario.getRoles()) {
			//Registramos estos roles en spring security
			authorities.add(new SimpleGrantedAuthority(r.getAuthority())); 
		}
		if(authorities.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no tiene roles asignados"); 
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),true,true,true,authorities);
	}

}
