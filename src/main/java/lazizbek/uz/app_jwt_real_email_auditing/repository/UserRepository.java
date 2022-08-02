package lazizbek.uz.app_jwt_real_email_auditing.repository;

import lazizbek.uz.app_jwt_real_email_auditing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmailCodeAndEmail(String emailCode, String email);
    Optional<User> findByEmail(String email);
}
