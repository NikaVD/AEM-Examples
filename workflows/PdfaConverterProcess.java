//package com.traineeproject.core.workflows;
//
//import com.adobe.aemfd.docmanager.Document;
//import com.adobe.granite.workflow.WorkflowException;
//import com.adobe.granite.workflow.WorkflowSession;
//import com.adobe.granite.workflow.exec.WorkItem;
//import com.adobe.granite.workflow.exec.WorkflowProcess;
//import com.adobe.granite.workflow.metadata.MetaDataMap;
//import com.day.cq.dam.api.AssetManager;
//import com.traineeproject.core.services.ConverterService;
//import org.apache.sling.api.resource.LoginException;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//
//import javax.jcr.Binary;
//import javax.jcr.RepositoryException;
//import javax.jcr.Session;
//import javax.jcr.ValueFactory;
//import java.io.File;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component(service = WorkflowProcess.class, property = {"process.label=PDFA conv"})
//public class PdfaConverterProcess implements WorkflowProcess {
//
//    @Reference
//    private ConverterService converterService;
//
//
//    @Override
//    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
//        String srt = (String) workItem.getWorkflowData().getPayload();
//        try (ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class)) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("doc1.pdf", new Document(srt + "/DoR.pdf", resourceResolver));
//            map.put("faData.xml", new Document(srt + "/Data.xml", resourceResolver));
//
//            File file1 = converterService.assembleDocuments(map);
//            File file = converterService.convertToPDFA(file1);
//
//            InputStream inputStream2 = Files.newInputStream(file.toPath());
//            createNewAsset(createBinary(resourceResolver, inputStream2), resourceResolver);
//
//        } catch (Exception e) {
//            //do nothing
//        }
//    }
//
//    private Binary createBinary(ResourceResolver resourceResolver, InputStream inputStream) throws RepositoryException {
//        Session currentSession = resourceResolver.adaptTo(Session.class);
//        if (currentSession == null) {
//            return null;
//        }
//        ValueFactory valueFactory = currentSession.getValueFactory();
//        return valueFactory.createBinary(inputStream);
//    }
//
//    private void createNewAsset(Binary binary, ResourceResolver resourceResolver) throws LoginException {
//        AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
//        assert assetManager != null;
//        assetManager.createOrUpdateAsset("/content/dam/test.pdf", binary, "application/pdf", true);
//    }
//
//}
