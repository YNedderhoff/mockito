/*
 * Copyright (c) 2021 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockitoinline;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;

public class EnumMockingTest {

    @Test
    public void testMockSimpleEnum() {
        Animal a = Mockito.mock(Animal.class);
        assertThat(a, not(equalTo(Animal.CAT)));
        assertThat(a.sound(), nullValue(String.class));
        assertThat(a.name(), nullValue(String.class));
    }

    @Test
    public void testMockAbstractEnumIsRejectedWithHelpfulMessage() {
        assertThatThrownBy(() -> Mockito.mock(AbstractAnimal.class))
                .isInstanceOf(org.mockito.exceptions.base.MockitoException.class)
                .hasMessageContaining("abstract enums can't be mocked");
    }

    enum Animal {
        CAT("meow");
        private final String sound;

        Animal(String sound) {
            this.sound = sound;
        }

        public String sound() {
            return sound;
        }
    }

    enum AbstractAnimal {
        CAT {
            @Override
            public String sound() {
                return "meow";
            }
        };

        public abstract String sound();
    }
}
