package msp.group.rento.system.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Autowired
    UserDeatilsServices userDeatilsServices;

    private HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.handlerExceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            String role = null;
            try {

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                    username = jwtService.extractUserName(token);
                    role = jwtService.getPermissionsFromToken(token);
                    System.out.println(role);

                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                    UserDetails userDetails = userDeatilsServices.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(userDetails, username, role);

                    // Set additional details from the request
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }


                filterChain.doFilter(request, response);

            }
            catch (Exception ex){
                handlerExceptionResolver.resolveException(request, response ,null , ex);
            }
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserDetails userDetails, String username, String role) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Create a list of authorities based on the role
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        // Create the authentication token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, authorities);
        return authenticationToken;
    }
}
