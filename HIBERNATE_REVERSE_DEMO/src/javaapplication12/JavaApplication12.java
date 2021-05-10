/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author davidf
 */
public class JavaApplication12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic 
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("JavaApplication12PU");

        //CAMBIAR ESTE DE AQUÍ ABAJO POR EL CORRESPONDIENTE
        List<Persona> vjpa = new PersonaJpaController(fabrica).findPersonaEntities();
        
        for (Persona objeto : vjpa) {
            System.out.println(objeto.getNombre());
        }
        
    }
    
}
