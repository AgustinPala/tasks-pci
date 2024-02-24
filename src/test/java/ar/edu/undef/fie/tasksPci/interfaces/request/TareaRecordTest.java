package ar.edu.undef.fie.tasksPci.interfaces.request;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareaRecordTest {

    @Test
    void tarea() {
        List<TareaRecord> tareaList = new ArrayList<>();
        tareaList.add(new TareaRecord(List.of(), "Sub Tarea"));
        TareaRecord tareaRecord = new TareaRecord(tareaList, "Test Name");
    }

    @Test
    void name() {
        List<TareaRecord> tareaList = new ArrayList<>();
        tareaList.add(new TareaRecord(List.of(), "Sub Tarea"));
        TareaRecord tareaRecord = new TareaRecord(tareaList, "Test Name");
        assertEquals("Test Name", tareaRecord.name());
    }
}