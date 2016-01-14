/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editorframework;

import editorframework.interfaces.ICore;
import editorframework.interfaces.IPlugin;
import editorframework.interfaces.IPluginController;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class PluginController implements IPluginController {

    // Armazena os plugins carregados
    private ArrayList<IPlugin> loadedPlugins;

    public PluginController(ICore core) throws MalformedURLException {
        // Encontra a pasta plugins
        File currentDir = new File("./plugins");
        // Obtem todos os arquivos dentro desta pasta
        String[] plugins = currentDir.list();
        // Criar um array de URL do mesmo tamanho do anterior
        URL[] jars = new URL[plugins.length];
        // Loop para popular o array jars
        for (int i = 0; i < plugins.length; i++) {
            jars[i] = (new File("./plugins/" + plugins[i])).toURI().toURL();
        }
        // Adiciona todos os jars (plugins) no CLASSPATH
        URLClassLoader ulc = new URLClassLoader(jars);
        // Loop para inicialicao dos plugins
        IPlugin iplugin = null;

        // inicialização de loadedPlugins
        if (loadedPlugins == null) {
            loadedPlugins = new ArrayList<>();
        }

        String pluginName;
        for (String plugin : plugins) {
            if (plugin.endsWith(".jar")) {
                pluginName = plugin.split("\\.")[0];
                try {
                    iplugin = (IPlugin) Class.forName("editorframework." + pluginName, true, ulc).newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(PluginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (iplugin != null) {
                    iplugin.initialize(core);
                    loadedPlugins.add(iplugin);
                }
            }
        }
    }

    @Override
    public ArrayList<IPlugin> loadedPlugins() {
        return loadedPlugins;
    }

}
