package me.nothing.login_.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.nothing.login_.model.Staff;

@Transactional
public interface StaffRepository extends JpaRepository<Staff, Integer> {
	@Query("SELECT u FROM Staff u WHERE u.username = :username")
	public Staff findByUsername(@Param("username") String uername);

	@Query("UPDATE Staff SET failedAttempt = :failedAttempt WHERE username = :username ")
	@Modifying
	public void updateFailedAttempt(@Param("failedAttempt") int failedAttempt, @Param("username") String username);
}