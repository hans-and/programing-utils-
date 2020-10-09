package hasses.magical.tools.string;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import hasses.magical.tools.dto.ListOperation;
import hasses.magical.tools.logic.listoperation.specifics.StringIgnoreCaseWrapper;

class StringIgnoreCaseWrapperTest {

	@Test
	void test() {
		StringIgnoreCaseWrapper ignoreCase_A = new StringIgnoreCaseWrapper("A", true);
		StringIgnoreCaseWrapper ignoreCase_a = new StringIgnoreCaseWrapper("a", true);
		StringIgnoreCaseWrapper case_A = new StringIgnoreCaseWrapper("A", false);
		StringIgnoreCaseWrapper case_a = new StringIgnoreCaseWrapper("a", false);

		StringIgnoreCaseWrapper ignoreCase_B = new StringIgnoreCaseWrapper("B", true);
		StringIgnoreCaseWrapper ignoreCase_b = new StringIgnoreCaseWrapper("b", true);
		StringIgnoreCaseWrapper case_B = new StringIgnoreCaseWrapper("B", false);
		StringIgnoreCaseWrapper case_b = new StringIgnoreCaseWrapper("b", false);

		assertTrue(ignoreCase_A.equals(ignoreCase_a));

		assertTrue(ignoreCase_A.equals(case_a));

		assertFalse(ignoreCase_A.equals(ignoreCase_b));

		assertFalse(ignoreCase_A.equals(case_b));

		assertFalse(case_A.equals(case_b));

		assertFalse(case_a.equals(ignoreCase_A));

		assertFalse(case_a.equals(ignoreCase_A));

		assertFalse(case_a.equals(case_A));

		Set<StringIgnoreCaseWrapper> set = new TreeSet<StringIgnoreCaseWrapper>();
		set.add(ignoreCase_b);
		set.add(ignoreCase_B);
		assertTrue(set.size() == 1);
		set.clear();
		set.add(case_b);
		set.add(case_B);
		assertTrue(set.size() == 2);

		set.clear();
		set.add(ignoreCase_A);
		set.add(ignoreCase_B);
		assertTrue(set.size() == 2);

		set.clear();
		set.add(ignoreCase_A);
		set.add(ignoreCase_B);
		set.add(ignoreCase_b);
		set.add(ignoreCase_a);

		assertTrue(set.size() == 2);

		set.clear();
		set.add(case_A);
		set.add(case_B);
		set.add(case_b);
		set.add(case_a);

		assertTrue(set.size() == 4);
	
		set.clear();
		set.add(case_A);
		set.add(case_b);
		set.add(case_B);

		Set<StringIgnoreCaseWrapper> set2 = new TreeSet<StringIgnoreCaseWrapper>();
		set2.add(new StringIgnoreCaseWrapper("a", false)); 		
		ListOperation.A_EXCEPT_B.excecute(set, set2);
		assertTrue(set.size() == 3);

		set2.clear();
		set2.add(new StringIgnoreCaseWrapper("A", false));
		ListOperation.A_EXCEPT_B.excecute(set, set2);
		assertTrue(set.size() == 2);

	}

}
