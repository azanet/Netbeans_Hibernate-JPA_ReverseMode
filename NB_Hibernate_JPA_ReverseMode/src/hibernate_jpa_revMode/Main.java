/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate_jpa_revMode;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

/**
 *
 * @author davidf
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Forma correcta para persistir usando Hibernate                                   //Unidad de persistencia//
        EntityManagerFactory emf = new HibernatePersistenceProvider().createEntityManagerFactory("Hibernate_PU", Collections.EMPTY_MAP);

  
        List<Coches> vjpa = new CochesJpaController(emf).findCochesEntities();

        for (Coches objeto : vjpa) {

            System.out.println(objeto.getMatricula());
        }
        
            //CREANDO UNA MATRICULA NUEVA
              String matricula_aux= String.format("%f",Math.random()).substring(2,6 )+" ZZZ";//;
       
        try {
            CochesJpaController cocheJPA = new CochesJpaController(emf);

            Coches c = new Coches();
            c.setId(2); //Vamos a crear un coche, y le indicaremos el ID(1), para solicitar sus datos y trabajarlos

            Coches coche = cocheJPA.findCoches(c.getId()); //haciendo SELECT en la BBDD
            
            System.out.println("\n\nCambiando Matricula: "+coche.getMatricula()+" Por matrícula ==>"+matricula_aux);
            
            coche.setMatricula(matricula_aux);
            //haciendo UPDATE en la BBDD
            cocheJPA.edit(coche);  

            coche = cocheJPA.findCoches(c.getId());
            System.out.println(coche.getColor());

//            System.out.println("\n\nCambiando color:");
//            coche.setColor("verde alga");
//            cocheJPA.edit(coche);

         //   cocheJPA.destroy(c.getId());
          //  System.out.println(coche.getColor());

            emf.close();
        } catch (Exception e) {
            emf.close();

        }

    }
}
