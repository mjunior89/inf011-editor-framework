/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editorframework;

import editorframework.interfaces.IAbstractFactory;
import editorframework.interfaces.IPlugin;
import editorframework.interfaces.IPluginController;
import editorframework.interfaces.IUIController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author aluno
 */
public class UIController implements IUIController {

    private MainFrame mainFrame;

    public UIController() {
        (mainFrame = new MainFrame()).setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    @Override
    public JMenuItem addMenuItem(String menu, String menuItem) {
        JMenuBar menuBar = mainFrame.getJMenuBar();
        int menuCount = menuBar.getMenuCount();
        JMenu targetMenu = null;
        for (int i = 0; i < menuCount; i++) {
            if (menuBar.getMenu(i).getText().equalsIgnoreCase(menu)) {
                targetMenu = menuBar.getMenu(i);
            }
        }
        if (targetMenu == null) {
            targetMenu = new JMenu(menu);
            menuBar.add(targetMenu);
        }
        int itemCount = targetMenu.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (targetMenu.getItem(i).getText().equalsIgnoreCase(menuItem)) {
                return null;
            }
        }
        JMenuItem targetMenuItem = new JMenuItem(menuItem);
        targetMenu.add(targetMenuItem);
        return targetMenuItem;
    }

    @Override
    public void loadPluginsUI(IPluginController pluginController) {
        this.loadPluginMenuItem(pluginController);
    }

    private void loadPluginMenuItem(IPluginController pluginController) {
        JMenuItem newItem = this.addMenuItem("File", "Open");
        if (newItem != null) {
            delegateFileOpenAction(newItem, pluginController);
        }
    }

    private void delegateFileOpenAction(JMenuItem newItem, IPluginController pluginController) {
        ArrayList<String> extensions = this.fetchSupportedPluginExtentions(pluginController);

        newItem.addActionListener(new ActionListener() {

            // Ação executada ao clicar no menu File->Open
            public void actionPerformed(ActionEvent evt) {
                JFileChooser jfc = new JFileChooser();
                jfc.setAcceptAllFileFilterUsed(false);
                {
                    for (String extension : extensions) {
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("(*." + extension + ")", extension);
                        jfc.setFileFilter(filter);
                    }
                    int retornoOperacao = jfc.showDialog(mainFrame, "Selecione o arquivo");
                    if (retornoOperacao == JFileChooser.APPROVE_OPTION) {
                        this.treatChoosenFile(jfc);
                    }
                }
            }

            // Ação executada ao selecionar um arquivo através seletor de arquivos
            private void treatChoosenFile(JFileChooser jfc) {
                File arquivo = jfc.getSelectedFile();
                final String fileName = arquivo.getName();
                for (IPlugin iplugin : pluginController.loadedPlugins()) {
                    if (iplugin instanceof IAbstractFactory) {
                        final String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                        IAbstractFactory factory = (IAbstractFactory) iplugin;
                        if (factory.supportedExtensions().contains(fileExtension.toLowerCase())) {
                            factory.treatFile(arquivo);
                            break;
                        }
                    }
                }
            }

        });
    }

    // Obter uma lista mesclada com todas as extensoes suportadas pelos plugins carregados em pluginController
    private ArrayList<String> fetchSupportedPluginExtentions(IPluginController pluginController) {
        ArrayList<String> extensions = new ArrayList<>();
        for (IPlugin iplugin : pluginController.loadedPlugins()) {
            if (iplugin instanceof IAbstractFactory) {
                extensions.addAll(((IAbstractFactory) iplugin).supportedExtensions());
            }
        }
        return extensions;
    }

}
