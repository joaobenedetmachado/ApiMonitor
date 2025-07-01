package api.monitor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class EndPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // isso quer dizer que o id sera gerado pelo banco
    private Integer id;
    private String url;
    private String method;
    private Boolean requiresApiKey;
    private Integer apiKeyId;
    private Integer frequency;
    private Integer lastStatusCode;
    private Date lastCheckedAt;

}
