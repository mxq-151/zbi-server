package org.zbi.server.model.nested;

import java.io.Serializable;

/**
 * select * from
 *(
 *	select * from (select * from a where a.id=1) as a2
 *	left join b as b2 on b2.id=a2.id
 *  left join c as c2 on c2.name=b2.name
 * 
 * ) as a1
 * 
 * 
 * 其中a1,a2,a都是MAINTABLE
 * **/
public enum NestedJoinType implements Serializable{
	
	INNERJOIN,OUTERJOIN,LEFTJOIN,RIGHTJOIN,UNION,UNIONALL,INSELECT

}
