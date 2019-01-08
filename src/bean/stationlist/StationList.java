/**
  * Copyright 2018 bejson.com 
  */
package bean.stationlist;

import java.util.List;

/**
 * Auto-generated: 2018-12-09 19:31:24
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class StationList {

	private String status;
	private String info;
	private String infocode;
	private String count;
	private Suggestion suggestion;
	private List<Buslines> buslines;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}

	public String getInfocode() {
		return infocode;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return count;
	}

	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	public void setBuslines(List<Buslines> buslines) {
		this.buslines = buslines;
	}

	public List<Buslines> getBuslines() {
		return buslines;
	}

	@Override
	public String toString() {
		return "StationList [status=" + status + ", info=" + info + ", infocode=" + infocode + ", count=" + count
				+ ", suggestion=" + suggestion + ", buslines=" + buslines + "]";
	}

}