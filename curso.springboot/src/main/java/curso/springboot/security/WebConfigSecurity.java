package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import curso.springboot.service.ImplementacaoUserDatailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementacaoUserDatailsService implementacaoUserDatailsService;

	@Override // Configura as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // desativa as configurações padrão de memória (spring)
				.authorizeRequests() // restringir acessos
				.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a página inicial
				.antMatchers(HttpMethod.GET, "/cadastropessoa").hasRole("ADMIN") // Somente admin vai acessar está
																					// página
				.anyRequest().authenticated().and().formLogin().permitAll() // permite qualquer usuário
				.loginPage("/login").defaultSuccessUrl("/cadastropessoa").failureUrl("/login?error=true").and().logout()
				.logoutSuccessUrl("/login") // Mapeia url de saida e invalida usuário autenticado
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

	@Override // cria autenticação do usuário com BD ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(implementacaoUserDatailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override // ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
}
