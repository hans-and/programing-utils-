package hasses.magical.tools.logic;

import java.math.BigInteger;
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
import hasses.magical.tools.model.SlApiData;
import hasses.magical.tools.repository.SlApiDataRepository;

import static hasses.magical.helpers.HTTPHelper.*;
import static hasses.magical.helpers.DateHelper.*;

public class SLBus55 {

	private static final Logger LOGGER = Logger.getLogger(SLBus55.class);

	private static final long ID_BUSDATA = 3;

	private static String SL_BUS_55_REQUEST = "https://api.resrobot.se/v2/departureBoard?key=f3423994-0197-4952-9078-005de02a6db2&id=740046010&maxJourneys=4&products=128";

	private Date lastCallToSL_API = null;

	private List<Date> departureDates = new ArrayList<Date>();

	private SlApiDataRepository repository;

	public SLBus55(SlApiDataRepository slData) {
		repository = slData;
		SlApiData peristedData = repository.getOne(ID_BUSDATA);
		lastCallToSL_API = peristedData.getLastSLApiCall();
	}

	private Consumer<Element> onDepartureElement(List<Date> dates) {
		return departure -> {
			try {
				dates.add(DateHelper.parseSLDate(getDateString(departure)));
			} catch (ParseException e) {
				LOGGER.error("Unexpected SLBuss55", e);
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

		updateFromSL_WhenNeeded();
		Date now = new Date();
		List<String> minuteItems = new ArrayList<>();
		departureDates.forEach(date -> addIfNotPast(date, minuteItems, now));
		LOGGER.info("getMinutesToDeparture invoked returns " + minuteItems);

		BusResultDTO res = new BusResultDTO();
		res.setAvg1(getSafe(minuteItems, 0));
		res.setAvg2(getSafe(minuteItems, 1));
		res.setAvg3(getSafe(minuteItems, 2));
		return res;
	}

	private synchronized void updateFromSL_WhenNeeded() {

		if (theBusHasDeparturesAtThisTime()) {
			LOGGER.debug("the Bus Has Departures At This Time");
			if (moreThan5MinSince(lastCallToSL_API)) {
				LOGGER.debug("there been more than five minutes since we asked SL");
				updateLastCallToSL_AndPersist();
				updateFromSL();
			}
		}
	}

	private void updateLastCallToSL_AndPersist() {
		Date beforeUdate = lastCallToSL_API;
		lastCallToSL_API = new Date();
		SlApiData peristedData = repository.getOne(ID_BUSDATA);
		peristedData.setLastSLApiCall(lastCallToSL_API);
		incrementTotalCalls(peristedData);
		incrementMonthlyCalls(beforeUdate, peristedData);
		repository.saveAndFlush(peristedData);
	}

	private void incrementTotalCalls(SlApiData peristedData) {
		peristedData.setTotalNoOfCalls(peristedData.getTotalNoOfCalls().add(new BigInteger("1")));
	}

	private void incrementMonthlyCalls(Date beforeUdate, SlApiData peristedData) {
		if (monthDiffer(beforeUdate, lastCallToSL_API)) {
			peristedData.setNumberOfCallsThisMonth(new BigInteger("1"));
		} else {
			peristedData.setNumberOfCallsThisMonth(peristedData.getNumberOfCallsThisMonth().add(new BigInteger("1")));
		}
	}

	private boolean theBusHasDeparturesAtThisTime() {
		return !currentHourIsBetween(1, 5);
	}

	private String getSafe(List<String> minuteItems, int i) {

		return (minuteItems.size() > i) ? minuteItems.get(i) : "N/A";

	}

	private void addIfNotPast(Date departureDate, List<String> minuteItems, Date now) {
		if (now.before(departureDate)) {
			minuteItems.add(toUserFriendlyMinuteString(
					DateHelper.millisecondToFloorMin(DateHelper.millisecondBetween(now, departureDate))));
		}
	}

	private String toUserFriendlyMinuteString(long minutes) {
		if (minutes == 0) {
			return "Nu";
		} else if (minutes > 59) {
			long h = minutes / 60;
			long m = minutes % 60;
			return h == 1 ? h + " Timme och " + m + " Minuter" : h + " Timmar och " + m + " Minuter";
		}
		return minutes + " Minuter";
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

			LOGGER.error("Something went wrong calling SL", e);
			// ToDo update from cache
		}
	}

}
