import java.net.URL;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class jarSelfExtrator {
String jarPatchFile;

	public static void main(String[] args){
		jarSelfExtrator obj = new jarSelfExtrator();
		String patchJar = obj.getJarFileName();
		System.out.println(patchJar); //must comment 
		obj.extractPatchXML();
	}
/*Function getJarFileName will return String that will contain the full path of the running jar file
* Created by Himadri Mandal
* date 2/13/2013
*/
	private String getJarFileName()
    {
		jarPatchFile = this.getClass().getName() + ".class";
      URL urlJar = this.getClass().getClassLoader().getSystemResource(jarPatchFile);
      String urlStr = urlJar.toString();
      int from = "jar:file:".length();
      int to = urlStr.indexOf("!/");
      return urlStr.substring(from, to);
    }
// Function getJarFileName Ends
	
/* Function to extract the patch.xml and current directory
* Created by Himadri Mandal
* date 2/14/2013
*/
		private void extractPatchXML(){
		try{
		OutputStream out = new FileOutputStream("patch.xml");
		FileInputStream fin = new FileInputStream(jarPatchFile);
		BufferedInputStream bin = new BufferedInputStream(fin);
		ZipInputStream zin = new ZipInputStream(bin);
		ZipEntry ze = null;
		while ((ze = zin.getNextEntry()) != null) {
		    if (ze.getName().equals("your.file")) {
		        byte[] buffer = new byte[8192];
		        int len;
		        while ((len = zin.read(buffer)) != -1) {
		            out.write(buffer, 0, len);
		        }
		        out.close();
		        break;
		    }
		}
		} catch(IOException e ){
			System.out.println("IO error");
		}
	}

}
