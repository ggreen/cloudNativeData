package com.vmware.data.demo.retail.store.api.product;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer>
{

    Optional<ProductEntity> findById(Integer productId);
}















