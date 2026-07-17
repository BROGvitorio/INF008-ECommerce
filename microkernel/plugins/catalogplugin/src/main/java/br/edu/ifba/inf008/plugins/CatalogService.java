package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.domain.StockMovement;

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
        if (quantity > getAvailableStock(product)) {
            throw new InsufficientStockException();
        }
    }
}