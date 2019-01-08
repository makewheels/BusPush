package run;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.avos.avoscloud.AVObject;

import bean.allbusposition.AllBusPosition;
import bean.allbusposition.BusList;
import bean.stationlist.StationList;
import leancloud.LeanCloud;
import util.AliyunMailUtil;
import util.QcloudSmsUtil;
import util.WebUtil;

public class Start implements StreamRequestHandler {

	/**
	 * 公交到某站时自动推送
	 */
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		// 开始时间
		String startTimeString = "11:05";
		// 收信人
		String to = "17745293025";
		// 公交名
		String busName = "50";
		// 公交方向
		String direction = "1";
		// 起始站点
		int startStation = 12;
		// 结束站点
		int endStation = 13;

		// 验证时间
		Calendar startTime = Calendar.getInstance();
		String[] split = startTimeString.split(":");
		startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
		startTime.set(Calendar.MINUTE, Integer.parseInt(split[1]));
		Calendar currentTime = Calendar.getInstance();
		if (currentTime.before(startTime)) {
			return;
		}
		System.out.println("时间验证通过");
		String currentDateString = currentTime.get(Calendar.YEAR) + "-" + (currentTime.get(Calendar.MONTH) + 1) + "-"
				+ currentTime.get(Calendar.DAY_OF_MONTH);
		// 验证是否已经推送过了
		AVObject pushLog = LeanCloud.getPushLogByDateAndTime(currentDateString, startTimeString);
		if (pushLog != null && pushLog.getBoolean("isPushed")) {
			return;
		}
		System.out.println("leancloud重复性验证通过");
		// 验证公交位置
		List<BusList> busList = JSON.parseObject(WebUtil.sendGet(
				"http://busmishu.com:8080/BusComeServer/pages/index/IndexAction.do?action=mapxxx&bus_direction="
						+ direction + "&bus_name=" + busName),
				AllBusPosition.class).getBusList();
		for (BusList bus : busList) {
			System.out.println(bus);
			double busPosition = Double.parseDouble(bus.getDz());
			// 判断是否在指定站点区间内
			if (((int) busPosition >= startStation) && ((int) busPosition <= endStation)) {
				System.out.println("公交位置验证通过：busPosition=" + busPosition + ", startStation=" + startStation
						+ ", endStation=" + endStation);
				// 站点id转化为站点名
				String getStationListUrl = "http://restapi.amap.com/v3/bus/linename?s=rsv3&key=3be642e30fc349691f5fb9de0a4785a0&extensions=all&city=%E5%A4%A7%E5%BA%86&keywords="
						+ busName;
				// 站点名
				String indexString = "0";
				if (direction.equals("0")) {
					indexString = "1";
				} else if (direction.equals("1")) {
					indexString = "0";
				}
				String stationName = JSON.parseObject(WebUtil.sendGet(getStationListUrl), StationList.class)
						.getBuslines().get(Integer.parseInt(indexString)).getBusstops().get((int) busPosition - 1)
						.getName();
				// 执行推送
				int tmplId = 243975;
				ArrayList<String> params = new ArrayList<String>();
				params.add(busName);
				params.add(busPosition + "" + stationName);
				QcloudSmsUtil.sendSms(to, tmplId, params);
				// 保存推送记录到LeanCloud
				LeanCloud.savePushLog("pushLog", busName, direction, startStation + "", endStation + "",
						currentDateString, startTimeString, to, true);
				// 再发邮件玩玩
				String mailSubject = busName + "已到：" + stationName;
				String mailContent = "我简直就屌爆了，我就问一下，还有SEI？<br><br><font color=\"red\"><b>" + stationName
						+ "</b></font><br><br>" + "busPosition：" + busPosition + "<br>当前timestamp："
						+ System.currentTimeMillis() + "<br>设定日期和时间：" + currentDateString + "&nbsp;" + startTimeString
						+ "&nbsp;<br>公交名：" + busName + "<br>方向：" + direction + "<br>startStation：" + startStation
						+ "<br>endStation：" + endStation;
				AliyunMailUtil.send("finalbird@foxmail.com", "已执行通知：" + UUID.randomUUID().toString(),
						System.currentTimeMillis() + "");
				AliyunMailUtil.send("finalbird@foxmail.com", mailSubject, mailContent);
				AliyunMailUtil.send("tongbuwangpan@163.com", mailSubject, mailContent);
				AliyunMailUtil.send("174582555@qq.com", mailSubject, mailContent);
				break;
			}
		}
	}

}
