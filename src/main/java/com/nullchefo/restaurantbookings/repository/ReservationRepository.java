package com.nullchefo.restaurantbookings.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>,
		JpaSpecificationExecutor<Reservation> {
	List<Reservation> findAllByReservationTime(LocalDate date);

	//	@Query("SELECT r FROM Reservation r WHERE r.reservationTime = :date AND r.table IN :tables")
	//	List<Reservation> findAllByReservationTimeAndContainingTable(@Param("date") LocalDate date, @Param("tables") List<RestaurantTable> tables);

	List<Reservation> findAllByReservationTimeAndTableIn(LocalDate date, List<RestaurantTable> tables);

	List<Reservation> findAllByOrderByReservationTimeDesc();

	List<Reservation> findAllByUserOrderByReservationTimeDesc(User user);

	List<Reservation> findAllByTableInOrderByReservationTimeDesc(List<RestaurantTable> tables);
}
