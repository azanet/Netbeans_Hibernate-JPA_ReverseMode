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


        try {
            CochesJpaController cocheJPA = new CochesJpaController(emf);

            Coches c = new Coches();
            c.setId(1);

            Coches l = cocheJPA.findCoches(c.getId()); //haciendo SELECT en la BBDD

            System.out.println(l.getColor());
            System.out.println("\n\nCambiando color:");
            l.setColor("naranja fluorescente");
            cocheJPA.edit(l);  //haciendo UPDATE en la BBDD

            l = cocheJPA.findCoches(c.getId());
            System.out.println(l.getColor());

            System.out.println("\n\nCambiando color:");
            l.setColor("verde alga");
            cocheJPA.edit(l);

            l = cocheJPA.findCoches(c.getId());
            System.out.println(l.getColor());

            emf.close();
        } catch (Exception e) {
            emf.close();

        }

    }
}
