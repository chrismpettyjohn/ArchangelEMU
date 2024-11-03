package com.us.nova;

import com.eu.habbo.core.ConfigurationManager;
import com.us.nova.betacode.BetaCodeManager;
import com.us.nova.bugreport.BugReportManager;
import com.us.nova.core.DatabaseConfig;
import com.us.nova.user.UserManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Getter
public class Nova {

    private static final Logger LOGGER = LoggerFactory.getLogger(Nova.class);

    private BetaCodeManager betaCodeManager;
    private BugReportManager bugReportManager;
    private UserManager userManager;

    public void load(ConfigurationManager config) {
        long millis = System.currentTimeMillis();

        DatabaseConfig.initialize(config);

        this.betaCodeManager = BetaCodeManager.getInstance();
        this.bugReportManager = BugReportManager.getInstance();
        this.userManager = UserManager.getInstance();

        LOGGER.info("Nova -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        this.userManager.dispose();

        DatabaseConfig.shutdown();

        log.info("Nova -> Disposed!");
    }
}
