import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class CountRecsReducer
extends Reducer<Text, IntWritable, Text, IntWritable> { 
@Override
public void reduce(Text key, Iterable<IntWritable> values, Context context) 
throws IOException, InterruptedException {
int maxValue = 0;
for (IntWritable value : values) {
maxValue++; 
}
context.write(new Text("Total number of good lines:"), new IntWritable(maxValue)); 
}
}