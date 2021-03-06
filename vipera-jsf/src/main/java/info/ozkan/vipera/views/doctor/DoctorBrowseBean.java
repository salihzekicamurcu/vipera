package info.ozkan.vipera.views.doctor;

import info.ozkan.vipera.business.doctor.DoctorBrowseModel;
import info.ozkan.vipera.business.doctor.DoctorFacade;
import info.ozkan.vipera.business.doctor.DoctorManagerResult;
import info.ozkan.vipera.entities.Doctor;
import info.ozkan.vipera.entities.DoctorTitle;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 * Hekimler üzerinde arama işlemi yapan Bean sınıfı
 * 
 * @author Ömer Özkan
 * 
 */
@Named("doctorBrowse")
@Scope("session")
public class DoctorBrowseBean implements Serializable {
    /**
     * serial
     */
    private static final long serialVersionUID = 4019437522915983064L;
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DoctorBrowseBean.class);
    /**
     * Arama model sınıfı nesnesi
     */
    private DoctorBrowseModel model = new DoctorBrowseModel();
    /**
     * Üye aktif mi?
     */
    private boolean active;

    /**
     * Arama sonucu
     */
    private List<Doctor> result;
    /**
     * İşletme katmanı
     */
    @Inject
    private DoctorFacade doctorFacade;

    /**
     * Veritabanından hekim arama işlemi yapar ekranda gösterir
     */
    public void search() {
        final DoctorManagerResult searchResult = doctorFacade.search(model);
        result = searchResult.getDoctors();
        LOGGER.info("{} doctors are found", result.size());
    }

    /**
     * @return the model
     */
    public DoctorBrowseModel getModel() {
        return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(final DoctorBrowseModel model) {
        this.model = model;
    }

    /**
     * Hekim Ünvanları
     * 
     * @return
     */
    public DoctorTitle[] getDoctorTitles() {
        return DoctorTitle.values();
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * @return the result
     */
    public List<Doctor> getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(final List<Doctor> result) {
        this.result = result;
    }

    /**
     * @param doctorFacade
     *            the doctorFacade to set
     */
    public void setDoctorFacade(final DoctorFacade doctorFacade) {
        this.doctorFacade = doctorFacade;
    }
}
