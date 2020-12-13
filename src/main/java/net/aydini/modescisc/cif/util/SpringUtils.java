package net.aydini.modescisc.cif.util;

import java.util.Locale;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class SpringUtils  {

    public static void autoWire(Class<?> clz) {
        AutowireCapableBeanFactory autowireCapableBeanFactory = ApplicationContextHolder.getConfigurableApplicationContext().getAutowireCapableBeanFactory();
        Object bean = autowireCapableBeanFactory.createBean(clz, 1, true);
        autowireCapableBeanFactory.autowireBean(bean);
    }

    public static <T> T getBean(Class<T> clz) {
        return ApplicationContextHolder.getConfigurableApplicationContext().getAutowireCapableBeanFactory().getBean(clz);
    }

    public static boolean containsBean(String name) {
        return ApplicationContextHolder.getConfigurableApplicationContext().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return ApplicationContextHolder.getConfigurableApplicationContext().isSingleton(name);
    }

    public static boolean isPrototype(String name) {
        return ApplicationContextHolder.getConfigurableApplicationContext().isPrototype(name);
    }

    public static String getMessage(String code, Object[] args) {
        MessageSource bean = (MessageSource)getBean(MessageSource.class);
        return bean.getMessage(code, args, new Locale("fa", "IR"));
    }
}
