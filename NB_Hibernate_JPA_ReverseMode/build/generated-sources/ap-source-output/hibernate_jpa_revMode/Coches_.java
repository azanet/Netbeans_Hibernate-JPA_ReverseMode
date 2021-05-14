package hibernate_jpa_revMode;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Coches.class)
public abstract class Coches_ {

	public static volatile SingularAttribute<Coches, String> marca;
	public static volatile SingularAttribute<Coches, Persona> persona;
	public static volatile SingularAttribute<Coches, String> color;
	public static volatile SingularAttribute<Coches, String> matricula;
	public static volatile SingularAttribute<Coches, Integer> id;

	public static final String MARCA = "marca";
	public static final String PERSONA = "persona";
	public static final String COLOR = "color";
	public static final String MATRICULA = "matricula";
	public static final String ID = "id";

}

