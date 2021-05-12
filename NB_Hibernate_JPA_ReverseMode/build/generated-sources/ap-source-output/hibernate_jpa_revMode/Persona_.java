package hibernate_jpa_revMode;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Persona.class)
public abstract class Persona_ {

	public static volatile SetAttribute<Persona, ?> cocheses_1;
	public static volatile SingularAttribute<Persona, Integer> id;
	public static volatile SetAttribute<Persona, ?> cocheses;
	public static volatile SingularAttribute<Persona, String> nombre;
	public static volatile SingularAttribute<Persona, String> dni;

	public static final String COCHESES_1 = "cocheses_1";
	public static final String ID = "id";
	public static final String COCHESES = "cocheses";
	public static final String NOMBRE = "nombre";
	public static final String DNI = "dni";

}

