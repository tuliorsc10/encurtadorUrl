package tulio.costa.encurtadorUrl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tulio.costa.encurtadorUrl.domain.entity.Urls;

import java.util.Optional;

public interface UrlsRepository extends JpaRepository<Urls, Long> {

    Optional<Urls> findByUrlEncurtada(String urlCurta);
}
