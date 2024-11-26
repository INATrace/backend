package com.abelium.inatrace.tools;

import com.abelium.inatrace.types.SortDirection;
import org.torpedoquery.jakarta.jpa.Torpedo;

public class QueryTools {
	
	public static <T> void orderBy(SortDirection dir, T column) {
		if (dir == SortDirection.ASC) Torpedo.orderBy(Torpedo.asc(column));
		else Torpedo.orderBy(Torpedo.desc(column));
	}
}
