/**
 * 
 */
package com.rohitghatol.spring.odata.edm.providers.product;

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Property;
import org.apache.olingo.server.api.edm.provider.PropertyRef;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.stereotype.Component;

import com.rohitghatol.spring.odata.edm.providers.EntityProvider;

/**
 * @author rohitghatol
 *
 */
@Component
public class CategoryEntityProvider implements EntityProvider {

	// Service Namespace
	public static final String NAMESPACE = "com.example.model";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_CATEGORY_NAME = "Category";
	public static final FullQualifiedName ET_CATEGORY_FQN = new FullQualifiedName(
			NAMESPACE, ET_CATEGORY_NAME);

	// Entity Set Names
	public static final String ES_CATEGORIES_NAME = "Categories";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntityType()
	 */
	@Override
	public EntityType getEntityType() {
		// create EntityType properties
		Property id = new Property().setName("ID").setType(
				EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		Property name = new Property().setName("Name").setType(
				EdmPrimitiveTypeKind.String.getFullQualifiedName());
		Property description = new Property().setName("Description").setType(
				EdmPrimitiveTypeKind.String.getFullQualifiedName());

		// create PropertyRef for Key element
		PropertyRef propertyRef = new PropertyRef();
		propertyRef.setPropertyName("ID");

		// configure EntityType
		EntityType entityType = new EntityType();
		entityType.setName(ET_CATEGORY_NAME);
		entityType.setProperties(Arrays.asList(id, name, description));
		entityType.setKey(Arrays.asList(propertyRef));
		

		return entityType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntitySet
	 * (org.apache.olingo.server.api.uri.UriInfo)
	 */
	@Override
	public EntitySet getEntitySet(UriInfo uriInfo) {
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();

		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet

		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = getData(edmEntitySet);

		return entitySet;
	}

	/**
	 * Helper method for providing some sample data.
	 *
	 * @param edmEntitySet
	 *            for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(EdmEntitySet edmEntitySet) {

		EntitySet entitySet = new EntitySetImpl();

		List<Entity> entityList = entitySet.getEntities();

		// add some sample product entities
		entityList
				.add(new EntityImpl()
						.addProperty(
								new PropertyImpl(null, "ID",
										ValueType.PRIMITIVE, 1))
						.addProperty(
								new PropertyImpl(null, "Name",
										ValueType.PRIMITIVE,
										"Notebook Basic 15"))
						.addProperty(
								new PropertyImpl(null, "Description",
										ValueType.PRIMITIVE,
										"Notebook Basic, 1.7GHz - 15 XGA - 1024MB DDR2 SDRAM - 40GB")));

		entityList
				.add(new EntityImpl()
						.addProperty(
								new PropertyImpl(null, "ID",
										ValueType.PRIMITIVE, 2))
						.addProperty(
								new PropertyImpl(null, "Name",
										ValueType.PRIMITIVE, "1UMTS PDA"))
						.addProperty(
								new PropertyImpl(null, "Description",
										ValueType.PRIMITIVE,
										"Ultrafast 3G UMTS/HSDPA Pocket PC, supports GSM network")));

		entityList
				.add(new EntityImpl()
						.addProperty(
								new PropertyImpl(null, "ID",
										ValueType.PRIMITIVE, 3))
						.addProperty(
								new PropertyImpl(null, "Name",
										ValueType.PRIMITIVE, "Ergo Screen"))
						.addProperty(
								new PropertyImpl(null, "Description",
										ValueType.PRIMITIVE,
										"17 Optimum Resolution 1024 x 768 @ 85Hz, resolution 1280 x 960")));

		return entitySet;
	}

	@Override
	public String getEntitySetName() {		
		return ES_CATEGORIES_NAME;
	}

	/* (non-Javadoc)
	 * @see com.rohitghatol.spring.odata.edm.providers.EntityProvider#getFullyQualifiedName()
	 */
	@Override
	public FullQualifiedName getFullyQualifiedName() {
		return ET_CATEGORY_FQN;
	}

}
