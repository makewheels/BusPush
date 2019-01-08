package leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

public class LeanCloud {

	static {
		AVOSCloud.initialize("Uwlix6itd8gPfgjpe7xBY0JJ-gzGzoHsz", "S0wVbxSAkst3zHEMAtkxxc04",
				"iUfQ99tiKt3UtFzlzohLuLnE");
	}

	/**
	 * 根据日期和时间查推送日志
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static AVObject getPushLogByDateAndTime(String date, String time) {
		AVQuery<AVObject> query = new AVQuery<>("pushLog");
		query.whereEqualTo("date", date);
		query.whereEqualTo("time", time);
		AVObject pushLog = null;
		try {
			pushLog = query.getFirst();
		} catch (AVException e) {
			e.printStackTrace();
		}
		return pushLog;
	}

	/**
	 * 保存推送记录
	 * 
	 * @param table
	 * @param busName
	 * @param direction
	 * @param startStation
	 * @param endStation
	 * @param date
	 * @param time
	 * @param to
	 */
	public static void savePushLog(String table, String busName, String direction, String startStation,
			String endStation, String date, String time, String to, boolean isPushed) {
		AVObject obj = new AVObject(table);
		obj.put("busName", busName);
		obj.put("direction", direction);
		obj.put("startStation", startStation);
		obj.put("endStation", endStation);
		obj.put("date", date);
		obj.put("time", time);
		obj.put("to", to);
		obj.put("isPushed", isPushed);
		obj.put("executor", "aliyunCloudFunction");
		obj.put("timestamp", System.currentTimeMillis() + "");
		try {
			obj.save();
		} catch (AVException e) {
			e.printStackTrace();
		}
	}

}
