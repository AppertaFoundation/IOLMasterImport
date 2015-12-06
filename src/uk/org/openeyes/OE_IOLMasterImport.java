
package uk.org.openeyes;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.HelpFormatter;

/**
 * @author Tamas Vedelek <vetusko@gmail.com>
 */
public class OE_IOLMasterImport  {

    /**
     *
     * @param options
     */
    private static void printHelp(Options options) {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("annotator-app [options], where [options] can be any of:", options);
        System.exit(0);
    } 

    // main
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        	
        if (args.length == 0) {
            System.out.println("Error, please specify at least a filename with -f, or try -h");
            System.exit(1);
        }
	
        String inputFile = "";
        String configFile = "";
        boolean debug = false;
        DICOMLogger SystemLogger = new DICOMLogger();

        Options options = new Options();
        Option optionHelp = new Option("h", "help", false,
                            "Display help.");
        Option optionFileName = new Option("f", "file", true,
                            "Specify a filename to import.");
        Option optionConfigFile = new Option("c", "config", true,
                            "Specify a Hibernate config file. The default can be found in resources/hibernate.cfg.xml");
        Option optionDebug = new Option("d", "debug", false,
                            "If specified all the processes will run in debug mode, and display more output messages");

        options.addOption(optionHelp);
        options.addOption(optionFileName);
        options.addOption(optionConfigFile);
        options.addOption(optionDebug);

        CommandLineParser parser;
        parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help") || cmd.hasOption('h')) {
                OE_IOLMasterImport.printHelp(options);
            }
            if (cmd.hasOption("f") || cmd.hasOption("file")) {
                inputFile = cmd.getOptionValue("file");
            }
            if (cmd.hasOption("c") || cmd.hasOption("config")) {
                configFile = cmd.getOptionValue("config");
            }
            if (cmd.hasOption("d") || cmd.hasOption("debug")) {
                debug = true;
            }
            
            DICOMParser DicomParser = new DICOMParser(debug);
            
            if(inputFile.equals("")){
                inputFile = "test/data/input_test.dcm";    // original IOLMaster file with multi lense
                //String fname = "test/data/new_input_test.dcm";  // new IOLMaster file with multi formula
                //String fname = "test/data/compressed.dcm";    // compressed original file
                //String fname = "test/data/vfa_test.dcm";      // visual fields dicom file
            }

            SystemLogger.getLogger().setFileName(inputFile);
            DicomParser.parseDicomFile(inputFile, SystemLogger);
            DicomParser.processParsedData(configFile);
            
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            if(debug){
                ex.printStackTrace();
            }
            
            System.exit(2);
        }
    }


}