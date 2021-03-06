package info.ozkan.vipera.healthdata;

import info.ozkan.vipera.entities.HealthDataField;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link HealthDataField} örnek verileri
 * 
 * @author Ömer Özkan
 * 
 */
public final class HealthDataFieldTestData {
    /**
     * test data id
     */
    private static final int TEST_DATA_ID = 8;

    /**
     * Alanlar
     */
    private static final Map<Integer, HealthDataField> FIELDS =
            new HashMap<Integer, HealthDataField>();

    /**
     * alan adları
     */
    private static final String[] FIELD_NAMES = { "pulse", "systolic",
            "diastolic", "respirations", "pulseOximetry", "bodyTemp", "bmi",
            "glucoseLevel", "notificationTest" };

    /**
     * utility class
     */
    private HealthDataFieldTestData() {
    }

    /**
     * Örnek veri üretir.
     * 
     * @param key
     *            0 - 3 arası bir değer giriniz
     * @return
     */
    public static HealthDataField getTestData(final int key) {
        if (FIELDS.isEmpty()) {
            initiAlizeTestData();
        }
        return FIELDS.get(key);
    }

    /**
     * Bildirim için kullanılan alanı dönderir
     * 
     * @return
     */
    public static HealthDataField getTestNotificationField() {
        return getTestData(TEST_DATA_ID);
    }

    /**
     * Test alanlarını üretir
     */
    private static void initiAlizeTestData() {
        for (int i = 0; i < FIELD_NAMES.length; i++) {
            final HealthDataField field = new HealthDataField();
            field.setName(FIELD_NAMES[i]);
            FIELDS.put(i, field);
        }

    }
}
