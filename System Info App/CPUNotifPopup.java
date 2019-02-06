/** Developed by:
 *      Mark Porowicz
 *      Nicholas O'Donnell
 *      Kalvary Hawkins
 *
 *      Created to implement the use of the OSHI Library found at (https://github.com/oshi/oshi)
 **/

package NotificationStuff;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.*;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.lang.management.*;
import javax.swing.*;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Scanner;


import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;


public class CPUNotifPopup {

    String line = "";
    String pidInfo = "";
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    GlobalMemory memory = hal.getMemory();
    CentralProcessor cp = si.getHardware().getProcessor();
    OperatingSystem os = si.getOperatingSystem();
    long totMem = memory.getTotal();
    long availMem = memory.getAvailable();
    int numProcess = os.getProcessCount();

    public static void main(String[] args) throws AWTException, MalformedURLException, IOException {

        SystemInfo si = new SystemInfo();
        CentralProcessor cp = si.getHardware().getProcessor();

        Process process = Runtime.getRuntime().exec("tasklist.exe");
        int notifCount = 0;

        if (SystemTray.isSupported()) {
                CPUNotifPopup td = new CPUNotifPopup();

            Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
            td.displayMemTray();
            td.displayProcessTray();
            System.out.println(" Average Process Time " + cp.getSystemLoadAverage());
        //    while (scanner.hasNext() && notifCount < 1)
        //    {
               // System.out.println(scanner.next());
               // System.out.println(scanner.nextLong());
             //   if (scanner.nextLine().contains("chrome.exe"))
               // {

                   // notifCount++;
               // }
         //   }
            scanner.close();
            } else {
                System.err.println("System tray not supported!");
            }

    }




    public void displayTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Available Processors", Runtime.getRuntime().availableProcessors() + " cores", MessageType.INFO);

    }

    public void displayMemTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        DecimalFormat numberFormat = new DecimalFormat("#.00");

        if (availMem <= (totMem/2))
        {
            trayIcon.displayMessage("WARNING!",  "More than half of this system's Memory is in use!", MessageType.WARNING);

        }
            else
            trayIcon.displayMessage("Available Memory", ((double)availMem / 1000000000) + " GB", MessageType.INFO);
      //  trayIcon.displayMessage("Total Memory", ((double)totMem / 1000000000) + " GB", MessageType.INFO);

        trayIcon.displayMessage("Available Memory", numberFormat.format((double)availMem / 1000000000) + " GB", MessageType.INFO);
        trayIcon.displayMessage("Total Memory", numberFormat.format((double)totMem / 1000000000) + " GB", MessageType.INFO);
    }

    public void displayProcessTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Processes Running:",  numProcess + "", MessageType.NONE);
    }
}