package dany.springframerwork.spring5webfluxrest.repositories;

import dany.springframerwork.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by bautisj on 4/20/2018.
 */
public interface CategoryRepository extends ReactiveMongoRepository <Category, String>{


}
