package pzinsta.pizzeria.web.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class StaffFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Optional<Object> staffUserOptional = Optional
                .ofNullable(((HttpServletRequest) request).getSession().getAttribute("staff-user"));
        if (staffUserOptional.isPresent() || isLoginPage((HttpServletRequest) request)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("login");
        }

    }

    private boolean isLoginPage(HttpServletRequest request) {
        return StringUtils.equals(request.getRequestURI(), request.getContextPath() + "/staff/login");
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
