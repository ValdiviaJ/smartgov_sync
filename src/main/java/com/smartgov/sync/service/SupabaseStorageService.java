package com.smartgov.sync.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    public String subirFoto(byte[] fotoBytes, String nombreArchivo) {
        // Clean URL trailing slash if any
        String baseUrl = supabaseUrl;
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        String uploadUrl = baseUrl + "/storage/v1/object/evidencias/" + nombreArchivo;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        HttpEntity<byte[]> request = new HttpEntity<>(fotoBytes, headers);
        
        // Exchange with Supabase Storage
        restTemplate.exchange(uploadUrl, HttpMethod.POST, request, String.class);
        
        // Return the public URL
        return baseUrl + "/storage/v1/object/public/evidencias/" + nombreArchivo;
    }
}
