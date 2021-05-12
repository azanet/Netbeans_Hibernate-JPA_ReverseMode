package hibernate_reversemode;
// Generated 11 may. 2021 21:43:32 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Coches generated by hbm2java
 */
@Entity
@Table(name="coches"
    ,catalog="hibernate_test"
)
public class Coches  implements java.io.Serializable {


     private Integer id;
     private Persona persona;
     private String matricula;
     private String marca;
     private String color;

    public Coches() {
    }

    public Coches(Persona persona, String matricula, String marca, String color) {
       this.persona = persona;
       this.matricula = matricula;
       this.marca = marca;
       this.color = color;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_persona", nullable=false)
    public Persona getPersona() {
        return this.persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    
    @Column(name="matricula", nullable=false, length=40)
    public String getMatricula() {
        return this.matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    
    @Column(name="marca", nullable=false, length=40)
    public String getMarca() {
        return this.marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }

    
    @Column(name="color", nullable=false, length=40)
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }




}

