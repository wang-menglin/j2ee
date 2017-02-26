package com.kylin.repository;


import com.kylin.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kylin on 17/02/2017.
 * All rights reserved.
 */
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("select h from Hotel h where h.location like %:address%")
    List<Hotel> findByLocation(@Param("address") String address);

}
