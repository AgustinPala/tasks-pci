package ar.edu.undef.fie.tasksPci.interfaces.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponsableRecordTest {

    @Test
    void name() {
        ResponsableRecord responsableRecord = new ResponsableRecord("Test Responsable");
        assertEquals("Test Responsable", responsableRecord.name());
    }

    @Test
    void responsable() {
        ResponsableRecord responsableRecord = new ResponsableRecord("Test Responsable");
        assertNotNull(responsableRecord);
    }

    @Test
    void testName() {
        ResponsableRecord responsableRecord = new ResponsableRecord("Test Name");
        assertEquals("Test Name", responsableRecord.name());
    }
}