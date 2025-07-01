package api.monitor.repository;

import api.monitor.model.EndPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndPointRepository extends JpaRepository<EndPoint, Integer>{
}
