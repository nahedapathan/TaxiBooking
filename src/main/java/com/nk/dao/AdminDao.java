package com.nk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nk.model.Admin;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

	Optional<Admin> findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query(value="update admin set username=:newusername,password=:newpassword where username=:oldusername",nativeQuery = true)
	public int updateCredentials(
			@Param("newusername") String newusername,
			@Param("newpassword") String newpassword,
			@Param("oldusername") String oldusername
			);
}
