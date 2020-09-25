package web.app.scrable.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.app.scrable.entity.PolishWords;

import java.util.List;

@Repository
public interface PolishWordsRepository extends CrudRepository<PolishWords, Long> {
    PolishWords findByWord(String word);

    @Query("from PolishWords pw WHERE pw.word like CONCAT(:letter,'%')")
    List<PolishWords> findByFirstLetter(@Param("letter") String letter);

    @Query("from PolishWords pw WHERE pw.length <= :length")
    List<PolishWords> findByWordLength(@Param("length") int length);
}
