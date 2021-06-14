package com.mcosta.validator;

import com.mcosta.model.Publisher;

public class PublisherValidator {

    public static void isValid(Publisher publisher) throws Exception {
        isValid(publisher, true);
    }

    public static void isValid(Publisher publisher, boolean isCreating) throws Exception {

        if (publisher.getName() == null || publisher.getName().isEmpty()) {
            throw new Exception("Nome não informado.");
        }


    }

}
