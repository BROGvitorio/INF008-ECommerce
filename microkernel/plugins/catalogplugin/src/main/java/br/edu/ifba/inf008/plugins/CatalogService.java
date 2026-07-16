package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.domain.Product;

import java.util.List;

public class CatalogService
{
    private IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();

    public void createProduct(Product product) {
        persistenceController.save(product);
    }

    public List<Product> findByNameProduct(String name) {
        return persistenceController.findByName(Product.class, name);
    }

    public List<Product> findAllProducts() {
        return persistenceController.findAll(Product.class);
    }
}