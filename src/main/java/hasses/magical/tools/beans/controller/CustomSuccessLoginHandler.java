package hasses.magical.tools.beans.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class CustomSuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler{

	private static final Logger LOGGER = Logger.getLogger(CustomSuccessLoginHandler.class);
	@Override
	protected void handle(HttpServletRequest request,HttpServletResponse responce, Authentication authentication)throws IOException{
		String targetUrl=getTargetUrlByAuthentication(authentication);
		
		if(responce.isCommitted()) {
			return;
		}
		RedirectStrategy redirectstrategy = new DefaultRedirectStrategy();
		redirectstrategy.sendRedirect(request,responce,targetUrl);
	}
	
	protected String getTargetUrlByAuthentication(Authentication authentication)throws IOException{
		String result ="";
		
		Collection<? extends GrantedAuthority> autorities = authentication.getAuthorities();
		
		List<String> roles = new ArrayList<>();
		for (Iterator<? extends GrantedAuthority> iterator = autorities.iterator(); iterator.hasNext();) {
			GrantedAuthority grantedAuthority =  iterator.next();
			roles.add(grantedAuthority.getAuthority());
			
		}
		
		if(roles.contains("ADMIN_USER")) {
			result = "/admin";
		}else if(roles.contains("SITE_USER")) {
			result = "/home";
		}
		
		LOGGER.debug("redirect in succeshandler yelds: "+result);
		return result;
		
		
	}
}
