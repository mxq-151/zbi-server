package org.zbi.server.model.service;

import java.util.Comparator;

import org.zbi.server.model.parse.ParseColumn;

public class OrderByComparator implements Comparator<ParseColumn>{

	@Override
	public int compare(ParseColumn o1, ParseColumn o2) {
		// TODO Auto-generated method stub
		if(o1.getSortOrder()>o2.getSortOrder())
		{
			return 1;
			
		}else if(o1.getSortOrder()<o2.getSortOrder())
		{
			return -1;
		}
		
		return 0;
	}

}
