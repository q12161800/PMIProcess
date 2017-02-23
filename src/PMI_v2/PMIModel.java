package PMI_v2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class PMIModel {
	
	HashMap<Integer, HashMap<String, Integer>> AllDocument;
	
	public PMIModel(HashMap<Integer, HashMap<String, Integer>> alldocument){
		this.AllDocument = alldocument;
	}
	
	public double calculatePMI(ArrayList<String> topictermlist){
		double singletopicpmi = 0;
		double singlewordpmi =0;
		//PMI公式的兩個Σ
		for(int i=0; i<topictermlist.size(); i++){
			for(int j=i+1; j<topictermlist.size(); j++){
				//尋訪每一篇文章
				for(Integer alldocumentkey : AllDocument.keySet()){
					HashMap<String, Integer> singledocument = AllDocument.get(alldocumentkey);
					//尋訪文章中的字詞
						String wi = topictermlist.get(i);
						String wj = topictermlist.get(j);
						//如果該文章中有wi與wj
						if(singledocument.containsKey(wi) && singledocument.containsKey(wj)){
							//統計該文章中的所有字詞頻率
							double totalfreq = 0;
							//.forEach((k, v) -> {v});
							for(String keyvalue : singledocument.keySet()){
								totalfreq += singledocument.get(keyvalue);
							}
							//以下為PMI(wi , wj)function
							double pwi = (singledocument.get(wi) / totalfreq);
							double pwj = (singledocument.get(wj) / totalfreq);
							//Math.min(singledocument.get(wi), singledocument.get(wj))
							//取最小的
							double pwij = (Math.min(singledocument.get(wi),singledocument.get(wj))/ totalfreq);
							double result = (pwij/(pwi*pwj));
							//取log2
							singlewordpmi += (Math.log(result) / Math.log(2));
						}
						//該文件沒有wi或wj，結果為log2(1)=0
						else {
							singlewordpmi += 0;
						}
					}
				singletopicpmi += singlewordpmi / AllDocument.size();
				singlewordpmi = 0;
				}
			}
		double mean = ((double)2/((topictermlist.size())*(topictermlist.size()-1)));
		singletopicpmi = mean*singletopicpmi;
		//System.out.println("test1 " + singletopicpmi);
		DecimalFormat m = new DecimalFormat("#.##########");
		//System.out.println("test2 " + m.format(singletopicpmi));
		return singletopicpmi;
	}
	
	

}

