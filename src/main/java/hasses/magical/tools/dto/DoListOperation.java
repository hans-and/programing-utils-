package hasses.magical.tools.dto;

import java.util.Collection;

import hasses.magical.tools.logic.listoperation.specifics.StringIgnoreCaseWrapper;

public interface DoListOperation {

	Collection<StringIgnoreCaseWrapper> excecute(Collection<StringIgnoreCaseWrapper> listA, Collection<StringIgnoreCaseWrapper> listB);

	
}
