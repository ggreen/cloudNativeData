package io.pivotal.market.api.dao;

import com.vmware.data.retail.store.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Integer>
{

    Optional<Product> findById(Integer productId);
}















