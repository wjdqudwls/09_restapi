package com.ohgiraffers.cqrs.product.command.application.service;

import com.ohgiraffers.cqrs.common.service.FileStorageService;
import com.ohgiraffers.cqrs.product.command.application.dto.request.ProductCreateRequest;
import com.ohgiraffers.cqrs.product.command.domain.aggregate.Product;
import com.ohgiraffers.cqrs.product.command.domain.aggregate.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository; //JpaproductRepository를 구현한 Proxy가 Bean 존재
    private final ModelMapper modelMapper; // modelMapper를 Bean으로 등록하는 cofing 필요
    private final FileStorageService fileStorageService;

    @Value("${image.image-url}")
    private String IMAGE_URL; //http://localhost:8080/productimgs/

  /* 상품 등록 */
  @Transactional
  public long createProduct(ProductCreateRequest productCreateRequest, MultipartFile productImg){

    /* 전달 받은 파일을 저장 */
    String replaceFileName = fileStorageService.storeFile(productImg);

    /* DTO -> Entity 변환 */
    Product newProduct = modelMapper.map(productCreateRequest, Product.class);

    /* 이미지 경로 추가 */
    newProduct.changeProductImageUrl(IMAGE_URL + replaceFileName);


    // 부모쪽이라서 안보임 productRepository. save이런거 없음 => ProductRepository에 Product save(Product product);생성
    // newProduct : 비영속
    // productRepository.save() 반환값 product : 영속     결과로 반환받은 엔티티는 영속이 됨
    Product product = productRepository.save(newProduct);

    //Product 삽입 후 반환된 productCode를 결과로 반환
    return product.getProductCode();
  }
}
