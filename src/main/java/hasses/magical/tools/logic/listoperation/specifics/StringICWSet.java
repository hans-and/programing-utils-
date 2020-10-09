package hasses.magical.tools.logic.listoperation.specifics;

import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.dom4j.IllegalAddException;

public class StringICWSet extends TreeSet<StringIgnoreCaseWrapper> implements IgnoreStringCaseCollection{

	private Boolean ignoreCase = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(StringIgnoreCaseWrapper item) {
		if (ignoreCase == null) {
			ignoreCase = Boolean.valueOf(item.isIgnoreCase());
		} else {
			if (ignoreCase.booleanValue() != item.isIgnoreCase()) {
				throw new IllegalAddException("Illigal attemt to mix Items that ignore case with that dont.");
			}
		}
		return super.add(item);
	}

	@Override
	public boolean addAll(Collection<? extends StringIgnoreCaseWrapper> items) {
		AtomicBoolean result = new AtomicBoolean(true);

		items.forEach(item -> {
			if (!add(item)) {
				result.set(false);
			}
		});
		return result.get();
	}

}
