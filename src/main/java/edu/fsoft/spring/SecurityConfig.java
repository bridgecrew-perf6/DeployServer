package edu.fsoft.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.fsoft.spring.interfaceService.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	
	@Autowired
	public CustomUserDetailService customUserDetailService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin", "/admin/**").hasRole("ADMIN")
			.antMatchers("/add", "/listBooking").hasAnyRole("ADMIN", "STAFF")
			.antMatchers("/staff", "/staff/**").hasRole("STAFF")
			.antMatchers("/newBooking/**", "/newProduct").hasRole("USER")
			.antMatchers("/login", "/register", "/home", "/", "/profile", "/news","/news/**","/product/**", "/addAccount", "/saveNewBooking", "/menu", "/about", "/contact","/loginfail","/search").permitAll()
			.antMatchers("/Bill_Form/**", "/BookingForm/**", "/BookingProduct/**", "/css/**", "/fontawesome-free-5.15.3-web/**", "/fonts/**", "/FormProfile/**", "/images/**", "/IncomeForm/**", "/js/**", "/LoginRegisterForm/**", "/ManagerForm/**", "/php/**", "/ProductForm/**").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable().cors().and()
			.formLogin()
		    .loginPage("/login")
		    .successHandler(customizeAuthenticationSuccessHandler)
		    .usernameParameter("username")
		    .passwordParameter("password")
		    .loginProcessingUrl("/j_spring_security_check")
		    .defaultSuccessUrl("/home")
		    .failureUrl("/loginfail")
			.and()
			.logout().permitAll()
			;
	}
}
