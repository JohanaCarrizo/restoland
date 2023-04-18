package com.app.restoland.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "/image")
public interface ImageRest {

    @PostMapping(path = "/edit/{id}")
    ResponseEntity<byte[]> uploadImage(@PathVariable Long id);
}
