package tulio.costa.encurtadorUrl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tulio.costa.encurtadorUrl.domain.dto.UrlOriginal;
import tulio.costa.encurtadorUrl.domain.dto.UrlsDto;
import tulio.costa.encurtadorUrl.domain.entity.Urls;
import tulio.costa.encurtadorUrl.exception.AppError;
import tulio.costa.encurtadorUrl.repository.UrlsRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UrlsService {

    private static final String enderecoLocal = "http://localhost:8080/";
    private final UrlsRepository urlsRepository;


    public Urls getUrls(UrlsDto urlCurta) {
        Optional<Urls> getObjetoUrl = urlsRepository.findByUrlEncurtada(urlCurta.getNomeUrlEncurtada());

        if(getObjetoUrl.isEmpty()) {
            throw new AppError(HttpStatus.BAD_REQUEST, "Url não encontrada!");
        }
        return getObjetoUrl.get();
    }

    public Urls geradorUrl(UrlOriginal url) {
        if(!url.getUrlOriginal().startsWith("http") || !url.getUrlOriginal().startsWith("https")) {
            throw new AppError(HttpStatus.BAD_REQUEST, "Insira uma Url válida");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyymmddhhmmss");

        return urlsRepository.save(Urls.builder()
                .urlsOriginal(url.getUrlOriginal())
                .urlEncurtada(enderecoLocal.concat(logicaEncurtador(url)))
                .data(LocalDateTime.now().format(dateTimeFormatter))
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
