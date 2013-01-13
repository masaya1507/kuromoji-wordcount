package jp.projects.miya.kuromoji_wordcount;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Builder;
import org.atilika.kuromoji.Tokenizer.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 *
 */
public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	/**
	 *
	 */
	private static final Logger LOG = LoggerFactory.getLogger(WordMapper.class);

	/**
	 * Kuromoji Tokenizer
	 */
	private Tokenizer _tokenizer;
	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#setup(org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Builder builder = Tokenizer.builder();
		builder.mode(Mode.NORMAL);
		this._tokenizer = builder.build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		List<Token> tokens = this._tokenizer.tokenize(value.toString());
		for (Token t : tokens) {
			String word = t.getSurfaceForm();
			if (word.replaceAll("(\\s|\\t|　)*", "").length() > 0 ||
					"名詞".equals(t.getAllFeaturesArray()[0])) {
				context.write(new Text(t.getSurfaceForm()), new IntWritable(1));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#cleanup(org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		WordMapper.LOG.info("cleanup");
	}
}
