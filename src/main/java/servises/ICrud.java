package servises;

import models.DTO.DBQueryDTO;

public interface ICrud {
    DBQueryDTO create(Object object);
    DBQueryDTO read(String query);
    DBQueryDTO update(String query);
    DBQueryDTO delete(String query);
}
