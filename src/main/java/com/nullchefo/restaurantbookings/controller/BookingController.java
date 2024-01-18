package com.nullchefo.restaurantbookings.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.dto.BookingDTO;
import com.nullchefo.restaurantbookings.entity.Booking;
import com.nullchefo.restaurantbookings.service.BookingService;

@RestController()
@RequestMapping(path = "/booking")
public class BookingController {

//	private final BookingService bookingService;
//
//	private final ModelMapper modelMapper;
//
//	public BookingController(final BookingService bookingService,  ModelMapper modelMapper) {
//		this.bookingService = bookingService;
//		this.modelMapper = modelMapper;
//	}
//
//	@GetMapping
//	public List<BookingDTO> findAll(){
//		final List<Booking> bookings = bookingService.findAll();
//		return bookings
//				.stream()
//				.map(this::convertToDto)
//				.collect(Collectors.toList());
//
//	}
//
//	private BookingDTO convertToDto(Booking booking) {
//		return modelMapper.map(booking, BookingDTO.class);
//	}
//
//	@GetMapping(path = "/{bookingId}")
//	public BookingDTO getBy(@PathVariable(name = "bookingId") UUID bookingId){
//		final Optional<Booking> optionalBooking = bookingService.findByIdOptional(bookingId);
//		return optionalBooking.map(this::convertToDto).orElse(null);
//	}
//
//
//
//	@PostMapping()
//	public BookingDTO create(@RequestBody BookingDTO bookingDTO) {
//		final Booking booking = convertToEntity(bookingDTO);
//		booking.setId(null);
//		final Booking bookingBaseDTO = bookingService.create(booking);
//		return convertToDto(bookingBaseDTO);
//	}
//
//	private Booking convertToEntity(final BookingDTO bookingDTO) {
//		final Booking booking = modelMapper.map(bookingDTO, Booking.class);
//// TODO fix
////		if (bookingDTO.getCategoryId() > 0) {
////			final Optional<Category> optionalCategory = categoryService.getEntity(bookingDTO.getCategoryId());
////			if (optionalCategory.isPresent()) {
////				booking.setCategory(optionalCategory.get());
////			}
////			else {
////				throw new IllegalStateException("The category with id: "+ bookingDTO.getCategoryId() + " is not found!");
////			}
////
////		}
//
//		return booking;
//	}
//
//	@PutMapping()
//	public BookingDTO update(@RequestBody BookingDTO bookingDTO) {
//		final Booking booking = convertToEntity(bookingDTO);
//
//		return convertToDto(bookingService.update(booking));
//	}
//
//	@DeleteMapping(path = "/{bookingId}")
//	public ResponseEntity<String> remove(@PathVariable(name = "bookingId") UUID bookingId) {
//
//		boolean isRemoved = bookingService.delete(bookingId);
//
//		String deletedMessage = "Booking with id: '" + bookingId + "' was deleted!";
//		String notDeletedMessage = "Booking with id: '" + bookingId + "' does not exists!";
//		return isRemoved ?
//				new ResponseEntity<>(deletedMessage, HttpStatusCode.valueOf(200)):
//				new ResponseEntity<>(notDeletedMessage, HttpStatusCode.valueOf(404));
//	}

}
