package com.abelium.inatrace.types;

/**
 * base enum status (only active and disabled), in case other statuses are
 * needed, create new enum
 */
public enum Status {
	ACTIVE, DISABLED;

	public static Status valueOf(boolean bool) {
		return bool ? ACTIVE : DISABLED;
	}
}
