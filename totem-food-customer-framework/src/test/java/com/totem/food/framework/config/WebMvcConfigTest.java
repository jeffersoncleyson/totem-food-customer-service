package com.totem.food.framework.config;

import com.totem.food.framework.adapters.in.rest.interceptors.XUserIdentifierInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static com.totem.food.framework.adapters.in.rest.constants.Routes.API_VERSION_1;
import static com.totem.food.framework.adapters.in.rest.constants.Routes.TOTEM_ORDER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebMvcConfigTest {

    @Mock
    private XUserIdentifierInterceptor xUserIdentifierInterceptor;

    @Mock
    private InterceptorRegistry interceptorRegistry;

    @Mock
    private InterceptorRegistration interceptorRegistration;

    @InjectMocks
    private WebMvcConfig webMvcConfig;

    @Test
    void testAddInterceptors() {
        when(interceptorRegistry.addInterceptor(xUserIdentifierInterceptor)).thenReturn(interceptorRegistration);
        when(interceptorRegistration.addPathPatterns(API_VERSION_1 + TOTEM_ORDER + "/**")).thenReturn(interceptorRegistration);

        webMvcConfig.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry, times(1)).addInterceptor(xUserIdentifierInterceptor);
        verify(interceptorRegistration, times(1)).addPathPatterns(API_VERSION_1 + TOTEM_ORDER + "/**");
        verify(interceptorRegistration, times(1)).excludePathPatterns(WebMvcConfig.EXCLUDE_PATH_PATTERNS);
    }

}
