package com.dikara.cruds.repository;

import com.dikara.cruds.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT b FROM User b WHERE b.userStatus = 'ACTIVE' ORDER BY b.createdDate DESC")
    Page<User> findByIsDeletedFalseOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT c \n" +
            "FROM User c \n" +
            "WHERE c.userStatus = 'ACTIVE'\n" +
            "AND (\n" +
            "    UPPER(c.username) LIKE UPPER(CONCAT('%', :keyword, '%'))\n" +
            "    OR UPPER(c.name) LIKE UPPER(CONCAT('%', :keyword, '%'))\n" +
            "    OR UPPER(c.email) LIKE UPPER(CONCAT('%', :keyword, '%'))\n" +
            ")\n" +
            "ORDER BY c.createdDate DESC\n")
    Page<User> findByKeywordOrderByCreatedDateDesc(String keyword, Pageable pageable);
}
