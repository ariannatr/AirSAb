package airbnb.repository;

import airbnb.model.ReservedEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arianna on 22/9/2017.
 */
@Repository("reservedRepository")
public interface ReservedRepository extends PagingAndSortingRepository<ReservedEntity,Long> {
}
