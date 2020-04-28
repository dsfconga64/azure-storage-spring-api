package com.cenfotec.storageApiRest.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cenfotec.storageApiRest.azure.AzureBlobAdapter;
import com.microsoft.azure.storage.StorageException;

@RestController
@RequestMapping("/")
public class AzureController {

	@Autowired
    private AzureBlobAdapter azureBlobAdapter;

    @PostMapping("/container")
    public ResponseEntity createContainer(@RequestBody String containerName) throws InvalidKeyException, URISyntaxException, StorageException{
        boolean created = azureBlobAdapter.createContainer(containerName);
        return ResponseEntity.ok(created);
    }

    @PostMapping
    public ResponseEntity upload(@RequestParam MultipartFile multipartFile,@RequestBody String containerName) throws InvalidKeyException, URISyntaxException, StorageException{
        URI url = azureBlobAdapter.upload(containerName,multipartFile);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/blobs")
    public ResponseEntity getAllBlobs(@RequestParam String containerName) throws InvalidKeyException, URISyntaxException, StorageException{
        List uris = azureBlobAdapter.listBlobs(containerName);
        return ResponseEntity.ok(uris);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam String containerName, @RequestParam String blobName) throws InvalidKeyException, URISyntaxException, StorageException{
        azureBlobAdapter.deleteBlob(containerName, blobName);
        return ResponseEntity.ok().build();
    }

	
}
