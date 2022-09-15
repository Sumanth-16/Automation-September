package com.testsuite.commandLineAutomation;

import com.jcraft.jsch.Session;
import com.testng.Assert;
import com.utilities.SSHConnection;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Scanner;

public class CommandLineAutomationTest {

    SSHConnection sshConnection;
    Session session;

    /**
     * Authenticate to SSH
     */
    @BeforeMethod
    public void setup() {
        sshConnection = new SSHConnection();
        session = sshConnection.authenticateSSH();
    }

    /**
     * Test to demonstrate usage of SSHConnection Utility class
     */
   // @Test
    public void validateCommandLineOutput() {
        //Provide command to send
        String sCommand = "java -version";

        //Send command and read output to string buffer
        StringBuffer stringBuffer = sshConnection.sendCommandAndCaptureOutput(session, sCommand);

        //Read line by line from the output stored in stringBuffer object
        Scanner scan = new Scanner(stringBuffer.toString());
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            //If line ends with description validate the line contains only Alphabets
            if (line.endsWith("Description")) {
                Assert.assertTrue(line.matches("[a-zA-Z]+"));
            }
            // if  line starts with Java version validate it has 1.8 as version number.
            if (line.startsWith("Java version"))
                Assert.assertFalse(line.contains("1.8"));
        }
    }
}
