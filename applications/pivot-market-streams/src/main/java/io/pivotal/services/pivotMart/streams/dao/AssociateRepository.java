package io.pivotal.services.pivotMart.streams.dao;

import io.pivotal.services.pivotMart.streams.entity.ProductAssociationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends CrudRepository<ProductAssociationEntity,String>
{
}
