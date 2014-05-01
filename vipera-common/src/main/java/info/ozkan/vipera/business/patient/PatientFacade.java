package info.ozkan.vipera.business.patient;

import info.ozkan.vipera.entities.Patient;

/**
 * Hasta işlemlerini yapan Facade arayüzü
 * 
 * @author Ömer Özkan
 * 
 */
public interface PatientFacade {
    /**
     * Sisteme yeni bir hasta ekler
     * 
     * @param patient
     * @return
     */
    PatientManagerResult add(Patient patient);

}
