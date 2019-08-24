import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateModifiedUpdater {

    private DateModifiedUpdater() {

    }

    static boolean updateDateModifiedOfFile(File file) {
        return file.setLastModified(createDate(parseDate(file.getName())).getTime());
    }

    static String parseDate(String fileName) {
        final String regex = "\\d+";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(fileName);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group(0));
            result.append("-");
        }

        if ("".equals(result.toString())) {
            throw new IllegalArgumentException(fileName);
        }

        // ignore millisecond digit of timestamp
        return result.substring(0, result.length() - 2);
    }

    public static Date createDate(String dateFromFileName) {
        try {
            return new SimpleDateFormat("yyyyMMdd-HHmmss").parse(dateFromFileName);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unsupported date format:" + dateFromFileName);
        }
    }
}
