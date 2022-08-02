package lazizbek.uz.app_jwt_real_email_auditing.repository;

import lazizbek.uz.app_jwt_real_email_auditing.entity.Role;
import lazizbek.uz.app_jwt_real_email_auditing.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
