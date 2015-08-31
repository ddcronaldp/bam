package com.dealertrack.bam;

import com.google.common.collect.ImmutableMap;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfig{

    //Spring security Filter Chain SHOULD be as Follows:
    // http://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html
    // (this is not what we are doing)
    //
    //	SecurityContextPersistenceFilter, so a SecurityContext can be set up in the SecurityContextHolder at the beginning of a web request, and any changes to the SecurityContext can be copied to the HttpSession when the web request ends (ready for use with the next web request)
    //	Authentication processing mechanisms - UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, BasicAuthenticationFilter etc - so that the SecurityContextHolder can be modified to contain a valid Authentication request token
    //	The SecurityContextHolderAwareRequestFilter, if you are using it to install a Spring Security aware HttpServletRequestWrapper into your servlet container
    //	AnonymousAuthenticationFilter, so that if no earlier authentication processing mechanism updated the SecurityContextHolder, an anonymous Authentication object will be put there
    //	ExceptionTranslationFilter, to catch any Spring Security exceptions so that either an HTTP error response can be returned or an appropriate AuthenticationEntryPoint can be launched
    //	FilterSecurityInterceptor, to protect web URIs and raise exceptions when access is denied
    //
    //  However, we need to place the AnonymousAuthenticationFilter before the OAuth2AuthenticationFilter because the OAuth doesn't allow the processing chain to continue.
    //  it has continueFilterChainOnUnsuccessfulAuthentication set to false.
    //
    //  We force the Anonymous Filter to be added before the OAuth2 filter but restrict it to ONLY operate on auth-login urls.  The Anonymous filter must be restricted to
    // just these URLs otherwise all request would have the anonymous security context, and since we are not currently enforcing any ROLE restrictions on resources, no resource
    // would require authentication.


    private static final int CORS_FILTER_ORDER                         =  Ordered.HIGHEST_PRECEDENCE;
    private static final int SECURITY_CONTEXT_PERSISTENCE_FILTER_ORDER =  CORS_FILTER_ORDER + 1;
    private static final int ANONYMOUS_AUTH_FILTER_ORDER               =  SECURITY_CONTEXT_PERSISTENCE_FILTER_ORDER + 1;

    @Bean
    FilterRegistrationBean corsFilter() throws ServletException{

        final FilterRegistrationBean filterRegistration = registerFilterWithOrder(new CorsFilter(), CORS_FILTER_ORDER);

        //add Authorization to the list of default CORS allowed headers
        filterRegistration.setInitParameters(ImmutableMap.of(CorsFilter.PARAM_CORS_ALLOWED_HEADERS, "Authorization," + CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS));
        return filterRegistration;
    }

    @Bean
    public FilterRegistrationBean securityContextPersistenceFilter () {

        //the SecurityContextPersistenceFilter is responsible for clearing the threads security context after
        //processing the request.
        // We are not using the persistence feature of the filter, hence the NullSecurityContextRepository.
        // The OAuth filter verifies the token on each request and maintains its own local security context cache.
        Filter filter = new SecurityContextPersistenceFilter(new NullSecurityContextRepository());
        return registerFilterWithOrder(filter, SECURITY_CONTEXT_PERSISTENCE_FILTER_ORDER);
    }

    @Bean
    public FilterRegistrationBean anonymousAuthFilterOnLoginUrls () {

        Filter anonymousAuthenticationFilter = new AnonymousAuthenticationFilter("ANONYMOUS_AUTHENTICATION_FILTER");

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(anonymousAuthenticationFilter);
        registrationBean.setOrder(ANONYMOUS_AUTH_FILTER_ORDER);

        //anonymous login is only applicable to oauth urls
        Set<String> urlPatterns = new HashSet();
        urlPatterns.add(ApplicationConfig.BASE_URL + "oauth/*");
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

    /**
     * Helper utility to make it easy to register a filter with an order
     * @param filter - filter to register
     * @param order - order of the filter, lower number is higher priority
     * @return
     */
    private FilterRegistrationBean registerFilterWithOrder(Filter filter, int order){

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(order);
        return registrationBean;
    }
}
