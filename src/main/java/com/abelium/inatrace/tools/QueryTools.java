package com.abelium.inatrace.tools;

import org.torpedoquery.jpa.Torpedo;

import com.abelium.inatrace.types.SortDirection;

public class QueryTools {
	
	public static <T> void orderBy(SortDirection dir, T column) {
		if (dir == SortDirection.ASC) Torpedo.orderBy(Torpedo.asc(column));
		else Torpedo.orderBy(Torpedo.desc(column));
	}
}
