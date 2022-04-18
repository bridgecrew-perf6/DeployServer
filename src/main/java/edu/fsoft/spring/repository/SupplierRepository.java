package edu.fsoft.spring.repository;

import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query(value="SELECT * FROM supplier where supplier.is_deleted = 'false';", nativeQuery = true)
    List<Supplier> listAllSupplier();
}
