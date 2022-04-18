package edu.fsoft.spring.interfaceService;

import edu.fsoft.spring.model.Supplier;
import edu.fsoft.spring.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ISupplierRepository {

    public void save(Supplier supplier);
    public List<Supplier> listAll();
    public void delete(int id);
    public Supplier get(int id);
    public void deleleSupplier(Supplier supplier);
}
