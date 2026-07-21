package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.core.*;
import br.edu.ifba.inf008.interfaces.core.IAuthenticationController;
import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IIOController;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.core.IPluginController;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;
import br.edu.ifba.inf008.interfaces.core.IUIController;

public class Core extends ICore {
    private Core() {
    }

    public static boolean init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

        instance = new Core();
        UIController.launch(UIController.class);

        return true;
    }

    public IUIController getUIController() {
        return UIController.getInstance();
    }

    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }

    public IIOController getIOController() {
        return ioController;
    }

    public IPluginController getPluginController() {
        return pluginController;
    }

    public IPersistenceController getPersistenceController() {
        return persistenceController;
    }

    public IPluginRegistry getPluginRegistry() {
        return pluginRegistry;
    }

    private IPersistenceController persistenceController = new PersistenceController();
    private IAuthenticationController authenticationController = new AuthenticationController();
    private IIOController ioController = new IOController();
    private IPluginController pluginController = new PluginController();
    private IPluginRegistry pluginRegistry = new PluginRegistry();
}