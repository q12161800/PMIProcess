package PMI_v2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FileMgr {
	
	public HashMap<String, ArrayList<String>> ReadPaper(String foldername, String msg) throws Exception {
		System.out.println(msg);
		//int i = 0;
		HashMap<String, ArrayList<String>> PaperListMap = new HashMap();
		for (File f : new File(foldername).listFiles()) {
			//i++;
			String filename = SplitFileName(f.toString(), foldername);
			String context = "";
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			ArrayList<String> trim = new ArrayList<String>();
			while (br.ready()) {
				trim.add(br.readLine().trim());	
			}
			//System.out.println("Filename: "+filename+", Context: "+trim);
			PaperListMap.put(filename, trim);
		}
		//System.out.println(i);
		return PaperListMap;
	}
	
	public void WriteFile(String foldername, String papername, String context) throws IOException {
		BufferedWriter fw = null;
		File f = new File(foldername + "\\"+ papername +"");
		fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,false), "utf-8"));
		fw.write(context);
		fw.flush();
		fw.close();
	}
	
	public String SplitFileName(String file, String split) {
		
		//因為路徑的關係，不得不取代4條
		String[] File = file.split("\\\\");
		//根據資料夾路徑，設定在第幾層給予數字
		File[3] = File[3].replace("\\", "");

		return File[3].trim();

	}

}
