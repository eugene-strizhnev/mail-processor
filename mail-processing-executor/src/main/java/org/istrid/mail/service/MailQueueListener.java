package org.istrid.mail.service;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailQueueListener implements MessageListener {

    private final List<String> idsBatch = new ArrayList<>();

    @Value("max.batch.number")
    private int maxBatchNumber;

    @Override
    public void onMessage(Message message) {
        String mailId;
        try {
            mailId = ((TextMessage) message).getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        idsBatch.add(mailId);
        if (idsBatch.size() >= maxBatchNumber) {
            try {
                handleBatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleBatch() throws IOException, ClassNotFoundException, InterruptedException {


        Job job = Job.getInstance();
// here you have to put your mapper class
        job.setMapperClass(Mapper.class);
// here you have to put your reducer class
        job.setReducerClass(Reducer.class);
// here you have to set the jar which is containing your
// map/reduce class, so you can use the mapper class
        job.setJarByClass(Mapper.class);
// key/value of your reducer output
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
// this is setting the format of your input, can be TextInputFormat
        job.setInputFormatClass(SequenceFileInputFormat.class);
// same with output
        job.setOutputFormatClass(TextOutputFormat.class);
// here you can set the path of your input
        SequenceFileInputFormat.addInputPath(job, new Path("files/toMap/"));
// this deletes possible output paths to prevent job failures
        FileSystem fs = FileSystem.get(job.getConfiguration());
        Path out = new Path("files/out/processed/");
        fs.delete(out, true);

// finally set the empty out path
        TextOutputFormat.setOutputPath(job, out);

// this waits until the job completes and prints debug out to STDOUT or whatever
// has been configured in your log4j properties.
        job.waitForCompletion(true);

    }
}
