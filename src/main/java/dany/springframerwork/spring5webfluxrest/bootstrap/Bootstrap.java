package dany.springframerwork.spring5webfluxrest.bootstrap;

import dany.springframerwork.spring5webfluxrest.domain.Category;
import dany.springframerwork.spring5webfluxrest.domain.Vendor;
import dany.springframerwork.spring5webfluxrest.repositories.CategoryRepository;
import dany.springframerwork.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by bautisj on 4/20/2018.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0) {
            //load data
            System.out.println("###### Loading Data On Bootstrap #####");

            categoryRepository.save(Category.builder()
                    .description("Fruits")
                    .build());

            categoryRepository.save(Category.builder()
                    .description("Nuts")
                    .build());

            categoryRepository.save(Category.builder()
                    .description("Breads")
                    .build());

            categoryRepository.save(Category.builder()
                    .description("Meatss")
                    .build());

            categoryRepository.save(Category.builder()
                    .description("Eggs")
                    .build());

            System.out.println("Loaded Categories" + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Dany")
                    .lastName("Bautista")
                    .build());

            vendorRepository.save(Vendor.builder()
                    .firstName("Jhony")
                    .lastName("Foo")
                    .build());

            vendorRepository.save(Vendor.builder()
                    .firstName("Lety")
                    .lastName("Gomez")
                    .build());

            vendorRepository.save(Vendor.builder()
                    .firstName("Cristina")
                    .lastName("Ximenez")
                    .build());

            System.out.println("Loaded Vendrs: " + vendorRepository.count().block());

        }
    }
}
