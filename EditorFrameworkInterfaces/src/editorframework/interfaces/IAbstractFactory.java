/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editorframework.interfaces;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author aluno
 */
public interface IAbstractFactory {

    ArrayList<String> supportedExtensions();
    
    boolean treatFile(File file);

}