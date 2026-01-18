package kz.danik.edition.app;

import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import kz.danik.edition.dto.ProductDto;
import kz.danik.edition.entity.Image;
import kz.danik.edition.entity.ImageDto;
import kz.danik.edition.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("edt_ProductService")
public class ProductService {


    @Value("${kz.danik.edition.fileUrl}")
    protected String baseUrl;
    @Autowired
    DataManager dataManager;
    @Autowired
    Messages messages;

    public List<ProductDto> getProducts(String category){

        List<Product> products = dataManager.load(Product.class)
                .query("select e from edt_Product e where e.category.code = :category")
                .parameter("category",category)
                .fetchPlan("product-rest-all-fetch-plan")
                .list();


        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getLangValueRu());
            if(product.getCategory() != null)
                productDto.setType(product.getCategory().getLangValueRu());
            productDto.setFloor(product.getFloor());
            productDto.setFloorStringLabel(product.getFloorStringValue());
            productDto.setPrice(product.getPrice());
            if(product.getPriceType() != null)
                productDto.setPriceType(messages.getMessage(product.getPriceType()));
            productDto.setCapacity(product.getPeopleCount());
            productDto.setAvailable(true);
            productDto.setBookingType(product.getBookingType() != null ? product.getBookingType().getId() : null);

            productDto.setAmenities(product.getAmenities().stream().map(e -> e.getLangValueRu()).toList());

            productDto.setImages(getImages(product));
            productDto.setRoomSize(product.getRoomSpace());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public List<ImageDto> getImages(Product product){

        List<ImageDto> images = new ArrayList<>();

        for (Image image : product.getImages()) {
            images.add(new ImageDto(baseUrl+image.getFile().getPath(),image.getOrder()));
        }

        return images;
    }

}