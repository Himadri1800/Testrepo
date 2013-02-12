/*******************************************************************************************
*Updated Date 2/12/2013
*Working Status: Working
*Auther:- Himadri
*Updating the Zip file
*
*
*
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class updatejar
{
	public static void main(String[] args)
	{
		File jarFile = new File("ekaEar\\APP-INF\\lib\\eka-AgsPhysical-service-8.0.jar");
		File[] contents = {new File("AbstractAccrualNode.class"),
		new File("C:\\Documents and Settings\\Himadri Mandal\\Desktop\\him\\sample2.txt")
		};
		try {
			updateJar(jarFile, contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void updateJar(File zipFile, File[] files) throws IOException {
		File tempFile = File.createTempFile(zipFile.getName(), null);
		System.out.println("Creating temp for " + zipFile.getName());
		String ipath="com/ekaplus/service/cost/posting/propogation/";
		tempFile.delete();
		boolean renameOk=zipFile.renameTo(tempFile);
		if (!renameOk){
			throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
		}
		byte[] buf = new byte[1024];
		ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		ZipEntry entry = zin.getNextEntry();
		while (entry != null) {
			String name = entry.getName();
			//System.out.println(name);  ----- reson creating lots of output
			boolean notInFiles = true;
			System.out.println(ipath + files[0].getName());
			if(name.contains(ipath + files[0].getName())){
				System.out.println("updating file " + files[0].getName());
				InputStream in = new FileInputStream(files[0]);
				out.putNextEntry(new ZipEntry(ipath + files[0].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				notInFiles = false;
			}
		if (notInFiles) {
			out.putNextEntry(new ZipEntry(name));
			int len;
			while ((len = zin.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		}
		entry = zin.getNextEntry();
		}
		
		out.close();
		tempFile.delete();
	}
	
};