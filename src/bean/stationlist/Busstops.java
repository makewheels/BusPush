/**
  * Copyright 2018 bejson.com 
  */
package bean.stationlist;

/**
 * Auto-generated: 2018-12-09 19:31:24
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Busstops {

	private String id;
	private String location;
	private String name;
	private String sequence;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}

	@Override
	public String toString() {
		return "Busstops [id=" + id + ", location=" + location + ", name=" + name + ", sequence=" + sequence + "]";
	}

}