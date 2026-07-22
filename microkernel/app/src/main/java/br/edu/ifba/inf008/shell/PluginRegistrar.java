package br.edu.ifba.inf008.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifba.inf008.interfaces.core.IPluginRegistrar;

public class PluginRegistrar implements IPluginRegistrar {
    private Map<Class<?>, List<Object>> plugins = new HashMap<>();

    public <T> void register(Class<T> type, T plugin) {
        Class<?> pluginType = null;

        for (Class<?> key : plugins.keySet()){
            if (key == type) {
                pluginType = key;
                break;
            }
        }

        if (pluginType == null) {
            List<Object> newPluginSet = new ArrayList<>();
            newPluginSet.add(plugin);

            plugins.put(type, newPluginSet);
            return;
        }

        plugins.get(pluginType).add(plugin);
    }

    public <T> List<Object> getPlugins(Class<T> type) {
        return plugins.get(type);
    }

    public <T> Object getPlugin(Class<T> type) {
        return plugins.get(type).getFirst();
    }
}
