package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.jpa.CatalogEntity;

import javax.naming.NameNotFoundException;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
    CatalogDto getCatalogsByProductId(String productId) throws NameNotFoundException;

    //CatalogDto createCatalog(CatalogDto catalogDto);
}
