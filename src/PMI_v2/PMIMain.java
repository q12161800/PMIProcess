package PMI_v2;

public class PMIMain {
	//System.out.println(financenews.get(1).split("美國聯邦").length-1);
	//System.out.println(Math.log(1.49) / Math.log(2)); 算log
	public static void main(String[] arg){
		PMIContorller PMIContorller = new PMIContorller();
		//主題結果路徑, 資料表名稱(三個), 欄位(DTM_Compound、DTM_Mix、DTM_Unigram)
		PMIContorller.readTopicResult("PMI//A//C","finance","life","politic","DTM_Compound");
//		double i = (Math.log(4) / Math.log(2));
//		double ii = (Math.log(9) / Math.log(2));
//		double iii = (Math.log(1) / Math.log(2));
//		double iiii = iii;
//		System.out.println(iii);
	}
}
