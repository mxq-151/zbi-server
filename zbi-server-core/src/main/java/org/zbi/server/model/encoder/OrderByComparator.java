package org.zbi.server.model.encoder;

import java.util.Comparator;

import org.zbi.server.model.parse.ParseDimension;

public class OrderByComparator implements Comparator<ParseDimension>{

	@Override
	public int compare(ParseDimension o1, ParseDimension o2) {
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
