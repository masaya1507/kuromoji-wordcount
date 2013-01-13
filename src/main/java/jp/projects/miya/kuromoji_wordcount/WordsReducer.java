package jp.projects.miya.kuromoji_wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 
 *
 */
public class WordsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	/**
	 *
	 */
	private static final Logger LOG = LoggerFactory.getLogger(WordsReducer.class);

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#setup(org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		WordsReducer.LOG.info("setup");
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int wordCount = 0;
		for (IntWritable value : values) {
			wordCount += value.get();
		}
		context.write(key, new IntWritable(wordCount));
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#cleanup(org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		WordsReducer.LOG.info("cleanup");
	}

}
