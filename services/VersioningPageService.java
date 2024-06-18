package com.traineeproject.core.services;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface VersioningPageService {
    void versioningPage(Node parentNode, Session session) throws RepositoryException;
}
