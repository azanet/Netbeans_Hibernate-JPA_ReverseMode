/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate_reversemode;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;

/**
 *
 * @author davidf
 */
public class Hibernate_ReverseMode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
    //Forma correcta para persistir usando Hibernate     
    EntityManagerFactory fabrica =  new HibernatePersistenceProvider().createEntityManagerFactory("Hibernate_ReverseModePU", Collections.EMPTY_MAP);
        
        //Forma correcta para persistir usando Hibernate     
        EntityManagerFactory emf = new HibernatePersistenceProvider().createEntityManagerFactory("Hibernate_ReverseModePU", Collections.EMPTY_MAP);

        //CAMBIAR ESTE DE AQUÍ ABAJO POR EL CORRESPONDIENTE
        List<Coches> vjpa = new CochesJpaController(emf).findCochesEntities();
        
        vjpa.forEach(objeto -> {
            System.out.println(objeto.getColor());
        });
        
        emf.close();
        
   //     fabrica = provider.createEntityManagerFactory("Hibernate_ReverseModePU", Collections.EMPTY_MAP);
        
        
//EntityManagerFactory emf = provider.createEntityManagerFactory(
  // PERSISTENCE_UNIT, props);
     //    EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("Hibernate_ReverseModePU");
try{
      

        CochesJpaController cocheJPA = new CochesJpaController(fabrica);

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
        
        fabrica.close();
}catch(Exception e){
fabrica.close();
}
    }

}
