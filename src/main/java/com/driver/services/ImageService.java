package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    BlogRepository blogRepository;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image = new Image();
        image.setDimension(dimensions);
        image.setDescription(description);
        image.setBlog(blog);

        List<Image> imageList = blog.getImageList();

        if(imageList==null) imageList= new ArrayList<>();

        imageList.add(image);
        blog.setImageList(imageList);

        imageRepository2.save(image);
        blogRepository.save(blog);
        return image;

    }

    public void deleteImage(Image image){
        if(imageRepository2.existsById(image.getId())) {
            Blog blog = image.getBlog();
            List<Image> list = blog.getImageList();
            list.remove(image);
            blog.setImageList(list);

            imageRepository2.delete(image);
            blogRepository.save(blog);
        }
    }

    public Image findById(int id) {
        return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        if(screenDimensions.split("X").length==2 || Objects.nonNull(image)) {
            String[] imageDimensions = image.getDimension().split("X");

            int imageWidth = Integer.parseInt(imageDimensions[0]);
            int imageHeight = Integer.parseInt(imageDimensions[1]);

            String[] screenDim = screenDimensions.split("X");
            int screenWidth = Integer.parseInt(screenDim[0]);
            int screenHeight = Integer.parseInt(screenDim[1]);

            int a = screenWidth / imageWidth;

            int b = screenHeight / imageHeight;

            return a*b ;
        }
        return 0;
    }

}
