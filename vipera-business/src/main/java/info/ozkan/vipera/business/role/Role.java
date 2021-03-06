package info.ozkan.vipera.business.role;

/**
 * Kullanıcı rollerini tanımlayan model sınıfı
 * 
 * @author Ömer Özkan
 * 
 */
public final class Role {
    /**
     * Yönetici rolü
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    /**
     * Hekim rolü
     */
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    /**
     * Hasta rolü
     */
    public static final String ROLE_PATIENT = "ROLE_PATIENT";

    /**
     * private
     */
    private Role() {

    }
}
