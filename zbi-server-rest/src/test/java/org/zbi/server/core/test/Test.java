package org.zbi.server.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

class Test {

	@org.junit.jupiter.api.Test
	void test() throws IOException {

		FileInputStream f4 = new FileInputStream(new File("/Users/moxuqiang/Desktop/order.csv"));
		BufferedReader br = new BufferedReader(new InputStreamReader(f4, "GB2312"));

		String contentLine;
		
		FileWriter writer=new FileWriter("/Users/moxuqiang/Desktop/order.sql");

		String str = " insert into `order` (`order_date` ,`order_year` ,`order_num` ,`product_id` ,`custom_id` ,`order_type` ,`sale_dist_id` ,`sale_dist` ,`sale_country` ,`sale_area` ,`product_type` ,`product_num` ,`product_name` ,`product_cost` ,`sale_profit` ,`sale_price` ,`sale_total`) values ";

		int loop=0;
		StringBuilder sb = new StringBuilder();
		while ((contentLine = br.readLine()) != null) {
			String[] tmp = contentLine.split(",");
			
			if(loop==0)
			{
				sb.append(str);
			}
			sb.append("(");
			for (int i = 0; i < tmp.length; i++) {
				String t = tmp[i];
				if (i < tmp.length - 1) {
					sb.append("\"").append(t).append("\"").append(",");
				} else {
					sb.append("\"").append(t).append("\"").append(")");
				}
			}
			loop++;
			
			if(loop==100)
			{
				writer.write(sb.toString());
				writer.write("\n");
				sb = new StringBuilder();
				loop=0;
			}

		}

		writer.flush();
		writer.close();
		br.close();
	}

}
