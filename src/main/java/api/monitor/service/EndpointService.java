package api.monitor.service;

import api.monitor.model.ApiKey;
import api.monitor.model.EndPoint;
import api.monitor.repository.ApiKeyRepository;
import api.monitor.repository.EndPointRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

}
