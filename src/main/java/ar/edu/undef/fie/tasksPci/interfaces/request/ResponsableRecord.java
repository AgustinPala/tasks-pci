package ar.edu.undef.fie.tasksPci.interfaces.request;

import java.util.List;

public record ResponsableRecord(List<ResponsableRecord> responsable, String name) {
}