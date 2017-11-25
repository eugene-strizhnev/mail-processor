package org.istrid.mail.service;

import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;

public class JobRunnerService {

    public static void main(String ... args) throws IOException, ClassNotFoundException, InterruptedException {


        final String javaHome = "/Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home";
        final String sparkHome = "/Users/istrid/Downloads/spark-2.2.0-bin-hadoop2.7";
        final String appResource = "mail-processing-executor/build/mapreducelib/mail-hadoop-processor-0.0.1-SNAPSHOT.jar";
        final String mainClass = "org.istrid.mail.HadoopJob";
        //
        // parameters passed to the  SparkFriendRecommendation
        final String[] appArgs = new String[]{

                //"--arg",
                "hdfs://sandbox.hortonworks.com:8020/input/input.txt",

                //"--arg",
                "hdfs://sandbox.hortonworks.com:8020/friends/output6"
        };
        System.setProperty("YARN_CONF_DIR","/Users/istrid/IdeaProjects/mail-processor/mail-processing-executor/src/main/resources");
        //export YARN_CONF_DIR=/Users/istrid/IdeaProjects/mail-processor/mail-processing-executor/src/main/resources

        //
        SparkLauncher spark = new SparkLauncher()
                .setVerbose(true)
                .setJavaHome(javaHome)
                .setSparkHome(sparkHome)
                .setAppResource(appResource)    // "/my/app.jar"
                .setMainClass(mainClass)        // "my.spark.app.Main"
                .setMaster("yarn")
//                .setDeployMode("cluster")
                .setConf(SparkLauncher.DRIVER_MEMORY, "1g")
                .addAppArgs(appArgs);
        //
        // Launches a sub-process that will start the configured Spark application.
        Process proc = spark.launch();
        //
        InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(proc.getInputStream(), "input");
        Thread inputThread = new Thread(inputStreamReaderRunnable, "LogStreamReader input");
        inputThread.start();
        //
        InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(proc.getErrorStream(), "error");
        Thread errorThread = new Thread(errorStreamReaderRunnable, "LogStreamReader error");
        errorThread.start();
        //
        System.out.println("Waiting for finish...");
        int exitCode = proc.waitFor();
        System.out.println("Finished! Exit code:" + exitCode);
    }
}
