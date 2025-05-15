package com.quick.immi.ai.database;

import com.quick.immi.ai.dao.DocumentMapper;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.entity.Document;
import com.quick.immi.ai.entity.FormGenerationTask;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DocumentGenerationManager {
    private final SqlSessionFactory sqlSessionFactory;

    public DocumentGenerationManager(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Document getDocument(Long documentId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DocumentMapper mapper = sqlSession.getMapper(DocumentMapper.class);

            Document document = mapper.get(documentId);
            if(document == null){
                throw new RuntimeException(String.format("fail to getDocument for given documentId: %s", documentId));
            }
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to getDocument by given document Id %s", documentId), e);
        }
    }

    public void updateFormGenerationTask(FormGenerationTask formGenerationTask) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FormGenerationTaskMapper mapper = sqlSession.getMapper(FormGenerationTaskMapper.class);
            mapper.update(formGenerationTask);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to updateFormGenerationTask by given task %s", formGenerationTask), e);
        }
    }

    public void updateDocument(Document document) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DocumentMapper mapper = sqlSession.getMapper(DocumentMapper.class);
            mapper.update(document);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to updateDocument %s", document), e);
        }
    }
}
