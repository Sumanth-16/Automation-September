package com.utilities;

import com.datamanager.ConfigManager;
import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * To Establish SSH connection
 *
 * @author Venkata Sai Annam
 */
public class SSHConnection {

    private Logger log = LogManager.getLogger("SSHConnection");
    private ConfigManager app = new ConfigManager("App");

    /**
     * Authenticate to SSH using data configured in App.properties file
     * @return Session
     */
    public Session authenticateSSH() {
        String SSH_userName = app.getProperty("App.SSH_userName");
        String SSH_Password = app.getProperty("App.SSH_Password");
        String SSH_host = app.getProperty("App.SSH_host");
        int SSH_port = Integer.parseInt(app.getProperty("App.SSH_port"));

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(SSH_userName, SSH_host, SSH_port);
        } catch (JSchException e) {
            e.printStackTrace();
            log.error("Could not connect to " + SSH_host + " with " + "port " + SSH_port +": "+ e.getStackTrace());
        }
        session.setPassword(SSH_Password);
        session.setConfig(config);
        try {
            session.connect();
            log.info("Connected to " + SSH_host + " with " + "port " + SSH_port);
        } catch (JSchException e1) {
            e1.printStackTrace();
            log.error("Could not connect to " + SSH_host + " with " + "port " + SSH_port +": "+ e1.getStackTrace());
        }
        return session;
    }

    /**
     * Sends commands to authenticated SSH connection and returns Output
     * @param session
     * @param sCommand
     * @return StringBuffer
     */
    public StringBuffer sendCommandAndCaptureOutput(Session session, String sCommand) {

        Channel channel = null;
        try {
            channel = session.openChannel("exec");
        } catch (JSchException e1) {
            e1.printStackTrace();
        }

        ((ChannelExec) channel).setCommand(sCommand);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        log.info("Executed command : " + sCommand);
        InputStream in = null;
        try {
            in = channel.getInputStream();
            channel.connect();
        } catch (IOException | JSchException e1) {
            e1.printStackTrace();
            log.error("Could not read output: "+ e1.getStackTrace());
        }
        StringBuffer reader = new StringBuffer();
        try {
            byte[] tmp = new byte[1024];
            while (true) {
                while (true) {

                    if (!(in.available() > 0)) break;

                    int i = in.read(tmp, 0, 1024);
                    if (i < 0)
                        break;
                    reader.append(new String(tmp, 0, i));
                    //  System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        channel.disconnect();
        return reader;
    }

    /**
     * Disconnect SSH session
     * @param session
     */
    public void disconnectSSH(Session session) {
        session.disconnect();
        log.info(session + " disconnected");
    }

}