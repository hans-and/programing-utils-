package hasses.magical.tools.logic.listoperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import hasses.magical.helpers.StringHelper;
import hasses.magical.tools.dto.ListOperation;
import hasses.magical.tools.dto.ListOperationResultDTO;
import hasses.magical.tools.dto.ListOperationsDTO;
import hasses.magical.tools.logic.listoperation.specifics.StringICWSet;
import hasses.magical.tools.logic.listoperation.specifics.StringIgnoreCaseWrapper;

public class ListOperatorHandler {

	public static ListOperatorHandler getInstance() {
		if (lopHandler == null) {
			lopHandler = new ListOperatorHandler();
			return lopHandler;
		} else {
			return lopHandler;
		}
	}

	public ListOperationResultDTO getResult(ListOperationsDTO source) {
		ListOperationResultDTO result = new ListOperationResultDTO();
		
		Collection<StringIgnoreCaseWrapper> listA;
		Collection<StringIgnoreCaseWrapper> listB;
		
		if (ListOperation.operationResultIsASet(ListOperation.valueOf(source.getOperation()))) {
			listA = getAsSet(source.getListA(), source.getTrim(), source.isIgnoreCase());
			listB = getAsSet(source.getListB(), source.getTrim(), source.isIgnoreCase());
		} else {
			listA = getAsList(source.getListA(), source.getTrim(), source.isIgnoreCase());
			listB = getAsList(source.getListB(), source.getTrim(), source.isIgnoreCase());

		}
		listA = ListOperation.valueOf(source.getOperation()).excecute(listA, listB);
		if (source.getSort()) {
			listA = sort(listA);
		}

		StringBuilder sb = new StringBuilder();
		listA.forEach(item -> sb.append(item+"\n"));
		result.setResult(sb.toString());

		return result;
	}

	private static ListOperatorHandler lopHandler = null;

	private ListOperatorHandler() {

	}


	private  Collection<StringIgnoreCaseWrapper> sort(Collection<StringIgnoreCaseWrapper> listA) {
	    ArrayList<StringIgnoreCaseWrapper> result = new ArrayList<StringIgnoreCaseWrapper>();
	    result.addAll(listA);
        Collections.sort(result);
		return result;
	}

	
	private Collection<StringIgnoreCaseWrapper> getAsSet(String source, boolean trim, boolean pIgnoreCase) {
		StringICWSet result = new StringICWSet();
		for (String item : StringHelper.splitInRows(source)) {
			result.add(new StringIgnoreCaseWrapper(trimIf(item, trim), pIgnoreCase));
		}
		return result;
	}

	private Collection<StringIgnoreCaseWrapper> getAsList(String source, boolean trim, boolean pIgnoreCase) {

		List<StringIgnoreCaseWrapper> result = new ArrayList<>();
		for (String item : StringHelper.splitInRows(source)) {
			result.add(new StringIgnoreCaseWrapper(trimIf(item, trim), pIgnoreCase));
		}
		return result;
	}

	

	private String trimIf(String item, boolean trim) {
		if (trim) {
			return item.trim();
		}
		return item;
	}

	
}
