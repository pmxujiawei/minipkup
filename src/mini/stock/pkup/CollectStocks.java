package mini.stock.pkup;

import java.io.IOException;

import mini.common.dao.StockInfoDao;
import mini.stock.entity.StockInfo;

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

public class CollectStocks {

	Log log = LogFactory.getLog(CollectStocks.class);

	private final StockInfoDao stockInfoDao = new StockInfoDao();

	public void collectStocks() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建httpget:http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/%s.phtml?year=%d&jidu=%d
			String url = "http://quote.eastmoney.com/stocklist.html";
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println(response.getStatusLine());

			Document doc = Jsoup.parse(EntityUtils.toString(entity, "gb2312"));
			Elements es = doc.getElementsByClass("quotebody");
			int i = 1;
			if (es.size() > 0) {
				Element content = es.first();
				if (null != content) {
					Elements lis = content.getElementsByTag("li");
					for (Element e : lis) {
						StockInfo stock = new StockInfo();
						if (e.html().toLowerCase().indexOf("sz") != -1) {
							stock.setOriginal("sz");
						} else if (e.html().toLowerCase().indexOf("sh") != -1) {
							stock.setOriginal("sh");
						}
						String ele = e.getElementsByTag("a").first().html();
						ele = ele.substring(0, ele.length() - 1);
						String[] eleArrs = ele.split("\\(");
						stock.setStockName(eleArrs[0]);
						stock.setStockId(Integer.parseInt(eleArrs[1]));
						// if (0 == stockInfoDao.updateStockNameById(stock)) {
						stockInfoDao.insert(stock);
						System.out.println(i++);
						// }
					}
				} else {
					log.error("stocks列表抓取不到:" + url);
					throw new Exception("stocks列表抓取不到!");
				}
			} else {
				log.error("stocks列表抓取不到:" + url);
				throw new Exception("stocks列表抓取不到!");
			}
		} catch (Exception e) {
			log.error("获取stocks列表出错", e);
			throw e;
		} finally {
			// 关闭连接,释放资源
			try {
				if (null != response) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				log.error("获取stocks列表出错", e);
				throw e;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new CollectStocks().collectStocks();
	}
}
