/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino_coba.comport;

import static arduino_coba.coba.DATA_RATE;
import static arduino_coba.coba.TIME_OUT;
import static arduino_coba.coba.input;
import static arduino_coba.coba.output;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 *
 * @author Brontok
 */
public class serialConnect extends Thread {

    public SerialPort serialPort;
//    private static final String PORT_NAMES[] = {"COM15"};

    public void run(String PORT_NAMES[]) {
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                for (String portName : PORT_NAMES) {
                    if (currPortId.getName().equals(portName)) {
                        portId = currPortId;
                        break;
                    }
                }
            }
            if (portId == null) {
                System.out.println("COM Port tidak ditemukan");
                return;
            } else {
                System.out.println("Terkoneksi");
            }
            try {
                serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
                serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();
                char ch = 1;
                output.write(ch);
                serialPort.addEventListener((SerialPortEventListener) this);
                serialPort.notifyOnDataAvailable(true);
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

    }

;

}
