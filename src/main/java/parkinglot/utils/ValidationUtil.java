package parkinglot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;

    @Component
    public class ValidationUtil {

        private final Validator validator;

        @Autowired
        public ValidationUtil(Validator validator) {
            this.validator = validator;
        }

        public <E> boolean isValid(E entity) {
            return this.validator.validate(entity).isEmpty();
        }
    }

