/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.rohitghatol.spring.odata.edm.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.ODataException;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.edm.provider.EdmProvider;
import org.apache.olingo.server.api.edm.provider.EntityContainer;
import org.apache.olingo.server.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.server.api.edm.provider.EntitySet;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Property;
import org.apache.olingo.server.api.edm.provider.PropertyRef;
import org.apache.olingo.server.api.edm.provider.Schema;
import org.springframework.stereotype.Component;

@Component
public class GenericEdmProvider extends EdmProvider {

	// Service Namespace
	public static final String NAMESPACE = "com.example.model";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_PRODUCT_NAME = "Product";
	public static final FullQualifiedName ET_PRODUCT_FQN = new FullQualifiedName(
			NAMESPACE, ET_PRODUCT_NAME);

	// Entity Set Names
	public static final String ES_PRODUCTS_NAME = "Products";

	@Override
	public List<Schema> getSchemas() throws ODataException {

		// create Schema
		Schema schema = new Schema();
		schema.setNamespace(NAMESPACE);

		// add EntityTypes
		List<EntityType> entityTypes = new ArrayList<EntityType>();
		entityTypes.add(getEntityType(ET_PRODUCT_FQN));
		schema.setEntityTypes(entityTypes);

		// add EntityContainer
		schema.setEntityContainer(getEntityContainer());

		// finally
		List<Schema> schemas = new ArrayList<Schema>();
		schemas.add(schema);

		return schemas;
	}

	@Override
	public EntityType getEntityType(FullQualifiedName entityTypeName)
			throws ODataException {

		// this method is called for one of the EntityTypes that are configured
		// in the Schema
		if (entityTypeName.equals(ET_PRODUCT_FQN)) {

			// create EntityType properties
			Property id = new Property().setName("ID").setType(
					EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
			Property name = new Property().setName("Name").setType(
					EdmPrimitiveTypeKind.String.getFullQualifiedName());
			Property description = new Property()
					.setName("Description")
					.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

			// create PropertyRef for Key element
			PropertyRef propertyRef = new PropertyRef();
			propertyRef.setPropertyName("ID");

			// configure EntityType
			EntityType entityType = new EntityType();
			entityType.setName(ET_PRODUCT_NAME);
			entityType.setProperties(Arrays.asList(id, name, description));
			entityType.setKey(Arrays.asList(propertyRef));

			return entityType;
		}

		return null;
	}

	@Override
	public EntitySet getEntitySet(FullQualifiedName entityContainer,
			String entitySetName) throws ODataException {

		if (entityContainer.equals(CONTAINER)) {
			if (entitySetName.equals(ES_PRODUCTS_NAME)) {
				EntitySet entitySet = new EntitySet();
				entitySet.setName(ES_PRODUCTS_NAME);
				entitySet.setType(ET_PRODUCT_FQN);

				return entitySet;
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.olingo.server.api.edm.provider.EdmProvider#getEntityContainer()
	 */
	@Override
	public EntityContainer getEntityContainer() throws ODataException {

		// create EntitySets
		List<EntitySet> entitySets = new ArrayList<EntitySet>();
		entitySets.add(getEntitySet(CONTAINER, ES_PRODUCTS_NAME));

		// create EntityContainer
		EntityContainer entityContainer = new EntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}

	@Override
	public EntityContainerInfo getEntityContainerInfo(
			FullQualifiedName entityContainerName) throws ODataException {

		// This method is invoked when displaying the service document at e.g.
		// http://localhost:8080/DemoService/DemoService.svc
		if (entityContainerName == null
				|| entityContainerName.equals(CONTAINER)) {
			EntityContainerInfo entityContainerInfo = new EntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}
}
