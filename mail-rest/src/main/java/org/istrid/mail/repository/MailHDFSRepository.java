package org.istrid.mail.repository;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.store.codec.Codecs;
import org.springframework.data.hadoop.store.output.OutputStreamWriter;
import org.springframework.data.hadoop.store.support.StoreUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Repository
public class MailHDFSRepository {

    @Autowired
    private Configuration hadoopConfiguration;

    public String saveMessage(InputStream mailData) throws IOException {
        String id = UUID.randomUUID().toString();
        saveToHDFS(id, mailData);
        return id;
    }

    private void saveToHDFS(String id, InputStream mailData) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(
                hadoopConfiguration,
                new Path(hadoopConfiguration.get("base.path") + "/" + id),
                Codecs.GZIP.getCodecInfo()
        );
        StoreUtils.copy(mailData, writer);
    }
}
