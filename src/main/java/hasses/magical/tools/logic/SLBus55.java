package hasses.magical.tools.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.validator.internal.util.StringHelper;

import hasses.magical.helpers.DateHelper;
import hasses.magical.helpers.XMLHelper;
import hasses.magical.tools.dto.BusResultDTO;
import hasses.magical.tools.dto.ListOperationResultDTO;

import static hasses.magical.helpers.HTTPHelper.*;
import static hasses.magical.helpers.DateHelper.*;

public class SLBus55 {

	private static final Logger LOGGER = Logger.getLogger(SLBus55.class);

	private static String SL_BUS_55_REQUEST = "https://api.resrobot.se/v2/departureBoard?key=f3423994-0197-4952-9078-005de02a6db2&id=740046010&maxJourneys=4&products=128";

	private Date lastTimeOfUpdate = null;

	private List<Date> departureDates;

	private Consumer<Element> onDepartureElement(List<Date> dates) {

		return departure -> {
			try {
				dates.add(DateHelper.parseSLDate(getDateString(departure)));
			} catch (ParseException e) {

			}
		};
	}

	private String getDateString(Element departure) {
		String d = departure.attributeValue("rtDate");
		String t = departure.attributeValue("rtTime");
		if (StringHelper.isNullOrEmptyString(d) || StringHelper.isNullOrEmptyString(t)) {
			d = departure.attributeValue("date");
			t = departure.attributeValue("time");
		}
		return d + " " + t;

	}

	public BusResultDTO getMinutesToDeparture() {
		if (!currentHourIsBetween(1, 5)) {
			if (lastTimeOfUpdate == null || moreThan4MinSince(lastTimeOfUpdate)) {
				updateFromSL();
			}
		}

		Date now = new Date();
		List<String> minuteItems = new ArrayList<>();
		departureDates.forEach(date -> addIfNotPast(date, minuteItems, now));
		LOGGER.debug("getMinutesToDeparture invoked returns " + minuteItems);
		
		BusResultDTO res = new BusResultDTO(); 
		res.setAvg1(getSafe(minuteItems,0));
		res.setAvg2(getSafe(minuteItems,1));
		res.setAvg3(getSafe(minuteItems,2));
		return res;
	}

	private String getSafe(List<String> minuteItems, int i) {
		
		return  (minuteItems.size()>i)?minuteItems.get(i):"N/A";
		
	}

	private void addIfNotPast(Date departureDate, List<String> minuteItems, Date now) {
		if (now.before(departureDate)) {
			minuteItems.add(
					divModIfNeded(DateHelper.millisecondToFloorMin(DateHelper.millisecondBetween(now, departureDate))));
		}
	}

	private String divModIfNeded(long millisecondToFloorMin) {
		if (millisecondToFloorMin > 59) {
			long h = millisecondToFloorMin / 60;
			long m = millisecondToFloorMin % 60;
			return h == 1 ? h + " Timme och " + m + " Minuter" : h + " Timmar och " + m + " Minuter";
		}
		return  millisecondToFloorMin+" Minuter";
	}

	private void updateFromSL() {
		try {
			LOGGER.debug("UpdateFromSL invoked");
			Optional<Document> res = getSimpleRequestAsDocument(SL_BUS_55_REQUEST);
			if (res.isPresent()) {
				List<Date> dates = new ArrayList<>();
				XMLHelper.onEachXpathElementSelect(res.get(), "m:DepartureBoard/m:Departure", onDepartureElement(dates),
						"m=hafas_rest_v1");
				departureDates = dates;
			}
		} catch (Exception e) {
		}
	}

}
