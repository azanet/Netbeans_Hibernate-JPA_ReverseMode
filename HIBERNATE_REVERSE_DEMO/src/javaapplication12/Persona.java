package javaapplication12;
// Generated 10 may. 2021 13:41:14 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Persona generated by hbm2java
 */
@Entity
@Table(name="persona"
    ,catalog="hibernate_test"
)
public class Persona  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String dni;
     private Set<Coches> cocheses = new HashSet<Coches>(0);

    public Persona() {
    }

	
    public Persona(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }
    public Persona(String nombre, String dni, Set<Coches> cocheses) {
       this.nombre = nombre;
       this.dni = dni;
       this.cocheses = cocheses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="nombre", nullable=false, length=40)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Column(name="dni", nullable=false, length=40)
    public String getDni() {
        return this.dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="persona")
    public Set<Coches> getCocheses() {
        return this.cocheses;
    }
    
    public void setCocheses(Set<Coches> cocheses) {
        this.cocheses = cocheses;
    }




}


