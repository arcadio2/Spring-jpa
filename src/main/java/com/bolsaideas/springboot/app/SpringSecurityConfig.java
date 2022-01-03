package com.bolsaideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsaideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsaideas.springboot.app.service.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
/*AQui se registran los usuarios*/
	
	@Autowired
	private LoginSuccessHandler succesHandler; 
	
	@Autowired
	private  JpaUserDetailService userDetailsService; 

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		
		builder.userDetailsService(userDetailsService)
		.passwordEncoder(MvcConfig.passwordEncoder());
		
		/*builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(MvcConfig.passwordEncoder() )
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?");
		*/
		
		//UserBuilder users = User.withDefaultPasswordEncoder();
		/*PasswordEncoder encoder = MvcConfig.passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder((password)->{
			return encoder.encode(password); 
		});*/
		/*Se encripta por cada usuario
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("sin encriptar").roles("ADMIN","USER"))
		.withUser(users.username("andres").password("1234").roles("USER"));*/
	}

	/*Metodo para las rutas*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Aquí ponemos nuestras rutas publicas, todo dentro de css
		http.authorizeHttpRequests().antMatchers("/","/css/**","/js/**","/images/**","/listar**","/locale","/api/**").permitAll()
		.antMatchers("/ver/**").hasAnyRole("USER","ADMIN")//rutas privadas para rol USER
		/*.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.antMatchers("/form/**").hasAnyRole("ADMIN")*/
		.antMatchers("/logout").permitAll()
		.antMatchers("/login").permitAll() //solución, debes permitir a todos entrar
		.anyRequest().authenticated() //finalizar
		.and() //agregamos cosas
		.formLogin().loginPage("/login") //el  get de la ruta
		.successHandler(succesHandler)
		//.defaultSuccessUrl("/").failureUrl("/")
		.and()
		.logout().logoutUrl("/logout")
		.and()
		.exceptionHandling().accessDeniedPage("/error_403"); //lo manda a 404 si no ponemos la pagina error
		
		/*
		  http.csrf().disable().authorizeRequests()
	            .and().formLogin().loginPage("/login").permitAll()
	                .defaultSuccessUrl("/index").failureUrl("/loginError");  */
	}
	
	
	
}
