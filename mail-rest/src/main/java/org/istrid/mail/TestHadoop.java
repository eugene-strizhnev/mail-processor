package org.istrid.mail;

//import com.google.protobuf.ServiceException;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HBaseAdmin;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.FileInputFormat;
//import org.apache.hadoop.mapred.FileOutputFormat;
//import org.apache.hadoop.mapred.JobClient;
//import org.apache.hadoop.mapred.JobConf;
//import org.apache.hadoop.mapred.MapReduceBase;
//import org.apache.hadoop.mapred.Mapper;
//import org.apache.hadoop.mapred.OutputCollector;
//import org.apache.hadoop.mapred.Reporter;
//import org.apache.hadoop.mapred.TextInputFormat;
//import org.apache.hadoop.mapred.TextOutputFormat;
//import org.apache.hadoop.util.Progressable;
//import org.junit.Test;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.URI;
//import java.net.URISyntaxException;

public class TestHadoop {

//    private static class Map extends MapReduceBase implements Mapper<LongWritable, Text, OutputCollector, Reporter> {
//
//        private final static IntWritable one = new IntWritable(1);
//        private Text locText = new Text();
//
//        @Override
//        public void map(LongWritable key, Text value, OutputCollector output, Reporter reporter) throws IOException {
//            String line = value.toString();
//            String location = line.split(",")[14] + "," + line.split(",")[15];
//            long male = 0L;
//            long female = 0L;
//            if (line.split(",")[17].matches("\\d+") && line.split(",")[18].matches("\\d+")) {
//                male = Long.parseLong(line.split(",")[17]);
//                female = Long.parseLong(line.split(",")[18]);
//            }
//            long diff = male - female;
//            locText.set(location);
////            if (Gender.genderCheck.toLowerCase().equals("female") && diff < 0) {
////                output.collect(locText, new LongWritable(diff * -1L));
////            } else if (Gender.genderCheck.toLowerCase().equals("male") && diff > 0) {
////                output.collect(locText, new LongWritable(diff));
////
////            }
//        }
//
//    }
//
//
//
//    private static void putSmthTOHBase() throws IOException, ServiceException {
//        Configuration config = HBaseConfiguration.create();
//
//        String path = TestHadoop.class
//                .getClassLoader()
//                .getResource("hbase-site.xml")
//                .getPath();
//        config.addResource(new Path(path));
//        HBaseAdmin.checkHBaseAvailable(config);
//
//        TableName tableName = TableName.valueOf("Table1");
//        String family1 = "Family1";
//        String family2 = "Family2";
//        Connection connection = ConnectionFactory.createConnection(config);
//        Admin admin = connection.getAdmin();
//        HTableDescriptor desc = new HTableDescriptor(tableName);
//        desc.addFamily(new HColumnDescriptor(family1));
//        desc.addFamily(new HColumnDescriptor(family2));
//        admin.createTable(desc);
//
//        Table table = connection.getTable(tableName);
//        byte[] row1 = Bytes.toBytes("row1");
//        Put p = new Put(row1);
//        p.addImmutable(family1.getBytes(), Bytes.toBytes("my_column"), Bytes.toBytes("cell_data"));
//        table.put(p);
//
//    }
//
//    private static void readFromHbase() throws IOException, ServiceException {
//        Configuration config = HBaseConfiguration.create();
//
//        String path = TestHadoop.class
//                .getClassLoader()
//                .getResource("hbase-site.xml")
//                .getPath();
//        config.addResource(new Path(path));
//        HBaseAdmin.checkHBaseAvailable(config);
//
//        TableName tableName = TableName.valueOf("Table1");
//        Connection connection = ConnectionFactory.createConnection(config);
//        Table table = connection.getTable(tableName);
//        String family1 = "Family1";
//        byte[] row1 = Bytes.toBytes("row1");
//        byte[] qualifier = Bytes.toBytes("my_column");
//        Get g = new Get(row1);
//        Result r = table.get(g);
//        byte[] value = r.getValue(family1.getBytes(), qualifier);
//        System.out.println(new String(value));
//    }
//
//    private static void runJob() throws IOException {
//
////        JobConf conf = new JobConf(Gender.class);
////        conf.setJobName("gender");
////        conf.setOutputKeyClass(Text.class);
////        conf.setOutputValueClass(LongWritable.class);
////        conf.setInputFormat(TextInputFormat.class);
////        conf.setOutputFormat(TextOutputFormat.class);
////        conf.setMapperClass(Map.class);
////        Gender.genderCheck = args[0];
////
////        FileInputFormat.setInputPaths(conf, new Path(args[1]));
////        FileOutputFormat.setOutputPath(conf, new Path(args[2]));
////        JobClient.runJob(conf);
//    }
//
//    public static void testHadoop() throws IOException, URISyntaxException, ServiceException {
////        putSmthToHDFS();
////        putSmthTOHBase();
//        readFromHbase();
////        runJob();
//    }
}
