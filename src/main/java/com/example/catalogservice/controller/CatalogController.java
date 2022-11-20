package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/catalog-service")
public class CatalogController {
    Environment environment;
    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment environment, CatalogService catalogService) {
        this.environment = environment;
        this.catalogService = catalogService;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's working in user service on port %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();

        catalogList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/catalogs/{productId}")
    public ResponseEntity<ResponseCatalog> getCatalog(@PathVariable String productId) throws NameNotFoundException {
        CatalogDto catalog = catalogService.getCatalogsByProductId(productId);

        ResponseCatalog returnCatalog = new ModelMapper().map(catalog, ResponseCatalog.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnCatalog);
    }
}
