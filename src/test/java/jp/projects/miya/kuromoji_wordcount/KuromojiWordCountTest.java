package jp.projects.miya.kuromoji_wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class KuromojiWordCountTest {
    private MapDriver<LongWritable, Text, Text, IntWritable> driver;

	@Before
    public void setUp() {
    	Mapper<LongWritable, Text, Text, IntWritable> mapper = new WordMapper();
        this.driver = new MapDriver<LongWritable, Text, Text, IntWritable>(mapper);
    }

    @Test
    public void testMapper() {
    	this.driver.withInput(
                new LongWritable(1),
                new Text("We must know"));
    	this.driver.withOutput(new Text("We"), new IntWritable(1));
    	this.driver.withOutput(new Text("must"), new IntWritable(1));
    	this.driver.withOutput(new Text("know"), new IntWritable(1));
    	this.driver.runTest();
    }

	@Test
	public void testMain() throws Exception {
		KuromojiWordCount.main(new String[] {"input", "output"} );
	}
}
