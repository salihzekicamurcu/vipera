package info.ozkan.vipera.views.patient;

import info.ozkan.vipera.business.patient.PatientFacade;
import info.ozkan.vipera.business.patient.PatientManagerResult;
import info.ozkan.vipera.business.patient.PatientManagerStatus;
import info.ozkan.vipera.common.EmailValidator;
import info.ozkan.vipera.entities.Authorize;
import info.ozkan.vipera.entities.Patient;
import info.ozkan.vipera.jsf.FacesMessage2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 * Hasta ekleme ekranı
 * 
 * @author Ömer Özkan
 * 
 */
@Named("patientAdd")
@Scope("session")
public class PatientAddBean implements Serializable {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PatientAddBean.class);
    /**
     * Kaydedildi mesajı deseni
     */
    private static final String SUCCESS_MSG_FORMAT = "%s %s kaydedildi!";
    /**
     * TCKN kayıtlı hata mesaj başlığı
     */
    private static final String DUPLICATE_PATIENT_ERROR = "Girdiğiniz TC kimlik numarası ile kayıtlı başka bir hasta bulunmaktadır!";
    /**
     * TCKN kayıtlı hata mesajı
     */
    private static final FacesMessage2 DUPLICATE_PATIENT_MSG = new FacesMessage2(
            FacesMessage.SEVERITY_ERROR, DUPLICATE_PATIENT_ERROR, "");
    /**
     * Geçersiz eposta mesaj başlığı
     */
    private static final String EMAIL_VALIDATION_ERROR = "Girdiğiniz eposta adresi geçersiz!";
    /**
     * Geçersiz eposta mesajı
     */
    private static final FacesMessage2 EMAIL_VALIDATION_ERROR_MSG = new FacesMessage2(
            FacesMessage.SEVERITY_ERROR, EMAIL_VALIDATION_ERROR, "");
    /**
     * Doğum tarihi hata mesaj başlığı
     */
    private static final String BIRTHDAY_ERROR = "Girdiğiniz doğum tarihi bugünden önce olmalıdır!";
    /**
     * Doğum tarihi hata mesajı
     */
    private static final FacesMessage2 BIRTDAY_ERROR_MSG = new FacesMessage2(
            FacesMessage.SEVERITY_ERROR, BIRTHDAY_ERROR, "");
    /**
     * Serial
     */
    private static final long serialVersionUID = 1397754955333904084L;
    /**
     * Business layer object
     */
    @Inject
    private PatientFacade patientFacade;
    /**
     * Yeni hasta
     */
    private Patient patient = new Patient();
    /**
     * Parola
     */
    private String password;
    /**
     * Parola tekrarı
     */
    private String password2;
    /**
     * Hesabın aktifliği
     */
    private boolean enabled = true;

    public PatientAddBean() {
        createDefaultDate();
    }

    private void createDefaultDate() {
        final Calendar calendar = new GregorianCalendar();
        calendar.set(1990, Calendar.JANUARY, 1);
        patient.setBirthDate(calendar.getTime());
    }

    /**
     * Hastayı sisteme kaydeder
     */
    public void save() {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean success = true;
        setPassword();
        if (checkBirthDateInPast()) {
            addMessage(context, BIRTDAY_ERROR_MSG);
            success = false;
            LOGGER.error("The new patient {}'s birthday is in future!",
                    patient.getFullname());
        }
        if (!isValidEmail(patient.getEmail())) {
            addMessage(context, EMAIL_VALIDATION_ERROR_MSG);
            success = false;
            LOGGER.error("The new patient {}'s email is invalid!",
                    patient.getFullname());
        }
        if (success) {
            savePatient(context);
        }

    }

    private boolean checkBirthDateInPast() {
        return BirthDateChecker.checkBirthDateInPast(patient.getBirthDate());
    }

    /**
     * Hastayı sisteme kaydeder
     */
    private void savePatient(final FacesContext context) {
        setEnabled();
        final PatientManagerResult result = patientFacade.add(patient);

        if (result.getStatus().equals(PatientManagerStatus.SUCCESS)) {
            final String summary = String.format(SUCCESS_MSG_FORMAT,
                    patient.getTckn(), patient.getFullname());
            final FacesMessage2 successMessage = new FacesMessage2(
                    FacesMessage.SEVERITY_INFO, summary, "");
            addMessage(context, successMessage);
            LOGGER.info("The new patient has added! {}", patient.getFullname());
            patient = new Patient();
        } else if (result.getStatus().equals(
                PatientManagerStatus.TCKN_HAS_EXIST)) {
            addMessage(context, DUPLICATE_PATIENT_MSG);
            LOGGER.error(
                    "The new patient {} cannot be added because of duplicate entry",
                    patient.getFullname());
        }
    }

    /**
     * Hastanın hesap aktifliğini hasta nesnesine atar
     */
    private void setEnabled() {
        final Authorize enable = enabled ? Authorize.ENABLE : Authorize.DISABLE;
        patient.setEnable(enable);
    }

    /**
     * Eposta adresinin geçerli olup olmadığını kontrol eder
     * 
     * @param email
     *            Eposta adresi
     * @return
     */
    private boolean isValidEmail(final String email) {
        if (email != null && !email.isEmpty()) {
            return EmailValidator.isValid(email);
        }
        return true;
    }

    /**
     * Parola alanlarını kontrol edip parolayı hasta nesnesine atar
     */
    private void setPassword() {
        if (!password.isEmpty() && password.equals(password2)) {
            patient.setPassword(password);
        }
    }

    /**
     * Context'e mesaj ekler
     * 
     * @param message
     */
    private void addMessage(final FacesContext context,
            final FacesMessage message) {
        context.addMessage(null, message);
    }

    /**
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * @param patient
     *            the patient to set
     */
    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * @param password2
     *            the password2 to set
     */
    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the patientFacade
     */
    public PatientFacade getPatientFacade() {
        return patientFacade;
    }

    /**
     * @param patientFacade
     *            the patientFacade to set
     */
    public void setPatientFacade(final PatientFacade patientFacade) {
        this.patientFacade = patientFacade;
    }
}
