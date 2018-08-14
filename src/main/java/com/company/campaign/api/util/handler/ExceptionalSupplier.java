package com.company.campaign.api.util.handler;

public interface ExceptionalSupplier<T, E extends Throwable> {
	T get() throws E;
}
