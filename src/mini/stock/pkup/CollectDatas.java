package mini.stock.pkup;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mini.common.dao.StockHistoryDao;
import mini.stock.entity.StockHistory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CollectDatas {
	Log log = LogFactory.getLog(CollectDatas.class);

	public List<StockHistory> collectionData(String stockId, int year)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<StockHistory> collectionDatas = new ArrayList<StockHistory>();
			for (int jidu = 1; jidu <= 4; jidu++) {
				// 创建httpget:http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/%s.phtml?year=%d&jidu=%d
				HttpGet httpget = new HttpGet(
						"http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/"
								+ stockId + ".phtml?year=" + year + "&jidu="
								+ jidu);
				// 执行get请求.
				response = httpclient.execute(httpget);
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				System.out.println(response.getStatusLine());

				Document doc = Jsoup.parse(EntityUtils.toString(entity,
						"gb2312"));
				Element content = doc.getElementById("FundHoldSharesTable");
				if (null != content) {
					Element tableContent = content.getElementsByTag("tbody")
							.get(0);
					Elements trs = tableContent.getElementsByTag("tr");
					int trCount = trs.size();
					for (int i = 1; i < trCount; i++) {
						Elements tds = trs.get(i).getElementsByTag("td");
						int tdCount = tds.size();
						StockHistory stock = new StockHistory();
						stock.setStockId(stockId);
						for (int j = 0; j < tdCount; j++) {
							Element element = null;
							Elements hrefs = tds.get(j).getElementsByTag("a");
							int hrefCount = hrefs.size();
							if (hrefCount > 0) {
								element = hrefs.get(0);
							} else {
								element = tds.get(j).getElementsByTag("div")
										.get(0);
							}
							switch (j) {
							case 0:
								try {
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									java.util.Date date;
									date = sdf.parse(element.text());
									stock.setBusinessDate(date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								break;
							case 1:
								stock.setOpenPrice(Double.parseDouble(element
										.text()));
								break;
							case 2:
								stock.setMaxPrice(Double.parseDouble(element
										.text()));
								break;
							case 3:
								stock.setClosePrice(Double.parseDouble(element
										.text()));
								break;
							case 4:
								stock.setMinPrice(Double.parseDouble(element
										.text()));
								break;
							case 5:
								stock.setVolume(Long.parseLong(element.text()));
								break;
							case 6:
								stock.setVolumeMoney(Long.parseLong(element
										.text()));
								break;
							default:
								break;
							}
						}
						collectionDatas.add(stock);
						// System.out.println(stock);
					}
				} else {
					break;
				}
			}
			return collectionDatas;
		} finally {
			// 关闭连接,释放资源
			try {
				if (null != response) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				throw e;
			}
		}

	}

	public static void main(String[] args) throws Exception {
		List<StockHistory> collectionData = new CollectDatas().collectionData(
				"000666", 2015);
		// new StockHistoryDao().insert(collectionData.get(0));
		new StockHistoryDao().batchInsert(collectionData);
	}
}
