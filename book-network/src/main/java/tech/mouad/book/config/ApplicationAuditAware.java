package tech.mouad.book.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.mouad.book.user.User;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication== null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        User userPrincipal= (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
        // Il nous aides ne ne pas apple null pointer exception
        // Si je lui donne un null il va retourne une valeur vide .

    }
}
