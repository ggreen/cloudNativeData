package io.pivotal.market.api.dao;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.gemfire.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer>
{

    Optional<ProductEntity> findById(Integer productId);
}















