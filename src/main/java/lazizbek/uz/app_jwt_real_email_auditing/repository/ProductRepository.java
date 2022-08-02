package lazizbek.uz.app_jwt_real_email_auditing.repository;

import lazizbek.uz.app_jwt_real_email_auditing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "product")
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
