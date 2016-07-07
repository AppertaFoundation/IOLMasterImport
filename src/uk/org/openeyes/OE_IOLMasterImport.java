
package uk.org.openeyes;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.HelpFormatter;
import uk.org.openeyes.models.DicomFiles;

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
        String APIconfigFile = "";
        
        boolean debug = false;
        DICOMLogger SystemLogger = new DICOMLogger();

        Options options = new Options();
        Option optionHelp = new Option("h", "help", false,
                            "Display help.");
        Option optionFileName = new Option("f", "file", true,
                            "Specify a filename to import.");
        Option optionConfigFile = new Option("c", "config", true,
                            "Specify a Hibernate config file. The default can be found in resources/hibernate.cfg.xml, if the name of the config file is not hibernate it will try to parse it as an ini file (eg. /etc/openeyes/dbconf)");
        Option optionAPIConfigFile = new Option("a", "apiconfig", true,
                            "Specify an API ini config file. If no API config specified the program won't use API call.");
        Option optionDebug = new Option("d", "debug", false,
                            "If specified all the processes will run in debug mode, and display more output messages");

        options.addOption(optionHelp);
        options.addOption(optionFileName);
        options.addOption(optionConfigFile);
        options.addOption(optionAPIConfigFile);
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
            if (cmd.hasOption("a") || cmd.hasOption("apiconfig")) {
                APIconfigFile = cmd.getOptionValue("apiconfig");
            }
            if (cmd.hasOption("d") || cmd.hasOption("debug")) {
                debug = true;
            }
            
            DICOMParser DicomParser = new DICOMParser();
            
            DicomParser.initParser(debug, configFile, SystemLogger, APIconfigFile);
            
            if(inputFile.equals("")){
                inputFile = "test/data/input_test.dcm";    // original IOLMaster file with multi lense
                //String fname = "test/data/new_input_test.dcm";  // new IOLMaster file with multi formula
                //String fname = "test/data/compressed.dcm";    // compressed original file
                //String fname = "test/data/vfa_test.dcm";      // visual fields dicom file
            }

            SystemLogger.getLogger().setDicomFileId(DicomParser.searchDicomFile(inputFile));
            DicomParser.parseDicomFile(inputFile);
            DicomParser.processParsedData();
            
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            if(debug){
                ex.printStackTrace();
            }
            
            SystemLogger.getLogger().setStatus("failed");
            SystemLogger.addToRawOutput("ERROR: something went wrong, please check the error messages!");

            // exit code 1: unable to parse command line arguments
            System.exit(1);
        }
    }


}