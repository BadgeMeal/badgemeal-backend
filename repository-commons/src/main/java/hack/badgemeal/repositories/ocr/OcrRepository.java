package hack.badgemeal.repositories.ocr;

import hack.badgemeal.models.ocr.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcrRepository extends JpaRepository<Receipt, String> {
}
