package com.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.domain.User.class.getName());
            createCache(cm, com.domain.Authority.class.getName());
            createCache(cm, com.domain.User.class.getName() + ".authorities");
            createCache(cm, com.domain.Profile.class.getName());
            createCache(cm, com.domain.Competition.class.getName());
            createCache(cm, com.domain.Competition.class.getName() + ".localisations");
            createCache(cm, com.domain.Competition.class.getName() + ".accreditateUsers");
            createCache(cm, com.domain.Competition.class.getName() + ".competitionServicesOffers");
            createCache(cm, com.domain.Competition.class.getName() + ".accreditationSteps");
            createCache(cm, com.domain.Competition.class.getName() + ".competitionservicejoineds");
            createCache(cm, com.domain.Competition.class.getName() + ".disciplines");
            createCache(cm, com.domain.Competition.class.getName() + ".options");
            createCache(cm, com.domain.Competition.class.getName() + ".formats");
            createCache(cm, com.domain.Competition.class.getName() + ".files");
            createCache(cm, com.domain.Journee.class.getName());
            createCache(cm, com.domain.Journee.class.getName() + ".confrontations");
            createCache(cm, com.domain.Team.class.getName());
            createCache(cm, com.domain.Team.class.getName() + ".users");
            createCache(cm, com.domain.Confrontation.class.getName());
            createCache(cm, com.domain.Confrontation.class.getName() + ".scores");
            createCache(cm, com.domain.Confrontation.class.getName() + ".teams");
            createCache(cm, com.domain.Score.class.getName());
            createCache(cm, com.domain.Poules.class.getName());
            createCache(cm, com.domain.Poules.class.getName() + ".teams");
            createCache(cm, com.domain.MatchSheet.class.getName());
            createCache(cm, com.domain.Discipline.class.getName());
            createCache(cm, com.domain.Discipline.class.getName() + ".phases");
            createCache(cm, com.domain.Discipline.class.getName() + ".categories");
            createCache(cm, com.domain.Options.class.getName());
            createCache(cm, com.domain.Delegation.class.getName());
            createCache(cm, com.domain.Delegation.class.getName() + ".teams");
            createCache(cm, com.domain.DelegationMembers.class.getName());
            createCache(cm, com.domain.DelegationMembers.class.getName() + ".users");
            createCache(cm, com.domain.Category.class.getName());
            createCache(cm, com.domain.Phase.class.getName());
            createCache(cm, com.domain.Phase.class.getName() + ".journees");
            createCache(cm, com.domain.Localisation.class.getName());
            createCache(cm, com.domain.Format.class.getName());
            createCache(cm, com.domain.Notification.class.getName());
            createCache(cm, com.domain.PrestationService.class.getName());
            createCache(cm, com.domain.PrestationService.class.getName() + ".files");
            createCache(cm, com.domain.PrestationService.class.getName() + ".localisations");
            createCache(cm, com.domain.Rubrique.class.getName());
            createCache(cm, com.domain.Rubrique.class.getName() + ".files");
            createCache(cm, com.domain.Files.class.getName());
            createCache(cm, com.domain.Competitionservicejoined.class.getName());
            createCache(cm, com.domain.CompetitionServicesOffer.class.getName());
            createCache(cm, com.domain.CompetitionServicesOffer.class.getName() + ".files");
            createCache(cm, com.domain.Accreditation.class.getName());
            createCache(cm, com.domain.AccreditationStep.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
