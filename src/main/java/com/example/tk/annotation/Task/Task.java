
package com.example.tk.annotation.Task;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Task {

    Class<?>[] value() default {};

}
