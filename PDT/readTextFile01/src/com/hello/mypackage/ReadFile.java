package com.hello.mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ReadFile {
	public static void main(String[] args) {
		Configuration cfg=new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory=cfg.buildSessionFactory();
		
		//creating session object
		Session session=factory.openSession();
		
		//creating transaction object
		
		File folder = new File("C://Users//pkfre//Documents//TEXT_FILE//");
		File[] listOfFiles = folder.listFiles();

		for (int indx = 0; indx < listOfFiles.length; indx++) {
		  File file = listOfFiles[indx];
		  if (file.isFile() && file.getName().endsWith(".txt")) {
				try {
					
					StockOutHistory stHistory = new StockOutHistory();
					stHistory.setFile_name(file.getName());
					stHistory.setUpload_date(new Date());
					

					Query query = session.createQuery("select count(*) from StockOutHistory stHis where stHis.file_name =:file_name ");
					query.setString("file_name", stHistory.getFile_name() );
					Long count = (Long)query.uniqueResult();
					
					System.out.println("rowsAffected:"+count);
//					if(count < 1){ //duplicate file name
//					}
					
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					int i = 0;
					List<String> listStr = new ArrayList<>();
					while ((line = br.readLine()) != null) {
						listStr.add(i, line);
						i++;
					}
					int rec = 0;
					if(listStr.size() > 0){
						rec = listStr.size() - 1;
					}
					stHistory.setRecord_amount(new BigDecimal(rec));
					for(int j=0; j < listStr.size(); j++){
						Transaction t =session.beginTransaction();
						String[] value_split = listStr.get(j).split("\\|");
						if(value_split[0] != null && "T6".equalsIgnoreCase(value_split[0])){
							break;
						}
						StockOut stOut = new StockOut();
						stOut.setId("id"+indx+""+j);
					
						for(int k=0; k < value_split.length; k++){
							System.out.println("value_split"+k+": "+ value_split[k]);
							if(k == 0){
								stOut.setOrganization_code(value_split[k]);
							}else if(k == 1){
								stOut.setOrganization_name(value_split[k]);
							}else if(k == 2){
								stOut.setEntity(value_split[k]);
							}else if(k == 3){
								stOut.setTransaction_id(value_split[k]);
							}else if(k == 4){
								stOut.setItem_number(value_split[k]);
							}else if(k == 5){
								stOut.setItem_description(value_split[k]);
							}else if(k == 6){
								stOut.setTransaction_date(value_split[k]);
							}else if(k == 7){
								stOut.setIr_line_attribute1(value_split[k]);
							}else if(k == 8){
								stOut.setQuantity(value_split[k]);
							}else if(k == 9){
								stOut.setUom(value_split[k]);
							}else if(k == 10){
								stOut.setLot_number(value_split[k]);
							}else if(k == 11){
								stOut.setClaim_price(value_split[k]);
							}else if(k == 12){
								stOut.setCost(value_split[k]);
							}else if(k == 13){
								stOut.setWht_rate(value_split[k]);
							}else if(k == 14){
								stOut.setOutput_vat_rate(value_split[k]);
							}else if(k == 15){
								stOut.setIr_number(value_split[k]);
							}else if(k == 16){
								stOut.setIr_line(value_split[k]);
							}else if(k == 17){
								stOut.setSub_inventory(value_split[k]);
							}else if(k == 18){
								stOut.setSub_inventory_description(value_split[k]);
							}else if(k == 19){
								stOut.setTransaction_type(value_split[k]);
							}else if(k == 20){
								stOut.setMkt_activity_name(value_split[k]);
							}
						}
						session.persist(stOut);//persisting the object
						
						t.commit();//transaction is commited
					
						System.out.println("--------------------------------------------");
					}
					Transaction t =session.beginTransaction();
					session.persist(stHistory);
					t.commit();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  } 
		}
		session.close();
		
//		String path = "C://Users//pkfre//Documents//TEXT_FILE//example.txt";
//		File file = new File(path);
	
		
		
		
		System.out.println("successfully saved");
	}

}
