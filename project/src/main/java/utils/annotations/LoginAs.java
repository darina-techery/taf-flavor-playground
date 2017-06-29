package utils.annotations;

import user.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LoginAs {
	String username() default "";
	String password() default "";
	UserRole role() default UserRole.DEFAULT;
}
