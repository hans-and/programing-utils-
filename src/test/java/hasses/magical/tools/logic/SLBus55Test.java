package hasses.magical.tools.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.validator.internal.util.StringHelper;
import org.junit.jupiter.api.Test;

import hasses.magical.helpers.DateHelper;
import hasses.magical.helpers.FileHelper;
import hasses.magical.helpers.XMLHelper;

class SLBus55Test {

	@Test
	void test() throws DocumentException, IOException {
		List<String> dates = new ArrayList<>();
		Document doc = DocumentHelper.parseText(FileHelper.getResourceAsString("TestSL.xml"));
		
		XMLHelper.onEachXpathElementSelect(doc, "m:DepartureBoard/m:Departure",element->onElement(element,dates) , "m=hafas_rest_v1");
		
		
		assertTrue(dates.size() > 2);
		List<Date> datedates = new ArrayList<>();
		dates.forEach(dateStr -> {
			try {
				datedates.add(DateHelper.parseSLDate(dateStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});

		assertTrue(datedates.size() > 3);
		
	}
	
	@Test
	void test2() throws DocumentException, IOException {
		SLBus55 b55 = new SLBus55();
		String s = ""+b55.getMinutesToDeparture();
		assertTrue(s!=null);
	}

	private Object onElement(Element element, List<String> dates) {
		String d = element.attributeValue("rtDate");
		String t = element.attributeValue("rtTime");
		if(StringHelper.isNullOrEmptyString(d)||StringHelper.isNullOrEmptyString(t)) {
			d = element.attributeValue("date");
			t = element.attributeValue("time");
		}
		dates.add(d+" "+t);
		return element;
	}



}
