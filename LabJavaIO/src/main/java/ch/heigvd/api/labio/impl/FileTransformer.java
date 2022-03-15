package ch.heigvd.api.labio.impl;

import ch.heigvd.api.labio.impl.transformers.LineNumberingCharTransformer;
import ch.heigvd.api.labio.impl.transformers.NoOpCharTransformer;
import ch.heigvd.api.labio.impl.transformers.UpperCaseCharTransformer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class transforms files. The transform method receives an inputFile.
 * It writes a copy of the input file to an output file, but applies a
 * character transformer before writing each character.
 *
 * @author Juergen Ehrensberger
 */
public class FileTransformer {
  private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());

  public void transform(File inputFile) {
    NoOpCharTransformer noTransform = new NoOpCharTransformer();
    LineNumberingCharTransformer lineAdd = new LineNumberingCharTransformer();
    UpperCaseCharTransformer upperTransform = new UpperCaseCharTransformer();

    try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(inputFile.getPath() + ".out"), StandardCharsets.UTF_8)) {
      try (InputStreamReader fr = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8)) {
        while (fr.ready()) {
          String c = noTransform.transform(String.valueOf((char)fr.read()));
          c = upperTransform.transform(c);
          c = lineAdd.transform(c);
          LOG.info(c);
          fw.write(c);
        }
      }
    } catch (FileNotFoundException e) {
      LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", e);
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", e);
    }
  }
}