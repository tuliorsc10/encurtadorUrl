package tulio.costa.encurtadorUrl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tulio.costa.encurtadorUrl.domain.dto.UrlOriginal;
import tulio.costa.encurtadorUrl.domain.dto.UrlsDto;
import tulio.costa.encurtadorUrl.domain.entity.Urls;
import tulio.costa.encurtadorUrl.service.UrlsService;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class UrlController {

    private final UrlsService urlsService;

    @PostMapping("/{url}")
    public String getObjetoUrl(@PathVariable String url) throws ParseException {
        return urlsService.getUrls(url);
    }

    @PostMapping()
    public Urls geraUrl(@RequestBody UrlOriginal url) {
        return urlsService.geradorUrl(url);
    }
}
