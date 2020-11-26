package com.company.service;

import com.company.dao.QuestDao;
import com.company.dao.StudentDao;
import com.company.dao.StudentQuestDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Quest;
import com.company.model.StudentQuests;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class StudentsQuestsService {

    private StudentDao studentDao;
    private StudentQuestDao studentQuestDao;
    private QuestDao questDao;
    private ObjectMapper mapper;

    public StudentsQuestsService() {
        this.studentDao = new StudentDao();
        this.questDao = new QuestDao();
        this.studentQuestDao = new StudentQuestDao();
        this.mapper = new ObjectMapper();
    }

    public String startQuest(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        StudentQuests studentQuest = createStartedQuest(formData, uuid);
        studentQuestDao.insertQuestToList(studentQuest);

        return mapper.writeValueAsString(studentQuest);
    }

    public StudentQuests createStartedQuest(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException {
        User student = studentDao.getBySessionId(uuid);
        Student studentWithAdditionalData = studentDao.getStudentByIdWithAdditionalData(student.getId());
        Quest quest = questDao.getById(Integer.parseInt(formData.get("quest_id")));

        StudentQuests studentQuests = new StudentQuests(
                                            LocalDate.now(),
                                            quest,
                                            studentWithAdditionalData);
        studentWithAdditionalData.setBalance(studentWithAdditionalData.getBalance() + quest.getReward());
        studentDao.updateAdditionalStudentData(studentWithAdditionalData);
        return studentQuests;
    }
}
