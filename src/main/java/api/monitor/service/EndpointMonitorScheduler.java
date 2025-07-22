package api.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EndpointMonitorScheduler {
    @Autowired
    private EndpointService endpointService;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    // executa a cada 60 segundos (pode ajustar o cron depois)
    @Scheduled(fixedRateString = "60000")
    public void checkAndSendStatus() {
        List<EndpointService.EndpointStatus> statuses = endpointService.checkAllEndpointsStatus();
        for (EndpointService.EndpointStatus status : statuses) {
            String msg = String.format("id: %d, url: %s, status: %d", status.getId(), status.getUrl(), status.getStatusCode());
            kafkaProducerService.sendStatus(msg);
        }
    }
} 