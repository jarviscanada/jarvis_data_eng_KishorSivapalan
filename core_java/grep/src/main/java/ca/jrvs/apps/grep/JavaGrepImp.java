package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepImp javaGrepImp = new JavaGrepImp(args[0], args[1], args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex){
            javaGrepImp.logger.error("ERROR: Unable to process.", ex);
        }
    }

    public JavaGrepImp(String regex, String rootPath, String outFile) {
        this.regex = regex;
        this.rootPath = rootPath;
        this.outFile = outFile;
    }

    @Override
    public void process() throws IOException {
        ArrayList<String> matched = new ArrayList<String>();
        for (File file : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matched.add(line);
                }
            }
        }
        writeToFile(matched);
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.matches(getRegex(),line);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> result = new ArrayList<>();
        File root = new File(rootDir);
        File[] rootFiles = root.listFiles();
        if (rootFiles != null){
            for (File file : rootFiles){
                if (file.isFile()){
                    result.add(file);
                } else if (file.isDirectory()){
                    result.addAll(listFiles(file.getAbsolutePath()));
                }
            }
        }
        return result;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String curr;
            while ((curr = br.readLine()) != null) {
                lines.add(curr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getOutFile()))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Unable to write to file: " + getOutFile());
        }
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
