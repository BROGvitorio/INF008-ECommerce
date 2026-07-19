package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.IPluginController;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICheckoutComponent;
import br.edu.ifba.inf008.interfaces.ICore;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginController implements IPluginController {
    List<IPlugin> plugins = new ArrayList<>();

    public boolean init() {
        try {
            File currentDir = new File("./plugins");

            // Define a FilenameFilter to include only .jar files
            FilenameFilter jarFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jar");
                }
            };

            String[] pluginNames = currentDir.list(jarFilter);
            int i;
            URL[] jars = new URL[pluginNames.length];
            for (i = 0; i < pluginNames.length; i++) {
                jars[i] = (new File("./plugins/" + pluginNames[i]))
                        .toURI()
                        .toURL();
            }
            URLClassLoader ulc = new URLClassLoader(jars, App.class.getClassLoader());
            for (i = 0; i < pluginNames.length; i++) {
                String pluginName = pluginNames[i].split("\\.")[0];
                IPlugin plugin = (IPlugin) Class.forName("br.edu.ifba.inf008.plugins." + pluginName, true, ulc)
                        .getDeclaredConstructor()
                        .newInstance();
                
                plugins.add(plugin);
                        
                plugin.init();
            }

            for (IPlugin plugin : plugins) {
                plugin.start();
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());

            return false;
        }
    }
}
