package qlw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wiki on 2017/4/5.
 */
public class UserFilter  extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info(request.getRequestURI());
        if (null == request.getSession().getAttribute("user")) {
            response.sendRedirect(request.getContextPath() + "/userlogin");
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
