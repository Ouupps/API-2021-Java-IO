package ch.heigvd.api.labio.impl;

import java.io.File;

/**
 * The FileExplorer performs an exploration of the file system. It
 * visits the files and directory in alphabetic order.
 * When the explorer sees a directory, it recursively explores the directory.
 * When the explorer sees a file, it invokes the transformation on it.
 *
 * @author Olivier Liechti, Juergen Ehrensberger
 */
public class FileExplorer {

    public void explore(File rootDirectory) {
        FileTransformer transformer = new FileTransformer();
        exploreRecursiv(rootDirectory, transformer);
    }

    private void exploreRecursiv(File rootDirectory, FileTransformer transformer){

        if(!rootDirectory.exists())
            return;
        File filesList[] = rootDirectory.listFiles();

        for (File currentFile : filesList){
            if(currentFile.isDirectory())
                exploreRecursiv(currentFile, transformer);
            else
                if(currentFile.isFile())
                    transformer.transform(currentFile);
        }
    }
}