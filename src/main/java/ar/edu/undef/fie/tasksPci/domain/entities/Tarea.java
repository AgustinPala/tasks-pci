package ar.edu.undef.fie.tasksPci.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroDeRequerimiento;
    private String titulo;
    private String descripcion;
    @OneToMany
    private List<Responsable> responsables;
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Tarea() { };

    public Tarea(String numeroDeRequerimiento, String descripcion, String titulo, List<Responsable> responsables, Prioridad prioridad) {
        this.numeroDeRequerimiento = numeroDeRequerimiento;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.responsables = responsables;
        this.estado = Estado.PENDIENTE;
        this.prioridad = prioridad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDeRequerimiento() {
        return numeroDeRequerimiento;
    }

    public void setNumeroDeRequerimiento(String numeroDeRequerimiento) {
        this.numeroDeRequerimiento = numeroDeRequerimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // Enumerado para la prioridad de las tareas
    public enum Prioridad {
        BAJA, MEDIA, ALTA
    }

    public enum Estado {
        PENDIENTE, COMPLETA
    }
}