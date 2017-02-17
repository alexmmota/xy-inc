package br.com.alex.model.constraint;

public enum Category {

    CLOTHES("Roupas"), ELECTRONIC("Eletrônicos"), GAMES("Jogos"), BOOKS("Livros");

    private Category(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }

}
