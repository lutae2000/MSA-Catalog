package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
    @Override
    public CatalogDto getCatalogsByProductId(String productId) throws NameNotFoundException {
        CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);

        if(catalogEntity == null)
            throw new NameNotFoundException("Catalog not found");

        CatalogDto catalogDto = new ModelMapper().map(catalogEntity, CatalogDto.class);
        return catalogDto;
    }

}
