
package com.example.tk.annotations.task;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Task {

    Class<?>[] value() default {};

}
