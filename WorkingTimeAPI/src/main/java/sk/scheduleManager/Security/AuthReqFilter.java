package sk.scheduleManager.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.scheduleManager.JWT.JWTManager;
import sk.scheduleManager.Models.Employee;
import sk.scheduleManager.Repos.IEmployeesRepo;

import javax.management.relation.Role;
import java.io.Console;
import java.io.IOException;

@Component
public class AuthReqFilter extends OncePerRequestFilter {

    @Autowired
    private IEmployeesRepo employeesRepo;

    @Autowired
    private JWTManager jwtManager;

    @Order(5)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {

                var token = header.substring(7);
                var login = jwtManager.GetUsernameFromToken(token);

                if (login != null) {

                    var employee = employeesRepo.GetByLogin(login);

                    if (employee != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                        if (jwtManager.ValidateToken(token, employee)) {

                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                    new SimpleGrantedAuthority("Default"), employee, null);
                            usernamePasswordAuthenticationToken
                                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {

        }

        filterChain.doFilter(request, response);
    }
}
