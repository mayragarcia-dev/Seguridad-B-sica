package com.tecsup.config;

import com.tecsup.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracion de seguridad del laboratorio 07: autenticacion basica
 * con roles (USER, ADMIN, MANAGER) y contrasenas cifradas con BCrypt.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;

	public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Define la cadena de filtros de seguridad y las reglas de acceso.
	 *
	 * @param http constructor de la cadena de filtros
	 * @return cadena de filtros configurada
	 * @throws Exception si la configuracion falla
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				// CSRF deshabilitado: las peticiones usan Basic Auth, no formularios
				.csrf(AbstractHttpConfigurer::disable)
				// Reglas de autorizacion por ruta (rutas exactas)
				.authorizeHttpRequests(authorize -> authorize
						// Ruta publica: no requiere autenticacion
						.requestMatchers("/api/free").permitAll()
						// Area del usuario: requiere rol USER o ADMIN
						.requestMatchers("/client/home").hasAnyRole("USER", "ADMIN")
						// Panel de administracion: solo rol ADMIN
						.requestMatchers("/management/dashboard").hasRole("ADMIN")
						// Reportes del gerente: solo rol MANAGER
						.requestMatchers("/manager/reportes").hasRole("MANAGER")
						// Cualquier otra ruta: exige estar autenticado
						.anyRequest().authenticated())
				// Carga de usuarios desde la base de datos
				.userDetailsService(userDetailsService)
				// Autenticacion por cabecera Authorization con valores por defecto
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	/**
	 * Codificador de contrasenas.
	 *
	 * @return codificador BCrypt
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Gestor de autenticacion utilizado por el resto de la aplicacion.
	 *
	 * @param config configuracion global de autenticacion
	 * @return gestor de autenticacion
	 * @throws Exception si no se puede construir
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
