package br.edu.ifba.inf008.interfaces.core;

import java.util.List;

public interface IPluginRegistry {
    <T> void register(Class<T> type, T plugin);
    <T> List<Object> getPlugins(Class<T> type);
}
