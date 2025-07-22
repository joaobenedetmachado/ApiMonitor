package api.monitor.service;

import api.monitor.model.ApiKey;
import api.monitor.model.EndPoint;
import api.monitor.repository.ApiKeyRepository;
import api.monitor.repository.EndPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import api.monitor.model.EndPoint;
import api.monitor.repository.EndPointRepository;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class EndpointService {

    @Autowired
    private final EndPointRepository endPointRepository;

    public EndpointService(EndPointRepository endPointRepository) {
        this.endPointRepository = endPointRepository;
    }

    public EndPoint createEndPoint(EndPoint endPoint){
        return endPointRepository.save(endPoint);
    }

    public EndPoint updateEndPoint(Integer id, EndPoint novoEndPoint) {
        EndPoint e = endPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endpoint não encontrado"));

        e.setUrl(novoEndPoint.getUrl());
        e.setMethod(novoEndPoint.getMethod());
        e.setRequiresApiKey(novoEndPoint.getRequiresApiKey());
        e.setApiKeyId(novoEndPoint.getApiKeyId());
        e.setFrequency(novoEndPoint.getFrequency());
        e.setLastStatusCode(novoEndPoint.getLastStatusCode());
        e.setLastCheckedAt(novoEndPoint.getLastCheckedAt());

        return endPointRepository.save(e);
    }


    public EndPoint deleteEndPoint(Integer id) { //ele pega o id do endPoint
        EndPoint e = endPointRepository.findById(id) //tenta encontrar;
                .orElseThrow(() -> new RuntimeException("Endpoint não encontrado"));

        endPointRepository.deleteById(id); //se encontrar ele deleta
        return e;
    }

    public List<EndPoint> getAllEndPoints() {
        return endPointRepository.findAll();
    }

    public List<EndPoint> findByApiKeyId(Integer apiKeyId){
        return endPointRepository.findByApiKeyId(apiKeyId);
    }

    public List<EndpointStatus> checkAllEndpointsStatus() {
        List<EndPoint> endpoints = endPointRepository.findAll();
        List<EndpointStatus> statusList = new ArrayList<>();
        for (EndPoint endpoint : endpoints) {
            int status = getStatusCode(endpoint.getUrl());
            statusList.add(new EndpointStatus(endpoint.getId(), endpoint.getUrl(), status));
        }
        return statusList;
    }

    private int getStatusCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            return -1;
        }
    }

    public static class EndpointStatus {
        private Long id;
        private String url;
        private int statusCode;

        public EndpointStatus(Long id, String url, int statusCode) {
            this.id = id;
            this.url = url;
            this.statusCode = statusCode;
        }
        public Long getId() { return id; }
        public String getUrl() { return url; }
        public int getStatusCode() { return statusCode; }
    }
}
