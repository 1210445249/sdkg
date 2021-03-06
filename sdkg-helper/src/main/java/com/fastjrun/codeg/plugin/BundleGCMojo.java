package com.fastjrun.codeg.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "bundleGc", defaultPhase = LifecyclePhase.INITIALIZE)
public class BundleGCMojo extends CodeGMogo {

    @Parameter(property = "bdgc.skip", defaultValue = "true")
    private boolean skip;

    public void execute() throws MojoExecutionException {

        if (skip) {
            return;
        }

        this.executeInternal(CodeGCommand.BundleG);

    }
}