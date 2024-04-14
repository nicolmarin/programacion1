package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 * Clase para manejar la información de un curso
 * @author Área de programación UQ
 * @since 2024-01
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
public class Curso {
    private final String nombre;
    private final Collection<Estudiante> estudiantes;
    private final Collection<NotaParcial> notasParciales;

    /**
     * Método constructor de la clase Curso
     * 
     * @param nombre Nombre del curso
     */
    public Curso(String nombre) {
        if (nombre == null){
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        this.nombre = nombre;
        estudiantes = new LinkedList<>();
        notasParciales = new LinkedList<>();
    }

    /**
     * Método para obtener el nombre del curso
     * 
     * @return Nombre del curso
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para agregar a un estudiante al curso
     * 
     * @param estudiante Estudiante que se desea agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        if (!validarNumeroIdentificacionExiste(estudiante.getNumeroIdentificacion())) {
            estudiantes.add(estudiante);
        } else {
            throw new AssertionError("El número de identificación ya existe.");
        }
    }
    

    /**
     * Método privado para determinar si ya existe un estudiante registro en el
     * mismo número de identificación
     * 
     * @param numeroIdentificacion Número de identificación a buscar
     * @return Valor boolean que indica si el número de identificación ya está o no
     *         registrado.
     */
    private boolean validarNumeroIdentificacionExiste(String numeroIdentificacion) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para buscar un estudiante dado el número de indicación
     * 
     * @param numeroIdenficacion Número de identificación del estudiante a buscar
     * @return Estudiante con el número de indicación indicado o null
     */
    public Optional<Estudiante> obtenerEstudiante(String numeroIdentificacion) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return Optional.of(estudiante);
            }
        }
    
        return Optional.empty();
    }

    /**
     * Método para obtener la colección NO modificable de los estudiantes del curso
     * 
     * @return colección NO modificable de los estudiantes del curso
     */
    public Collection<Estudiante> getEstudiantes() {
        return Collections.unmodifiableCollection(estudiantes);
    }

    /**
     * Método para obtener la colección NO modificable de los estudiantes del curso
     * en orden alfabético
     * 
     * @return colección NO modificable de los estudiantes del curso en
     *         orden alfabético
     */
    public Collection<Estudiante> obtenerListadoAlfabetico() {
        List<Estudiante> estudiantesOrdenados = new ArrayList<>(estudiantes);
        Comparator<Estudiante> comparador = Comparator.comparing(Estudiante::getNombres);
        estudiantesOrdenados.sort(comparador);
        return Collections.unmodifiableCollection(estudiantesOrdenados);
    }

    /**
     * Método para obtener la colección NO modificable de los estudiantes del curso
     * en orden descendente de la edad
     * 
     * @return la colección NO modificable de los estudiantes del curso registrados
     *         en el curso descendente por edad.
     */
    public Collection<Estudiante> obtenerListadoEdadDescendente() {
        List<Estudiante> estudiantesOrdenados = new ArrayList<>(estudiantes);
        Comparator<Estudiante> comparador = Comparator.comparing(Estudiante::getEdad).reversed();
        estudiantesOrdenados.sort(comparador);
        return Collections.unmodifiableCollection(estudiantesOrdenados);
    }

    /**
     * Método para obtener la colección NO modificable de los estudiantes del curso
     * que son menores de edad
     * 
     * @return la colección NO modificable de los estudiantes del curso que
     *         son menores de edad.
     */
    public Collection<Estudiante> obtenerListadoMenoresEdad() {
        List<Estudiante> listadoMenoresEdad = new ArrayList<>();
    
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getEdad() < 18) {
                listadoMenoresEdad.add(estudiante);
            }
        }
    
        return listadoMenoresEdad;
    }

    /**
     * Método para adicionar una nota parcial
     * TODO: Se puede validar que la suma de los porcentajes no sobrepase en ningún
     * momento 1.0 (100%)
     * 
     * @param notaParcial nota parcial que se desea adicionar al curso
     */
    public void adicionarNotaParcial(NotaParcial notaParcial) {
        notasParciales.add(notaParcial);
    }

    /**
     * Método para obtener una nota parcial dado el nombre de la nota parcial
     * @param nombreNotaParcial nombre de la nota parcial a buscar
     * @return nota parcial encontrada o un excepción de no entrada.
     */
    public NotaParcial getNotaParcial(String nombreNotaParcial) {
        for (NotaParcial notaParcial : notasParciales) {
            if (notaParcial.nombre().equals(nombreNotaParcial)) {
                return notaParcial;
            }
        }
    
        return null;
    }

    /**
     * Método que obtiene la colección de los estudiantes con mayor nota.
     * @return colección de los estudiantes con la mayor nota.
     */
    public Collection<Estudiante> obtenerListadoMayorNota() {
        double mayorNota = obtenerMayorNota();
        List<Estudiante> listadoMayorNota = new ArrayList<>();
    
        for (Estudiante estudiante : estudiantes) {
            if (Math.abs(mayorNota - estudiante.getDefinitiva()) <= App.PRECISION) {
                listadoMayorNota.add(estudiante);
            }
        }
    
        return listadoMayorNota;
    }

    /**
     * Método de apoyo (privado) que determinar el valor de la mayor nota definitiva
     * @return mayor nota definitiva de los estudiantes del curso.
     */
    private double obtenerMayorNota() {
        double mayorNota = Double.MIN_VALUE;
    
        for (Estudiante estudiante : estudiantes) {
            double definitiva = estudiante.getDefinitiva();
            if (definitiva > mayorNota) {
                mayorNota = definitiva;
            }
        }
    
        return mayorNota;
    }

    /**
     * Método para obtener la colección de los estudiantes que perdieron en orden alfabético.
     * @return colección de los estudiantes que perdieron en orden alfabético.
     */
    public Collection<Estudiante> obtenerListadoAlfabeticoPerdieron() {
        List<Estudiante> listadoAlfabeticoPerdieron = new ArrayList<>();
    
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getDefinitiva() < App.MINIMA_NOTA) {
                listadoAlfabeticoPerdieron.add(estudiante);
            }
        }
    
        listadoAlfabeticoPerdieron.sort(Comparator.comparing(Estudiante::getNombres));
    
        return listadoAlfabeticoPerdieron;
    }

    /**
     * Método para validar que la suma de los porcentajes de las notas parciales esa 1.0 (100%)
     * @return verdadero si la suma de los porcentajes es 1.0 (100%) o tan cercano como la precisión indicada.
     */
    public boolean validarPorcentajes() {
        double pesoNotas = notasParciales.stream()
                .mapToDouble(n -> n.porcentaje()).sum();
        return (1.0 - pesoNotas) <= App.PRECISION;
    }

}