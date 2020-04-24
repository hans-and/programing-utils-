package hasses.magical.tools.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

import hasses.magical.tools.logic.listoperation.specifics.StringIgnoreCaseWrapper;

public enum ListOperation {

	UNION(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(UNION, listA, listB);
			listB.forEach(object -> listA.add(object));
			return listA;
		}
	}), UNION_ALL(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(UNION_ALL, listA, listB);
			listB.forEach(object -> listA.add(object));
			return listA;
		}
	}), INTERSECTION(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(INTERSECTION, listA, listB);
			listA.retainAll(listB);
			return listA;
		}

	}), INTERSECTION_ALL(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(INTERSECTION_ALL, listA, listB);
			Collection<StringIgnoreCaseWrapper> result = new ArrayList<>();
			listA.forEach(object -> {
				if (listB.remove(object)) {
					result.add(object);
				}
			});
			return result;
		}

	}), A_EXCEPT_B(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(A_EXCEPT_B, listA, listB);
			listB.forEach(object -> listA.remove(object));
			return listA;
		}
	}), A_EXCEPT_B_ALL(new DoListOperation() {

		@Override
		public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
				Collection<StringIgnoreCaseWrapper> listB) {
			testArgumentType(A_EXCEPT_B_ALL, listA, listB);
			listB.forEach(object -> listA.remove(object));
			return listA;
		}

	});

	@SafeVarargs
	private static void testArgumentType(ListOperation op, Collection<StringIgnoreCaseWrapper>... args) {
		for (Collection<StringIgnoreCaseWrapper> lList : args) {
			if (operationResultIsASet(op)) {
				if (!(lList instanceof Set)) {
					throw new IllegalArgumentException(
							"at least one of the argument is not an instance of set, not allowed for operation expecting set result");
				}
			} else {
				if (lList instanceof Set) {
					throw new IllegalArgumentException(
							"at least one of the argument is an instance of set, not allowed for operation expecting a bag/list result");
				}
			}
		}
	}

	private DoListOperation excecutor;

	private ListOperation(DoListOperation pExcecutor) {
		excecutor = pExcecutor;
	}

	public Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA,
			Collection<StringIgnoreCaseWrapper> listB) {
		return excecutor.excecute(listA, listB);
	}

	/**
	 * @return returns true if the result of candidate operation is supposed to bee
	 *         a set (as opposed to a bag)
	 */
	public static boolean operationResultIsASet(ListOperation candidate) {
		return IntStream.of(UNION.ordinal(), INTERSECTION.ordinal(), A_EXCEPT_B.ordinal())
				.anyMatch(ordinal -> ordinal == candidate.ordinal());
	}

	public boolean inSetOfUnion(ListOperation candidate) {
		return IntStream.of(UNION.ordinal(), UNION_ALL.ordinal()).anyMatch(ordinal -> ordinal == candidate.ordinal());
	}

	public static boolean inSetOfIntersection(ListOperation candidate) {
		return IntStream.of(INTERSECTION.ordinal(), INTERSECTION_ALL.ordinal())
				.anyMatch(ordinal -> ordinal == candidate.ordinal());
	}

	public static boolean inSetOfExcept(ListOperation candidate) {
		return IntStream.of(A_EXCEPT_B.ordinal(), A_EXCEPT_B_ALL.ordinal())
				.anyMatch(ordinal -> ordinal == candidate.ordinal());
	}

}
