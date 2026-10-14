package com.loja.cupcake.config;

import com.loja.cupcake.model.Cupcake;
import com.loja.cupcake.repository.CupcakeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(CupcakeRepository cupcakeRepository) {
        return args -> {
            if (cupcakeRepository.count() == 0) {
                Cupcake c1 = new Cupcake(null, "Cupcake Açaí Tropical", "Massa fofinha de baunilha com recheio cremoso de açaí puro e cobertura de creme de leite ninho.", 12.00, "/images/acai.jpg", 15);
                Cupcake c2 = new Cupcake(null, "Cupcake Chocolate Intenso", "Massa artesanal de cacau 70% recheada com ganache belga e cobertura aveludada de chocolate.", 10.00, "/images/chocolate.jpg", 20);
                Cupcake c3 = new Cupcake(null, "Cupcake Coco Queimado", "Massa leve de coco recheada com beijinho cremoso e gratinada com flocos de coco queimado.", 9.50, "/images/coco.jpg", 12);
                Cupcake c4 = new Cupcake(null, "Cupcake Maracujá Supreme", "Massa delicada com recheio azedinho de maracujá e cobertura de merengue suíço tostado.", 11.00, "/images/maracuja.jpg", 18);
                Cupcake c5 = new Cupcake(null, "Cupcake Nutella Gold", "Massa de avelã recheada com molho generoso de Nutella pura e finalizada com avelãs crocantes.", 14.00, "/images/nutella.jpg", 25);
                Cupcake c6 = new Cupcake(null, "Cupcake Pistache Monument", "Massa suave de pistache siciliano recheada com brigadeiro de pistache e pedaços da semente.", 15.00, "/images/pistache.jpg", 10);

                cupcakeRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6));
                System.out.println(">>> 6 sabores de cupcakes cadastrados com sucesso usando as imagens locais!");
            }
        };
    }
}