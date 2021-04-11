package project.vaadin.securing;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {
    private SecurityUtils() {
    }

    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        String paramValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return paramValue != null
                && Stream.of(ServletHelper.RequestType.values())
                         .anyMatch(r -> r.getIdentifier().equals(paramValue));
    }
}
