package dev.manere.utils.command.annotation;

import dev.manere.utils.command.Commander;
import dev.manere.utils.event.EventListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to automatically register classes for commands, events, etc.
 * @see Commander
 * @see EventListener
 * @see AutoRegisterHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoRegister {

}
