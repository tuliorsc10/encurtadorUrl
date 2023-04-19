package tulio.costa.encurtadorUrl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tulio.costa.encurtadorUrl.domain.dto.UrlOriginal;
import tulio.costa.encurtadorUrl.domain.entity.Urls;
import tulio.costa.encurtadorUrl.exception.AppError;
import tulio.costa.encurtadorUrl.repository.UrlsRepository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UrlsService {

    private static final String enderecoLocal = "http://localhost:8080/";
    private final UrlsRepository urlsRepository;


    public String getUrls(String urlCurta) throws ParseException {
        Optional<Urls> getObjetoUrl = urlsRepository.findByUrlEncurtada(enderecoLocal.concat(urlCurta));

        if(getObjetoUrl.isEmpty()) {
            throw new AppError(HttpStatus.NOT_FOUND, "Url não encontrada!");
        }

        long duracao = ChronoUnit.DAYS.between(LocalDateTime.parse(getObjetoUrl.get().getData()), LocalDateTime.now());

        if(duracao >= 2) {
            throw new AppError(HttpStatus.BAD_REQUEST, "Link Expirado");
        }
        return getObjetoUrl.get().getUrlsOriginal();
    }

    public Urls geradorUrl(UrlOriginal url) {
        if(!url.getUrlOriginal().startsWith("http") || !url.getUrlOriginal().startsWith("https")) {
            throw new AppError(HttpStatus.BAD_REQUEST, "Insira uma Url válida");
        }
        DateTimeFormatter segundo = DateTimeFormatter.ofPattern("ss");
        Optional<Urls> getObjetoUrl = urlsRepository.findByUrlEncurtada(enderecoLocal.concat(logicaEncurtador(url)));

        StringBuilder resultadoLogica = new StringBuilder();
        if(!getObjetoUrl.isEmpty()){
            String resultado = logicaEncurtador(url);
            resultadoLogica.append(logicaEncurtador(url)
                    .replace(resultado.substring(
                            resultado.length() -2, resultado.length()),
                            LocalDateTime.now().format(segundo)
                    )
            );
        } else {
            resultadoLogica.append(logicaEncurtador(url));
        }
        return urlsRepository.save(Urls.builder()
                .urlsOriginal(url.getUrlOriginal())
                .urlEncurtada(enderecoLocal.concat(resultadoLogica.toString()))
                .data(LocalDateTime.now().toString())
                .build());
    }

    public String tamanhoUrl(UrlOriginal url) {
        if(url.getUrlOriginal().length() == 0) {
            throw new AppError(HttpStatus.BAD_REQUEST, "O tamanho da string é zero!");
        }
        Double tamanhoEncurtador = 0.0;
        for(Integer i = 2; i < url.getUrlOriginal().length(); i++) {
            Double numberParse = i.doubleValue();
            Double tamanho = url.getUrlOriginal().length()/Math.pow(2, numberParse);
            if(tamanho <= 10) {
                tamanhoEncurtador = tamanho;
                String teste = url.getUrlOriginal().substring(url.getUrlOriginal().length() - tamanhoEncurtador.intValue(), url.getUrlOriginal().length());
                break;
            }
        }
        return url.getUrlOriginal().substring(url.getUrlOriginal().length() - tamanhoEncurtador.intValue(), url.getUrlOriginal().length());
    }

    public String logicaEncurtador(UrlOriginal url) {
        char[] arrayUrl = tamanhoUrl(url).toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayUrl.length; i++) {
            if(arrayUrl[i] == '-') {
                stringBuilder.append("1");
            }
            int codigo = (int) arrayUrl[i];
            char caractere = (char)( codigo + 5);
            stringBuilder.append(caractere);
        }
        return stringBuilder.toString();
    }
}
