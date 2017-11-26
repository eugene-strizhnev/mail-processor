package org.istrid.mail.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamReaderRunnable implements Runnable {

    private String name = null;
    private BufferedReader reader = null;


    public InputStreamReaderRunnable(InputStream is, String name) {
        this.name = name;
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public void run() {
        try {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("run() failed. for name=" + name);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
