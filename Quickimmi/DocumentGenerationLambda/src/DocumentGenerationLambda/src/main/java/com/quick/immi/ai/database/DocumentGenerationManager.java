package com.quick.immi.ai.database;

import com.quick.immi.ai.dao.ApplicationCaseMapper;
import com.quick.immi.ai.dao.DocumentMapper;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.dao.LawyerMapper;
import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.Document;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.Lawyer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class DocumentGenerationManager {
    private final SqlSessionFactory sqlSessionFactory;

    public DocumentGenerationManager(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public ApplicationCase getApplicationCase(Long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ApplicationCaseMapper mapper = sqlSession.getMapper(ApplicationCaseMapper.class);
            return mapper.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to getApplicationCase for case %s", id), e);
        }
    }

    public Lawyer getLawyer(Integer id){
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            LawyerMapper mapper = sqlSession.getMapper(LawyerMapper.class);
            return mapper.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to LawyerMapper for lawyer id %s", id), e);
        }
    }

    public FormGenerationTask getFormGenerationTask(Long id) {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FormGenerationTaskMapper mapper = sqlSession.getMapper(FormGenerationTaskMapper.class);
            return mapper.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to getFormGenerationTask for task %s", id), e);
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


    public void createDocument(Document document) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DocumentMapper mapper = sqlSession.getMapper(DocumentMapper.class);
            mapper.create(document);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("fail to create document by given document %s", document), e);
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
