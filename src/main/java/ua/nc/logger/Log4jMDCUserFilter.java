package ua.nc.logger;

import org.apache.log4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.nc.service.UserDetailsImpl;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Pavel on 06.05.2016.
 */
public class Log4jMDCUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth == null || auth instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) auth).getPrincipal();
            MDC.put("user", "[" + userDetails.getId() + "]" + userDetails.getUsername());
        } else {
            MDC.put("user", "Anonymous user");
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("user");
        }
    }

    @Override
    public void destroy() {

    }
}
