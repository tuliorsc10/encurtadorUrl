package tulio.costa.encurtadorUrl.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Urls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Urloriginal")
    private String urlsOriginal;
    @Column(name = "Urlencurtada")
    private String urlEncurtada;
    @Column(name = "Datagerada")
    private String data;
}
