package Problems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import java.util.*;

public class MainTests {
	@Test
	public void Tests() {
//		Test cases:
//		All_subsets_1
		All_subsets_1 as1 = new All_subsets_1();
//		Test1
		assertEquals(as1.subSets("a").equals(Arrays.asList("" , "a")), true);
//		Test2
		assertEquals(as1.subSets("abc").equals(Arrays.asList("", "c", "b", "bc", "a", "ac", "ab", "abc")), true);
//		Test3
		assertEquals(as1.subSets("").equals(Arrays.asList("")), true);
		System.out.println("All All_subsets_1 tests passed");
	}
}
