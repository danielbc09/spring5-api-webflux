package dany.springframerwork.spring5webfluxrest.controllers;

import dany.springframerwork.spring5webfluxrest.domain.Vendor;
import dany.springframerwork.spring5webfluxrest.repositories.CategoryRepository;
import dany.springframerwork.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bautisj on 4/23/2018.
 */
@RestController
public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    private Vendor vendor1;
    private Vendor vendor2;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

        vendor1 = Vendor.builder()
                .id("abcdefg")
                .firstName("Daniel")
                .lastName("Bautista").build();
        vendor2 = Vendor.builder()
                .id("avsssd")
                .firstName("Jhon")
                .lastName("Doe")
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get().uri(vendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);

    }

    @Test
    public void getVendorById() throws Exception {
        given(vendorRepository.findById("avsssd"))
                .willReturn(Mono.just(vendor1));

        webTestClient.get()
                .uri(vendorController.BASE_URL + "/avsssd")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor(){
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(vendor1);

        webTestClient.post()
                .uri(vendorController.BASE_URL)
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(vendor1);

        webTestClient.put()
                .uri(vendorController.BASE_URL+ "/qwee")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchVendorWithChanges() {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(vendor1));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Dany").build());

        webTestClient.patch()
                .uri(vendorController.BASE_URL+ "/qwee")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    public void testPatchVendorWithNoChanges() {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(vendor1));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Daniel").build());

        webTestClient.patch()
                .uri(vendorController.BASE_URL+ "/qwee")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());
    }
}