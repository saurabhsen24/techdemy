package com.techdemy.repository;

import com.techdemy.entities.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken,Long> {

    Optional<ResetToken> findByToken( String token );

    @Modifying
    @Transactional
    void deleteByToken( String token );

}
