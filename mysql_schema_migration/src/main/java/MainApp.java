import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.flywaydb.core.Flyway;

/**
 * Created by toshikijahja on 6/15/19.
 */
public class MainApp {

    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println("Noop. No argument passed");
            return;
        }

        final CommandLineParser commandLineParser = new DefaultParser();
        final CommandLine commandLine;

        final Options options = new Options();

        final Option clean = new Option("c", "clean", false, "Clean schema migrations");
        clean.setRequired(false);
        options.addOption(clean);

        final Option migrate = new Option("m", "migrate", false, "Run schema migrations");
        migrate.setRequired(false);
        options.addOption(migrate);

        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (final ParseException exception) {
            System.out.println("Failed to parse command line properties");
            exception.printStackTrace();
            return;
        }

        final Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/freight?serverTimezone=UTC&useSSL=true", "root", "tos")
                .load();

        System.out.println("Running flyway");

        if (commandLine.hasOption("clean")) {
            System.out.println("Cleaning schema migrations");
            flyway.clean();
        }

        if (commandLine.hasOption("migrate")) {
            System.out.println("Running schema migrations");
            flyway.migrate();
        }

        System.out.println("Finished running flyway");
    }
}
