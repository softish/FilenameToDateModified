import java.io.File;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            File providedFile = new File(args[0]);
            if (providedFile.isDirectory()) {
                System.out.println("Metadata update initiated ...");
                for (File fileInDirectory : Objects.requireNonNull(providedFile.listFiles())) {
                    try {
                        DateModifiedUpdater.updateDateModifiedOfFile(fileInDirectory);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage() + ", was skipped");
                    }
                }
                System.out.println("Update complete!");
            } else {
                System.err.println("The provided argument is not a directory");
            }
        } else {
            System.err.println("Argument of directory with files to update is expected");
        }
    }
}
