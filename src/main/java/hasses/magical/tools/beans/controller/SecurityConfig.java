package hasses.magical.tools.beans.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import hasses.magical.tools.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Autowired
     private UserService userService;

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	private String usersQuery = "select email, password, '1' as enabled from auth_user where email=? and status='VERIFIED'";

	private String rolesQuery = "select u.email, r.role_name from auth_user u inner join auth_user_role ur on(u.auth_user_id=ur.auth_user_id) inner join auth_role r on(ur.auth_role_id=r.auth_role_id) where u.email=?";

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requiresChannel().anyRequest().requiresSecure();
		http.headers().frameOptions().sameOrigin();
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				// URLs matching for access rights
				.antMatchers("/").permitAll().antMatchers("/login").permitAll().antMatchers("/register").permitAll()
				.antMatchers(
						"/reset/password").permitAll()
				.antMatchers("/confirm").permitAll().antMatchers("/bus55").permitAll().antMatchers("/list/operation")
				.permitAll().antMatchers("/home/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER", "SITE_USER")
				.antMatchers("/admin/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER").anyRequest().authenticated().and()
				// form login
				.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
				.successHandler(successHandler)

				.usernameParameter("email").passwordParameter("password").and()
				// logout
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
				.exceptionHandling().accessDeniedPage("/access-denied");

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
