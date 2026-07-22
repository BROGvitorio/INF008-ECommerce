package br.edu.ifba.inf008.interfaces.plugins;

import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.interfaces.core.IUIController;
import javafx.scene.control.Tab;

import java.util.List;

public interface ICatalogService {
    void createProduct(Product product);
    List<Product> findByNameProduct(String name);
    List<Product> findAllProducts();
    int getAvailableStock(Product product);
    void checkStock(Product product, int quantity);

    void closeTab(IUIController uiController, Tab tab);
}


