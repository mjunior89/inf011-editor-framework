package editorframework;

import editorframework.interfaces.IAbstractFactory;
import editorframework.interfaces.ICore;
import editorframework.interfaces.IPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class TextFactory implements IAbstractFactory, IPlugin {

    private ArrayList<String> extensions;

    @Override
    public ArrayList<String> supportedExtensions() {
        return extensions;
    }

    @Override
    public boolean initialize(ICore core) {
        initExtensions();
        return true;
    }

    private void initExtensions() {
        extensions = new ArrayList<>();
        extensions.add("txt");
    }

    @Override
    public boolean treatFile(File file) {
        Logger.getLogger("TextFactory").log(Level.INFO, "Arquivo de texto tratado em TextFactory: {0}", file.getName());
        return true;
    }

}
