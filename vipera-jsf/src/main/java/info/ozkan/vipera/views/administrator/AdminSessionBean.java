package info.ozkan.vipera.views.administrator;

import info.ozkan.vipera.entities.Administrator;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Yönetici oturum bilgisi
 * 
 * @author Ömer Özkan
 * 
 */
@Named("adminSession")
@Scope("session")
public class AdminSessionBean {
    /**
     * Yönetici
     */
    private Administrator administrator;

    /**
     * setup
     */
    @PostConstruct
    public void setUp() {
        final Authentication obj =
                SecurityContextHolder.getContext().getAuthentication();
        final Object administrator = obj.getPrincipal();
        if (administrator instanceof Administrator) {
            this.administrator = (Administrator) administrator;
        }
    }

    /**
     * Kullanıcı adı
     * 
     * @return
     */
    public String getUsername() {
        return administrator.getUsername();
    }
}
