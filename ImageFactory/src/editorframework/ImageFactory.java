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
public class ImageFactory implements IAbstractFactory, IPlugin {

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
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("png");
        extensions.add("gif");
    }

    @Override
    public boolean treatFile(File file) {
        Logger.getLogger("ImageFactory").log(Level.INFO, "Arquivo de imagem tratado em ImageFactory: {0}", file.getName());
        return true;
    }

}
