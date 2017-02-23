package PMI_v2;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PMIContorller {

	FileMgr FileMgr = new FileMgr();
	MySQL MySQL = new MySQL();

	public HashMap<Integer, HashMap<String, Integer>> getcontent(String table1, String table2, String table3,
			String cloumename) {
		ArrayList<String> news = null;
		HashMap<Integer, HashMap<String, Integer>> alldocument = null;
		try {
			// connected db
			news = MySQL.getNews(table1, table2, table3, cloumename);
			// get all news result, then transfer to json and put it in map
			alldocument = new HashMap<Integer, HashMap<String, Integer>>();
			// for each all the document and create one JSONArray instance
			for (int index = 0, size = news.size(); index < size; index++) {
				JSONArray jsontermlist = new JSONArray(news.get(index));
				HashMap<String, Integer> onedocument = new HashMap<String, Integer>();
				// for each JSONArray then put key and value inside map
				for (int i = 0, len = jsontermlist.length(); i < len; i++) {
					JSONObject termobject = jsontermlist.getJSONObject(i);
					String key = termobject.getString("term");
					Integer value = termobject.getInt("orgTf");
					onedocument.put(key, value);
					// System.out.println(key+" "+ value);
				}
				// put single document result in map
				alldocument.put(index, onedocument);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alldocument;
	}

	public void readTopicResult(String topiresultcpath, String table1, String table2, String table3,
			String cloumename) {
		try {
			HashMap<String, ArrayList<String>> topicmap = new HashMap<String, ArrayList<String>>();
			HashMap<Integer, HashMap<String, Integer>> alldocument = this.getcontent(table1, table2, table3,
					cloumename);
			PMIModel pmimodel = new PMIModel(alldocument);

			// read all topic result

			topicmap = FileMgr.ReadPaper(topiresultcpath, "Yooooooooooo");
			// 把機率剔除，只保留字詞，並比對文章
			for (String key : topicmap.keySet()) {
				ArrayList<String> t = topicmap.get(key);
				String[] a = null;
				double totalpmi = 0;

				for (int p = 0, size = t.size(); p < size; p++) {
					double singletopicpmi = 0;
					// System.out.println(key+" "+p +" "+ t.get(p));
					a = t.get(p).split("	");
					ArrayList<String> termlist = new ArrayList<String>();
					for (int l = 0, len = a.length; l < len; l++) {
						String b = a[l].substring(0, a[l].indexOf(" "));
						termlist.add(b);
					}

					// 開始比對字串，計算PMI
					// System.out.println("topic-"+p+" "+termlist);
					totalpmi += pmimodel.calculatePMI(termlist);
				}
				System.out.println(key + " : " + (double) (totalpmi / t.size()));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
