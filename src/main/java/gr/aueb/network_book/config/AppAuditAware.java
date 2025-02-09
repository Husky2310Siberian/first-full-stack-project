package gr.aueb.network_book.config;

import gr.aueb.network_book.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

/**
 * Custom auditor provider, which tells Spring who the currently authenticated user is.
 * Returns the ID of the currently authenticated user, which can be automatically set on the
 * @CreatedBy or @LastModifiedBy fields in our entities  Book, Feedback.
 * Ensures that the auditing mechanism is tied to the currently authenticated user.
 */

public class AppAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null ||
                !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
