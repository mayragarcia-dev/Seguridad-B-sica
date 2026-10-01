package com.tecsup.util;

import com.tecsup.model.Role;
import com.tecsup.model.User;
import com.tecsup.repository.RoleRepository;
import com.tecsup.repository.UserRepository;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Carga los roles y usuarios iniciales del laboratorio.
 * Es idempotente: si los usuarios ya existen, se actualiza su contraseña
 * y sus roles para que el cambio de contraseñas siempre surta efecto.
 */
@Configuration
public class DataLoader {

	@Bean
	public CommandLineRunner initData(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
		return args -> {

			// 1. Crear los roles si no existen
			Role userRole = getOrCreateRole(roleRepo, "ROLE_USER");
			Role adminRole = getOrCreateRole(roleRepo, "ROLE_ADMIN");
			Role managerRole = getOrCreateRole(roleRepo, "ROLE_MANAGER");

			// 2. Crear o actualizar los usuarios con sus contraseñas y roles
			saveUser(userRepo, encoder, "user", "user123", userRole);
			saveUser(userRepo, encoder, "admin", "admin123", adminRole);
			saveUser(userRepo, encoder, "manager", "manager123", managerRole);

			System.out.println("Datos iniciales cargados correctamente");
		};
	}

	/**
	 * Devuelve el rol indicado o lo crea si aun no existe.
	 */
	private Role getOrCreateRole(RoleRepository roleRepo, String name) {
		return roleRepo.findByName(name)
				.orElseGet(() -> {
					Role role = new Role();
					role.setName(name);
					return roleRepo.save(role);
				});
	}

	/**
	 * Crea el usuario si no existe; si ya existe, actualiza su contraseña
	 * y su rol. Siempre guarda el resultado.
	 */
	private void saveUser(UserRepository userRepo, PasswordEncoder encoder,
			String username, String rawPassword, Role role) {

		User user = userRepo.findByUsername(username).orElseGet(User::new);

		user.setUsername(username);
		user.setPassword(encoder.encode(rawPassword));

		HashSet<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);

		userRepo.save(user);
	}
}
