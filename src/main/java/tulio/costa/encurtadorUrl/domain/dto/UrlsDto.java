package tulio.costa.encurtadorUrl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tulio.costa.encurtadorUrl.domain.entity.Urls;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlsDto {

    private String nomeUrlEncurtada;

}
