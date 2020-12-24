package dao;

import entity.CodeResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author namvdo
 */
@Repository
public interface CodeResponseRepository extends CrudRepository<CodeResponse, Integer> {
}
