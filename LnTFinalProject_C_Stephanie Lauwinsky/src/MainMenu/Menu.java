package MainMenu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Menu {

	public static void main(String[] args) throws IOException {
		Scanner terminalInput = new Scanner(System.in);
		String menu;
		boolean ulang = true;
		
		while(ulang) {
		System.out.println("List Menu PT Pudding\n");
		System.out.println("1. Insert Menu Baru");
		System.out.println("2. View Menu");
		System.out.println("3. Update Menu");
		System.out.println("4. Delete Menu");
		
		System.out.print("Select Menu: ");
		menu = terminalInput.next();
			
		
//		int menu = scan.nextInt();
//		System.out.print("Select Menu:");
		
		switch(menu) {
		case "1":
			System.out.println("======================");
			System.out.println("Please Insert New Menu");
			System.out.println("======================");
			insertData();
			tampilkanData();
			break;
		case "2":
			System.out.println("\n\n======================");
			System.out.println("List All Menu");
			System.out.println("======================");
			tampilkanData();
			break;
		case "3":
			System.out.println("\n\n======================");
			System.out.println("Update The Menu");
			System.out.println("======================");
			updateData();
			break;
		case "4":
			System.out.println("\n\n======================");
			System.out.println("Delete Menu");
			System.out.println("======================");
			deleteData();
			break;
		default:
			System.err.println("\nPlease Input Number between 1-4!");

		}
		
		ulang = YesorNo("\nDo You Want to Continue? ");
	
	}

}
	
	
	
	public static void insertData() throws IOException{
		FileWriter fileOutput = new FileWriter("database", true);
		BufferedWriter bufferOutput = new BufferedWriter(fileOutput);
		
		Scanner terminalInput = new Scanner(System.in);
		String nama, kode, harga, stok;
//		Double kode, harga, stok;
		
		System.out.print("Enter Menu Code: ");
		kode = terminalInput.nextLine();
		System.out.print("Enter Menu Name: ");
		nama = terminalInput.nextLine();
		System.out.print("Enter Menu Price: ");
		harga = terminalInput.nextLine();
		System.out.print("Enter Menu Stock: ");
		stok = terminalInput.nextLine();
		
		String[] keywords = {kode+","+nama+","+harga+","+stok};
		System.out.println(Arrays.toString(keywords));
		
		bufferOutput.close();
		fileOutput.close();
		
	}
	
	public static boolean YesorNo(String message) {
		Scanner terminalInput = new Scanner(System.in);
		System.out.print("\n"+ message+" (y/n)?");
		String menu = terminalInput.next();
		
		while(!menu.equalsIgnoreCase("y") && !menu.equalsIgnoreCase("n")) {
			System.err.println("Please choose y or n!");
			System.out.print("\n"+ message+" (y/n)?");
			menu = terminalInput.next();
		}
		return menu.equalsIgnoreCase("y");
	}
	
	public static void tampilkanData() throws IOException{
		FileReader fileInput;
		BufferedReader bufferInput;
		
		try {
			fileInput = new FileReader("database");
			bufferInput = new BufferedReader(fileInput);
		}catch(Exception e) {
			System.err.println("Database Not Found");
			System.err.println("Please Add Data First!");
			insertData();
			return;
			
		}
		
		String data = bufferInput.readLine();
		int nomorData = 0;
		
		while(data != null) {
			nomorData++;
			StringTokenizer stringToken = new StringTokenizer(data, ",");
			System.out.println("\n| No |\tKode Menu |\tNama Menu    |\tHarga   |\tStok ");
			System.out.println("---------------------------------------------------------------");
			stringToken.nextToken();
			System.out.printf("| %2d ",1);
			System.out.printf("|\t%s    ",stringToken.nextToken());
			System.out.printf("|\t%s   ",stringToken.nextToken());
			System.out.printf("|\t%s   ",stringToken.nextToken());
			System.out.printf("|\t%s ",stringToken.nextToken());
			System.out.print("\n");
			
			data = bufferInput.readLine();
			bufferInput.close();
			fileInput.close();
			
		}
		
		
	}
	
	public static void deleteData() throws IOException{
		//ngambil database asli
		File database = new File("database");
		FileReader fileInput = new FileReader(database);
		BufferedReader bufferedInput = new BufferedReader(fileInput);
		
		//database setelah delete suatu data
		File newFile = new File("databasebaru");
		FileWriter fileOutput = new FileWriter(newFile);
		BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);
		
		//nampilin data
		System.out.println("List Menu");
		tampilkanData();
		
		Scanner terminalInput = new Scanner(System.in);
		System.out.print("\nMasukkan nomor menu yang akan dihapus: ");
		int deleteNomor = terminalInput.nextInt();
		
		boolean isFound = false;
		int dataCounts = 0;
		
		String data = bufferedInput.readLine();
		
		while(data != null) {
			dataCounts++;
			boolean isDelete = false;
			
			StringTokenizer st = new StringTokenizer(data, ",");
			if(deleteNomor == dataCounts) {
				System.out.println("\nMenu yang ingin dihapus: ");
				System.out.println("-------------------------");
				System.out.println("Kode Menu           : " + st.nextToken());
				System.out.println("Nama Menu           : " + st.nextToken());
				System.out.println("Harga               : " + st.nextToken());
				System.out.println("Stok              : " + st.nextToken());
				isDelete = YesorNo("Apakah anda yakin ingin menghapus? ");
				isFound = true;
				
			}
			if(isDelete) {
				System.out.println("Menu berhasil dihapus!");
				
			}else {
				bufferedOutput.write(data);
				bufferedOutput.newLine(); 
			}
			data = bufferedInput.readLine();
		}
		if(!isFound) {
			System.err.println("Menu tidak ditemukan!");
		}
		
		bufferedOutput.flush();
		database.delete();
		newFile.renameTo(database);
	}
	
	public static boolean cekMenuDiDatabase(String[] keywords, boolean isDisplay) throws IOException{
		
		 FileReader fileInput = new FileReader("database");
	     BufferedReader bufferInput = new BufferedReader(fileInput);

	     String data = bufferInput.readLine();
	     boolean isExist = false;
	     int nomorData = 0;
	     
	     System.out.println("\n| No |\tKode Menu |\tNama Menu    |\tHarga   |\tStok ");
		 System.out.println("---------------------------------------------------------------");
		 
		 while(data != null){

	            //cek keywords didalam baris
	            isExist = true;

	            for(String keyword:keywords){
	                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
	            }

	            //jika keywordsnya cocok maka tampilkan

	            if(isExist){
	                if(isDisplay) {
	                	nomorData++;
		                StringTokenizer stringToken = new StringTokenizer(data, ",");

		                stringToken.nextToken();
		                System.out.printf("| %2d ",1);
		    			System.out.printf("|\t%s    ",stringToken.nextToken());
		    			System.out.printf("|\t%s   ",stringToken.nextToken());
		    			System.out.printf("|\t%s   ",stringToken.nextToken());
		    			System.out.printf("|\t%s ",stringToken.nextToken());
		    			System.out.print("\n");
	                }else {
	                	break;
	                }
	            }

	            data = bufferInput.readLine();
	        }
		 
		 	if(isDisplay) {
	        System.out.println("----------------------------------------------------------------------------------------------------------");
		 	}
		 	
		 	return isExist;
	
	}     
	
	
	public static void updateData() throws IOException{
		File database = new File("database");
		FileReader fileInput = new FileReader(database);
		BufferedReader bufferedInput = new BufferedReader(fileInput);
		
		File newFile = new File("databasebaru");
		FileWriter fileOutput = new FileWriter(newFile);
		BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);
		
		System.out.println("List Menu");
		tampilkanData();
		
		//pilihan user
		Scanner terminalInput = new Scanner(System.in);
		System.out.print("\nEnter the menu number that you want to update: ");
		int updateNum = terminalInput.nextInt();
		
		//menampilkan data yang ingin diupdate
		String data = bufferedInput.readLine();
		int dataCounts = 0;
		while(data != null) {
			dataCounts++;
			StringTokenizer st = new StringTokenizer(data, ",");
			
			if(updateNum == dataCounts) {
				System.out.println("\nMenu you want to update: ");
				System.out.println("---------------------------");
				System.out.println("Kode Menu		: " + st.nextToken());
				System.out.println("Nama Menu		: " + st.nextToken());
				System.out.println("Harga			: " + st.nextToken());
				System.out.println("Stok          : " + st.nextToken());
				
				
				
				String[] fieldData = {"kode menu","nama menu","harga","stok"};
				String[] newFile1 = new String[4];
				
				//refresh token
				st = new StringTokenizer(data, ",");
				String originalData = st.nextToken();
				
				for(int i=0; i<fieldData.length; i++) {
					boolean isUpdate = YesorNo("Do you want to change the name of the menu" + fieldData);
					
					originalData = st.nextToken();
					if(isUpdate) {
						terminalInput = new Scanner(System.in);
						System.out.print("\nEnter " + fieldData[i] + " baru: ");
						newFile1[i] = terminalInput.nextLine();
					}else {
						newFile1[i] = originalData;
						
					}
				}
				
				//nampilin data baru ke layar user
				st = new StringTokenizer(data, ",");
				st.nextToken();
				System.out.println("\nUpdated menu data: ");
				System.out.println("---------------------------");
				System.out.println("Kode Menu		: " + st.nextToken() + "-->" + newFile1[0]);
				System.out.println("Nama Menu		: " + st.nextToken() + "-->" + newFile1[1]);
				System.out.println("Harga			: " + st.nextToken() + "-->" + newFile1[2]);
				System.out.println("Stok          : " + st.nextToken() + "-->" + newFile1[3]);
				
				boolean isUpdate = YesorNo("Do you want to update the data?");
				
				if(isUpdate) {
					boolean isExist = cekMenuDiDatabase(newFile1, false);
					
					if(isExist) {
						System.err.println("The menu is already exist in the database, update process is cancelled!");
						bufferedOutput.write(data);
					}
				}else {
					
					String kodemenu = newFile1[0];
					String namamenu = newFile1[1];
					String harga = newFile1[2];
					String stok = newFile1[3];
					
					
					bufferedOutput.write(data);
					bufferedOutput.newLine();
				}
				
				
			}else{
				bufferedOutput.write(data);
				bufferedOutput.newLine();
			}
			data = bufferedInput.readLine();
		}
		bufferedOutput.flush();
	}
	
}