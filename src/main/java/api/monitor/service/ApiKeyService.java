package api.monitor.service;

import api.monitor.model.ApiKey;
import api.monitor.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey createApiKey(ApiKey apiKey) {
        if (apiKey.getCreatedAt() == null) {
            apiKey.setCreatedAt(new Date());
        }
        if (apiKey.getRevoked() == null) {
            apiKey.setRevoked(false);
        }
        return apiKeyRepository.save(apiKey);
    }

    public ApiKey getApiKeyById(Integer id) {
        return apiKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API Key nao encontrada"));
    }

    public ApiKey getApiKeyByKey(String key) {
        return apiKeyRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("API Key nao encontrada"));
    }

    public ApiKey revokeApiKey(Integer id) {
        ApiKey apiKey = getApiKeyById(id);
        apiKey.setRevoked(true);
        return apiKeyRepository.save(apiKey);
    }

    public List<ApiKey> getAllApiKeys() {
        return apiKeyRepository.findAll();
    }

    public void deleteApiKey(Integer id) {
        apiKeyRepository.deleteById(id);
    }
}
