package com.example.demo.Controller;


import com.example.demo.DAO.SShDAO;
import com.example.demo.Entities.Commande;
import com.example.demo.Entities.SSHConnection;
import com.example.demo.Entities.sqlhost;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin("*")
@RequestMapping("/ssh")
public class SShController {


    @Autowired
    private SShDAO sshDAO;
    private Session session;
    private String str3;
    public SShController() {
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public SSHConnection saveSSH(@RequestBody SSHConnection c) {
        return sshDAO.saveSshConnection(c);
    }

    //connexion vers VM
    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public Session connect(@RequestBody SSHConnection c) throws JSchException {
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();

             session = jsch.getSession(c.getUser(), c.getHost(), 22);
            session.setPassword(c.getPassword());
            session.setConfig(config);
            session.connect();

           System.out.println("connect");
            return session;
        }catch(Exception e){
            System.out.println("Non Connected");

            return null;
        }
    }


    //Excution des commandes
   @RequestMapping(value = "/commande", method = RequestMethod.POST)
       public  Commande  ExecuteCommande(@RequestBody Commande commande) {
           String CommandOutput = null;
           try {
               // System.out.println("Connected");
               Channel channel = session.openChannel("exec");
               ((ChannelExec)channel).setPty(true);
               InputStream in = channel.getInputStream();
               channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

               ((ChannelExec) channel).setCommand(commande.getCommande());
               ((ChannelExec) channel).setErrStream(System.err);


               channel.connect();
               byte[] tmp = new byte[1024];
               while (true) {
                   while (in.available() > 0) {
                       int i = in.read(tmp, 0, 1024);

                       if (i < 0)
                           break;
                       // System.out.print(new String(tmp, 0, i));
                       CommandOutput = new String(tmp, 0, i);
                   }

                   if (channel.isClosed()) {
                       // System.out.println("exit-status: " +
                       // channel.getExitStatus());
                       break;
                   }
                   try {
                       Thread.sleep(1000);
                   } catch (Exception ee) {
                   }
               }
            //   channel.disconnect();
           //    session.disconnect();
               // System.out.println("DONE");

           } catch (Exception e) {
               e.printStackTrace();
           }
       commande.setCommande(CommandOutput);
       System.out.println(commande.getCommande());
       return commande;

       }


    //copy fichier local vers server linux
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public void copyfichier(String local,String host){


    }

    @RequestMapping(value = "/commande_host", method = RequestMethod.POST)
    public  String  ExecuteCommande_hosts(@RequestBody sqlhost shost) {
        String[] parts = shost.getInstance().split("\r\n");
        String chaine=shost.getInstance()+"="+ "\\(DESCRIPTION =" +

                "\\(ADDRESS = \\(PROTOCOL = TCP\\)\\( HOST = "+shost.getIp() +" \\)\\(PORT ="+shost.getPort()  +

                "\\)\\)\\(CONNECT_DATA =" +

                "\\(SERVER = DEDICATED\\)" +
                "\\(SERVICE_NAME ="+shost.getInstance()+"\\)\\)\\)";
        String CommandOutput = null;
        try {
            // System.out.println("Connected");
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setPty(true);
            InputStream in = channel.getInputStream();
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            System.out.println(parts[0]);
            ((ChannelExec) channel).setCommand("sudo su - `ls -l /work/install/profile|cut -d' ' -f3 | grep -v ' '|tail -1` -c 'echo -e "+ chaine+" >>  $ORACLE_HOME/network/admin/tnsnames.ora'");
            ((ChannelExec) channel).setErrStream(System.err);


            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);

                    if (i < 0)
                        break;
                    // System.out.print(new String(tmp, 0, i));
                    CommandOutput = new String(tmp, 0, i);
                }

                if (channel.isClosed()) {
                    // System.out.println("exit-status: " +
                    // channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            //   channel.disconnect();
            //    session.disconnect();
            // System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }

    //    System.out.println(shost.getInstance());
        return CommandOutput;


    }

    @RequestMapping(value = "/deconnect", method = RequestMethod.GET)
     public String deconnect(){
         session.disconnect();
         System.out.println("DONE");
         return "deconnect";
     }
    }
