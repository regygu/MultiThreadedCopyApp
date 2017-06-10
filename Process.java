import javax.swing.*;
import java.io.*;

public class Process extends SwingWorker {

    boolean overwrite;
    String from;
    String to;
    File inputFile;
    File outputFile;
    FileInputStream in;
    FileOutputStream out;
    JProgressBar jProgressBar;

    public Process(boolean overwrite, String from, String to, JProgressBar jProgressBar) {
        this.overwrite = overwrite;
        this.from = from;
        this.to = to;
        this.jProgressBar = jProgressBar;
        jProgressBar.setStringPainted(true);
    }

    public void copy() {

        try {
            outputFile = new File(to);
            inputFile = new File(from);
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
            long fileLenght = inputFile.length();
            long counter = 0;
            int read = 0;
            byte[] buffer = new byte[1024];

            while ((read = in.read(buffer)) != -1 && !isCancelled()) {
                counter += read;
                jProgressBar.setValue((int) (Math.floor((1.0 * counter / fileLenght) * 100)));
                out.write(buffer, 0, read);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
                if (isCancelled()) {
                    outputFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Integer doInBackground() throws Exception {
        copy();
        return null;
    }

}
