package com.ssafy.sub.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;				// repository 종류 네 가지 중 나머지 세 개
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ssafy.sub.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUid(String uid);
	User findById(int id);
	User findByUnick(String unick);
	User findByUemail(String uemail);
	Long deleteByUid(String uid);

	@Query("Select u from User u where unick like CONCAT(:keyword,'%')")
	List<User> findUserNickByKeyword(@Param("keyword") String keyword);
	
	@Transactional
    @Modifying
    @Query("UPDATE User u SET u.upw = :upw WHERE u.uid = :uid")
    int updateUpw(@Param("upw") String upw, @Param("uid") String uid);
	
	@Transactional
    @Modifying
    @Query("UPDATE User u SET u.unick = :unick WHERE u.id = :id")
	int updateUnick(@Param("id") int id, @Param("unick") String unick);
	
	
//	@Transactional
//	@Modifying
//	@Query("SELECT uid, unick user")
    
}