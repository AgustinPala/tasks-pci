package ar.edu.undef.fie.tasksPci.interfaces.request;

import java.util.List;

public record TareaRecord(List<TareaRecord> tarea, String name) {
}