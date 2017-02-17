package br.com.alex.dto;

import br.com.alex.model.constraint.Category;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;

public class ProductDTO {
    private Long id;

    @NotBlank(message = "Nome deve ser informado")
    private String name;

    @NotBlank(message = "Descricao deve ser informado")
    private String description;

    @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
    private Double price;

    private Category category;
    private String changeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
}
