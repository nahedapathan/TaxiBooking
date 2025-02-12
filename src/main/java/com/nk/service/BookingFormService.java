package com.nk.service;

import java.util.List;

import com.nk.model.BookingForm;


public interface BookingFormService {

	public BookingForm saveBookingFormService(BookingForm bookingForm);
	
    public List<BookingForm> readAllBookingsService();
	
	public void deleteBookingsService(int id);
}
