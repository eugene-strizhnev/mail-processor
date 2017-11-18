package org.istrid.mail.repository;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Repository
public class MailHDFSRepository {

    public static final String HDFS_URL = "hdfs://localhost:8020";
    private FileSystem hdfs;

    @PostConstruct
    private void init() throws URISyntaxException, IOException {
//        Configuration configuration = new Configuration();
//        configuration.set("dfs.replication", "1");
//        String hdfsURL = HDFS_URL;
//        hdfs = FileSystem.get(new URI(hdfsURL), configuration);
    }

    public Mono<String> saveMessage(Flux<DataBuffer> mailData) {
        String id = UUID.randomUUID().toString();
        // save to HDFS
        return Mono.just(id);
    }

    private void putSmthToHDFS() throws URISyntaxException, IOException {
        Path file = new Path(HDFS_URL + "/s2013/batch/table.html");
        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }
        OutputStream os = hdfs.create(file, (short) 1);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        br.write("Hello World");
        br.close();
        hdfs.close();
    }
}
