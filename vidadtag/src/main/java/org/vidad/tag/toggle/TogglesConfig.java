/**
 * 
 */
package org.vidad.tag.toggle;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;
import org.togglz.servlet.user.ServletUserProvider;

/**
 * @author moshe
 *
 */
@Component
public class TogglesConfig implements TogglzConfig {
	transient Logger log = Logger.getLogger(TogglesConfig.class);

	public TogglesConfig() {
	}
	
    public Class<? extends Feature> getFeatureClass() {
        return VidFeature.class;
    }

    public StateRepository getStateRepository() {
        return new FileBasedStateRepository(new File("/home/moshe/tmp/features.properties"));
    }

    public UserProvider getUserProvider() {
       // return new ServletUserProvider(null);
        return new UserProvider() {
            @Override
            public FeatureUser getCurrentUser() {
                return new SimpleFeatureUser("admin", true);
            }
        };
    }
}
