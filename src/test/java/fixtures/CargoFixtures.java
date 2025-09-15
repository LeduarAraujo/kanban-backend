package fixtures;

import com.facilit.kanban_backend.domain.entity.CargoEntity;

public class CargoFixtures {

    public static CargoEntity criarCargoEntity() {
        CargoEntity cargo = new CargoEntity();
        cargo.setId(1L);
        cargo.setNome("Desenvolvedor");

        return cargo;
    }
}
