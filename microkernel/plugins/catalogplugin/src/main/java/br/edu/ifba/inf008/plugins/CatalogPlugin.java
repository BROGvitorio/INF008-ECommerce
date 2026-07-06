package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;

public class CatalogPlugin implements IPlugin
{
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.setPrefSize(100, 100);
        vbox.setStyle(
            "-fx-border-color: black;" +
            "-fx-border-width: 1;"
        );

        String sql = "SELECT name, description, unit_price FROM products WHERE active = true";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Label name = new Label(resultSet.getString("name"));
            Label description = new Label(resultSet.getString("description"));
            Label unit_price = new Label("R$ " + resultSet.getBigDecimal("unit_price"));

            vbox.getChildren().addAll(name, description, unit_price);
        }

        uiController.createTab("Catalog", vbox);

        return true;
    }
}
