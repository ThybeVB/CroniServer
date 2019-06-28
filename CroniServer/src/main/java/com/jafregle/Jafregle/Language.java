package com.jafregle.Jafregle;

public enum Language
{
    PORTUGUESE("pt"), 
    ENGLISH("en"), 
    FRENCH("fr"), 
    GERMAN("gr"),
    INDONESIAN("id"),
    SPANISH("es");
    
    String value;
    
    private Language(String value) {
        this.value = value;
    }
    
    public String value(){
        return this.value;
    }
}
