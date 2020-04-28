package com.cenfotec.storageApiRest.azure;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

@Component
public class AzureBlobAdapter {
	
	@Autowired
	private Environment env;
	
	
	 public CloudBlobClient cloudBlobClient() throws URISyntaxException, StorageException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(env.getProperty("azure.storage.connection-string"));
        return storageAccount.createCloudBlobClient();
    }
	
    
    public CloudBlobContainer testBlobContainer() throws URISyntaxException, StorageException, InvalidKeyException {
        return cloudBlobClient().getContainerReference(env.getProperty("azure.storage.container-name"));
    }
	
	public boolean createContainer(String containerName) throws InvalidKeyException, URISyntaxException, StorageException{
		
        boolean containerCreated = false;
        
        CloudBlobContainer container = null;
        
        CloudBlobClient cloudBlobClient = this.cloudBlobClient();
        
        container = cloudBlobClient.getContainerReference(containerName);
        
        try {
            container = cloudBlobClient.getContainerReference(containerName);
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        } catch (StorageException e) {
            
            e.printStackTrace();
        }
        try {
            containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (StorageException e) {
            
            e.printStackTrace();
        }
        return containerCreated;
    }
	
	public URI upload(MultipartFile multipartFile) throws InvalidKeyException, URISyntaxException, StorageException {
		URI uri = null;
        CloudBlockBlob blob = null;
        CloudBlobContainer cloudBlobContainer = this.testBlobContainer();
        try {
            blob = cloudBlobContainer.getBlockBlobReference(multipartFile.getOriginalFilename());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return uri;

	}

	
	public List listBlobs(String containerName) throws InvalidKeyException, URISyntaxException, StorageException {
		List uris = new ArrayList<>();
		
		CloudBlobClient cloudBlobClient = this.cloudBlobClient();
		
		
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            for (ListBlobItem blobItem : container.listBlobs()) {
                uris.add(blobItem.getUri());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return uris;
	}
	
	public void deleteBlob(String containerName, String blobName)
			throws InvalidKeyException, URISyntaxException, StorageException {
		
		
		
		try {
            CloudBlobContainer container = this.cloudBlobClient().getContainerReference(containerName);
            CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
            blobToBeDeleted.deleteIfExists();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
		
	}



}
