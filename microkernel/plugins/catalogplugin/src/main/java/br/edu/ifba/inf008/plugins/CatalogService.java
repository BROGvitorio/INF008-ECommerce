package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.domain.StockMovement;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.exceptions.InsufficientStockException;
import br.edu.ifba.inf008.interfaces.exceptions.NotFoundException;
import br.edu.ifba.inf008.interfaces.plugins.ICatalogService;

import java.util.List;

public class CatalogService implements ICatalogService
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

    public int getAvailableStock(Product product) {
        List<StockMovement> movements = persistenceController.findBy(StockMovement.class, "product", product);

        int stock = 0;

        for (StockMovement m : movements) {
            if (m.getMovementType().equals("INBOUND")) {
                stock += m.getQuantity();
            } else if (m.getMovementType().equals("OUTBOUND")) {
                stock -= m.getQuantity();
            }
        }

        return stock;
    }

    public void checkStock(Product product, int quantity) {
        int stock = getAvailableStock(product);
        Product p = persistenceController.findById(Product.class, product.getId());

        if (p == null) {
            throw new NotFoundException("No product could be found with that ID.");
        }

        if (quantity > stock) {
            throw new InsufficientStockException(stock);
        }
    }
}