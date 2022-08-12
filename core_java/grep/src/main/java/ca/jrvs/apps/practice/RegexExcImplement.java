package ca.jrvs.apps.practice;

public class RegexExcImplement implements RegexExc {

    @Override
    public boolean matchJpeg(String filename) {
        return filename.matches("(?i)(^.+)(\\.)(jpg|jpeg)$");
    }

    @Override
    public boolean matchIp(String ip) {
        return ip.matches("\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\b");
    }

    @Override
    public boolean isEmptyLine(String line) {
        return line.matches("^\\s*$");
    }
}