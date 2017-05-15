package udf;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test
 * Created by noah on 17-5-15.
 */
public class TestUDF {
	@Test
	public void testUDF() {
		udf.SimpleUDFExample example = new udf.SimpleUDFExample();
		assertEquals(true, example.evaluate(new Text("2"), new Text("1")));
	}
}
