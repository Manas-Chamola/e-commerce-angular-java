package com.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.ecommerce.entity.Country;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductCategory;
import com.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{
	
	private EntityManager entityManager;
	
	@Autowired
	MyDataRestConfig(EntityManager theEntityManager) {
		entityManager= theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		//RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config);
		HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
		
		//diable HttpMethods for Product: PUT, POST, DELETE
		disableHttpMethod(Product.class,config, theUnsupportedActions);
		
		//diable HttpMethods for ProductCategory: PUT, POST, DELETE
		disableHttpMethod(ProductCategory.class,config, theUnsupportedActions);
		
		disableHttpMethod(Country.class,config, theUnsupportedActions);
		
		disableHttpMethod(State.class,config, theUnsupportedActions);
		
		exposeIds(config);
	}

	private void disableHttpMethod(Class theClass,RepositoryRestConfiguration config,
			HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration()
		.forDomainType(theClass)
		.withItemExposure((metdata, HttpMethods) -> HttpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata, HttpMethods) -> HttpMethods.disable(theUnsupportedActions));
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType tempEntitytype : entities) {
			entityClasses.add(tempEntitytype.getJavaType());
		}
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
			
		}

}
