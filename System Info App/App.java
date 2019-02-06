/** Developed by:
 *      Mark Porowicz
 *      Nicholas O'Donnell
 *      Kalvary Hawkins
 *
 *      Created to implement the use of the OSHI Library found at (https://github.com/oshi/oshi)
 **/
package oshi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.*;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;

public class App {
    private JPanel mainPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;


    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    ComputerSystem cs = hal.getComputerSystem();
    GlobalMemory memory = hal.getMemory();
    OperatingSystem os = si.getOperatingSystem();
    Sensors sensors = hal.getSensors();

    public App() {
        button1.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          OperatingSystem os = si.getOperatingSystem();
                                          ComputerSystem cs = hal.getComputerSystem();
                                          Firmware fw = cs.getFirmware();
                                          Baseboard bb = cs.getBaseboard();
                                          JOptionPane.showMessageDialog(null, "Operating System: "
                                                  + os + "\nManufacturer: " + cs.getManufacturer()
                                                  + "\nModel: " + cs.getModel()
                                                  + "\nSerial Number: " + cs.getSerialNumber()
                                                  + "\n\nFirmware\nManufacturer: " + fw.getManufacturer()
                                                  + "\nName: " + fw.getName()
                                                  + "\nDescription: " + fw.getDescription()
                                                  + "\nVersion: " + fw.getVersion()
                                                  + "\nRelease Date: " + fw.getReleaseDate()
                                                  + "\n\nBaseboard\nManufacturer: " + bb.getManufacturer()
                                                  + "\nModel: " + bb.getModel()
                                                  + "\nVersion: " + bb.getVersion()
                                                  + "\nSerial Number: " + bb.getSerialNumber());

                                      }
                                  }

        );

        button2.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          CentralProcessor p = hal.getProcessor();
                                          JOptionPane.showMessageDialog(null, "Processor\n"
                                                  + "Vendor: " + p.getVendor()
                                                  + "\nName: " + p.getName()
                                                  + "\nIdentifier: " + p.getIdentifier()
                                                  + "\nProcessor ID: " + p.getProcessorID()
                                                  + "\nPhysical CPU Packages: " + p.getPhysicalPackageCount()
                                                  + "\nPhysical CPU Cores: " + p.getPhysicalProcessorCount()
                                                  + "\nLogical CPU Cores: " + p.getLogicalProcessorCount());
                                      }
                                  }

        );

        button3.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          long totalMemory = memory.getTotal()/ 1000000000;
                                          long availMemory = memory.getAvailable()/ 1000000000;
                                          long swapUsed = memory.getSwapUsed();
                                          long swapTotal = memory.getSwapTotal();
                                          JOptionPane.showMessageDialog(null, "Total Memory: "
                                                  + totalMemory + " GB"
                                                  + "\nTotal Available Memory: " + availMemory + " GB"
                                                  + "\n\nSwap Used:\n" + swapUsed + " bytes used out of "
                                                  + swapTotal + " bytes");
                                      }
                                  }

        );

        button4.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          CentralProcessor p = hal.getProcessor();
                                          double[] loadAverage = p.getSystemLoadAverage(3);
                                          StringBuilder procCpu = new StringBuilder("CPU Load per Processor:");
                                          double[] load = p.getProcessorCpuLoadBetweenTicks();
                                          for (double avg : load) {
                                              procCpu.append(String.format(" %.1f%%", avg * 100));
                                          }
                                          JOptionPane.showMessageDialog(null, "CPU Load: "
                                                  + p.getSystemCpuLoadBetweenTicks()*100 + "% (Counting Ticks)"
                                                  + "\nCPU Load: " + p.getSystemCpuLoad()*100 + "% (OS MXBean)"
                                                  + "\nCPU Load Averages: "
                                                  + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
                                                  + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
                                                  + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2]))
                                                  + "\n" + procCpu.toString());
                                      }
                                  }

        );

        button5.addActionListener(new ActionListener() {
                                      // @Override
                                      List<OSProcess> procs = Arrays.asList(os.getProcesses(6, ProcessSort.CPU));
                                      OSProcess p1 = procs.get(1);
                                      OSProcess p2 = procs.get(2);
                                      OSProcess p3 = procs.get(3);
                                      OSProcess p4 = procs.get(4);
                                      OSProcess p5 = procs.get(5);
                                      public void actionPerformed(ActionEvent e) {
                                          JOptionPane.showMessageDialog(null, "Processes: "
                                                  + os.getProcessCount()
                                                  + "\n\nProcess 1: " + p1.getName()
                                                  + "\nProcess ID: " + p1.getProcessID()
                                                  + "\n%CPU: " +(100d * (p1.getKernelTime() + p1.getUserTime()) / p1.getUpTime())
                                                  + "\n%Memory: " + 100d * p1.getResidentSetSize() / memory.getTotal()
                                                  + "\nVSZ: " + FormatUtil.formatBytes(p1.getVirtualSize())
                                                  + "\nRSS: " + FormatUtil.formatBytes(p1.getResidentSetSize())
                                                  + "\n\nProcess 2: " + p2.getName()
                                                  + "\nProcess ID: " + p2.getProcessID()
                                                  + "\n%CPU: " +(100d * (p2.getKernelTime() + p2.getUserTime()) / p2.getUpTime())
                                                  + "\n%Memory: " + 100d * p2.getResidentSetSize() / memory.getTotal()
                                                  + "\nVSZ: " + FormatUtil.formatBytes(p2.getVirtualSize())
                                                  + "\nRSS: " + FormatUtil.formatBytes(p2.getResidentSetSize())
                                                  + "\n\nProcess 3: " + p3.getName()
                                                  + "\nProcess ID: " + p3.getProcessID()
                                                  + "\n%CPU: " +(100d * (p3.getKernelTime() + p3.getUserTime()) / p3.getUpTime())
                                                  + "\n%Memory: " + 100d * p3.getResidentSetSize() / memory.getTotal()
                                                  + "\nVSZ: " + FormatUtil.formatBytes(p3.getVirtualSize())
                                                  + "\nRSS: " + FormatUtil.formatBytes(p3.getResidentSetSize())
                                                  + "\n\nProcess 4: " + p4.getName()
                                                  + "\nProcess ID: " + p4.getProcessID()
                                                  + "\n%CPU: " +(100d * (p4.getKernelTime() + p4.getUserTime()) / p4.getUpTime())
                                                  + "\n%Memory: " + 100d * p4.getResidentSetSize() / memory.getTotal()
                                                  + "\nVSZ: " + FormatUtil.formatBytes(p4.getVirtualSize())
                                                  + "\nRSS: " + FormatUtil.formatBytes(p4.getResidentSetSize())
                                                  + "\n\nProcess 5: " + p5.getName()
                                                  + "\nProcess ID: " + p5.getProcessID()
                                                  + "\n%CPU: " +(100d * (p5.getKernelTime() + p5.getUserTime()) / p5.getUpTime())
                                                  + "\n%Memory: " + 100d * p5.getResidentSetSize() / memory.getTotal()
                                                  + "\nVSZ: " + FormatUtil.formatBytes(p5.getVirtualSize())
                                                  + "\nRSS: " + FormatUtil.formatBytes(p5.getResidentSetSize()));
                                      }
                                  }

        );
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
