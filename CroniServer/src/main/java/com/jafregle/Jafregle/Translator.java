package com.jafregle.Jafregle;

import java.io.IOException;

public interface Translator {
    
    public String requestTranslation(String textToTranslate, String from, String to) throws IOException;
    
}
