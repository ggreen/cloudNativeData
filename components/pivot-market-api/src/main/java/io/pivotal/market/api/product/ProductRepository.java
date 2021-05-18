package io.pivotal.market.api.product;

import io.pivotal.market.api.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer>
{

    Optional<ProductEntity> findById(Integer productId);
}















