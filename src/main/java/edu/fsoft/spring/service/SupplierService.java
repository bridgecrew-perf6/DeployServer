package edu.fsoft.spring.service;

import edu.fsoft.spring.interfaceService.ISupplierRepository;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Supplier;
import edu.fsoft.spring.repository.CategoryRepository;
import edu.fsoft.spring.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService implements ISupplierRepository {

    @Autowired
    private SupplierRepository repo;
    public void save(Supplier supplier){
        supplier.setIsDeleted("false");
        repo.save(supplier);
    }
    public void deleleSupplier(Supplier supplier){
        supplier.setIsDeleted("true");
        repo.save(supplier);
    }
    public List<Supplier> listAll() {
        return repo.listAllSupplier();
    }
    public void delete(int id) {
        repo.deleteById(id);
    }
    public Supplier get(int id) {
        return repo.findById(id).get();
    }
}
