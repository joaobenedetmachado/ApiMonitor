package api.monitor.repository;

import api.monitor.model.EndPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndPointRepository extends JpaRepository<EndPoint, Integer>{

    List<EndPoint> findByApiKeyId(Integer id);

}
