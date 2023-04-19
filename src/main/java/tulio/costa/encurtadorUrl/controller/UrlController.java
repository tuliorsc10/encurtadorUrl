package tulio.costa.encurtadorUrl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tulio.costa.encurtadorUrl.domain.dto.UrlOriginal;
import tulio.costa.encurtadorUrl.domain.dto.UrlsDto;
import tulio.costa.encurtadorUrl.domain.entity.Urls;
import tulio.costa.encurtadorUrl.service.UrlsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class UrlController {

    private final UrlsService urlsService;

    @PostMapping("/roteamento")
    public Urls getObjetoUrl(@RequestBody UrlsDto url) {
        return urlsService.getUrls(url);
    }

    @PostMapping()
    public Urls geraUrl(@RequestBody UrlOriginal url) {
        return urlsService.geradorUrl(url);
    }
}
