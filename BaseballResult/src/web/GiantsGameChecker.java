package web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GiantsGameChecker {
	private static String path = "https://www.nikkansports.com/baseball/professional/team/giants/";
	private static StringBuilder resultStr = new StringBuilder("");

	public static void main(String[] args) {


		try {
			// URLの読み込み
			Document document = Jsoup.connect(path).get();

			if (document.select("h5").get(0).ownText().equals("【試合開始前】")) return;

			// 必要なタグを抜き出す
			Element tableEle = document.select("table").get(0);
			Elements elements = tableEle.select(".tr");

			// チームの状況を取得 ※Tableの1行目はヘッダー情報なので飛ばす
			for (Element teamEle : elements) {
				Elements t = teamEle.select(".team");
				if (t == null || t.size() == 0) continue;

				Element team = t.get(0);
				Element resultEle = teamEle.selectFirst(".totalScore");

				resultStr.append(team.ownText() + " " + resultEle.ownText());

				if (teamEle.nextElementSibling() != null) {
					resultEle.append("\r\n");
				}
			}
			//			System.out.println(document.html());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
