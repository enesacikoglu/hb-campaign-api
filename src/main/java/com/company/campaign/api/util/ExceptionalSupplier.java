package com.company.campaign.api.util;

public interface ExceptionalSupplier<T, E extends Throwable> {
	T get() throws E;
}
