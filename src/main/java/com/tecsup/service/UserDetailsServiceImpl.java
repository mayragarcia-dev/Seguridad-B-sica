package com.tecsup.service;

import com.tecsup.model.User;
import com.tecsup.repository.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio de seguridad encargado de cargar los datos del usuario que
 * intenta autenticarse (autenticacion basica HTTP).
 *
 * Spring Security llama a loadUserByUsername() cuando recibe las
 * credenciales y compara la contrasena hasheada con BCrypt.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Busca el usuario en la base de datos y lo convierte en un UserDetails.
	 *
	 * @param username nombre de usuario a buscar
	 * @return usuario con su contrasena y sus roles como autoridades
	 * @throws UsernameNotFoundException si el usuario no existe
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Busca el usuario; si no existe se lanza la excepcion requerida
		User usuario = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		// Convierte cada rol (ROLE_USER, ROLE_ADMIN, ...) en una autoridad
		Collection<SimpleGrantedAuthority> autoridades = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getName()))
				.collect(Collectors.toList());

		// Devuelve el usuario que Spring Security utilizara en la autenticacion
		return new org.springframework.security.core.userdetails.User(
				usuario.getUsername(),
				usuario.getPassword(),
				autoridades);
	}
}
